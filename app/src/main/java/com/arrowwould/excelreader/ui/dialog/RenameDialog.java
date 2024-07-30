package com.arrowwould.excelreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.listener.RenameDialogListener;


public class RenameDialog extends Dialog {
    private final EditText edtRename;
    private final RenameDialogListener listener;



    public RenameDialog(@NonNull Context context, String oldName, RenameDialogListener mListener) {
        super(context);
        setContentView(R.layout.dialog_file_rename);
        this.listener = mListener;
        edtRename = findViewById(R.id.et_name);
        edtRename.setText(oldName);
        edtRename.setSelectAllOnFocus(true);
        findViewById(R.id.tv_bt_negative).setOnClickListener(view -> dismiss());
        findViewById(R.id.tv_bt_positive).setOnClickListener(view -> {
            if (TextUtils.equals(oldName, edtRename.getText().toString()))
                edtRename.setError(context.getString(R.string.dialog_rename_error));
            else if (Utils.isFileNameValid(edtRename.getText().toString())) {
                if (listener != null) {
                    listener.onRenameDialog(edtRename.getText().toString());
                    dismiss();
                }
            } else {
                edtRename.setError(context.getString(R.string.dialog_rename_error_2));
            }
        });
    }
}
