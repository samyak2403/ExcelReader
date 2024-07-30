package com.arrowwould.excelreader.customviews.smartrefresh.api;

import android.content.Context;

import androidx.annotation.NonNull;



public interface DefaultRefreshInitializer {
    void initialize(@NonNull Context context, @NonNull RefreshLayout layout);
}
