package com.arrowwould.excelreader.ui.dialog;

import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;


public class PermissionDialog extends Dialog {
    Activity mContext;

    public PermissionDialog(@NonNull Activity context) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.dialog_permission_required);

        findViewById(R.id.tv_ok).setOnClickListener(v -> {
            Utils.askPermission(mContext);
            dismiss();
        });
        findViewById(R.id.ivCancel).setOnClickListener(v -> dismiss());

    }
}
