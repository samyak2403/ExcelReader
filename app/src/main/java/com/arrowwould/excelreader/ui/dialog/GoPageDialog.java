package com.arrowwould.excelreader.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.listener.GoPageDialogListener;

public class GoPageDialog extends Dialog {
    private final GoPageDialogListener listener;
    TextView tvRangePage;
    EditText edtPage;
    TextView tvOk;
    TextView tvCancel;

    @SuppressLint("SetTextI18n")
    public GoPageDialog(@NonNull Context context, GoPageDialogListener mListener, int pageCount) {
        super(context);
        setContentView(R.layout.dialog_goto_page);
        this.listener = mListener;
        tvRangePage = findViewById(R.id.tvPageSize);
        edtPage = findViewById(R.id.edtContent);
        tvOk = findViewById(R.id.btnOK);
        tvCancel = findViewById(R.id.btnCancel);
        tvRangePage.setText("(1-" + pageCount+")");
        tvCancel.setOnClickListener(v -> dismiss());
        tvOk.setOnClickListener(v -> {
            String obj = edtPage.getText().toString();
            if (isValidPageNumber(obj, pageCount)) {
                dismiss();
                if (listener!=null){
                    listener.onPageNumber(Integer.parseInt(obj)-1);
                }

                return;
            }
            edtPage.setError(context.getString(R.string.invalid_page_number));
        });
    }

    public boolean isValidPageNumber(String str, int pageCount) {
        boolean z = false;
        if (TextUtils.isEmpty(str) || !TextUtils.isDigitsOnly(str)) {
            return false;
        }
        try {
            int intValue = Integer.parseInt(str);
            if (intValue > 0 && intValue <= pageCount) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
