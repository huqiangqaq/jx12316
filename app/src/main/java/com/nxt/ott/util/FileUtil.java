package com.nxt.ott.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

public class FileUtil {

	public static final int startDownloadMeg = 1;
	public static final int updateDownloadMeg = 2;
	public static final int endDownloadMeg = 3;
	public static boolean checkSDCard()
	{
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static String setMkdir(Context context)
	{
		String filePath;
		if(checkSDCard())
		{
			filePath = Environment.getExternalStorageDirectory()+File.separator+"myfile";
		}else{
			filePath = context.getCacheDir().getAbsolutePath()+File.separator+"myfile";
		}
		File file = new File(filePath);
		if(!file.exists())
		{
			boolean b = file.mkdirs();
		}else{
		}
		return filePath;
	}

	public static  String getFileName(String url)
	{
		String name= null;
		try {
			name = url.substring(url.lastIndexOf("/")+1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
    public static boolean checkInstall(Context context, String pakage_name) {
        boolean install = false;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(pakage_name, 1);
            if (info != null && info.activities.length > 0) {
                install = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return install;
    }
}
