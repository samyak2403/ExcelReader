package com.arrowwould.excelreader.customviews.smartrefresh.api;

import android.content.Context;

import androidx.annotation.NonNull;




public interface DefaultRefreshHeaderCreator {
    @NonNull
    RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout);
}
