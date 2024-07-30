package kankan.wheel.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import kankan.wheel.widget.apps.e;
import kankan.wheel.widget.g.a;

public class WheelView extends View {
    private static final int[] c = new int[]{-15658735, 11184810, 11184810};
    boolean a = false;
    a b = new a() {
        public void a() {
            WheelView.this.k = true;
            WheelView.this.b();
        }

        public void a(int var1) {
            WheelView.this.b(var1);
            var1 = WheelView.this.getHeight();
            if (WheelView.this.l <= var1) {
                int var2 = WheelView.this.l;
                var1 = -var1;
                if (var2 >= var1) {
                    return;
                }
            }

            WheelView.this.l = var1;
            WheelView.this.j.a();
        }

        public void b() {
            if (WheelView.this.k) {
                WheelView.this.c();
                WheelView.this.k = false;
            }

            WheelView.this.l = 0;
            WheelView.this.invalidate();
        }

        public void c() {
            if (Math.abs(WheelView.this.l) > 1) {
                WheelView.this.j.a(WheelView.this.l, 0);
            }

        }
    };
    private int d = 0;
    private int e = 5;
    private int f = 0;


    private g j;
    private boolean k;
    private int l;
    private LinearLayout m;
    private int n;
    private e o;
    private final f p = new f(this);
    private final List<b> q = new LinkedList<>();
    private final List<d> r = new LinkedList<>();
    private final List<c> s = new LinkedList<>();
    private final DataSetObserver t = new DataSetObserver() {
        public void onChanged() {
            WheelView.this.a(false);
        }

        public void onInvalidated() {
            WheelView.this.a(true);
        }
    };

    public WheelView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    private int a(LinearLayout var1) {
        if (var1 != null && var1.getChildAt(0) != null) {
            this.f = var1.getChildAt(0).getMeasuredHeight();
        }

        int var2 = this.f;
        return Math.max(this.e * var2 - var2 * 10 / 50, this.getSuggestedMinimumHeight());
    }

    private void a(Context var1) {
        this.j = new g(this.getContext(), this.b);
    }

    private void a(Canvas var1) {
        var1.save();
        var1.translate(10.0F, (float) (-((this.d - this.n) * this.getItemHeight() + (this.getItemHeight() - this.getHeight()) / 2) + this.l));
        this.m.draw(var1);
        var1.restore();
    }

    private void b(int var1) {
        this.l += var1;
        int var2 = this.getItemHeight();
        int var3 = this.l / var2;
        int var4 = this.d - var3;
        int var5 = this.o.getItemsCount();
        var1 = this.l % var2;
        int var6 = var1;
        if (Math.abs(var1) <= var2 / 2) {
            var6 = 0;
        }

        int var7;
        if (this.a && var5 > 0) {
            if (var6 > 0) {
                var7 = var4 - 1;
                var1 = var3 + 1;
            } else {
                var1 = var3;
                var7 = var4;
                if (var6 < 0) {
                    var7 = var4 + 1;
                    var1 = var3 - 1;
                }
            }

            while (var7 < 0) {
                var7 += var5;
            }

            var7 %= var5;
        } else if (var4 < 0) {
            var1 = this.d;
            var7 = 0;
        } else if (var4 >= var5) {
            var1 = this.d - var5 + 1;
            var7 = var5 - 1;
        } else if (var4 > 0 && var6 > 0) {
            var7 = var4 - 1;
            var1 = var3 + 1;
        } else {
            var1 = var3;
            var7 = var4;
            if (var4 < var5 - 1) {
                var1 = var3;
                var7 = var4;
                if (var6 < 0) {
                    var7 = var4 + 1;
                    var1 = var3 - 1;
                }
            }
        }

        var3 = this.l;
        if (var7 != this.d) {
            this.a(var7, false);
        } else {
            this.invalidate();
        }

        var1 = var3 - var1 * var2;
        this.l = var1;
        if (var1 > this.getHeight()) {
            this.l = this.l % this.getHeight() + this.getHeight();
        }

    }

    private void b(Canvas var1) {
        double var3 = (double) (this.getItemHeight() / 2);
        Double.isNaN(var3);
        Double.isNaN(var3);
    }

    private boolean b(int var1, boolean var2) {
        View var3 = this.d(var1);
        if (var3 != null) {
            if (var2) {
                this.m.addView(var3, 0);
            } else {
                this.m.addView(var3);
            }

            return true;
        } else {
            return false;
        }
    }

    private int c(int var1, int var2) {
        this.e();
        this.m.setLayoutParams(new LayoutParams(-2, -2));
        this.m.measure(MeasureSpec.makeMeasureSpec(var1, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int var3 = this.m.getMeasuredWidth();
        if (var2 != 1073741824) {
            var3 = Math.max(var3 + 20, this.getSuggestedMinimumWidth());
            if (var2 != -2147483648 || var1 >= var3) {
                var1 = var3;
            }
        }

        this.m.measure(MeasureSpec.makeMeasureSpec(var1 - 20, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return var1;
    }

    private boolean c(int var1) {
        e var2 = this.o;
        boolean var3;
        var3 = var2 != null && var2.getItemsCount() > 0 && (this.a || (var1 >= 0 && var1 < this.o.getItemsCount()));

        return var3;
    }

    private View d(int var1) {
        e var2 = this.o;
        if (var2 != null && var2.getItemsCount() != 0) {
            int var3 = this.o.getItemsCount();
            int var4 = var1;
            if (!this.c(var1)) {
                return this.o.getEmptyItem(this.p.b(), this.m);
            } else {
                while (var4 < 0) {
                    var4 += var3;
                }

                return this.o.getItem(var4 % var3, this.p.a(), this.m);
            }
        } else {
            return null;
        }
    }

    private void d(int var1, int var2) {
        this.m.layout(0, 0, var1 - 20, var2);
    }

    private void e() {
    }

    private boolean f() {
        kankan.wheel.widget.a var1 = this.getItemsRange();
        LinearLayout var2 = this.m;
        int var3;
        boolean var4;
        if (var2 != null) {
            var3 = this.p.a(var2, this.n, var1);
            var4 = this.n != var3;

            this.n = var3;
        } else {
            this.h();
            var4 = true;
        }

        boolean var5 = var4;
        if (!var4) {
            var5 = this.n != var1.a() || this.m.getChildCount() != var1.c();
        }

        if (this.n > var1.a() && this.n <= var1.b()) {
            for (var3 = this.n - 1; var3 >= var1.a() && this.b(var3, true); this.n = var3--) {
            }
        } else {
            this.n = var1.a();
        }

        int var6 = this.n;

        int var7;
        for (var3 = this.m.getChildCount(); var3 < var1.c(); var6 = var7) {
            var7 = var6;
            if (!this.b(this.n + var3, false)) {
                var7 = var6;
                if (this.m.getChildCount() == 0) {
                    var7 = var6 + 1;
                }
            }

            ++var3;
        }

        this.n = var6;
        return var5;
    }

    private void g() {
        if (this.f()) {
            this.c(this.getWidth(), 1073741824);
            this.d(this.getWidth(), this.getHeight());
        }

    }

    private int getItemHeight() {
        int var1 = this.f;
        if (var1 != 0) {
            return var1;
        } else {
            LinearLayout var2 = this.m;
            if (var2 != null && var2.getChildAt(0) != null) {
                var1 = this.m.getChildAt(0).getHeight();
                this.f = var1;
                return var1;
            } else {
                return this.getHeight() / this.e;
            }
        }
    }

    private kankan.wheel.widget.a getItemsRange() {
        if (this.getItemHeight() == 0) {
            return null;
        } else {
            int var1 = this.d;

            int var2;
            for (var2 = 1; this.getItemHeight() * var2 < this.getHeight(); var2 += 2) {
                --var1;
            }

            int var3 = this.l;
            int var4 = var1;
            int var5 = var2;
            if (var3 != 0) {
                var5 = var1;
                if (var3 > 0) {
                    var5 = var1 - 1;
                }

                var1 = var3 / this.getItemHeight();
                var4 = var5 - var1;
                double var6 = (double) (var2 + 1);
                double var8 = Math.asin((double) var1);
                Double.isNaN(var6);
                Double.isNaN(var6);
                var5 = (int) (var6 + var8);
            }

            return new kankan.wheel.widget.a(var4, var5);
        }
    }

    private void h() {
        if (this.m == null) {
            LinearLayout var1 = new LinearLayout(this.getContext());
            this.m = var1;
            var1.setOrientation(LinearLayout.VERTICAL);
        }

    }

    private void i() {
        LinearLayout var1 = this.m;
        if (var1 != null) {
            this.p.a(var1, this.n, new kankan.wheel.widget.a());
        } else {
            this.h();
        }

        int var2 = this.e / 2;

        for (int var3 = this.d + var2; var3 >= this.d - var2; --var3) {
            if (this.b(var3, true)) {
                this.n = var3;
            }
        }

    }

    public void a() {
        this.r.clear();
    }

    protected void a(int var1) {

        for (kankan.wheel.widget.c value : this.s) {
            value.a(this, var1);
        }

    }

    protected void a(int var1, int var2) {

        for (kankan.wheel.widget.b value : this.q) {
            value.a(this, var1, var2);
        }

        this.playSoundEffect(SoundEffectConstants.CLICK);
    }

    public void a(int var1, boolean var2) {
        e var3 = this.o;
        if (var3 != null && var3.getItemsCount() != 0) {
            int var4;
            int var5;
            label45:
            {
                var4 = this.o.getItemsCount();
                if (var1 >= 0) {
                    var5 = var1;
                    if (var1 < var4) {
                        break label45;
                    }
                }

                if (!this.a) {
                    return;
                }

                while (var1 < 0) {
                    var1 += var4;
                }

                var5 = var1 % var4;
            }

            int var6 = this.d;
            if (var5 != var6) {
                if (var2) {
                    int var7 = var5 - var6;
                    var1 = var7;
                    if (this.a) {
                        var5 = var4 + Math.min(var5, var6) - Math.max(var5, this.d);
                        var1 = var7;
                        if (var5 < Math.abs(var7)) {
                            if (var7 < 0) {
                                var1 = var5;
                            } else {
                                var1 = -var5;
                            }
                        }
                    }

                    this.b(var1, 0);
                } else {
                    this.l = 0;
                    this.d = var5;
                    this.a(var6, var5);
                    this.invalidate();
                }
            }
        }

    }

    public void a(d var1) {
        this.r.add(var1);
    }

    public void a(boolean var1) {
        LinearLayout var2;
        if (var1) {
            this.p.c();
            var2 = this.m;
            if (var2 != null) {
                var2.removeAllViews();
            }

            this.l = 0;
        } else {
            var2 = this.m;
            if (var2 != null) {
                this.p.a(var2, this.n, new kankan.wheel.widget.a());
            }
        }

        this.invalidate();
    }

    protected void b() {

        for (kankan.wheel.widget.d value : this.r) {
            value.a(this);
        }

    }

    public void b(int var1, int var2) {
        int var3 = this.getItemHeight();
        int var4 = this.l;
        this.j.a(var1 * var3 - var4, var2);
    }

    protected void c() {

        for (kankan.wheel.widget.d value : this.r) {
            value.b(this);
        }

    }

    public boolean d() {
        return this.a;
    }

    public int getCurrentItem() {
        return this.d;
    }

    public e getViewAdapter() {
        return this.o;
    }

    public int getVisibleItems() {
        return this.e;
    }

    protected void onDraw(@NonNull Canvas var1) {
        super.onDraw(var1);
        e var2 = this.o;
        if (var2 != null && var2.getItemsCount() > 0) {
            this.g();
            this.a(var1);
            this.b(var1);
        }

    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        this.d(var4 - var2, var5 - var3);
    }

    protected void onMeasure(int var1, int var2) {
        int var3 = MeasureSpec.getMode(var1);
        int var4 = MeasureSpec.getMode(var2);
        int var5 = MeasureSpec.getSize(var1);
        var1 = MeasureSpec.getSize(var2);
        this.i();
        var3 = this.c(var5, var3);
        if (var4 != 1073741824) {
            var2 = this.a(this.m);
            if (var4 == -2147483648) {
                var1 = Math.min(var2, var1);
            } else {
                var1 = var2;
            }
        }

        this.setMeasuredDimension(var3, var1);
    }

    public boolean onTouchEvent(MotionEvent var1) {
        if (this.isEnabled() && this.getViewAdapter() != null) {
            switch (var1.getAction()) {
                case 1:
                    if (!this.k) {
                        int var2 = (int) var1.getY() - this.getHeight() / 2;
                        int var3 = this.getItemHeight() / 2;
                        if (var2 > 0) {
                            var3 += var2;
                        } else {
                            var3 = var2 - var3;
                        }

                        var3 /= this.getItemHeight();
                        if (var3 != 0 && this.c(this.d + var3)) {
                            this.a(this.d + var3);
                        }
                    }
                    break;
                case 2:
                    if (this.getParent() != null) {
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
            }

            return this.j.a(var1);
        } else {
            return true;
        }
    }

    public void setCurrentItem(int var1) {
        this.a(var1, false);
    }


    public void setInterpolator(Interpolator var1) {
        this.j.a(var1);
    }

    public void setViewAdapter(e var1) {
        e var2 = this.o;
        if (var2 != null) {
            var2.unregisterDataSetObserver(this.t);
        }

        this.o = var1;
        if (var1 != null) {
            var1.registerDataSetObserver(this.t);
        }

        this.a(true);
    }

    public void setVisibleItems(int var1) {
        this.e = var1;
    }
}
