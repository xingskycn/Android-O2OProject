package com.gez.cookery.jiaoshou.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gez.cookery.jiaoshou.activity.ActivityGlobal;
import com.gez.cookery.jiaoshou.net.NetAccess;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
    public static Calendar timeMsToCalendar(int timeMs) {
        Date date = new Date(timeMs * 1000L);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String timeMsToYYMMDD(int timeMs) {
        return String.format("%tF", timeMs * 1000L);
    }

    public static String timeMsToYYMMDDHHmmSS(int timeMs) {
        return String.format("%tF %<tT", timeMs * 1000L);
    }

    public static int getScreenHeight(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context paramContext) {
    	return paramContext.getResources().getDisplayMetrics().widthPixels;
    }
    
    //转换dip为px
    public static int convertDIP2PX(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    //转换px为dip
    public static int convertPX2DIP(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    public static String md5(String src) {
        try {
            int i;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            byte b[] = md.digest();
            StringBuffer buf = new StringBuffer();
            for (byte aB : b) {
                i = aB;
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String downLoad(String folder, String url, String suffix, String name) {
        InputStream is = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        HttpClient client = null;
        try {
            String filename = new StringBuffer().append(folder).append(null == name ? md5(url) : name).append(suffix).toString(); 
            File file = new File(filename);
            if (file.length() > 1000) return file.getAbsolutePath();
            
            client = NetAccess.getHttpClient();
            HttpResponse response = request(client, url, null);
            final long length = Long.parseLong(response.getFirstHeader("Content-Length").getValue());
            if (length == file.length()) return file.getAbsolutePath();
            is = response.getEntity().getContent();
            bis = new BufferedInputStream(is);
            os = new FileOutputStream(file);
            bos = new BufferedOutputStream(os);
            int d;
            while (-1 != (d = bis.read())) {
                bos.write(d);
            }
            bos.flush();

            return filename;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) is.close();
                if (null != bis) bis.close();
                if (null != os) os.close();
                if (null != bos) bos.close();
                if (null != client) client.getConnectionManager().shutdown();
                if (null != bos) bos.close();
            } catch (IOException e) {
                //
            }
        }
        return null;
    }

    /**
     * this method will be connect remote use cmwap or cmnet.
     * call this method you must close the client and the response.
     *
     * @param client HttpClient
     * @param url    url
     * @param data   List<BasicNameValuePair>
     * @return HttpResponse
     * @throws java.io.IOException IOException
     */
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

    public static boolean isEmpty(String val) {
        return null == val || "".equals(val.trim());
    }

    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile("\\w+@(\\w+\\.){1,3}\\w+");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean isNumber(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPhone(String str) {
    	try {
    		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
    		Matcher m = p.matcher(str);  
    		return m.matches();  
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
    
    //检查是否有网络
    public static boolean hasInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ActivityGlobal.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }
    
    public static int guidToInt(String guid) {
    	int value = 0;
    	
    	for (int i=0;i<guid.length();i++) {
    		String c = guid.substring(i, i + 1);
    		try {
    			value += Integer.parseInt(c, 16);
    		}
    		catch (Exception e) {
    			System.out.println(e.toString());
    		}
    	}
    	
    	return value;
    }
}
