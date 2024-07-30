package com.arrowwould.excelreader.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.ads.NativeAdsAdmob;


public class ExitDialog extends Dialog {

    public ExitDialog(@NonNull Activity context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_exit, null);
        setContentView(view);
        NativeAdsAdmob.loadNative(context, view);
        findViewById(R.id.tv_bt_positive).setOnClickListener(v -> context.finish());
        findViewById(R.id.tv_bt_negative).setOnClickListener(v -> dismiss());
    }
}
