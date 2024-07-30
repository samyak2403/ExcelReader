package com.arrowwould.excelreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.format.Formatter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.model.OfficeModel;


public class InfoDocumentDialog extends Dialog {
    TextView tvName;
    TextView tvSize;
    TextView tvFormat;
    TextView tvCreated;
    TextView tvSavePath;

    public InfoDocumentDialog(@NonNull Context context, OfficeModel fileHolderModel) {
        super(context);
        setContentView(R.layout.dialog_detail_file);
        tvName = findViewById(R.id.tvNamFile);
        tvSavePath = findViewById(R.id.tvSavedPath);
        tvSize = findViewById(R.id.tvSize);
        tvFormat = findViewById(R.id.tvFormat);
        tvCreated = findViewById(R.id.tvCreated);
        tvFormat.setText(fileHolderModel.getName().substring(fileHolderModel.getName().lastIndexOf(".")));
        tvName.setText(fileHolderModel.getName());
        tvSavePath.setText(fileHolderModel.getAbsolutePath());
        tvSize.setText(Formatter.formatShortFileSize(context, fileHolderModel.getLength()));
        tvCreated.setText(Utils.formatDateToHumanReadable(fileHolderModel.getLastModified()));
        findViewById(R.id.btnClose).setOnClickListener(v -> dismiss());
        tvSavePath.setOnClickListener(v -> Toast.makeText(context, fileHolderModel.getAbsolutePath(), Toast.LENGTH_SHORT).show());
    }
}
