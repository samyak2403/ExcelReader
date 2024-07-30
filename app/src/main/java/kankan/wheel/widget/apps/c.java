package kankan.wheel.widget.apps;

import android.content.Context;

public class c<T> extends b {
    private T[] f;

    public c(Context context, T[] tArr) {
        super(context);
        this.f = tArr;
    }

    public void a(T[] tArr) {
        this.f = tArr;
    }

    public CharSequence c(int i) {
        if (i >= 0) {
            T[] tArr = this.f;
            if (i < tArr.length) {
                T t = tArr[i];
                return t instanceof CharSequence ? (CharSequence) t : t.toString();
            }
        }
        return null;
    }

    public int getItemsCount() {
        return this.f.length;
    }
}