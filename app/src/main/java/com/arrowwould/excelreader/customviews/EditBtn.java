package com.arrowwould.excelreader.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arrowwould.excelreader.R;


public class EditBtn extends ConstraintLayout {

    View viewBg;

    public EditBtn(@NonNull Context context) {
        super(context);
    }

    public EditBtn(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_edit_buton, this, true);
        if (attrs != null) {
            viewBg = findViewById(R.id.viewBg);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.EditBtn);
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.EditBtn_ImageEdt, R.drawable.ic_preview_highlight);
            if (resourceId > 0) {
                ((ImageView) findViewById(R.id.imgEdit)).setImageResource(resourceId);
            }

            obtainStyledAttributes.recycle();
        }
        setClickable(true);
        setFocusable(true);
    }

    public EditBtn(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public void setChoose(boolean isChoose){
        if (isChoose){
            viewBg.setVisibility(VISIBLE);
        }else {
            viewBg.setVisibility(INVISIBLE);
        }
    }

    public void setEnable(boolean enable) {
        float alpha;
        if (enable) {
            alpha = 1.0F;
        } else {
            alpha = 0.4F;
        }

        EditBtn.this.setAlpha(alpha);
        EditBtn.this.setClickable(enable);
        EditBtn.this.setFocusable(enable);
    }

}
