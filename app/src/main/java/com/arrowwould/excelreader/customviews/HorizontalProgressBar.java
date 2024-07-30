package com.arrowwould.excelreader.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;


public class HorizontalProgressBar extends View {

    public int f32689a = 100;
    public int f32690b = 0;
    public boolean f32691c = false;
    public int f32692d;
    public int f32693e;
    public RectF f32694f = new RectF();
    public Paint f32695g;
    public Path f32696h = new Path();

    public HorizontalProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.hpb_colorBackground, R.attr.hpb_colorProgress, R.attr.hpb_progress, R.attr.hpb_useRoundRect}, 0, 0);
            try {
                this.f32692d = obtainStyledAttributes.getColor(0, 1683075321);
                this.f32693e = obtainStyledAttributes.getColor(1, -12942662);
                this.f32690b = obtainStyledAttributes.getInt(2, 0);
                this.f32691c = obtainStyledAttributes.getBoolean(3, false);
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            this.f32692d = 1683075321;
            this.f32693e = -12942662;
        }
        Paint paint = new Paint(1);
        this.f32695g = paint;
        paint.setStyle(Paint.Style.FILL);
    }

    public int getProgress() {
        return this.f32690b;
    }

    public void onDraw(@NonNull Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float f = (float) width;
        int i = (int) ((((float) this.f32690b) / ((float) this.f32689a)) * f);
        if (this.f32691c) {
            this.f32695g.setColor(this.f32692d);
            RectF rectF = this.f32694f;
            rectF.left = 0.0f;
            rectF.top = 0.0f;
            rectF.right = f;
            float f2 = (float) height;
            rectF.bottom = f2;
            float f3 = (float) (height / 2);
            canvas.drawRoundRect(rectF, f3, f3, this.f32695g);
            this.f32696h.addRect(0.0f, 0.0f, (float) i, f2, Path.Direction.CW);
            canvas.clipPath(this.f32696h);
            this.f32695g.setColor(this.f32693e);
            canvas.drawRoundRect(this.f32694f, f3, f3, this.f32695g);
            return;
        }
        this.f32695g.setColor(this.f32692d);
        float f4 = (float) height;
        canvas.drawRect(0.0f, 0.0f, f, f4, this.f32695g);
        this.f32695g.setColor(this.f32693e);
        canvas.drawRect(0.0f, 0.0f, (((float) this.f32690b) / ((float) this.f32689a)) * f, f4, this.f32695g);
    }

    public void setBackgroundColor(int i) {
        this.f32692d = i;
    }

    public void setForegroundColor(int i) {
        this.f32693e = i;
    }

    public void setMax(int i) {
        if (this.f32689a != i) {
            this.f32689a = Math.max(1, i);
            invalidate();
        }
    }

    public void setProgress(int i) {
        if (this.f32690b != i) {
            this.f32690b = Math.min(Math.max(0, i), this.f32689a);
            invalidate();
        }
    }

    public void setUseRoundRect(boolean z) {
        this.f32691c = z;
    }
}
