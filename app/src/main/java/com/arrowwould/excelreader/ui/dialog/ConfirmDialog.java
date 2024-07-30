package com.arrowwould.excelreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.listener.OnConfirmListener;


public class ConfirmDialog extends Dialog {

    private final OnConfirmListener mListener;
    private final int typeDialog;

    private AppCompatTextView tvTittle;
    private AppCompatTextView tvContent;
    private AppCompatTextView btnPositive;
    private AppCompatTextView btnNegative;


    public ConfirmDialog(@NonNull Context context, int mTypeDialog, OnConfirmListener listener) {
        super(context);
        setContentView(R.layout.dialog_confirm);
        this.mListener = listener;
        this.typeDialog = mTypeDialog;
        initViews();
        initData();
        btnPositive.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onConfirm();
                dismiss();
            }
            dismiss();
        });
        btnNegative.setOnClickListener(view -> dismiss());
    }

    private void initData() {
        if (typeDialog == GlobalConstant.DIALOG_CONFIRM_DELETE) {
            tvTittle.setText(R.string.str_action_delete);
            tvContent.setText(R.string.dialog_delete_body);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.str_action_delete);
        } else if (typeDialog == GlobalConstant.DIALOG_CONFIRM_REMOVE_RECENT) {
            tvTittle.setText(R.string.dialog_remove_title);
            tvContent.setText(R.string.dialog_remove_body);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.str_action_yes);
        } else if (typeDialog == GlobalConstant.DIALOG_CONFIRM_CLEAR_RECENT) {
            tvTittle.setText(R.string.dialog_clear_tittle);
            tvContent.setText(R.string.dialog_clear_body_1);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.str_action_clear_all);
        } else if (typeDialog == GlobalConstant.DIALOG_CONFIRM_CLEAR_FAV) {
            tvTittle.setText(R.string.dialog_clear_tittle);
            tvContent.setText(R.string.dialog_clear_body_2);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.str_action_clear_all);
        }else if (typeDialog==GlobalConstant.DIALOG_CONFIRM_REMOVE_FAV){
            tvTittle.setText(R.string.dialog_move_fav);
            tvContent.setText(R.string.dialog_remove_body);
            btnNegative.setText(R.string.str_action_cancel);
            btnPositive.setText(R.string.str_action_yes);
        }

    }

    private void initViews() {
        tvTittle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_hint);
        btnNegative = findViewById(R.id.tv_bt_negative);
        btnPositive = findViewById(R.id.tv_bt_positive);
    }
}
