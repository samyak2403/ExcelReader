package com.arrowwould.excelreader.customviews.smartrefresh.header;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.arrowwould.excelreader.customviews.smartrefresh.internal.InternalAbstract;
import com.arrowwould.excelreader.customviews.smartrefresh.util.SmartUtil;


public class FalsifyHeader extends InternalAbstract implements RefreshHeader {

    protected RefreshKernel mRefreshKernel;

    //<editor-fold desc="FalsifyHeader">
    public FalsifyHeader(Context context) {
        this(context, null);
    }

    public FalsifyHeader(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        final View thisView = this;
        if (thisView.isInEditMode()) {//这段代码在运行时不会执行，只会在Studio编辑预览时运行，不用在意性能问题
            final int d = SmartUtil.dp2px(5);
            final Context context = thisView.getContext();

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(0xcccccccc);
            paint.setStrokeWidth(SmartUtil.dp2px(1));
            paint.setPathEffect(new DashPathEffect(new float[]{d, d, d, d}, 1));
            canvas.drawRect(d, d, thisView.getWidth() - d, thisView.getBottom() - d, paint);

            TextView textView = new TextView(context);
            textView.setText(context.getString(R.string.srl_component_falsify, getClass().getSimpleName(), SmartUtil.px2dp(thisView.getHeight())));
            textView.setTextColor(0xcccccccc);
            textView.setGravity(Gravity.CENTER);
            //noinspection UnnecessaryLocalVariable
            View view = textView;
            view.measure(makeMeasureSpec(thisView.getWidth(), EXACTLY), makeMeasureSpec(thisView.getHeight(), EXACTLY));
            view.layout(0, 0, thisView.getWidth(), thisView.getHeight());
            view.draw(canvas);
        }
    }
    //</editor-fold>

    //<editor-fold desc="RefreshHeader">
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mRefreshKernel = kernel;
    }

    @Override
    public void onReleased(@NonNull RefreshLayout layout, int height, int maxDragHeight) {
        if (mRefreshKernel != null) {
            /*
             * 2020-3-15 BUG修复
             * https://github.com/scwang90/SmartRefreshLayout/issues/1018
             * 强化了 closeHeaderOrFooter 的关闭逻辑，帮助 Header 取消刷新
             * FalsifyHeader 是不能触发刷新的
             */
            layout.closeHeaderOrFooter();
//            mRefreshKernel.setState(RefreshState.None);
//            //onReleased 的时候 调用 setState(RefreshState.None); 并不会立刻改变成 None
//            //而是先执行一个回弹动画，RefreshFinish 是介于 Refreshing 和 None 之间的状态
//            //RefreshFinish 用于在回弹动画结束时候能顺利改变为 None
//            mRefreshKernel.setState(RefreshState.RefreshFinish);
        }
    }
    //</editor-fold>

}
