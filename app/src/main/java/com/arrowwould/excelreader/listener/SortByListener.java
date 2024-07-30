package com.arrowwould.excelreader.listener;

public interface SortByListener {
    void onSortByDateUp();

    void onSortByDateDown();

    void onSortAtoZ();

    void onSortZtoA();

    void onSortFileSizeUp();

    void onSortFileSizeDown();

}
