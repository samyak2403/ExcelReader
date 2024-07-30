package com.arrowwould.excelreader.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.artifex.sonui.AppNUIActivity;
import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.SharedPreferenceUtils;
import com.arrowwould.excelreader.Utils;


public class ViewOfficeActivity extends AppNUIActivity {
    private static final String TAG = ViewOfficeActivity.class.getName();

    public static final int REQUEST_CAMERA_PERMISSIONS = 0x11111;
    private ImageView imvActivityEditorBookmark;
    private LottieAnimationView imvActivityEditorAds;

    private ImageView imvBack;

    String tempFileExtension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            imvBack = findViewById(R.id.img_back);
            if (imvBack != null)
                imvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            if (SharedPreferenceUtils.getInstance(this).getBoolean(GlobalConstant.RATE_APP, true)) {
                Utils.showRate(this);
            }
            Intent intent = getIntent();

            String filePath;
            if (intent.getData() != null && intent.getData().getPath() != null && !intent.getData().getPath().equals("")) {
                filePath = intent.getStringExtra(GlobalConstant.KEY_SELECTED_FILE_URI);
                if (filePath != null)
                    tempFileExtension = filePath.substring(filePath.lastIndexOf("."));
                setGradientToToolBar();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGradientToToolBar() {
        switch (tempFileExtension) {
            case ".doc":
            case ".docx":
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_word));
                break;
            case ".txt":
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_txt));
                break;
            case ".ppt":
            case ".pptx":
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_ppt));
                break;
            case ".xml":
            case ".xls":
            case ".xlsx":
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_excel));
                break;
            case ".pdf":
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_pdf));
                break;
            default: {
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_txt));

            }
        }
    }
}
