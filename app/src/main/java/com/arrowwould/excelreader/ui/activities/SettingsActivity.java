package com.arrowwould.excelreader.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.applock.demoliboffice.BuildConfig;
import com.arrowwould.excelreader.BaseActivity;
import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.SharedPreferenceUtils;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.ads.NativeAdsAdmob;
import com.arrowwould.excelreader.db.DbHelper;
import com.arrowwould.excelreader.listener.OnConfirmListener;
import com.arrowwould.excelreader.ui.dialog.ConfirmDialog;

import java.util.Objects;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initToolBar();
        initViews();
        NativeAdsAdmob.loadNativeBanner1(this,null);
    }
    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        TextView tvLang = findViewById(R.id.pop_main_menu_lan_title);
        findViewById(R.id.btnShare).setOnClickListener(this);
        findViewById(R.id.btnRate).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnLang).setOnClickListener(this);
        findViewById(R.id.btnFeedback).setOnClickListener(this);
        findViewById(R.id.btnPrivacy).setOnClickListener(this);

        TextView tvVersion = findViewById(R.id.tvVersion);

        tvVersion.setText("Version " + BuildConfig.VERSION_NAME);

        tvLang.setText(SharedPreferenceUtils.getInstance(this).getString(GlobalConstant.LANGUAGE_NAME, "English"));

    }

    @Override
    public void onClick(View view) {
        int idView = view.getId();

        if (idView==R.id.btnShare){
            Utils.shareApp(this);

        }else if (idView==R.id.btnRate){
            Utils.showRateDialog(this);

        }else if (idView==R.id.btnClear){
            ConfirmDialog confirmDialog = new ConfirmDialog(this, GlobalConstant.DIALOG_CONFIRM_CLEAR_RECENT, new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    DbHelper.getInstance(SettingsActivity.this).clearRecentPDFs();
                    Toast.makeText(SettingsActivity.this, R.string.recent_cleared, Toast.LENGTH_SHORT).show();
                }
            });
            Window window3 = confirmDialog.getWindow();
            assert window3 != null;
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window3.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            confirmDialog.show();
        }else if (idView==R.id.btnLang){
            Intent intentLang = new Intent(this, LanguageActivity.class);
            startActivity(intentLang);
            finish();
        } else if (idView==R.id.btnFeedback){
            Utils.feedbackApp(this);
        }


    }
}