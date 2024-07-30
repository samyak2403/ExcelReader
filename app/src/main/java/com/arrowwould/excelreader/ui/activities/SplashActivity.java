package com.arrowwould.excelreader.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.arrowwould.excelreader.BaseActivity;
import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.SharedPreferenceUtils;
import com.arrowwould.excelreader.ads.AdClosedListener;
import com.arrowwould.excelreader.ads.FullAds;

public class SplashActivity extends BaseActivity {

    int adsCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        adsCase = 0;
        FullAds.loadAds(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getIntent() != null && getIntent().getData() != null) {
                    adsCase = 3;
                    FullAds.showAds(SplashActivity.this, new ExecuteAdsAction());
                } else {
                    if (!SharedPreferenceUtils.getInstance(SplashActivity.this).getBoolean(GlobalConstant.LANGUAGE_SET, false)) {
                        adsCase = 1;
                        FullAds.showAds(SplashActivity.this, new ExecuteAdsAction());
                    } else {
                        adsCase = 2;
                        FullAds.showAds(SplashActivity.this, new ExecuteAdsAction());

                    }
                }
            }
        }, 3500);


    }

    public class ExecuteAdsAction implements AdClosedListener {

        @Override
        public void AdClosed() {
            switch (adsCase) {
                case 1:
                    startActivity(new Intent(SplashActivity.this, LanguageActivity.class));
                    finish();
                    break;
                case 2:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    break;
                case 3:
                    Uri uri = getIntent().getData();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra(GlobalConstant.KEY_DATA_FROM_OUTSIDE, uri.toString());
                    Log.i("Thang123", uri.toString());
                    startActivity(intent);
                    finish();
            }
        }

        @Override
        public void AdLoad() {

        }
    }

}