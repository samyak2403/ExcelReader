package com.arrowwould.excelreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.customviews.HorizontalProgressBar;


public class ProgressLockingDialog extends Dialog {
    TextView tvPercent;
    HorizontalProgressBar progressBar;
    TextView tvTittle;


    public ProgressLockingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_progress_locking);
        tvPercent = findViewById(R.id.tvPercent);
        tvTittle = findViewById(R.id.tvTitle);
        progressBar = findViewById(R.id.progress_bar);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void setTvTittle(String tittle) {
        this.tvTittle.setText(tittle);
    }

    public void setTvPercent(String percent) {
        this.tvPercent.setText(percent);
    }

    public void setProgress(int progress) {
        this.progressBar.setProgress(progress);
    }
}
