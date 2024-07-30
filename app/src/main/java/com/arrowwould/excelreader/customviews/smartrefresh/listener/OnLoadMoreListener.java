package com.arrowwould.excelreader.customviews.smartrefresh.listener;


import androidx.annotation.NonNull;

import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshLayout;



public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
