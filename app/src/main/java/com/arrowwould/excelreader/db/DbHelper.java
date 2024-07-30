package com.arrowwould.excelreader.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.model.OfficeModel;

import java.io.File;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pdf_history.db";

    private static DbHelper sInstance;
    public Context context;
    private SQLiteDatabase mDatabase;
    private int mOpenCounter;

    public DbHelper(Context context2) {
        super(context2, DATABASE_NAME, null, 2);
        this.context = context2;
    }

    public static synchronized DbHelper getInstance(Context context2) {
        DbHelper dbHelper;
        synchronized (DbHelper.class) {
            if (sInstance == null) {
                sInstance = new DbHelper(context2.getApplicationContext());
            }
            dbHelper = sInstance;
        }
        return dbHelper;
    }

    public synchronized SQLiteDatabase getReadableDb() {
        this.mOpenCounter++;
        if (this.mOpenCounter == 1) {
            this.mDatabase = getWritableDatabase();
        }
        return this.mDatabase;
    }

    public synchronized void closeDb() {
        this.mOpenCounter--;
        if (this.mOpenCounter == 0) {
            this.mDatabase.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS history_pdfs ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, last_accessed_at DATETIME DEFAULT (DATETIME('now','localtime')))");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS stared_pdfs ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, created_at DATETIME DEFAULT (DATETIME('now','localtime')))");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS last_opened_page ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, page_number INTEGER)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS bookmarks ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, path TEXT, page_number INTEGER UNIQUE, created_at DATETIME DEFAULT (DATETIME('now','localtime')))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS last_opened_page ( _id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT UNIQUE, page_number INTEGER)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS bookmarks ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, path TEXT, page_number INTEGER UNIQUE, created_at DATETIME DEFAULT (DATETIME('now','localtime')))");
        }
    }

    public boolean isRecent(String str) {
        Cursor query = getReadableDb().query(DbContract.RecentEntry.TABLE_NAME, new String[]{"path"}, "path =?", new String[]{str}, null, null, null);
        boolean valueOf = query.moveToFirst();
        query.close();
        closeDb();
        return valueOf;
    }

    public void addRecentPDF(String str) {
        SQLiteDatabase readableDb = getReadableDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", str);
        readableDb.replace(DbContract.RecentEntry.TABLE_NAME, null, contentValues);
        closeDb();
    }

    public void removeRecentPDF(String str) {
        getReadableDb().delete(DbContract.RecentEntry.TABLE_NAME, "path =?", new String[]{str});
        closeDb();
    }

    public ArrayList<OfficeModel> getRecentPDFs() {
        ArrayList<OfficeModel> arrayList = new ArrayList<>();
        SQLiteDatabase readableDb = getReadableDb();
        Cursor rawQuery = readableDb.rawQuery("SELECT * FROM history_pdfs ORDER BY last_accessed_at DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                @SuppressLint("Range") String string = rawQuery.getString(rawQuery.getColumnIndex("path"));
                File file = new File(string);
                if (file.exists()) {
                    OfficeModel pdfDataType = new OfficeModel();
                    pdfDataType.setName(file.getName());
                    pdfDataType.setAbsolutePath(file.getAbsolutePath());
                    pdfDataType.setFileUri(file.getAbsolutePath());
                    pdfDataType.setLength(file.length());

                    pdfDataType.setLastModified(file.lastModified());
                    pdfDataType.setStarred(isStared(readableDb, file.getAbsolutePath()));
                    arrayList.add(pdfDataType);
                } else {
                    deleteRecentPDF(string);
                }
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        closeDb();
        return arrayList;
    }

    public void deleteRecentPDF(String str) {
        getReadableDb().delete(DbContract.RecentEntry.TABLE_NAME, "path =?", new String[]{str});
        closeDb();
//        EventBus.getDefault().post(new DataUpdatedEvent.RecentFileDeleteEvent());
    }

    public void updateHistory(String str, String str2) {
        try {
            SQLiteDatabase readableDb = getReadableDb();
            ContentValues contentValues = new ContentValues();
            contentValues.put("path", str2);
            readableDb.update(DbContract.RecentEntry.TABLE_NAME, contentValues, "path=?", new String[]{str});
            closeDb();
        } catch (Exception e) {
            Toast.makeText(this.context, R.string.str_action_failed, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void updateStarred(String str, String str2) {
        SQLiteDatabase readableDb = getReadableDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", str2);
        readableDb.update(DbContract.FavoriteEntry.TABLE_NAME, contentValues, "path=?", new String[]{str});
        closeDb();
    }

    public void clearRecentPDFs() {
        getReadableDb().delete(DbContract.RecentEntry.TABLE_NAME, null, null);
        closeDb();
//        EventBus.getDefault().post(new DataUpdatedEvent.RecentFileClearEvent());
    }

    public void clearFavorite() {
        getReadableDb().delete(DbContract.FavoriteEntry.TABLE_NAME, null, null);
        closeDb();
//        EventBus.getDefault().post(new DataUpdatedEvent.FavoriteFileDeleteEvent());

    }

    public ArrayList<OfficeModel> getStarredPdfs() {
        ArrayList<OfficeModel> arrayList = new ArrayList<>();
        Cursor rawQuery = getReadableDb().rawQuery("SELECT * FROM stared_pdfs ORDER BY created_at DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                @SuppressLint("Range") String string = rawQuery.getString(rawQuery.getColumnIndex("path"));
                File file = new File(string);
                if (file.exists()) {
                    OfficeModel pdfDataType = new OfficeModel();
                    pdfDataType.setName(file.getName());
                    pdfDataType.setAbsolutePath(file.getAbsolutePath());
                    pdfDataType.setFileUri(file.getAbsolutePath());
                    pdfDataType.setLength(file.length());
                    pdfDataType.setLastModified(file.lastModified());
                    pdfDataType.setStarred(true);
                    arrayList.add(pdfDataType);
                } else {
                    removeStaredPDF(string);
                }
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        closeDb();
        return arrayList;
    }

    public void addLastOpenedPage(String str, int i) {
        try {
            SQLiteDatabase readableDb = getReadableDb();
            ContentValues contentValues = new ContentValues();
            contentValues.put("path", str);
            contentValues.put("page_number", Integer.valueOf(i));
            readableDb.replace(DbContract.LastOpenedPageEntry.TABLE_NAME, null, contentValues);
            closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStaredPDF(String str) {
        SQLiteDatabase readableDb = getReadableDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", str);
        readableDb.replace(DbContract.FavoriteEntry.TABLE_NAME, null, contentValues);
        closeDb();
    }

    public void removeStaredPDF(String str) {
        getReadableDb().delete(DbContract.FavoriteEntry.TABLE_NAME, "path =?", new String[]{str});
        closeDb();
    }

    public void updateStaredPDF(String str, String str2) {
        try {
            SQLiteDatabase readableDb = getReadableDb();
            ContentValues contentValues = new ContentValues();
            contentValues.put("path", str2);
            readableDb.update(DbContract.FavoriteEntry.TABLE_NAME, contentValues, "path =?", new String[]{str});
            closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStared(String str) {
        Cursor query = getReadableDb().query(DbContract.FavoriteEntry.TABLE_NAME, new String[]{"path"}, "path =?", new String[]{str}, null, null, null);
        boolean valueOf = query.moveToFirst();
        query.close();
        closeDb();
        return valueOf;
    }

    public boolean isStared(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor query = sQLiteDatabase.query(DbContract.FavoriteEntry.TABLE_NAME, new String[]{"path"}, "path =?", new String[]{str}, null, null, null);
        boolean valueOf = query.moveToFirst();
        query.close();
        return valueOf;
    }

    @SuppressLint("Range")
    public int getLastOpenedPage(String str) {
        int i;
        try {
            Cursor query = getReadableDb().query(DbContract.LastOpenedPageEntry.TABLE_NAME, new String[]{"page_number"}, "path = ? ", new String[]{str}, null, null, null);
            if (query == null || !query.moveToFirst()) {
                i = 0;
                closeDb();
                return i;
            }
            i = query.getInt(query.getColumnIndex("page_number"));
            try {
                query.close();
            } catch (Exception ignored) {
            }
            closeDb();
            return i;
        } catch (Exception e2) {
            i = 0;
            e2.printStackTrace();
            closeDb();
            return i;
        }
    }

}
