package com.arrowwould.excelreader.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.customviews.ItemSelectSort;
import com.arrowwould.excelreader.listener.SortByListener;

public class SortByDialog extends Dialog implements View.OnClickListener {
    private ItemSelectSort btnSortDate;
    private ItemSelectSort btnSortName;
    private ItemSelectSort btnSortSize;
    private ItemSelectSort btnAscending;
    private ItemSelectSort btnDescending;
    private final SortByListener mListener;
    private int sortBy = 0;
    private boolean isAscending = true;

    public SortByDialog(@NonNull Activity context, SortByListener mListener) {
        super(context);
        setContentView(R.layout.dialog_sort_by);

        this.mListener = mListener;
        initView();
        setSortBy(sortBy);
        setBtnOder(isAscending);
    }

    private void initView( ) {
        TextView btnApply = findViewById(R.id.tv_bt_ok);
        btnSortDate = findViewById(R.id.dialogSelectSort_byDate);
        btnSortName = findViewById(R.id.dialogSelectSort_byName);
        btnSortSize = findViewById(R.id.dialogSelectSort_bySize);
        btnAscending = findViewById(R.id.dialogSelectSort_oderAsc);
        btnDescending = findViewById(R.id.dialogSelectSort_oderDesc);

        btnSortDate.setOnClickListener(this);
        btnSortName.setOnClickListener(this);
        btnSortSize.setOnClickListener(this);
        btnAscending.setOnClickListener(this);
        btnDescending.setOnClickListener(this);
        btnApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_bt_ok) {
            applySort(sortBy, isAscending);

        } else if (id == R.id.dialogSelectSort_byName) {
            sortBy = 0;
            setSortBy(0);
        } else if (id == R.id.dialogSelectSort_byDate) {
            sortBy = 1;
            setSortBy(1);
        } else if (id == R.id.dialogSelectSort_bySize) {
            sortBy = 2;
            setSortBy(2);
        } else if (id == R.id.dialogSelectSort_oderAsc) {
            isAscending = true;
            setBtnOder(true);
        } else if (id == R.id.dialogSelectSort_oderDesc) {
            isAscending = false;
            setBtnOder(false);
        }
    }

    private void setSortBy(int sortBy) {
        switch (sortBy) {
            case 0:
                btnSortName.tinImgSort(R.color.colorAccent);
                btnSortName.setTextColorSort(R.color.colorAccent);
                btnSortDate.tinImgSort(R.color.colorNormalText);
                btnSortDate.setTextColorSort(R.color.colorNormalText);
                btnSortSize.tinImgSort(R.color.colorNormalText);
                btnSortSize.setTextColorSort(R.color.colorNormalText);

                break;
            case 1:
                btnSortName.tinImgSort(R.color.colorNormalText);
                btnSortName.setTextColorSort(R.color.colorNormalText);
                btnSortDate.tinImgSort(R.color.colorAccent);
                btnSortDate.setTextColorSort(R.color.colorAccent);
                btnSortSize.tinImgSort(R.color.colorNormalText);
                btnSortSize.setTextColorSort(R.color.colorNormalText);

                break;
            case 2:
                btnSortName.tinImgSort(R.color.colorNormalText);
                btnSortName.setTextColorSort(R.color.colorNormalText);
                btnSortDate.tinImgSort(R.color.colorNormalText);
                btnSortDate.setTextColorSort(R.color.colorNormalText);
                btnSortSize.tinImgSort(R.color.colorAccent);
                btnSortSize.setTextColorSort(R.color.colorAccent);
                break;
        }
    }

    private void setBtnOder(boolean isAscending) {
        if (isAscending) {
            btnAscending.tinImgSort(R.color.colorAccent);
            btnAscending.setTextColorSort(R.color.colorAccent);
            btnDescending.tinImgSort(R.color.colorNormalText);
            btnDescending.setTextColorSort(R.color.colorNormalText);
        } else {
            btnAscending.tinImgSort(R.color.colorNormalText);
            btnAscending.setTextColorSort(R.color.colorNormalText);
            btnDescending.tinImgSort(R.color.colorAccent);
            btnDescending.setTextColorSort(R.color.colorAccent);
        }
    }

    private void applySort(int sortBy, boolean isAscending) {
        if (isAscending) {
            switch (sortBy) {
                case 0:
                    if (mListener != null) {
                        mListener.onSortAtoZ();
                    }
                    break;
                case 1:
                    if (mListener != null) {
                        mListener.onSortByDateDown();
                    }
                    break;
                case 2:
                    if (mListener != null) {
                        mListener.onSortFileSizeDown();
                    }
                    break;
            }
        } else {
            switch (sortBy) {
                case 0:
                    if (mListener != null) {
                        mListener.onSortZtoA();
                    }
                    break;
                case 1:
                    if (mListener != null) {
                        mListener.onSortByDateUp();
                    }
                    break;
                case 2:
                    if (mListener != null) {
                        mListener.onSortFileSizeUp();
                    }
                    break;
            }
        }
        dismiss();
    }
}
