package com.arrowwould.excelreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.listener.MoreDialogListener;
import com.arrowwould.excelreader.listener.OnConfirmListener;
import com.arrowwould.excelreader.model.OfficeModel;

import java.io.File;

public class BottomDialogFragment extends Dialog implements View.OnClickListener {
    private final OfficeModel fileHolder;
    private final Context mContext;
    private final MoreDialogListener mListener;

    private final TextView tvName;
    private final TextView tvFileSize;
    private final TextView tvLastModified;


    public BottomDialogFragment(@NonNull Context context, OfficeModel fileHolderModel, MoreDialogListener bottomDialogListener) {
        super(context);
        this.fileHolder = fileHolderModel;
        this.mContext = context;
        this.mListener = bottomDialogListener;
        setContentView(R.layout.dialog_more_document);
        tvName = findViewById(R.id.tv_name);
        tvFileSize = findViewById(R.id.fileSizeTv);
        tvLastModified = findViewById(R.id.tvFileModified);

        initData();

        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnRename).setOnClickListener(this);
        findViewById(R.id.btnDetail).setOnClickListener(this);
        findViewById(R.id.btnShare).setOnClickListener(this);
        findViewById(R.id.btnShortcut).setOnClickListener(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        tvName.setText(fileHolder.getName());
        tvFileSize.setText(Formatter.formatShortFileSize(mContext, fileHolder.getLength()));
        tvLastModified.setText(Utils.formatDateToHumanReadable(fileHolder.getLastModified()));

    }

    @Override
    public void onClick(View v) {
        int idButton = v.getId();

        if (idButton == R.id.btnShare) {
            Utils.shareFile(mContext, new File(fileHolder.getFileUri()));
            dismiss();
        } else if (idButton == R.id.btnShortcut) {
            Utils.createShortCut(mContext, fileHolder);
            dismiss();
        } else if (idButton == R.id.btnDelete) {
            ConfirmDialog confirmDialog = new ConfirmDialog(mContext, GlobalConstant.DIALOG_CONFIRM_DELETE, new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    if (mListener != null) {
                        mListener.onDelete();
                    }
                }
            });
            Window window1 = confirmDialog.getWindow();
            assert window1 != null;
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window1.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            confirmDialog.show();
            dismiss();
        } else if (idButton == R.id.btnRename) {
            RenameDialog renameDialog = new RenameDialog(mContext, Utils.removeExtension(fileHolder.getName()), newName -> {
                if (mListener != null) {
                    mListener.onRename(newName);
                }
            });
            Window window2 = renameDialog.getWindow();
            assert window2 != null;
            renameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            renameDialog.show();
            dismiss();

        } else if (idButton == R.id.btnDetail) {
            InfoDocumentDialog infoDocumentDialog = new InfoDocumentDialog(mContext, fileHolder);
            Window window = infoDocumentDialog.getWindow();
            assert window != null;
            infoDocumentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            infoDocumentDialog.show();
            dismiss();
        }
    }
}
