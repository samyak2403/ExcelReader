package com.arrowwould.excelreader.customviews.smartrefresh.api;

import android.animation.ValueAnimator;

import androidx.annotation.NonNull;

import com.arrowwould.excelreader.customviews.smartrefresh.constant.RefreshState;



@SuppressWarnings({"unused", "UnusedReturnValue", "SameParameterValue"})
public interface RefreshKernel {

    @NonNull
    RefreshLayout getRefreshLayout();
    @NonNull
    RefreshContent getRefreshContent();

    RefreshKernel setState(@NonNull RefreshState state);


    RefreshKernel startTwoLevel(boolean open);


    RefreshKernel finishTwoLevel();


    RefreshKernel moveSpinner(int spinner, boolean isDragging);


    ValueAnimator animSpinner(int endSpinner);

    RefreshKernel requestDrawBackgroundFor(@NonNull RefreshInternal internal, int backgroundColor);

    RefreshKernel requestNeedTouchEventFor(@NonNull RefreshInternal internal, boolean request);

    RefreshKernel requestDefaultTranslationContentFor(@NonNull RefreshInternal internal, boolean translation);

    RefreshKernel requestRemeasureHeightFor(@NonNull RefreshInternal internal);

    RefreshKernel requestFloorParams(int duration, float openLayoutRate, float dragLayoutRate);
    //</editor-fold>
}
