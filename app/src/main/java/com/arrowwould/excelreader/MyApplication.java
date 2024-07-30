package com.arrowwould.excelreader;

import android.app.Application;
import android.content.Context;

import com.arrowwould.excelreader.ads.FullAds;
import com.arrowwould.excelreader.model.OfficeModel;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static ArrayList<OfficeModel> arrayListAll;
    public static MyApplication getInstance() {
        return mInstance;
    }
    private static synchronized void setInstance(MyApplication myApplication) {
        synchronized (MyApplication.class) {
            mInstance = myApplication;
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            setInstance(this);
        }
        List<String> testDeviceIds = Collections.singletonList("FB63629A39156B8C1B8CAB79F1D9841C");

        Utils.setTheme(this, SharedPreferenceUtils.getInstance(this).getBoolean(GlobalConstant.NIGHT_MODE_KEY, false));


        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
        MobileAds.initialize(this, initializationStatus -> {


        });
        FullAds.loadAds(this);

        arrayListAll = new ArrayList<>();

    }
    public void setAllPdfList(ArrayList<OfficeModel> arrayList) {
        arrayListAll = arrayList;
    }
    public ArrayList<OfficeModel> getArrayListAll() {
        return arrayListAll;
    }

}
