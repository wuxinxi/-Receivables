package com.tangren.application;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class CustomApplication extends Application {

    private List<AppCompatActivity> mList = new LinkedList<AppCompatActivity>();

    private static CustomApplication instance;

    public CustomApplication() {

    }

    public synchronized static CustomApplication getInstance() {
        if (null == instance)
            instance = new CustomApplication();
        return instance;
    }


    public void addActivity(AppCompatActivity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (AppCompatActivity appCompatActivity : mList) {
                if (appCompatActivity != null)
                    appCompatActivity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        ActiveAndroid.initialize(this);
        Logger.setDebug(true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
