package com.nxt.zyl.data.volley;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;

public class Utils {

    static final String LOG_TAG = "PullToRefresh";
    
//    public static final int ANIMATION_FADE_IN_TIME = 200;
    public static final String SCHEME_VIDEO = "video";
    public static final String SCHEME_FILE = ContentResolver.SCHEME_FILE;
    public static final String SCHEME_CONTENT = ContentResolver.SCHEME_CONTENT;
    public static final String SCHEME_ANDROID_RESOURCE = ContentResolver.SCHEME_ANDROID_RESOURCE;

    private Utils() {
    }

    @TargetApi(11)
    public static void enableStrictMode() {
        if (Utils.hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();

            if (Utils.hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasGingerbreadMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Check how much usable space is available at a given path.
     * 
     * @param path
     *            The path to check
     * @return The space available in bytes
     */
    @SuppressWarnings("deprecation")
    @TargetApi(9)
    public static long getUsableSpace(File path) {
        if (Utils.hasGingerbread()) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }
    
    // 计算文件夹下所有文件的大小
    public static long getFileSize(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        if (flist == null) {
            return 0;
        }
        for (File aFlist : flist) {
            if (aFlist.isDirectory()) {
                size = size + getFileSize(aFlist);
            } else {
                size = size + aFlist.length();
            }
        }
        return size;
    }

    /*
     * private File getDiskCacheDir(Context context, String uniqueName) { final
     * String cachePath = context.getCacheDir().getPath(); return new
     * File(cachePath + File.separator + uniqueName); }
     */

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     * 
     * @param context
     *            The context to use
     * @param uniqueName
     *            A unique directory name to append to the cache dir
     * @return The cache dir
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir

        // TODO: getCacheDir() should be moved to a background thread as it
        // attempts to create the
        // directory if it does not exist (no disk access should happen on the
        // main/UI thread).
        final String cachePath;
        if (isExternalMounted() && null != getExternalCacheDir(context)) {
            cachePath = getExternalCacheDir(context).getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        Log.i("Cache dir", cachePath + File.separator + uniqueName);
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * Get the external app cache directory.
     * 
     * @param context
     *            The context to use
     * @return The external cache dir
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    private static File getExternalCacheDir(Context context) {
        // TODO: This needs to be moved to a background thread to ensure no disk
        // access on the
        // main/UI thread as unfortunately getExternalCacheDir() calls mkdirs()
        // for us (even
        // though the Volley library will later try and call mkdirs() as well
        // from a background
        // thread).
        return context.getExternalCacheDir();
    }

    @SuppressLint("NewApi")
    private static boolean isExternalMounted() {
        if (Utils.hasGingerbread()) {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
        }
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static String readFully(Reader reader) throws IOException {
        try {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
            return writer.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * Deletes the contents of {@code dir}. Throws an IOException if any file
     * could not be deleted, or if {@code dir} is not a readable directory.
     */
    public static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("not a readable directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }

    public static void closeQuietly(/* Auto */Closeable closeable) {
        if (closeable != null) {
            try {
            	// fix a crash when use huawei old device
                if (closeable instanceof Cursor) {
                    ((Cursor)closeable).close();
                } else {
                    closeable.close();
                }
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            } 
        }
    }
    
    public static boolean isSpecialType(String url){
        return url.startsWith(SCHEME_FILE)
        || url.startsWith(SCHEME_VIDEO)
        || url.startsWith(SCHEME_CONTENT)
        || url.startsWith(SCHEME_ANDROID_RESOURCE);
    }
    

    /**
     * Reads the contents of an InputStream into a byte[].
     * */
    public static byte[] streamToBytes(InputStream in, int length) throws IOException {
        byte[] bytes = new byte[length];
        int count;
        int pos = 0;
        while (pos < length && ((count = in.read(bytes, pos, length - pos)) != -1)) {
            pos += count;
        }
        if (pos != length) {
            throw new IOException("Expected " + length + " bytes, read " + pos + " bytes");
        }
        return bytes;
    }

    public static void warnDeprecation(String depreacted, String replacement) {
        Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }
}