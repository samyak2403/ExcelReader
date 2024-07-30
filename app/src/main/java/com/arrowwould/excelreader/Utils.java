package com.arrowwould.excelreader;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.arrowwould.excelreader.adapter.ListFileAdapter;
import com.arrowwould.excelreader.db.DbHelper;
import com.arrowwould.excelreader.model.OfficeModel;
import com.arrowwould.excelreader.ui.activities.ViewOfficeActivity;
import com.arrowwould.excelreader.ui.dialog.PermissionDialog;
import com.arrowwould.excelreader.ui.dialog.RateAppDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Utils {
    public static void searchDocument(String string, ArrayList<OfficeModel> itemList, ListFileAdapter adapter) {
        ArrayList<OfficeModel> arrayList = new ArrayList<>();
        for (OfficeModel documentModel : itemList) {
            if (documentModel.getName().toLowerCase().contains(string.toLowerCase())) {
                arrayList.add(documentModel);
            }
            adapter.filter(arrayList);
        }
    }

    public static void openDocument(Activity mContext) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimetypes = {
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivityForResult(intent, GlobalConstant.REQUEST_CODE_PICK_FILE);
    }

    public static void shareApp(Activity context) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "\"https://play.google.com/store/apps/details?id=" + context.getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Choose one"));
        } catch (Exception ignored) {

        }
    }

    public static void feedbackApp(Activity context) {
        try {
            Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + GlobalConstant.EMAIL_FEEDBACK));
            intent3.putExtra(Intent.EXTRA_SUBJECT, R.string.str_feed_back_app);
            intent3.putExtra(Intent.EXTRA_TEXT, "");
            context.startActivity(intent3);
        } catch (ActivityNotFoundException ignored) {
        }
    }

    public static void showRateDialog(Activity mContext) {
        RateAppDialog rateAppDialog = new RateAppDialog(mContext);
        Window window = rateAppDialog.getWindow();
        assert window != null;
        rateAppDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rateAppDialog.show();

    }

    public static void copy(Context mContext, Uri source, String destination) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = mContext.getContentResolver().openInputStream(source);
            out = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getFileNameFromUri(Uri contentUri, ContentResolver contentResolver) {
        try {
            String filePath;
            String[] filePathColumn = {MediaStore.MediaColumns.DISPLAY_NAME};
            Cursor cursor = contentResolver.query(contentUri, filePathColumn, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                cursor.close();
                return filePath;
            }
//            cursor.moveToFirst();
        } catch (SecurityException | CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public static void showPermissionDialog(Activity mContext) {
        PermissionDialog permissionDialog = new PermissionDialog(mContext);
        Window window = permissionDialog.getWindow();
        assert window != null;
        permissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        permissionDialog.show();

    }

    public static CharSequence setAppName(Context mContext) {
//        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "poppins_semibold.ttf");

        SpannableString first = new SpannableString("Excel");
        SpannableString second = new SpannableString(" Reader");
        first.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_excel)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        first.setSpan(new CustomTypefaceSpan("", font), 0, first.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        second.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimaryText)), 0, second.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        second.setSpan(new CustomTypefaceSpan("", font), 0, second.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return TextUtils.concat(first, second);
    }

    public static void askPermission(Activity mContext) {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", mContext.getApplicationContext().getPackageName())));
                mContext.startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                mContext.startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            Dexter.withContext(mContext).withPermissions(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                }
            }).onSameThread().check();
//            ActivityCompat.requestPermissions(mContext, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void openFile(Activity mContext, File file) {
        try {
//            File file = new File(fileHolderModel.getAbsolutePath());
            DbHelper.getInstance(mContext).addRecentPDF(file.getAbsolutePath());


            Uri fromFile = Uri.fromFile(file);
            Intent intent = new Intent(mContext, ViewOfficeActivity.class);
            intent.setAction("android.intent.action.VIEW");
            intent.setData(fromFile);
            intent.putExtra(GlobalConstant.KEY_SELECTED_FILE_URI, file.getAbsolutePath());
            intent.putExtra(GlobalConstant.KEY_SELECTED_FILE_NAME, file.getName());

            intent.putExtra("STARTED_FROM_EXPLORER", true);
            intent.putExtra("START_PAGE", 0);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkPermission(Context mContext) {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            return !(ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED);
        }
    }


    public static ArrayList<OfficeModel> getExcel(Context mContext) {
        ArrayList<OfficeModel> pdfList = new ArrayList<>();
        Uri collection;
        final String[] projection = new String[]{
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.MIME_TYPE,
        };
        final String sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

//        final String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Files.getContentUri("external");
        }
        try (Cursor cursor = mContext.getContentResolver().query(collection, projection, GlobalConstant.COUNT_EXCEL_FILE, null, sortOrder)) {
            assert cursor != null;
            if (cursor.moveToFirst()) {
                int columnData = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int columnName = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                do {
                    String file_path = cursor.getString(columnData);
                    String file_name = cursor.getString(columnName);
                    //you can get your pdf files
                    final File file = new File(file_path);
                    if (file_name == null) {
                        break;
                    }
                    if (file.length() != 0) {
                        OfficeModel fileHolderModel = new OfficeModel();
                        fileHolderModel.setName(file.getName());
                        fileHolderModel.setAbsolutePath(file.getAbsolutePath());
                        fileHolderModel.setFileUri(file.getAbsolutePath());
                        fileHolderModel.setLength(file.length());
                        fileHolderModel.setLastModified(file.lastModified());
                        fileHolderModel.setDirectory(file.isDirectory());
                        pdfList.add(fileHolderModel);
                    }
                } while (cursor.moveToNext());
            }
        }
        return pdfList;
    }

    public static String removeExtension(String str) {
        int lastIndexOf = str.lastIndexOf(Objects.requireNonNull(System.getProperty("file.separator")));
        if (lastIndexOf != -1) {
            str = str.substring(lastIndexOf + 1);
        }
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf2 == -1) {
            return str;
        }
        return str.substring(0, lastIndexOf2);
    }

    public static String formatDateToHumanReadable(Long l) {
        return new SimpleDateFormat("MMM dd yyyy", Locale.getDefault()).format(new Date(l));
    }

    public static void shareFile(Context mContext, File DocFile) {
        try {
            MediaScannerConnection.scanFile(mContext, new String[]{DocFile.getPath()},

                    null, (path, uri) -> {
                        Intent shareIntent = new Intent(
                                Intent.ACTION_SEND);
                        shareIntent.setType("application/*");
                        shareIntent.putExtra(
                                Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.app_name));
                        shareIntent.putExtra(
                                Intent.EXTRA_TITLE, mContext.getResources().getString(R.string.app_name));
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(Intent.createChooser(shareIntent, "Share File using"));

                    });
        } catch (Exception ignored) {

        }
    }

    public static String getMimeType(String uri) {
        String extension;
        extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri)).toString());
        return extension;
    }

    public static void showRate(Context mContext) {
        RateAppDialog rateAppDialog = new RateAppDialog(mContext);
        Window window = rateAppDialog.getWindow();
        assert window != null;
        rateAppDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rateAppDialog.show();
    }

    public static void showHideView(Context context, View layout, boolean isShow, int dimenId) {
        ValueAnimator valueAnimator;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
        if (layout != null) {
            if (layout.getVisibility() == View.GONE && isShow) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(dimenId);
                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                layoutParams.height = 0;
                layout.setLayoutParams(layoutParams);
                layout.setVisibility(View.VISIBLE);
                valueAnimator = ValueAnimator.ofInt(0, dimensionPixelSize);
                valueAnimator.setDuration(350);
                animatorUpdateListener = new showView(layout);
            } else if (layout.getVisibility() == View.VISIBLE) {
                valueAnimator = ValueAnimator.ofInt(layout.getHeight(), 0);
                valueAnimator.setDuration(350);
                animatorUpdateListener = new hideView(layout);
            } else {
                return;
            }
            valueAnimator.addUpdateListener(animatorUpdateListener);
            valueAnimator.start();
        }
    }

    public static class hideView implements ValueAnimator.AnimatorUpdateListener {

        public final View f1287a;

        public hideView(View linearLayout) {
            this.f1287a = linearLayout;
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            int intValue = (Integer) valueAnimator.getAnimatedValue();
            View linearLayout = this.f1287a;
            ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
            layoutParams.height = intValue;
            linearLayout.setLayoutParams(layoutParams);
            if (intValue == 0) {
                linearLayout.setVisibility(View.GONE);
            }
        }
    }

    public static void setTheme(Context mContext, boolean isDark) {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        SharedPreferenceUtils.getInstance(mContext).setBoolean(GlobalConstant.NIGHT_MODE_KEY, isDark);
    }

    public static class showView implements ValueAnimator.AnimatorUpdateListener {

        public final View linearLayout;

        public showView(View linearLayout) {
            this.linearLayout = linearLayout;
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            int intValue = (Integer) valueAnimator.getAnimatedValue();
            View linearLayout = this.linearLayout;
            ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
            layoutParams.height = intValue;
            linearLayout.setLayoutParams(layoutParams);
        }
    }

    public static void RateApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public static void createShortCut(Context mContext, OfficeModel documentModel) {
        int idResource = R.drawable.ic_excel;
        if (Build.VERSION.SDK_INT < 26) {
            Intent launchIntentForPackage = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
            Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            intent.putExtra("android.intent.extra.shortcut.INTENT", launchIntentForPackage);
            intent.putExtra("android.intent.extra.shortcut.NAME", documentModel.getName());
            intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(mContext, idResource));
            intent.putExtra("duplicate", true);
            mContext.sendBroadcast(intent);
        } else {
            ShortcutManager shortcutManager = (ShortcutManager) mContext.getSystemService(Context.SHORTCUT_SERVICE);
            Intent launchIntentForPackage = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
            launchIntentForPackage.setAction("android.intent.action.CREATE_SHORTCUT");
            launchIntentForPackage.setData(Uri.parse(documentModel.getFileUri()));
//            launchIntentForPackage.putExtra("URIFILE",documentModel.getName());
            if (shortcutManager.isRequestPinShortcutSupported()) {
                File file = new File(documentModel.getAbsolutePath());
                ShortcutInfo build = new ShortcutInfo.Builder(mContext, documentModel.getName())
                        .setShortLabel(file.getName()).setLongLabel(file.getPath())
                        .setIcon(Icon.createWithResource(mContext, idResource))
                        .setIntent(launchIntentForPackage).build();
                int flags;
                if (Build.VERSION.SDK_INT >= 31) {
                    flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
                } else {
                    flags = PendingIntent.FLAG_UPDATE_CURRENT;
                }
                shortcutManager.requestPinShortcut(build, PendingIntent.getBroadcast(mContext, 1000, new Intent("sc"), flags).getIntentSender());
            }
        }

    }

    public static boolean isFileNameValid(String str) {
        String trim = str.trim();
        return !TextUtils.isEmpty(trim) && trim.matches("[a-zA-Z0-9-_ ]*");
    }
}
