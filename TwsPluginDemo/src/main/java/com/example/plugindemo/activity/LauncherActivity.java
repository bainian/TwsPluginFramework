package com.example.plugindemo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

import com.example.plugindemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import qrom.component.log.QRomLog;

public class LauncherActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "rick_Print:LauncherActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_launcher);

        QRomLog.d("xxx1", "activity_welcome ID= " + R.layout.plugin_launcher);
        Log.e("xxx1", "activity_welcome ID= " + R.layout.plugin_launcher);
        Log.e("xxx2", getResources().getResourceEntryName(R.layout.plugin_launcher));
        QRomLog.d("xxx2", getResources().getResourceEntryName(R.layout.plugin_launcher));
        QRomLog.d(
                "xxx3",
                getResources().getString(R.string.app_name) + "  "
                        + getPackageManager().getApplicationLabel(getApplicationInfo()));
        QRomLog.d(
                "xxx3",
                getResources().getString(R.string.app_name) + "  "
                        + getPackageManager().getApplicationLabel(getApplicationInfo()));
        QRomLog.d("xxx4", getPackageName() + ", " + getText(R.string.app_name));
        QRomLog.d("xxx5", getResources().getString(android.R.string.httpErrorBadUrl));
        QRomLog.d("xxx6",
                getResources().getString(getResources().getIdentifier("app_name", "string", "com.example.plugindemo")));
        QRomLog.d("xxx7", getResources().getString(getResources().getIdentifier("app_name", "string", getPackageName())));

        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            setTitle("这是插件首屏");
        } else {
            actionBar.setTitle("这是插件首屏");
            actionBar.setSubtitle("这是副标题");
            actionBar.setLogo(R.drawable.ic_launcher);
            actionBar.setIcon(R.drawable.ic_launcher);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP
                    | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        findViewById(R.id.test_plugincore).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_plugincore:
                testPluginCore();
                break;
            default:
                break;
        }
    }

    private void testPluginCore() {
        Intent intent = new Intent();
        intent.setClassName(this, PluginCoreSamples.class.getName());
        startActivity(intent);
    }

    // 启动另外一个插件的方法
    private void onClickHellowrld(View v) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.pluginhelloworld");
        intent.putExtra("testParam", "testParam");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("cc");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        testDataApi();
    }

    private void testDataApi() {

        SharedPreferences sp = getSharedPreferences("aaa", 0);
        sp.edit().putString("xyz", "123").commit();
        File f = getDir("bbb", 0);
        QRomLog.d(TAG,
                f.getAbsoluteFile() + " exists:" + f.exists() + " canRead:" + f.canRead() + " canWrite:" + f.canWrite());

        f = getFilesDir();
        QRomLog.d(TAG,
                f.getAbsoluteFile() + " exists:" + f.exists() + " canRead:" + f.canRead() + " canWrite:" + f.canWrite());

        // if (Build.VERSION.SDK_INT >= 21) {
        // f = getNoBackupFilesDir();
        // QRomLog.d(TAG, f.getAbsoluteFile() + " exists:" + f.exists() +
        // " canRead:" + f.canRead() + " canWrite:" + f.canWrite());
        // }

        f = getCacheDir();
        QRomLog.d(TAG,
                f.getAbsoluteFile() + " exists:" + f.exists() + " canRead:" + f.canRead() + " canWrite:" + f.canWrite());

        // if (Build.VERSION.SDK_INT >= 21) {
        // f = getCodeCacheDir();
        // }
        QRomLog.d(TAG,
                f.getAbsoluteFile() + " exists:" + f.exists() + " canRead:" + f.canRead() + " canWrite:" + f.canWrite());

        SQLiteDatabase db = openOrCreateDatabase("ccc", 0, null);
        try {
            String sql = "create table IF NOT EXISTS  userDb (_id integer primary key autoincrement, column_one text not null);";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        f = getDatabasePath("ccc");
        QRomLog.d(TAG,
                f.getAbsoluteFile() + " exists:" + f.exists() + " canRead:" + f.canRead() + " canWrite:" + f.canWrite());

        String[] list = databaseList();

        try {
            FileOutputStream fo = openFileOutput("ddd", 0);
            fo.write(122);
            fo.flush();
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        QRomLog.d(TAG, getFileStreamPath("eee").getAbsolutePath());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        QRomLog.d(TAG, "onKeyDown keyCode=" + keyCode);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        QRomLog.d(TAG, "onKeyUp keyCode=" + keyCode);
        return super.onKeyUp(keyCode, event);
    }
}