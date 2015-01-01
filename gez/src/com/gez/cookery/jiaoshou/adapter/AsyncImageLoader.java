package com.gez.cookery.jiaoshou.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import com.gez.cookery.jiaoshou.util.Config;
import com.gez.cookery.jiaoshou.util.Utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncImageLoader {
    private final static String SUFFIX = ".jpg";
    private final static long expired_time = 0x83d600;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(500);
    private static final HashSet<Item> items = new HashSet<Item>();
    private static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

    static class ImageThread implements Runnable {
        private Item item;

        ImageThread(Item item) {
            this.item = item;
        }


        public Item getItem() {
            return item;
        }

        @Override
        public void run() {
            if (item.imageUrl == null) return;
            synchronized (items) {
                items.add(item);
            }
            checkAndLoadImage(this);
        }
    }

    private static void checkAndLoadImage(ImageThread thread) {
        final Item item = thread.getItem();
        if (null != item) {
            Utils.downLoad(Config.IMAGE_CACHE_DIR, item.imageUrl, SUFFIX, null);
            if (null != item.callback) {
                final Message message = handler.obtainMessage(0, item);
                handler.sendMessage(message);
            }
        }
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Item item = (Item) msg.obj;
            if (null != item.callback) {
                synchronized (items) {
                    final String filePath = getFilePath(item.imageUrl);
                    item.callback.imageLoaded(filePath, item.o);
                    items.remove(item);
                }
            }
        }
    };

    public static void loadDrawable(final String imageUrl, final Object o, final ImageCallback callback) {
        if (null == imageUrl || "".equals(imageUrl)) return;
        final Item item = new Item(imageUrl, o, callback);
        threadPool.execute(new ImageThread(item));
    }

    public static String getFilePath(String imageUrl) {
        String key = Utils.md5(imageUrl);
        final String filePath = Config.IMAGE_CACHE_DIR + key;
        return filePath.indexOf(SUFFIX) > 0 ? filePath : filePath + SUFFIX;
    }

    static class Item {
        String imageUrl;
        Object o;
        ImageCallback callback;

        public Item(String imageUrl, Object o, ImageCallback callback) {
            this.imageUrl = imageUrl;
            this.o = o;
            this.callback = callback;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Item)) return false;

            Item item = (Item) o;

            if (imageUrl != null ? !imageUrl.equals(item.imageUrl) : item.imageUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return imageUrl != null ? imageUrl.hashCode() : 0;
        }
    }

    public static void init() {
        System.out.println("init static AsyncImageLoader.");
    }

    public static interface ImageCallback {
        public void imageLoaded(String filePath, Object o);
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}
