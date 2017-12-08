package com.nxt.ott.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.StrictMode;

@TargetApi(11)
public class StrictModeWrapper {
	public static void init(Context context) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
	}
}
