package com.arrowwould.excelreader.db;

import android.provider.BaseColumns;

public class DbContract {

    public static class RecentEntry implements BaseColumns {
        public static final String TABLE_NAME = "history_pdfs";
    }

    public static class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "stared_pdfs";
    }

    public static class LastOpenedPageEntry implements BaseColumns {
        public static final String TABLE_NAME = "last_opened_page";
    }
}
