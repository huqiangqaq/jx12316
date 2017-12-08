package com.nxt.ott.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloadThread extends Thread{
    private static final String TAG = "FileDownloadThread";
    private static final int BUFF_SIZE = 1024;
    private URL url;
    private File file;
    private boolean finished = false;
    private int downloadSize = 0;


    public FileDownloadThread(URL url, File file) {
        this.url = url;
        this.file = file;
        Log.e(TAG, toString());
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        RandomAccessFile fos = null;
        byte[] buf = new byte[BUFF_SIZE];
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setAllowUserInteraction(true);
            fos = new RandomAccessFile(file,"rwd");
            fos.setLength(file.length());
            bis = new BufferedInputStream(conn.getInputStream(), BUFF_SIZE);
            while(true)
            {
                int len = bis.read(buf,0,BUFF_SIZE);
                if(len==-1)
                {
                    break;
                }
                fos.write(buf,0,len);
                int count = downloadSize+len;
                if(count > file.length())
                {
                    Log.e(TAG, "当前"+this.getName()+"下载多了!!!");
                    downloadSize = (int)file.length();
                    this.finished = true;
                }else{
                    downloadSize+=len;
                }
            }
            this.finished = true;
            Log.e(TAG, "当前"+this.getName()+"下载完成");
            bis.close();
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "download error Exception "+e.getMessage());
            e.printStackTrace();
        }
        super.run();
    }


    public boolean isFinished() {
        return finished;
    }

    public int getDownloadSize() {
        return downloadSize;
    }
    @Override
    public String toString() {
        return "FileDownloadThread [url=" + url + ", file=" + file
                + ", finished="+ finished + ", downloadSize=" + downloadSize + "]";
    }
}
