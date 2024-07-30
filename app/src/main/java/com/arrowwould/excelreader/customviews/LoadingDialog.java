package com.arrowwould.excelreader.customviews;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;


public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
