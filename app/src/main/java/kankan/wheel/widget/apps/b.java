package kankan.wheel.widget.apps;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arrowwould.excelreader.R;


public abstract class b extends a {
    protected Context a;
    protected LayoutInflater b;
    protected int c;
    protected int d;
    protected int e;
    private int f;
    private int g;

    protected b(Context var1) {
        this(var1, -1);
    }

    protected b(Context var1, int var2) {
        this(var1, var2, 0);
    }

    protected b(Context var1, int var2, int var3) {
        this.f = -15724528;
        this.g = 24;
        this.a = var1;
        this.c = var2;
        this.d = var3;
        this.b = (LayoutInflater) var1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private View a(int var1, ViewGroup var2) {
        switch (var1) {
            case -1:
                return new TextView(this.a);
            case 0:
                return null;
            default:
                return this.b.inflate(var1, var2, false);
        }
    }

    private TextView a(View view, int i) {
        if (i == 0) {
            try {
                if (view instanceof TextView) {
                    return (TextView) view;
                }
            } catch (ClassCastException e2) {
                Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
                throw new IllegalStateException("AbstractWheelAdapter requires the resource ID to be a TextView", e2);
            }
        }
        if (i == 0) {
            return null;
        }
        view = view.findViewById(i);
        return (TextView) view;
    }

    public void a(int var1) {
        this.f = var1;
    }

    protected void a(TextView var1) {
        var1.setTextColor(this.f);
        var1.setGravity(Gravity.LEFT);
        var1.setTextSize(var1.getContext().getResources().getDimension(R.dimen.size_dialog));
        var1.setLines(1);
        var1.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
    }

    public void b(int var1) {
        this.g = var1;
    }

    protected abstract CharSequence c(int var1);

    public View getEmptyItem(View var1, ViewGroup var2) {
        View var3 = var1;
        if (var1 == null) {
            var3 = this.a(this.e, var2);
        }

        if (this.e == -1 && var3 instanceof TextView) {
            this.a((TextView) var3);
        }

        return var3;
    }

    public View getItem(int var1, View var2, ViewGroup var3) {
        if (var1 >= 0 && var1 < this.getItemsCount()) {
            View var4 = var2;
            if (var2 == null) {
                var4 = this.a(this.c, var3);
            }

            TextView var5 = this.a(var4, this.d);
            if (var5 != null) {
                CharSequence var7 = this.c(var1);
                Object var6 = var7;
                if (var7 == null) {
                    var6 = "";
                }

                var5.setText((CharSequence) var6);
                if (this.c == -1) {
                    this.a(var5);
                }
            }

            return var4;
        } else {
            return null;
        }
    }
}
