package com.arrowwould.excelreader.customviews.smartrefresh.api;


import static androidx.annotation.RestrictTo.Scope.LIBRARY;
import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static androidx.annotation.RestrictTo.Scope.SUBCLASSES;

import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.arrowwould.excelreader.customviews.smartrefresh.constant.SpinnerStyle;
import com.arrowwould.excelreader.customviews.smartrefresh.listener.OnStateChangedListener;


public interface RefreshInternal extends OnStateChangedListener {

    @NonNull
    View getView();


    @NonNull
    SpinnerStyle getSpinnerStyle();


    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    void setPrimaryColors(@ColorInt int... colors);

    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight);

    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight);


    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight);


    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight);


    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    int onFinish(@NonNull RefreshLayout refreshLayout, boolean success);


    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    void onHorizontalDrag(float percentX, int offsetX, int offsetMax);


    boolean isSupportHorizontalDrag();
}
