package com.arrowwould.excelreader.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.arrowwould.excelreader.R;


public class ItemSelectSort extends LinearLayout {
    TextView tvNameSort;
    ImageView imgSort;

    Context mContext;

    public ItemSelectSort(Context context) {
        super(context);
        this.mContext = context;
    }

    public ItemSelectSort(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_select_sort, this, true);
        tvNameSort = findViewById(R.id.itemSelectSort_tvTitle);
        imgSort = findViewById(R.id.itemSelectSort_img);
        if (attrs != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ItemSelectSort);
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.ItemSelectSort_imgSort, R.drawable.ic_sort_date);
            if (resourceId > 0) {
                ((ImageView) findViewById(R.id.itemSelectSort_img)).setImageResource(resourceId);
            }
            String nameSort = obtainStyledAttributes.getString(R.styleable.ItemSelectSort_textNameSort);
            if (nameSort != null) {
                ((TextView) findViewById(R.id.itemSelectSort_tvTitle)).setText(nameSort);
            }
            obtainStyledAttributes.recycle();

        }
        setClickable(true);
        setFocusable(true);
    }

    public void setTextColorSort(int colorSort) {
        this.tvNameSort.setTextColor(ContextCompat.getColor(mContext, colorSort));
    }

    public void setImgSort(int imgSort) {
        this.imgSort.setImageResource(imgSort);
    }

    public void tinImgSort(int color) {
        this.imgSort.setColorFilter(ContextCompat.getColor(mContext, color));
    }
}
