package com.artifex.sonui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.artifex.sonui.AppFile.CloudPermissionChecked;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.Utilities;
import com.arrowwould.excelreader.R;

import java.util.ArrayList;
import java.util.Comparator;

public class FileBrowser extends RelativeLayout {
    private static AppFile c;
    ArrayList<AppFile> a;
    ArrayList<AppFile> b = new ArrayList<>();
    private Handler d;
    private Runnable e;
    private ListView f;
    private e g;
    private AppFile h;
    private SOEditText i;
    private AppCompatTextView btnSave;
    private BaseActivity k = null;

    public FileBrowser(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.b();
    }

    private void a(ListView var1, View var2, int var3, long var4) {
        final f var6 = (f) this.g.getItem(var3);
        if (var6 != null) {
            if (var6.a.isCloud() && !AppFile.i()) {
                Utilities.showMessage((Activity) this.getContext(), this.getContext().getString(R.string.sodk_editor_connection_error_title), this.getContext().getString(R.string.sodk_editor_connection_error_body));
                return;
            }

            if (var6.a.d()) {
                AppFile.checkCloudPermission(this.k, var6.a, new CloudPermissionChecked() {
                    public void a(boolean var1) {
                        if (var1) {
                            FileBrowser.this.b.add(var6.a);
                            FileBrowser.c = var6.a;
                            FileBrowser.this.d.post(FileBrowser.this.e);
                        }

                    }
                });
            } else {
                this.i.setText(Utilities.removeExtension(var6.a.b));
            }
        }

    }

    private void a(AppFile var1, String var2, LinearLayout var3) {
        Button var4 = (Button) LayoutInflater.from(this.getContext()).inflate(R.layout.sodk_breadcrumb_button, null);
        if (var2 == null) {
            var2 = var1.b();
        }

        var4.setText(var2);
        var4.setTag(var1);
        var4.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                AppFile var5 = (AppFile) var1.getTag();
                if (var5 == null) {
                    FileBrowser.this.b = new ArrayList<>();
                    var5 = null;
                } else {
                    ArrayList<AppFile> var2 = new ArrayList<>();

                    for (AppFile var4 : FileBrowser.this.b) {
                        var2.add(var4);
                        if (var4.isSameAs(var5)) {
                            break;
                        }
                    }

                    FileBrowser.this.b = var2;
                }

                FileBrowser.c = var5;
                FileBrowser.this.d.post(FileBrowser.this.e);
            }
        });
        var3.addView(var4);
    }

    private void b() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.sodk_file_browser, this);
        this.i = this.findViewById(R.id.edit_text);
        this.btnSave = this.findViewById(R.id.save_button);
        ArrayList<AppFile> var1 = new ArrayList<>();
        this.a = var1;
        var1.add(new b(com.artifex.solib.a.b(this.getContext()).getAbsolutePath(), this.getResources().getString(R.string.sodk_editor_my_documents)));
        if (com.artifex.solib.a.d(this.getContext())) {
            this.a.add(new b(Utilities.getDownloadDirectory(this.getContext()).getAbsolutePath(), this.getResources().getString(R.string.sodk_editor_download)));
            this.a.add(new b(Utilities.getRootDirectory(this.getContext()).getAbsolutePath(), this.getResources().getString(R.string.sodk_editor_all)));
            String var2 = Utilities.getSDCardPath(this.getContext());
            if (var2 != null) {
                this.a.add(new b(var2, "SD Card"));
            }
        }

//        AppFile var3 = com.artifex.sonui.c.a("root", "Google Drive", true, true);
//
//        var3 = com.artifex.sonui.c.b("0", "Box", true, true);
//
//        var3 = com.artifex.sonui.c.c("/", "Dropbox", true, true);

        c = null;
    }

    private void c() {
        SOEditText var1 = this.i;
        boolean var2;
        var2 = c != null;


        var1.setEnabled(var2);
        boolean var3;
        var3 = this.i.getText().toString().trim().length() > 0;
        Log.d(FileBrowser.class.getSimpleName(), "c: " + i.getText());

        if (var3 && c != null) {
            btnSave.setBackgroundResource(R.drawable.bg_button_action_round_10);
            this.btnSave.setEnabled(true);
        } else {
            btnSave.setBackgroundResource(R.drawable.bg_button_nagative);
            this.btnSave.setEnabled(false);
        }
    }

    private void d() {
        LinearLayout var1 = this.findViewById(R.id.names_bar);
        var1.removeAllViews();
        this.a(null, this.getResources().getString(R.string.sodk_editor_storage), var1);

        for (AppFile var3 : this.b) {
            var1.addView(LayoutInflater.from(this.getContext()).inflate(R.layout.sodk_breadcrumb_slash, (ViewGroup) null));
            this.a(var3, null, var1);
        }

    }

    public void a(BaseActivity var1, String var2) {
        this.k = var1;
        if (var2 != null && !var2.isEmpty()) {
            this.i.setText(Utilities.removeExtension(var2));
        }

        this.i.setSelectAllOnFocus(true);
        this.i.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable var1) {
                FileBrowser.this.c();
            }

            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }
        });
        ListView var3 = (ListView) this.findViewById(R.id.fileListView);
        this.f = var3;
        var3.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
                FileBrowser var6 = FileBrowser.this;
                var6.a(var6.f, var2, var3, var4);
            }
        });
        e var4 = new e(((Activity) this.getContext()).getLayoutInflater(), false);
        this.g = var4;
        this.f.setAdapter(var4);
        this.d = new Handler();
        Runnable var5 = new Runnable() {
            public void run() {
                FileBrowser.this.findViewById(R.id.no_documents_found).setVisibility(GONE);
                FileBrowser var1 = FileBrowser.this;
                int var2 = R.id.enumerate_progress;
                var1.findViewById(var2).setVisibility(GONE);
                FileBrowser.this.i.clearFocus();
                Utilities.hideKeyboard(FileBrowser.this.getContext());
                FileBrowser.this.c();
                AppFile var4 = FileBrowser.c;
                int var3 = 0;
                if (var4 == null) {
                    FileBrowser.this.g.a();

                    while (var3 < FileBrowser.this.a.size()) {
                        FileBrowser.this.g.a(new f(FileBrowser.this.a.get(var3)));
                        ++var3;
                    }

                    AppFile.i = null;
                    FileBrowser.this.b.clear();
                    FileBrowser.this.d();
                } else {
                    if (!FileBrowser.c.serviceAvailable()) {
                        FileBrowser.c = null;
                        FileBrowser.this.d.post(FileBrowser.this.e);
                        return;
                    }

                    FileBrowser.this.findViewById(var2).setVisibility(VISIBLE);
                    FileBrowser.this.g.a();
                    FileBrowser.this.h = FileBrowser.c.enumerateDir(new AppFile.EnumerateListener() {
                        public void a(ArrayList<AppFile> arrayList) {
                            if (arrayList != null) {

                                ArrayList<AppFile> arrayList2 = new ArrayList<>();
                                for (AppFile next : arrayList) {
                                    if (next.d() || next.b(FileBrowser.this.getContext())) {
                                        arrayList2.add(next);
                                    }
                                }
                                arrayList2.sort(new Comparator<AppFile>() {
                                    public int compare(AppFile appFile, AppFile appFile2) {
                                        return appFile.b().compareToIgnoreCase(appFile2.b());
                                    }
                                });
                                for (AppFile appFile : arrayList2) {
                                    FileBrowser.this.g.a(new f(appFile));
                                }
                                int size = arrayList2.size();
                                int intid = R.id.no_documents_found;
                                if (size == 0) {
                                    FileBrowser.this.findViewById(intid).setVisibility(VISIBLE);
                                } else {
                                    FileBrowser.this.findViewById(intid).setVisibility(GONE);
                                }
                                FileBrowser.this.findViewById(R.id.enumerate_progress).setVisibility(GONE);
                                return;
                            }
                            AppFile unused = FileBrowser.c = null;
                            FileBrowser.this.d.post(FileBrowser.this.e);
                        }
                    });
                }

                FileBrowser.this.d();
            }
        };
        this.e = var5;
        this.d.post(var5);
    }

    public SOEditText getEditText() {
        return this.i;
    }

    public String getFileName() {
        return this.i.getText().toString().trim();
    }

    public AppFile getFolderAppFile() {
        return c;
    }
}
