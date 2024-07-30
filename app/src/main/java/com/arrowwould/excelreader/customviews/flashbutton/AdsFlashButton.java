package com.arrowwould.excelreader.customviews.flashbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatButton;

public class AdsFlashButton extends AppCompatButton {

    public final C2434p f1267a;

    public AdsFlashButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        C2434p pVar = new C2434p();
        this.f1267a = pVar;
        pVar.f4946h = this;
        int i = 1;
        Paint paint = new Paint(1);
        pVar.f4939a = paint;
        paint.setStyle(Paint.Style.STROKE);
        pVar.f4939a.setColor(-1);
        pVar.f4939a.setStrokeWidth(100.0f);
        pVar.f4940b = new Path();
        int i2 = (int) (context.getResources().getDisplayMetrics().density * 8.0f);
        pVar.f4941c = i2 != 0 ? i2 : i;
    }

    @ColorInt
    public int getFlashColor() {
        return this.f1267a.f4939a.getColor();
    }

    public void onDetachedFromWindow() {
        C2434p pVar = this.f1267a;
        View view = pVar.f4946h;
        if (view != null) {
            view.removeCallbacks(pVar.f4947i);
        }
        super.onDetachedFromWindow();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        C2434p pVar = this.f1267a;
        if (pVar.f4946h.isEnabled() && pVar.f4945g && !pVar.f4943e) {
            int width = pVar.f4946h.getWidth();
            int height = pVar.f4946h.getHeight();
            if (pVar.f4944f) {
                pVar.f4944f = false;
                pVar.f4942d = -height;
                pVar.f4943e = true;
                pVar.f4946h.postDelayed(pVar.f4947i, 2000);
                return;
            }
            pVar.f4940b.reset();
            pVar.f4940b.moveTo((float) (pVar.f4942d - 50), (float) (height + 50));
            pVar.f4940b.lineTo((float) (pVar.f4942d + height + 50), -50.0f);
            pVar.f4940b.close();
            double d2 = (((double) ((height * 2) + width)) * 0.3d) - (double) height;
            int i = pVar.f4942d;
            pVar.f4939a.setAlpha((int) (((double) i < d2 ? ((((double) (i + height)) / (d2 + (double) height)) * 0.19999999999999998d) + 0.1d : 0.3d - ((((double) i - d2) / ((((double) width) - d2) + (double) height)) * 0.19999999999999998d)) * 255.0d));
            canvas.drawPath(pVar.f4940b, pVar.f4939a);
            int i2 = pVar.f4942d + pVar.f4941c;
            pVar.f4942d = i2;
            if (i2 >= width + height + 50) {
                pVar.f4942d = -height;
                pVar.f4943e = true;
                pVar.f4946h.postDelayed(pVar.f4947i, 2000);
                return;
            }
            pVar.f4946h.postInvalidate();
        }
    }

    public void setFlashColor(@ColorInt int i) {
        this.f1267a.f4939a.setColor(i);
    }

    public void setFlashEnabled(boolean z) {
        C2434p pVar = this.f1267a;
        pVar.f4945g = z;
        View view = pVar.f4946h;
        if (view != null) {
            view.invalidate();
        }
    }
}
