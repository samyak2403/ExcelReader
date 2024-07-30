package com.arrowwould.excelreader.customviews.smartrefresh.impl;

import android.annotation.SuppressLint;
import android.view.View;

import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.arrowwould.excelreader.customviews.smartrefresh.internal.InternalAbstract;


@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends InternalAbstract implements RefreshHeader/*, InvocationHandler*/ {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
