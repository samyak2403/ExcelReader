package com.arrowwould.excelreader;

import android.provider.MediaStore;


import com.arrowwould.excelreader.model.ColorModel;
import com.arrowwould.excelreader.model.Language;

import java.util.ArrayList;

public class GlobalConstant {
    public static String RECENT_OR_FAVORITE = "recent_or_fav";

    public static String ALL = "all";
    public static String RECENT = "recent";
    public static String FAV = "fav";
    public static int DIALOG_CONFIRM_DELETE = 0;
    public static int DIALOG_CONFIRM_REMOVE_RECENT = 1;
    public static int DIALOG_CONFIRM_CLEAR_RECENT = 5;

    public static int DIALOG_CONFIRM_CLEAR_FAV = 6;
    public static int DIALOG_CONFIRM_REMOVE_FAV = 7;
    public static final String ACTION_SORT_BY_DATE_UP = "action_sort_date_up";
    public static final String ACTION_SORT_BY_DATE_DOWN = "action_sort_date_down";
    public static final String ACTION_SORT_BY_SIZE_UP = "action_sort_size_up";
    public static final String ACTION_SORT_BY_SIZE_DOWN = "action_sort_size_down";
    public static final String ACTION_SORT_BY_A_Z = "action_sort_a_z";
    public static final String ACTION_SORT_BY_Z_A = "action_sort_z_a";
    public static final String ACTION_UPDATE_FAVORITE_FRAGMENT = "update_favorite_fragment";
    public static final String ACTION_UPDATE_ALL_FRAGMENT = "update_all_fragment";
    public static final String ACTION_UPDATE_3_FRAGMENT = "update_3_fragment";
    public static final String ACTION_UPDATE_RECENT_FRAGMENT = "update_recent_fragment";
    public static final String ACTION_UPDATE_ALL_FAVORITE = "update_all_favorite";

    public static final String KEY_DATA_FROM_OUTSIDE = "data123";
    public static final String ADS_COUNT_OPEN_FILE = "count_ads";
    public static final String ADS_COUNT_OPEN_TYPE = "count_ads_type";
    public static final String ADS_COUNT_BACK = "count_ads_back";
    public static final String RATE_APP = "RATE_APP";
    public static String NIGHT_MODE_KEY = "night_mode";

    public static final int ALL_FILE_CODE = 0;
    public static final int EXCEL_FILE_CODE = 1;
    public static final int PDF_FILE_CODE =2;
    public static final int DOC_FILE_CODE = 3;
    public static final int PPT_FILE_CODE = 4;
    public static final int TXT_FILE_CODE = 5;
    public static final int HTML_FILE_CODE = 6;
    public static final int RTF_FILE_CODE = 7;
    public static final int FAV_FILE_CODE = 8;

    public static final String PDF_URI = "PDF_URI";
    public static final String PDF_FILE_NAME = "PDF_FILE_NAME";
    public static final String PAGE_PDF = "page_pdf";

    public static final int REQUEST_CODE_GO_THUMBNAIL = 3432434;
    public static final String SWIPE_MODE = "prefs_swipe_horizontal_enabled";


    public static final String KEY_SELECTED_FILE_FORMAT = "KEY_SELECTED_FILE_FORMAT";
    public static final String KEY_SELECTED_FILE_URI = "KEY_SELECTED_FILE_URI";
    public static final String KEY_SELECTED_FILE_NAME = "KEY_SELECTED_FILE_NAME";
    public static final int REQUEST_CODE_PICK_FILE = 1321;
    public static String EMAIL_FEEDBACK = "arrowwould@gmail.com";
    public static String FAMILY_APP = "";

    public static String FILE_TYPE = "file_type";
    public static String LANGUAGE_SET = "language_set";

    public static String LANGUAGE_KEY = "language_key";
    public static String LANGUAGE_KEY_NUMBER = "language_key_number";
    public static String LANGUAGE_NAME = "language_name";

    public static final String COUNT_PDF_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/pdf'";
    public static final String COUNT_ALL_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.ms-excel'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/msword'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.openxmlformats-officedocument.presentationml.presentation'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.ms-powerpoint'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'text/plain'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'text/html'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/rtf'"
            + " OR  " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/pdf'";
    public static final String COUNT_EXCEL_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.ms-excel'";
    public static final String COUNT_WORD_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/msword'";

    public static final String COUNT_PPT_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.openxmlformats-officedocument.presentationml.presentation'"
            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/vnd.ms-powerpoint'";
    public static final String COUNT_TXT_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'text/plain'";
    public static final String COUNT_HTML_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'text/html'";
    public static final String COUNT_RTF_FILE = MediaStore.Files.FileColumns.MIME_TYPE + " LIKE 'application/rtf'";


    public static ArrayList<Language> createArrayLanguage() {
        ArrayList<Language> arrayList = new ArrayList<>();
        arrayList.add(new Language("en", "English", R.drawable.flag_en));
        arrayList.add(new Language("vi", "Tiếng Việt", R.drawable.flag_vi));
        arrayList.add(new Language("de", "Deutsch", R.drawable.flag_de));
        arrayList.add(new Language("in", "Indonesia", R.drawable.flag_in));
        arrayList.add(new Language("it", "Italiano", R.drawable.flag_it));
        arrayList.add(new Language("ja", "日本語", R.drawable.flag_ja));
        arrayList.add(new Language("ko", "한국어", R.drawable.flag_ko));
        arrayList.add(new Language("pt", "Português", R.drawable.flag_pt));
        arrayList.add(new Language("ru", "Русский", R.drawable.flag_ru));
        arrayList.add(new Language("ar", "عربي", R.drawable.flag_ar));
        arrayList.add(new Language("cs", "čeština", R.drawable.flag_cs));
        arrayList.add(new Language("es", "Español", R.drawable.flag_es));
        arrayList.add(new Language("hi", "हिंदी", R.drawable.flag_hi));
        arrayList.add(new Language("pl", "Język polski", R.drawable.flag_pl));
        arrayList.add(new Language("ro", "Română", R.drawable.flag_ro));
        arrayList.add(new Language("sv", "Svenska", R.drawable.flag_sv));
        arrayList.add(new Language("th", "แบบไทย", R.drawable.flag_th));
        return arrayList;
    }

    public static ArrayList<ColorModel> getColorBgList() {

        ArrayList<ColorModel> arrayList = new ArrayList<>();
        arrayList.add(new ColorModel(R.drawable.bg_color_tran, "transparent", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_1, "#000000", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_2, "#ffffff", true));
        arrayList.add(new ColorModel(R.drawable.bg_color_3, "#888888", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_4, "#fe0000", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_5, "#00f402", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_6, "#1f20fb", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_7, "#ff00aa", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_8, "#00effe", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_9, "#ff5c02", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_10, "#ffc44e", false));


        return arrayList;
    }
    public static ArrayList<ColorModel> getColorTextList() {

        ArrayList<ColorModel> arrayList = new ArrayList<>();
        arrayList.add(new ColorModel(R.drawable.bg_color_1, "#000000", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_2, "#ffffff", true));
        arrayList.add(new ColorModel(R.drawable.bg_color_3, "#888888", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_4, "#fe0000", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_5, "#00f402", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_6, "#1f20fb", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_7, "#ff00aa", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_8, "#00effe", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_9, "#ff5c02", false));
        arrayList.add(new ColorModel(R.drawable.bg_color_10, "#ffc44e", false));


        return arrayList;
    }
}
