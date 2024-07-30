package com.arrowwould.excelreader.customviews.smartrefresh.listener;


import androidx.annotation.NonNull;

import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshLayout;


/**
 * 刷新监听器
 * Created by scwang on 2017/5/26.
 */
public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
