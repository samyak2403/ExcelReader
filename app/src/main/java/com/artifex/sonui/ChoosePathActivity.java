package com.artifex.sonui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import androidx.appcompat.widget.AppCompatTextView;

import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.SOEditTextOnEditorActionListener;
import com.artifex.sonui.editor.Utilities;
import com.arrowwould.excelreader.R;

public class ChoosePathActivity extends BaseActivity {
    private static ChoosePathActivity.a a;
    private static int b;
    private static String c;
    private static boolean d;

    public ChoosePathActivity() {
    }

    public static void a(Activity var0, int var1, boolean var2, ChoosePathActivity.a var3, String var4) {
        a = var3;
        b = var1;
        c = var4;
        d = false;
        var0.startActivity(new Intent(var0, ChoosePathActivity.class));
    }

    public void finish() {
        if (!d) {
            a.a();
        }

        super.finish();
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(R.layout.sodk_choose_path);
        String var4 = c;
        final FileBrowser fileBrowser = this.findViewById(R.id.file_browser);
        fileBrowser.a(this, var4);
        AppCompatTextView tvSave = this.findViewById(R.id.save_button);
        if (b == 3) {
            var4 = getString(R.string.sodk_editor_copy);
        } else {
            var4 = getString(R.string.sodk_editor_save);
        }

        tvSave.setText(var4);
        tvSave.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                Utilities.hideKeyboard(ChoosePathActivity.this);
                ChoosePathActivity.d = true;
                ChoosePathActivity.this.finish();
                ChoosePathActivity.a.a(fileBrowser);
            }
        });

        this.findViewById(R.id.cancel_button).setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                Utilities.hideKeyboard(ChoosePathActivity.this);
                ChoosePathActivity.d = true;
                ChoosePathActivity.this.finish();
                if (a!=null){
                    ChoosePathActivity.a.a();
                }
            }
        });

        SOEditText var5 = fileBrowser.getEditText();
        var5.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View var1, int var2x, KeyEvent var3) {
                if (var3.getAction() == 0 && var2x == 66) {
                    Utilities.hideKeyboard(ChoosePathActivity.this);
                    ChoosePathActivity.d = true;
                    ChoosePathActivity.this.finish();
                    ChoosePathActivity.a.a(fileBrowser);
                    return true;
                } else {
                    return false;
                }
            }
        });

        var5.setOnEditorActionListener(new SOEditTextOnEditorActionListener() {
            public boolean onEditorAction(SOEditText var1, int var2x, KeyEvent var3) {
                boolean var4 = true;
                if (var2x == 6) {
                    Utilities.hideKeyboard(ChoosePathActivity.this);
                    ChoosePathActivity.d = true;
                    ChoosePathActivity.this.finish();
                    ChoosePathActivity.a.a(fileBrowser);
                } else {
                    var4 = false;
                }

                return var4;
            }
        });
    }

    public interface a {
        void a();

        void a(FileBrowser var1);
    }
}
