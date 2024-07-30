package com.artifex.sonui.editor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    private static BaseActivity mCurrentActivity;
    private static PermissionResultHandler mPermissionResultHandler;
    private static ResultHandler mResultHandler;
    private static ResumeHandler mResumeHandler;

    public BaseActivity() {
    }

    public static BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    private void setLocale(String var1) {
        this.setLocale(new Locale(var1));
    }

    private void setLocale(Locale var1) {
        Locale.setDefault(var1);
        Configuration var2 = this.getBaseContext().getResources().getConfiguration();
        var2.locale = var1;
        this.getBaseContext().getResources().updateConfiguration(var2, this.getBaseContext().getResources().getDisplayMetrics());
    }

    public static void setPermissionResultHandler(PermissionResultHandler var0) {
        mPermissionResultHandler = var0;
    }

    public static void setResultHandler(ResultHandler var0) {
        mResultHandler = var0;
    }

    public static void setResumeHandler(ResumeHandler var0) {
        mResumeHandler = var0;
    }

    public boolean isSlideShow() {
        return false;
    }

    protected void onActivityResult(int var1, int var2, Intent var3) {
        ResultHandler var4 = mResultHandler;
        if (var4 == null || !var4.handle(var1, var2, var3)) {
            super.onActivityResult(var1, var2, var3);
        }
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
    }

    protected void onPause() {
        mCurrentActivity = null;
        super.onPause();
    }

    public void onRequestPermissionsResult(int var1, @NonNull String[] var2, @NonNull int[] var3) {
        PermissionResultHandler var4 = mPermissionResultHandler;
        if (var4 == null || !var4.handle(var1, var2, var3)) {
            super.onRequestPermissionsResult(var1, var2, var3);
        }
    }

    protected void onResume() {
        mCurrentActivity = this;
        super.onResume();
        ResumeHandler var1 = mResumeHandler;
        if (var1 != null) {
            var1.handle();
        }

    }

    public interface PermissionResultHandler {
        boolean handle(int var1, String[] var2, int[] var3);
    }

    public interface ResultHandler {
        boolean handle(int var1, int var2, Intent var3);
    }

    public interface ResumeHandler {
        void handle();
    }
}
