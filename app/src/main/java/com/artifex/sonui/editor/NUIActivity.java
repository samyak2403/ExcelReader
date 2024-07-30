package com.artifex.sonui.editor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import com.artifex.solib.ConfigOptions;
import com.artifex.sonui.editor.NUIView.OnDoneListener;
import com.artifex.sonui.editor.SODocSession.SODocSessionLoadListenerCustom;

public class NUIActivity extends BaseActivity {
    private static SODocSession a;
    private Intent b = null;
    private long c = 0L;
    private int d = -1;
    protected NUIView mNUIView;

    public NUIActivity() {
    }
    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.initialise();
    }
    private void a() {
        Intent var1 = this.getIntent();
        Bundle var2 = var1.getExtras();
        boolean var3;
        if (var2 != null) {
            var3 = var2.getBoolean("SESSION", false);
            this.a(var1);
        } else {
            var3 = false;
        }

        if (var3 && a == null) {
            super.finish();
        } else {
            this.a(var1, false);
        }
    }

    private void a(Intent intent) {
        ConfigOptions var2 = ConfigOptions.a();
        Bundle bundle = intent.getExtras();
        if (intent.hasExtra("ENABLE_SAVE")) {
            var2.n(bundle.getBoolean("ENABLE_SAVE", true));
        }

        if (intent.hasExtra("ENABLE_SAVEAS")) {
            var2.b(bundle.getBoolean("ENABLE_SAVEAS", true));
        }

        if (intent.hasExtra("ENABLE_SAVEAS_PDF")) {
            var2.c(bundle.getBoolean("ENABLE_SAVEAS_PDF", true));
        }

        if (intent.hasExtra("ENABLE_CUSTOM_SAVE")) {
            var2.o(bundle.getBoolean("ENABLE_CUSTOM_SAVE", true));
        }

        if (intent.hasExtra("ALLOW_AUTO_OPEN")) {
            var2.q(bundle.getBoolean("ALLOW_AUTO_OPEN", true));
        }
    }

    private void a(Intent intent, boolean z) {
        SOFileState fromString;
        String string;
        String str;
        boolean z2;
        int i;
        NUIActivity nUIActivity = this;
        Bundle extras = intent.getExtras();
        boolean z3 = false;
        boolean z4 = extras != null && extras.getBoolean("SESSION", false);
        SODocSession sODocSession = a;
        setContentView(R.layout.sodk_editor_doc_view_activity);
        NUIView nUIView = findViewById(R.id.doc_view);
        nUIActivity.mNUIView = nUIView;
        nUIView.setOnDoneListener(new OnDoneListener() {
            public void done() {
                NUIActivity.super.finish();
            }
        });
        if (extras != null) {
            int i2 = extras.getInt("START_PAGE");
            fromString = SOFileState.fromString(extras.getString("STATE"), SOFileDatabase.getDatabase());
            string = extras.getString("FOREIGN_DATA", null);
            boolean z5 = extras.getBoolean("IS_TEMPLATE", true);
            String string2 = extras.getString("CUSTOM_DOC_DATA");
            if (fromString == null && !z) {
                fromString = SOFileState.getAutoOpen(this);
                if (fromString != null) {
                    z4 = z3;
                }
            }
            z3 = z4;
            str = string2;
            z4 = z3;
            z2 = z5;
            i = i2;
        } else {
            fromString = null;
            string = null;
            str = string;
            z2 = true;
            i = 1;
        }
        if (str == null) {
            Utilities.setSessionLoadListener(null);
        }
        if (z4) {
            nUIActivity.mNUIView.start(sODocSession, i, string);
        } else if (fromString != null) {
            nUIActivity.mNUIView.start(fromString, i);
        } else {
            nUIActivity.mNUIView.start(intent.getData(), z2, i, str, intent.getType());
        }
    }


    public static void setSession(SODocSession var0) {
        a = var0;
    }

    public Intent childIntent() {
        return this.b;
    }

    protected void doResumeActions() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onResume();
        }

    }

    public void finish() {
        if (this.mNUIView == null) {
            super.finish();
        } else {
            Utilities.dismissCurrentAlert();
            this.onBackPressed();
        }
    }

    protected void initialise() {
        this.a();
    }

    public boolean isDocModified() {
        NUIView var1 = this.mNUIView;
        return var1 != null && var1.isDocModified();
    }

    protected void onActivityResult(int var1, int var2, Intent var3) {
        NUIView var4 = this.mNUIView;
        if (var4 != null) {
            var4.onActivityResult(var1, var2, var3);
        }

    }

    public void onBackPressed() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onBackPressed();
        }

    }

    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        this.mNUIView.onConfigurationChange(var1);
    }



    protected void onDestroy() {
        super.onDestroy();
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onDestroy();
        }

    }

    public boolean onKeyDown(int var1, KeyEvent var2) {
        long var3 = var2.getEventTime();
        if (this.d == var1 && var3 - this.c <= 100L) {
            return true;
        } else {
            this.c = var3;
            this.d = var1;
            return this.mNUIView.doKeyDown(var1, var2);
        }
    }

    public void onNewIntent(final Intent var1) {
        ConfigOptions.a();
        if (this.isDocModified()) {
            Utilities.yesNoMessage(this, this.getString(R.string.sodk_editor_new_intent_title), this.getString(R.string.sodk_editor_new_intent_body), this.getString(R.string.sodk_editor_new_intent_yes_button), this.getString(R.string.sodk_editor_new_intent_no_button), new Runnable() {
                public void run() {
                    if (NUIActivity.this.mNUIView != null) {
                        NUIActivity.this.mNUIView.endDocSession(true);
                    }

                    NUIActivity.this.a(var1);
                    NUIActivity.this.a(var1, true);
                }
            }, new Runnable() {
                public void run() {
                    SODocSessionLoadListenerCustom var1 = Utilities.getSessionLoadListener();
                    if (var1 != null) {
                        var1.onSessionReject();
                    }

                    Utilities.setSessionLoadListener((SODocSessionLoadListenerCustom) null);
                }
            });
        } else {
            NUIView var2 = this.mNUIView;
            if (var2 != null) {
                var2.endDocSession(true);
            }

            this.a(var1);
            this.a(var1, true);
        }

    }

    public void onPause() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.onPause();
            this.mNUIView.releaseBitmaps();
        }

        if (Utilities.isChromebook(this)) {
            PrintHelperPdf.setPrinting(false);
        }

        super.onPause();
    }

    protected void onResume() {
        this.b = null;
        super.onResume();
        this.doResumeActions();
    }

    protected void setConfigurableButtons() {
        NUIView var1 = this.mNUIView;
        if (var1 != null) {
            var1.setConfigurableButtons();
        }

    }

    public void startActivity(Intent var1) {
        this.b = var1;
        super.startActivity(var1, (Bundle) null);
    }

    public void startActivityForResult(@NonNull Intent var1, int var2) {
        this.b = var1;
        super.startActivityForResult(var1, var2);
    }

    public void startActivityForResult(@NonNull Intent var1, int var2, Bundle var3) {
        this.b = var1;
        super.startActivityForResult(var1, var2, var3);
    }
}
