package com.example.plugindemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.rick.tws.framework.HostProxy;

public class PluginTestApplication extends Application {

    private static final String TAG = "PluginTestApplication";
    private String packageName;
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Host pkg:" + HostProxy.getApplication().getPackageName());

        if (isApplicationProcess()) {
            Log.i(TAG, "api欺骗成功，让插件以为自己在主进程运行");
        }
        sContext = this;
    }

    private boolean isApplicationProcess() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == android.os.Process.myPid()) {
                if (appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        packageName = getPackageName();
    }
}
