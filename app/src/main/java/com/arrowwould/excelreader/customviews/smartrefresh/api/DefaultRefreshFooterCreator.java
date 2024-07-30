package com.arrowwould.excelreader.customviews.smartrefresh.api;

import android.content.Context;

import androidx.annotation.NonNull;



public interface DefaultRefreshFooterCreator {
    @NonNull
    RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout);
}
