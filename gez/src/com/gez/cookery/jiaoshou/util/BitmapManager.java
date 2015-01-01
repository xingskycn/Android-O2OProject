package com.gez.cookery.jiaoshou.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.gez.cookery.jiaoshou.net.NetAccess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.widget.ImageView;

/**
 * 异步线程加载图片工具类
 * 使用说明：
 * BitmapManager bmpManager;
 * bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.loading));
 * bmpManager.loadBitmap(imageURL, imageView);
 */
public class BitmapManager {  
	  
    private static HashMap<String, SoftReference<Bitmap>> cache;  
    private static ExecutorService pool;  
    private static Map<ImageView, String> imageViews;  
    private Bitmap defaultBmp;  
    private final static int RETRY_TIME = 3;

    
    private int[] bitmapArray = null;
    
    static {  
        cache = new HashMap<String, SoftReference<Bitmap>>();  
        pool = Executors.newFixedThreadPool(5);  //固定线程池
        imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    }  
    
    public BitmapManager(){}
    
    public BitmapManager(Bitmap def) {
    	this.defaultBmp = def;
    }
    
    public BitmapManager(int[] bitmapArray) {
    	this.bitmapArray = bitmapArray;
    }
    
    /**
     * 设置默认图片
     * @param bmp
     */
    public void setDefaultBmp(Bitmap bmp) {  
    	defaultBmp = bmp;  
    }   
  
    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, ImageView imageView) {  
    	loadBitmap(-1, url, imageView, this.defaultBmp, 0, 0);
    }
    
    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadBitmap(int id, String url, ImageView imageView) {  
    	loadBitmap(id, url, imageView, this.defaultBmp, 0, 0);
    }
	
    /**
     * 加载图片-可设置加载失败后显示的默认图片
     * @param url
     * @param imageView
     * @param defaultBmp
     */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp) {  
    	loadBitmap(-1,url, imageView, defaultBmp, 0, 0);
    }
    
    /**
     * 加载图片-可指定显示图片的高宽
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void loadBitmap(String url, ImageView imageView, int width, int height) {  
    	loadBitmap(-1,url, imageView, this.defaultBmp, width, height);
    }
    
    /**
     * 加载图片-可指定显示图片的高宽
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void loadBitmap(int id, String url, ImageView imageView, int width, int height) {  
    	loadBitmap(id, url, imageView, this.defaultBmp, width, height);
    }
    
    
    
    /**
     * 加载图片-可指定显示图片的高宽
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void loadBitmap(int id, String url, ImageView imageView, Bitmap defaultBmp, int width, int height) {  
    	if (StringUtils.isEmpty(url)) {
    		
    		//获取默认图片
    		if (defaultBmp != null) {
    			
    			return;
    		}
    		else if (bitmapArray != null) {
    			if (id < 0) {
    				Random r = new Random();
    				imageView.setImageResource(bitmapArray[r.nextInt(bitmapArray.length)]);
    			}
    			else {
    				imageView.setImageResource(bitmapArray[id % bitmapArray.length]);
    			}
    	    	return;
    		}
    	}
    	
        imageViews.put(imageView, url);  
        Bitmap bitmap = getBitmapFromCache(url);  
   
        if (bitmap != null) {  
        	if(width > 0 && height > 0) {
				//指定显示图片的高宽
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			}
        	  	
			//显示缓存图片
            imageView.setImageBitmap(bitmap);  
        } else {  
        	//加载SD卡中的图片缓存
        	String filename = FileUtils.getFileName(url);
        	String filepath = imageView.getContext().getFilesDir() + File.separator + filename;
    		File file = new File(filepath);
    		if(file.exists()){
    			
    			
    			
				//显示SD卡中的图片缓存,有图片读不出来
    			Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(), filename);
    			
    			
    			if(width > 0 && height > 0) {
    				//指定显示图片的高宽
    				bmp = Bitmap.createScaledBitmap(bmp, width, height, true);
    			}
		
        		imageView.setImageBitmap(bmp);
        		
				//放入缓存,每次打开程序都要把内存的图片put到缓存当中去
				cache.put(url, new SoftReference<Bitmap>(bmp));
        	}else{
				//线程加载网络图片
        		if (defaultBmp != null) {
        			imageView.setImageBitmap(defaultBmp);        			
        		}
        		else if (bitmapArray != null) {
        			if (id < 0) {
        				Random r = new Random();
        				imageView.setImageResource(bitmapArray[r.nextInt(bitmapArray.length)]);
        			}
        			else {
        				imageView.setImageResource(bitmapArray[id % bitmapArray.length]);
        			}
        		}
        		queueJob(url, imageView, width, height);
        	}
        }  
    }  
  
    
    
    
    
    /**
     * 从缓存中获取图片
     * @param url
     */
    public Bitmap getBitmapFromCache(String url) {  
    	Bitmap bitmap = null;
        if (cache.containsKey(url)) {  
            bitmap = cache.get(url).get();  
        }  
        return bitmap;  
    }  
    
    /**
     * 从网络中加载图片
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void queueJob(final String url, final ImageView imageView, final int width, final int height) {  
        /* Create handler in UI thread. */  
        final Handler handler = new Handler() {  
            public void handleMessage(Message msg) {  
                String tag = imageViews.get(imageView);  
                if (tag != null && tag.equals(url)) {  
                    if (msg.obj != null) {  
                        imageView.setImageBitmap((Bitmap) msg.obj);  
                        try {
                        	//向SD卡中写入图片缓存
							ImageUtils.saveImage(imageView.getContext(), FileUtils.getFileName(url), (Bitmap) msg.obj);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    }
                }  
            }  
        };  
  
        pool.execute(new Runnable() {   
            public void run() {  
                Message message = Message.obtain();  
                message.obj = downloadBitmap(url, width, height);  
                handler.sendMessage(message);  
            }  
        });  
    } 
  
    /**
     * 下载图片-可指定显示图片的高宽
     * @param url
     * @param width
     * @param height
     */
    private Bitmap downloadBitmap(String url, int width, int height) {   
        Bitmap bitmap = null;
        try {
			//http加载图片
        	
        	//获得缩小比例
        	int scale = getScare(url, width, height);
        	
			bitmap = getNetBitmap(url, scale);
			if (bitmap != null) {
				if(width > 0 && height > 0) {
					//指定显示图片的高宽
					bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
				} 
				//放入缓存
				cache.put(url, new SoftReference<Bitmap>(bitmap));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return bitmap;  
    }
    
    /**
	 * 获取网络图片
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(String url, int scale) {
		HttpClient client = null;
		InputStream is = null;
		Bitmap bitmap = null;
		int time = 0;
		do{
			try 
			{
				client = NetAccess.getHttpClient();
		        HttpResponse response = request(client, url, null);
	            is = response.getEntity().getContent();
	            //**********************************************
	            Options opts = new Options();
		        
	            //根据计算出的比例进行缩放
	            opts.inSampleSize = scale;	//现在这里取的动态值，根据屏幕大小适配
 
	            bitmap = BitmapFactory.decodeStream(is, null, opts);
	            
//		        bitmap = BitmapFactory.decodeStream(is);
	            
	            //**********************************************
		        is.close();
		        break;
			} catch (Exception e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
			} finally {
				// 释放连接
                if (null != client) client.getConnectionManager().shutdown();
                client = null;
			}
		}while(time < RETRY_TIME);
		return bitmap;
	}
	
	private int getScare(String url, int width, int height) {
		HttpClient client = null;
		InputStream is = null;
		int time = 0;
		do{
			try 
			{
				client = NetAccess.getHttpClient();
		        HttpResponse response = request(client, url, null);
	            is = response.getEntity().getContent();
	            Options opts = new Options();
	            opts.inJustDecodeBounds = true;
	            BitmapFactory.decodeStream(is, null, opts);

	            int imageWidth = opts.outWidth;
	            int imageHeight = opts.outHeight;

	            int widthscale = imageWidth / width;
	            int heightscale = imageHeight / height;
	            int scale = widthscale > heightscale ? widthscale : heightscale;
	            is.close();
	            return scale;
			} catch (Exception e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
			} finally {
				// 释放连接
                if (null != client) client.getConnectionManager().shutdown();
                client = null;
			}
		}while(time < RETRY_TIME);
		return 1;
	}
	
	public static HttpResponse request(HttpClient client, String url, List<BasicNameValuePair> data) throws IOException {
        HttpUriRequest req;
        if (null != data && data.size() > 0) {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
            req = post;
        } else {
            req = new HttpGet(url);
        }
        return client.execute(req);
    }
}