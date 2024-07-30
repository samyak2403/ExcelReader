package com.arrowwould.excelreader.customviews.smartrefresh.api;



import static androidx.annotation.RestrictTo.Scope.LIBRARY;
import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static androidx.annotation.RestrictTo.Scope.SUBCLASSES;

import androidx.annotation.RestrictTo;



public interface RefreshFooter extends RefreshInternal {


    @RestrictTo({LIBRARY,LIBRARY_GROUP,SUBCLASSES})
    boolean setNoMoreData(boolean noMoreData);
}
