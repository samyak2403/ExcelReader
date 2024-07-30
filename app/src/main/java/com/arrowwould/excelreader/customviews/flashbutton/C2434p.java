package com.arrowwould.excelreader.customviews.flashbutton;


import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class C2434p {

    /* renamed from: a */
    public Paint f4939a;

    /* renamed from: b */
    public Path f4940b;

    /* renamed from: c */
    public int f4941c;

    /* renamed from: d */
    public int f4942d = 0;

    /* renamed from: e */
    public boolean f4943e = false;

    /* renamed from: f */
    public boolean f4944f = true;

    /* renamed from: g */
    public boolean f4945g = true;

    /* renamed from: h */
    public View f4946h;

    /* renamed from: i */
    public final Runnable f4947i = new C2435a();

    /* renamed from: d.a.a.p$a */
    /* compiled from: AdsFlashDelegate */
    public class C2435a implements Runnable {
        public C2435a() {
        }

        public void run() {
            C2434p pVar = C2434p.this;
            pVar.f4943e = false;
            View view = pVar.f4946h;
            if (view != null) {
                view.postInvalidate();
            }
        }
    }
}
