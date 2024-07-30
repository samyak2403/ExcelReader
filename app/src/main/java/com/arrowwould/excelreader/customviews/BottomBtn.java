package com.arrowwould.excelreader.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.arrowwould.excelreader.R;


public class BottomBtn extends LinearLayout {
    public BottomBtn(Context context) {
        super(context);
    }

    public BottomBtn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_button, this, true);
        if (attrs != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.BottomBtn);
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.BottomBtn_Image, R.drawable.ic_flip_black);
            if (resourceId > 0) {
                ((ImageView) findViewById(R.id.imgButton)).setImageResource(resourceId);
            }
            String nameFileType = obtainStyledAttributes.getString(R.styleable.BottomBtn_Name);
            if (nameFileType != null) {
                ((TextView) findViewById(R.id.tvButton)).setText(nameFileType);
            }
            obtainStyledAttributes.recycle();
        }
        setClickable(true);
        setFocusable(true);
    }
    public void setEnable(boolean enable) {
        float alpha;
        if (enable) {
            alpha = 1.0F;
        } else {
            alpha = 0.4F;
        }

        BottomBtn.this.setAlpha(alpha);
        BottomBtn.this.setClickable(enable);
        BottomBtn.this.setFocusable(enable);
    }
}
