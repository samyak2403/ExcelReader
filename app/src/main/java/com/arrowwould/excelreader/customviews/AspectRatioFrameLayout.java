package com.arrowwould.excelreader.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arrowwould.excelreader.R;


public class AspectRatioFrameLayout extends FrameLayout {
    public int f41101a = 0;

    public int f41102b = 0;


    public AspectRatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, new int[]{R.attr.arfl_ratioHeight, R.attr.arfl_ratioWidth, R.attr.resize_mode});
            this.f41101a = obtainStyledAttributes.getInteger(1, 0);
            this.f41102b = obtainStyledAttributes.getInteger(0, 0);
            obtainStyledAttributes.recycle();
        }
    }

    public void onMeasure(int i, int i2) {
        int[] c = m35003c(i, i2, this.f41101a, this.f41102b);
        super.onMeasure(c[0], c[1]);
    }

    public static int[] m35003c(int i, int i2, int i3, int i4) {
        if (i3 <= 0 || i4 <= 0) {
            return new int[]{i, i2};
        }
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (size > 0 || size2 > 0) {
            int i5 = (size * i4) / i3;
            if (size <= 0 || (size2 > 0 && i5 > size2)) {
                size = (i3 * size2) / i4;
            } else {
                size2 = i5;
            }
            return new int[]{MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(size2, MeasureSpec.EXACTLY)};
        }
        return new int[]{i, i2};
    }
}
