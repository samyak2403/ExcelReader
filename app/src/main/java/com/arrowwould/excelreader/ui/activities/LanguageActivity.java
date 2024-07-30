package com.arrowwould.excelreader.ui.activities;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arrowwould.excelreader.BaseActivity;
import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.SharedPreferenceUtils;
import com.arrowwould.excelreader.adapter.LanguageAdapter;
import com.arrowwould.excelreader.ads.NativeAdsAdmob;
import com.arrowwould.excelreader.model.Language;

import java.util.ArrayList;

public class LanguageActivity extends BaseActivity {

    RecyclerView recyclerView;
    LanguageAdapter adapter;
    int langChoice = 0;
    ArrayList<Language> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_excel));
        NativeAdsAdmob.loadNativeAdsBannerBig(this, null);

        recyclerView = findViewById(R.id.rcv_language_list);
        arrayList = GlobalConstant.createArrayLanguage();

        adapter = new LanguageAdapter(this, lang -> langChoice = lang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        findViewById(R.id.iv_select_over).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtils.getInstance(LanguageActivity.this).setBoolean(GlobalConstant.LANGUAGE_SET, true);
                SharedPreferenceUtils.getInstance(LanguageActivity.this).setString(GlobalConstant.LANGUAGE_NAME, arrayList.get(langChoice).getNameLanguage());
                SharedPreferenceUtils.getInstance(LanguageActivity.this).setString(GlobalConstant.LANGUAGE_KEY, arrayList.get(langChoice).getKeyLanguage());
                SharedPreferenceUtils.getInstance(LanguageActivity.this).setInt(GlobalConstant.LANGUAGE_KEY_NUMBER, langChoice);
                Intent refresh = new Intent(LanguageActivity.this, MainActivity.class);
                refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(refresh);
                finish();
            }
        });

//        findViewById(R.id.iv_select_over).setOnClickListener(view -> FullAds.showAds(LanguageActivity.this, new AdClosedListener() {
//            @Override
//            public void AdClosed() {
//                SharedPreferenceUtils.getInstance(LanguageActivity.this).setBoolean(GlobalConstant.LANGUAGE_SET, true);
//                SharedPreferenceUtils.getInstance(LanguageActivity.this).setString(GlobalConstant.LANGUAGE_NAME, arrayList.get(langChoice).getNameLanguage());
//                SharedPreferenceUtils.getInstance(LanguageActivity.this).setString(GlobalConstant.LANGUAGE_KEY, arrayList.get(langChoice).getKeyLanguage());
//                SharedPreferenceUtils.getInstance(LanguageActivity.this).setInt(GlobalConstant.LANGUAGE_KEY_NUMBER, langChoice);
//                Intent refresh = new Intent(LanguageActivity.this, MainActivity.class);
//                refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(refresh);
//                finish();
//            }
//
//            @Override
//            public void AdLoad() {
//
//            }
//        }));
    }

}