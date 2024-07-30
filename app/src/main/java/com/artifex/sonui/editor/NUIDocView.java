package com.artifex.sonui.editor;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.supportv1.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SODocSaveListener;
import com.artifex.solib.SOLib;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.j;
import com.artifex.solib.k;
import com.artifex.solib.p;
import com.artifex.sonui.editor.NUIView.OnDoneListener;
import com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener;
import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.ui.activities.ViewOfficeActivity;
import com.arrowwould.excelreader.adapter.ColorPickAdapter;
import com.arrowwould.excelreader.ads.NativeAdsAdmob;
import com.arrowwould.excelreader.customviews.BottomBtn;
import com.arrowwould.excelreader.customviews.EditBtn;
import com.arrowwould.excelreader.ui.dialog.GoPageDialog;
import com.arrowwould.excelreader.ui.dialog.LoadingDialog;
import com.arrowwould.excelreader.listener.GoPageDialogListener;
import com.arrowwould.excelreader.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NUIDocView extends FrameLayout implements OnClickListener, DocViewHost {
    public static int OVERSIZE_MARGIN;
    private static NUIDocView mNUIDocView;
    protected TextView tvPageNumber;
    protected EditBtn btnFontUp;
    protected EditBtn btnFontDown;
    protected EditBtn btnTextBold;
    protected EditBtn btnTextItalic;
    protected EditBtn btnTextUnder;
    protected EditBtn btnTextStrikeTrough;
    protected EditBtn btnTextColor;
    protected EditBtn btnTextBg;
    protected EditBtn btnAlignLeft;
    protected EditBtn btnAlignCenter;
    protected EditBtn btnAlignRight;
    protected EditBtn btnAlignJustify;
    protected EditBtn btnListBullets;
    protected EditBtn btnListNumbers;
    protected EditBtn btnIndentDecrease;
    protected EditBtn btnIndentIncrease;
    protected EditBtn btnInsertImage;
    protected EditBtn btnInsertPhoto;
    protected EditBtn btnCut;
    protected EditBtn btnCopy;
    protected EditBtn btnPaste;
    protected BottomBtn btnRotateTab;
    protected BottomBtn btnEditTab;
    protected BottomBtn btnSearchTab;
    protected BottomBtn btnThumbnailTab;
    protected AppCompatImageView btnFullScreen;
    protected ImageView btnRedo;
    protected ImageView btnUndo;
    protected ConstraintLayout headerContainer;
    protected ConstraintLayout bottomContainer;
    protected AppCompatImageView shadowBottom;
    protected ConstraintLayout toolbarSearchContainer;
    protected ConstraintLayout toolbarContainer;
    protected ConstraintLayout toolbarEditContainer;
    //Toolbar Main
    protected TextView tvTittle;
    protected ImageView btnBack;
    protected ImageView btnPrint;
    protected ImageView btnSave;
    protected ImageView btnSaveAsPdf;
    //Toolbar Search
    private SOEditText edtSearchTextInput;
    private AppCompatImageView btnClearSearchText;
    private AppCompatTextView tvCancelSearch;
    //Toolbar Edit
    protected AppCompatImageView btnCloseEdit;
    private LinearLayout llTextColorContainer;
    private LinearLayout llBgColorContainer;
    private AppCompatImageView btnSearchNext;
    private AppCompatImageView btnSearchPrevious;
    private InputView inputView = null;
    private ConstraintLayout bottomToolBarEdit;
    private LinearLayout bottomToolBar;
    private LinearLayout searchBackForwardContainer;

    private LinearLayout undoForwardContainer;
    protected int mPageCount;
    protected PageAdapter mAdapter;
    protected boolean mFinished = false;
    private int RS = 0;
    private final SOBitmap[] S = new SOBitmap[]{null, null};
    private boolean T = false;
    private String U = null;
    private int V = -1;
    private boolean W = false;
    OnDoneListener mDoneListener = null;
    private boolean aa = false;
    private int pageNumber = 0;
    private k ah;
    private final ArrayList<String> ai = new ArrayList<>();

    private View mView = null;
    private int mKeyboardHeight = 0;
    private int an = 0;
    private boolean ao = false;
    private boolean ap = false;
    private ProgressDialog progressDialog = null;
    private Runnable mRunnable = null;
    private boolean au = false;
    //    private Toast mToast;
    private boolean aw = false;
    private boolean c = false;
    private Boolean e;
    private SOFileState mSOFileState;
    private SOFileDatabase mSoFileDatabase;
    private LoadingDialog mProgressDialog;
    private DocView mDocView;
    private DocListPagesView mDocListPageViews;
    private String k;
    protected boolean keyboardShown = false;
    private String l;
    private p m = null;
    protected ConfigOptions mConfigOptions = null;
    protected boolean mIsSession = false;
    protected SODocSession mSession;
    protected Uri mStartUri = null;
    protected SOFileState mState = null;
    private SODataLeakHandlers n;

    public NUIDocView(Context context) {
        super(context);
        this.init(context);
    }

    public NUIDocView(Context context, AttributeSet var2) {
        super(context, var2);
        this.init(context);
    }

    public NUIDocView(Context context, AttributeSet var2, int var3) {
        super(context, var2, var3);
        this.init(context);
    }

    private void a() {
        int height = this.mView.getHeight();
        int var2 = height * 15 / 100;
        Rect var3 = new Rect();
        this.mView.getWindowVisibleDisplayFrame(var3);
        this.mKeyboardHeight = height - var3.bottom;
        Resources var4 = this.getContext().getResources();
        height = var4.getIdentifier("config_showNavigationBar", "bool", "android");
        if (height > 0 && var4.getBoolean(height) || Utilities.isEmulator()) {
            height = var4.getIdentifier("navigation_bar_height", "dimen", "android");
            if (height > 0) {
                height = var4.getDimensionPixelSize(height);
            } else {
                height = 0;
            }
            this.mKeyboardHeight -= height;
        }
        if (this.mKeyboardHeight >= var2) {
            if (!this.keyboardShown) {
                this.onShowKeyboard(true);
            }
        } else {
            this.mKeyboardHeight = 0;
            if (this.keyboardShown) {
                this.onShowKeyboard(false);
            }
        }
    }

    private void init(Context context) {
        this.mView = ((Activity) this.getContext()).getWindow().getDecorView();
        com.artifex.solib.a.a(context);
        this.V = context.getResources().getConfiguration().keyboard;
        this.mConfigOptions = ConfigOptions.a();
    }

    public void forceReload() {
        this.au = true;
    }

    private void setEnableButton(View view, boolean var2) {
        view.setEnabled(var2);
        if (view instanceof ViewGroup) {
            ViewGroup var5 = (ViewGroup) view;
            for (int var3 = 0; var3 < var5.getChildCount(); ++var3) {
                View var4 = var5.getChildAt(var3);
                if (var4 != this.btnBack) {
                    this.setEnableButton(var4, var2);
                }
            }
        }
    }

    private void a(String var1) {
        this.ah = com.artifex.solib.k.a(this.activity(), var1);
        Point var4 = Utilities.getRealScreenSize(this.activity());
        int var2 = Math.max(var4.x, var4.y);
        int var3 = var2 * 120 / 100;
        OVERSIZE_MARGIN = (var3 - var2) / 2;
        var2 = 0;
        while (true) {
            SOBitmap[] var5 = this.S;
            if (var2 >= var5.length) {
                return;
            }

            var5[var2] = this.ah.a(var3, var3);
            ++var2;
        }
    }

    private void a(final boolean var1) {
        if (this.n != null) {
            this.preSaveQuestion(new Runnable() {
                public void run() {
                    String var1x;
                    try {
                        var1x = NUIDocView.this.mSOFileState.getUserPath();
                    } catch (UnsupportedOperationException var7) {
                        return;
                    }

                    String var2 = var1x;
                    if (var1x == null) {
                        try {
                            var2 = NUIDocView.this.mSOFileState.getOpenedPath();
                        } catch (UnsupportedOperationException var6) {
                            return;
                        }
                    }

                    try {
                        File var8 = new File(var2);
                        SODataLeakHandlers var9 = NUIDocView.this.n;
                        var1x = var8.getName();
                        SODoc var3 = NUIDocView.this.mSession.getDoc();
                        SOSaveAsComplete var4 = new SOSaveAsComplete() {
                            public void onComplete(int var1x, String var2) {
                                if (var1x == 0) {
                                    NUIDocView.this.setFooterText(var2);
                                    NUIDocView.this.mSOFileState.setUserPath(var2);
                                    if (var1) {
                                        NUIDocView.this.prefinish();
                                    }

                                    if (NUIDocView.this.mFinished) {
                                        return;
                                    }
                                    NUIDocView.this.mSOFileState.setHasChanges(false);
                                    NUIDocView.this.onSelectionChanged();
                                    NUIDocView.this.reloadFile();
                                } else {
                                    NUIDocView.this.mSOFileState.setUserPath(null);
                                }

                                NUIDocView.this.T = NUIDocView.this.mSOFileState.isTemplate();
                            }
                        };
                        var9.saveAsHandler(var1x, var3, var4);
                    } catch (UnsupportedOperationException var5) {
                        var5.printStackTrace();
                    }
                }
            }, new Runnable() {
                public void run() {
                }
            });
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void setDisableClick() {
        this.setEnableButton(this, false);
    }

    private void c() {
        this.c = false;
        final ViewGroup var1 = (ViewGroup) ((LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.getLayoutId(), null);
        this.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                NUIDocView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (!NUIDocView.this.c) {
                    NUIDocView.this.mConfigOptions.b();
                    NUIDocView.this.afterFirstLayoutComplete();
                    NUIDocView.this.c = true;
                }

            }
        });
        this.addView(var1);
    }

    public static NUIDocView currentNUIDocView() {
        return mNUIDocView;
    }

    private void d() {
        label66:
        {
            String var14;
            label55:
            {
                label54:
                {
                    label53:
                    {
                        label52:
                        {
                            j var1;
                            try {
                                com.artifex.solib.k.b(this.activity());
                                var1 = SOLib.e();
                            } catch (ExceptionInInitializerError var10) {
                                break label54;
                            } catch (LinkageError var11) {
                                break label53;
                            } catch (SecurityException var12) {
                                break label52;
                            } catch (Exception var13) {
                                break label66;
                            }

                            if (var1 != null) {
                                try {
                                    var1.a(this.activity());
                                    return;
                                } catch (ExceptionInInitializerError var2) {
                                    break label54;
                                } catch (LinkageError var3) {
                                    break label53;
                                } catch (SecurityException var4) {
                                    var4.printStackTrace();
                                } catch (Exception var5) {
                                    break label66;
                                }
                            } else {
                                try {
                                    throw new ClassNotFoundException();
                                } catch (ExceptionInInitializerError var6) {
                                    break label54;
                                } catch (LinkageError var7) {
                                    break label53;
                                } catch (SecurityException var8) {
                                    var8.printStackTrace();
                                } catch (ClassNotFoundException var9) {
                                    break label66;
                                }
                            }
                        }

                        var14 = String.format("initClipboardHandler() experienced unexpected exception [%s]", "SecurityException");
                        break label55;
                    }

                    var14 = String.format("initClipboardHandler() experienced unexpected exception [%s]", "LinkageError");
                    break label55;
                }

                var14 = String.format("initClipboardHandler() experienced unexpected exception [%s]", "ExceptionInInitializerError");
            }

            Log.e("NUIDocView", var14);
            return;
        }

        Log.i("NUIDocView", "initClipboardHandler implementation unavailable");
    }

    private void e() {
        String var17;
        label78:
        {
            label67:
            {
                label66:
                {
                    label65:
                    {
                        label64:
                        {
                            label63:
                            {
                                {
                                    SODataLeakHandlers var1;
                                    try {
                                        var1 = Utilities.getDataLeakHandlers();
                                        this.n = var1;
                                    } catch (ExceptionInInitializerError var12) {
                                        break label66;
                                    } catch (LinkageError var13) {
                                        break label65;
                                    } catch (SecurityException var14) {
                                        break label64;
                                    } catch (Exception var15) {
                                        break label63;
                                    }

                                    if (var1 != null) {
                                        try {
                                            var1.initDataLeakHandlers(this.activity());
                                            return;
                                        } catch (ExceptionInInitializerError var2) {
                                            break label66;
                                        } catch (LinkageError var3) {
                                            break label65;
                                        } catch (SecurityException var4) {
                                            break label64;
                                        } catch (IOException var6) {
                                            var6.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            throw new ClassNotFoundException();
                                        } catch (ExceptionInInitializerError var7) {
                                            break label66;
                                        } catch (LinkageError var8) {
                                            break label65;
                                        } catch (SecurityException var9) {
                                            break label64;
                                        } catch (ClassNotFoundException var10) {
                                            break label63;
                                        } catch (Exception var11) {
                                            var11.printStackTrace();
                                        }
                                    }
                                }

                                var17 = "DataLeakHandlers IOException";
                                break label78;
                            }

                            var17 = "DataLeakHandlers implementation unavailable";
                            break label78;
                        }

                        var17 = String.format("setDataLeakHandlers() experienced unexpected exception [%s]", "SecurityException");
                        break label67;
                    }

                    var17 = String.format("setDataLeakHandlers() experienced unexpected exception [%s]", "LinkageError");
                    break label67;
                }

                var17 = String.format("setDataLeakHandlers() experienced unexpected exception [%s]", "ExceptionInInitializerError");
            }

            Log.e("NUIDocView", var17);
            return;
        }

        Log.i("NUIDocView", var17);
    }

    private void f() {
        Point var1 = Utilities.getRealScreenSize(this.activity());
        byte var2;
        if (var1.x > var1.y) {
            var2 = 2;
        } else {
            var2 = 1;
        }

        label18:
        {
            if (!this.ao) {
                int var3 = this.an;
                if (var2 == var3 || var3 == 0) {
                    break label18;
                }
            }

            this.onOrientationChange();
        }

        this.ao = false;
        this.an = var2;
    }

    private void g() {
        this.mDocView.requestLayout();
        if (this.usePagesView() && this.isPageListVisible()) {
            this.mDocListPageViews.requestLayout();
        }

    }

    private void h() {
        Utilities.hideKeyboard(this.getContext());
    }

    private void showProgressDialog() {
        this.mProgressDialog = new LoadingDialog(this.getContext());
        Window window5 = this.mProgressDialog.getWindow();
        assert window5 != null;
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window5.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        LoadingDialog var1 = this.mProgressDialog;
        if (var1 != null) {
            var1.dismiss();
            this.mProgressDialog = null;
        }

    }

    private void k() {
        if (this.m == null) {
            this.m = new p() {
                public void a() {
                    NUIDocView.this.v();
                    Utilities.yesNoMessage((Activity) NUIDocView.this.getContext(), NUIDocView.this.getResources().getString(R.string.sodk_editor_no_more_found), NUIDocView.this.getResources().getString(R.string.sodk_editor_keep_searching), NUIDocView.this.getResources().getString(R.string.sodk_editor_str_continue), NUIDocView.this.getResources().getString(R.string.sodk_editor_stop), new Runnable() {
                        public void run() {
                            (new Handler()).post(new Runnable() {
                                public void run() {
                                    NUIDocView.this.searchText();
                                }
                            });
                        }
                    }, new Runnable() {
                        public void run() {
                        }
                    });
                }

                public void a(int var1) {
                }

                public void a(int var1, RectF var2) {
                    NUIDocView.this.v();
                    NUIDocView.this.getDocView().onFoundText(var1, var2);
                }

                public boolean b() {
                    NUIDocView.this.searchText();
                    return true;
                }

                public boolean c() {
                    NUIDocView.this.searchText();
                    return true;
                }

                public void d() {
                    NUIDocView.this.v();
                }

                public void e() {
                    NUIDocView.this.v();
                }
            };
            this.mSession.getDoc().a(this.m);
        }

        this.mSession.getDoc().c(false);
    }

    private void n() {
        if (this.btnListNumbers.isSelected()) {
            this.mSession.getDoc().B();
        } else if (this.btnListBullets.isSelected()) {
            this.mSession.getDoc().C();
        } else {
            this.mSession.getDoc().A();
        }

    }

    private boolean o() {
        int[] var1 = this.mSession.getDoc().getIndentationLevel();
        return var1 != null && var1[0] < var1[1];
    }

    private boolean p() {
        int[] var1 = this.mSession.getDoc().getIndentationLevel();
        return var1 != null && var1[0] > 0;
    }

    private void q() {
        DocView var1 = this.mDocView;
        if (var1 != null) {
            var1.releaseBitmaps();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.mDocListPageViews;
            if (var3 != null) {
                var3.releaseBitmaps();
            }
        }

        int var2 = 0;

        while (true) {
            SOBitmap[] var4 = this.S;
            if (var2 >= var4.length) {
                return;
            }

            if (var4[var2] != null) {
                var4[var2].a().recycle();
                this.S[var2] = null;
            }

            ++var2;
        }
    }

    private void s() {
        DocView var1 = this.mDocView;
        if (var1 != null) {
            var1.setBitmaps(this.S);
        }

        if (this.usePagesView()) {
            DocListPagesView var2 = this.mDocListPageViews;
            if (var2 != null) {
                var2.setBitmaps(this.S);
            }
        }

    }

    private void setFooterText(String var1) {
        if (var1 != null && !var1.isEmpty()) {
            String var2 = (new File(var1)).getName();
            if (!var2.isEmpty()) {
                this.tvTittle.setText(var2);
            } else {
                this.tvTittle.setText(var1);
            }
        }

    }

    private void setupForDocType(String var1) {
        this.a(var1);
        this.s();
    }

    private void searchText() {
        Utilities.hideKeyboard(this.getContext());
        this.u();
        String var1 = this.edtSearchTextInput.getText().toString();
        SODoc var2 = this.getDoc();
        var2.b(var1);
        var2.q();
    }

    private void u() {
        if (this.progressDialog == null) {
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    SODoc var1 = NUIDocView.this.getDoc();
                    if (var1 != null && NUIDocView.this.progressDialog == null && var1.p()) {
                        NUIDocView var3 = NUIDocView.this;
                        var3.progressDialog = new ProgressDialog(var3.getContext(), R.style.sodk_editor_alert_dialog_style);
                        ProgressDialog var4 = NUIDocView.this.progressDialog;
                        String var2 = NUIDocView.this.getResources().getString(R.string.sodk_editor_searching) +
                                "...";
                        var4.setMessage(var2);
                        NUIDocView.this.progressDialog.setCancelable(false);
                        NUIDocView.this.progressDialog.setButton(-2, NUIDocView.this.getResources().getString(R.string.sodk_editor_cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface var1, int var2) {
                                NUIDocView.this.getDoc().cancelSearch();
                            }
                        });
                        NUIDocView.this.progressDialog.show();
                    }

                }
            }, 1000L);
        }
    }

    private void v() {
        ProgressDialog var1 = this.progressDialog;
        if (var1 != null) {
            var1.dismiss();
            this.progressDialog = null;
        }

    }

    private void w() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, var1.getHeight() * 9 / 10, 400);
    }

    private void x() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, -var1.getHeight() * 9 / 10, 400);
    }

    private void y() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, var1.getHeight() / 20, 100);
    }

    private void z() {
        DocView var1 = this.getDocView();
        var1.smoothScrollBy(0, -var1.getHeight() / 20, 100);
    }

    protected Activity activity() {
        return (Activity) this.getContext();
    }

    public void addDeleteOnClose(String var1) {
        this.ai.add(var1);
    }

    protected void afterFirstLayoutComplete() {

        NativeAdsAdmob.loadNativeBanner1(activity(), null);

        this.mFinished = false;
        if (this.mConfigOptions.r()) {
            SOFileDatabase.init(this.activity());
        }
        this.createEditButtons();
        this.createEditButtons2();
        this.createInsertButtons();
        this.createRecyclerColor();

        this.btnBack = (ImageView) this.createToolbarButton(R.id.img_back);
        this.btnUndo = (ImageView) this.createToolbarButton(R.id.img_undo);
        this.btnRedo = (ImageView) this.createToolbarButton(R.id.img_redo);
        this.btnFullScreen = (AppCompatImageView) this.createToolbarButton(R.id.btn_full_screen);

        this.headerContainer = this.findViewById(R.id.header);
        this.bottomContainer = this.findViewById(R.id.bottom_container);
        this.shadowBottom = this.findViewById(R.id.shadow_bottom);

        this.btnCloseEdit = (AppCompatImageView) this.createToolbarButton(R.id.iv_exit_edit);

        this.bottomToolBar = this.findViewById(R.id.bottomToolBar);
        this.bottomToolBarEdit = this.findViewById(R.id.bottomEditToolBar);

        this.btnRotateTab = (BottomBtn) this.createToolbarButton(R.id.btnRotateTab);
        this.btnEditTab = (BottomBtn) this.createToolbarButton(R.id.btnEditTab);
        this.btnSearchTab = (BottomBtn) this.createToolbarButton(R.id.btnSearchTab);
        this.btnThumbnailTab = (BottomBtn) this.createToolbarButton(R.id.btnThumbnailTab);

        this.toolbarSearchContainer = this.findViewById(R.id.search_container);
        this.toolbarContainer = this.findViewById(R.id.toolbar_container);
        this.toolbarEditContainer = this.findViewById(R.id.cl_toolbar_edit);
        this.searchBackForwardContainer = this.findViewById(R.id.ll_search_back_forward);

        this.undoForwardContainer = this.findViewById(R.id.doUndoLayout);
        this.btnClearSearchText = (AppCompatImageView) this.createToolbarButton(R.id.iv_clear);
        this.tvCancelSearch = (AppCompatTextView) this.createToolbarButton(R.id.tv_back);


        this.btnSearchNext = (AppCompatImageView) this.createToolbarButton(R.id.btn_search_forward);
        this.btnSearchPrevious = (AppCompatImageView) this.createToolbarButton(R.id.btn_search_back);

        if (!this.mConfigOptions.c()) {
            if (this.btnUndo != null) {
                this.btnUndo.setVisibility(VISIBLE);
            }

            if (this.btnRedo != null) {
                this.btnRedo.setVisibility(VISIBLE);
            }
        }

        this.edtSearchTextInput = this.findViewById(R.id.et_search_text);
        this.tvPageNumber = this.findViewById(R.id.footer_page_text);

        this.tvPageNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoPageDialog dialog = new GoPageDialog(getContext(), new GoPageDialogListener() {
                    @Override
                    public void onPageNumber(int pageNumber) {
                        goToPage(pageNumber);
                    }
                }, getPageCount());
                Window window = dialog.getWindow();
                assert window != null;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        this.edtSearchTextInput.setOnEditorActionListener(new SOEditTextOnEditorActionListener() {
            public boolean onEditorAction(SOEditText var1, int var2, KeyEvent var3) {
                if (var2 == 5) {
                    NUIDocView.this.onSearchNext();
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.edtSearchTextInput.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View var1, int var2, KeyEvent var3) {
                return var2 == 67 && NUIDocView.this.edtSearchTextInput.getSelectionStart() == 0 && NUIDocView.this.edtSearchTextInput.getSelectionEnd() == 0;
            }
        });
        this.btnSearchNext.setEnabled(false);
        this.btnSearchPrevious.setEnabled(false);
        this.edtSearchTextInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable var1) {
            }

            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            public void onTextChanged(CharSequence charSequence, int var2, int var3, int var4) {
                if (charSequence.length() > 0) {
                    btnClearSearchText.setVisibility(View.VISIBLE);
                } else if (charSequence.length() == 0) {
                    btnClearSearchText.setVisibility(View.GONE);
                }
                NUIDocView.this.setSearchStart();
                var2 = charSequence.toString().length();
                boolean var6 = false;
                boolean var7;
                var7 = var2 > 0;

                NUIDocView.this.btnSearchNext.setEnabled(var7);
                var7 = var6;
                if (charSequence.toString().length() > 0) {
                    var7 = true;
                }

                NUIDocView.this.btnSearchPrevious.setEnabled(var7);
            }
        });
        this.edtSearchTextInput.setCustomSelectionActionModeCallback(Utilities.editFieldDlpHandler);

        this.btnPrint = (ImageView) this.createToolbarButton(R.id.btnPrint);

        this.btnSave = (ImageView) this.createToolbarButton(R.id.btnSave);
        this.btnSaveAsPdf = (ImageView) this.createToolbarButton(R.id.btnSaveAsPdf);

        this.onDeviceSizeChange();
        this.setConfigurableButtons();

        this.mAdapter = this.createAdapter();
        DocView var5 = this.createMainView(this.activity());
        this.mDocView = var5;
        var5.setHost(this);
        this.mDocView.setAdapter(this.mAdapter);
        this.mDocView.setConfigOptions(this.mConfigOptions);
        if (this.usePagesView()) {
            DocListPagesView var6 = new DocListPagesView(this.activity());
            this.mDocListPageViews = var6;
            var6.setHost(this);
            this.mDocListPageViews.setAdapter(this.mAdapter);
            this.mDocListPageViews.setMainView(this.mDocView);
            this.mDocListPageViews.setBorderColor(this.mDocView.getBorderColor());
        }


        RelativeLayout var7 = this.findViewById(R.id.doc_inner_container);
        var7.addView(this.mDocView, 0);


        this.mDocView.setup(var7);
        if (this.usePagesView()) {
            var7 = this.findViewById(R.id.pages_container);
            var7.addView(this.mDocListPageViews);
            this.mDocListPageViews.setup(var7);
            this.mDocListPageViews.setCanManipulatePages(this.canCanManipulatePages());
        }

        this.tvTittle = this.findViewById(R.id.tvTittle);

        if (this.mConfigOptions.r()) {
            this.mSoFileDatabase = SOFileDatabase.getDatabase();
        }

        final Activity var9 = this.activity();
        this.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (NUIDocView.this.mFinished) {
                    NUIDocView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    NUIDocView.this.f();
                }
            }
        });
        this.mDocView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                NUIDocView.this.mDocView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                NUIDocView var1;
                if (NUIDocView.this.mIsSession) {
                    var1 = NUIDocView.this;
                    var1.l = var1.mSession.getUserPath();
                    var1 = NUIDocView.this;
                    var1.setupForDocType(var1.l);
                    if (!NUIDocView.this.mConfigOptions.r()) {
                        throw new UnsupportedOperationException();
                    }
                    NUIDocView.this.showProgressDialog();
                    var1 = NUIDocView.this;
                    var1.mSOFileState = var1.mSoFileDatabase.stateForPath(NUIDocView.this.l, NUIDocView.this.T);
                    NUIDocView.this.mSOFileState.setForeignData(NUIDocView.this.U);
                    NUIDocView.this.mSession.setFileState(NUIDocView.this.mSOFileState);
                    NUIDocView.this.mSOFileState.openFile(NUIDocView.this.T);
                    NUIDocView.this.mSOFileState.setHasChanges(false);
                    var1 = NUIDocView.this;
                    var1.setFooterText(var1.mSOFileState.getUserPath());
                    NUIDocView.this.mDocView.setDoc(NUIDocView.this.mSession.getDoc());
                    if (NUIDocView.this.usePagesView()) {
                        NUIDocView.this.mDocListPageViews.setDoc(NUIDocView.this.mSession.getDoc());
                    }

                    NUIDocView.this.mAdapter.setDoc(NUIDocView.this.mSession.getDoc());
                    NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSessionLoadListener() {
                        public void onCancel() {
                            NUIDocView.this.dismissProgressDialog();
                        }

                        public void onDocComplete() {
                            NUIDocView.this.dismissProgressDialog();
                            NUIDocView.this.onDocCompleted();
                            NUIDocView.this.setPageNumberText();
                        }

                        public void onError(int var1, int var2) {
                            if (NUIDocView.this.mSession.isOpen()) {
                                NUIDocView.this.setDisableClick();
                                NUIDocView.this.dismissProgressDialog();
                                if (!NUIDocView.this.mSession.isCancelled() || var1 != 6) {
                                    String var3 = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), var1);
                                    Utilities.showMessage(var9, NUIDocView.this.getContext().getString(R.string.sodk_editor_error), var3);
                                }
                            }

                        }

                        public void onLayoutCompleted() {
                            NUIDocView.this.onLayoutChanged();
                        }

                        public void onPageLoad(int var1) {
                            NUIDocView.this.dismissProgressDialog();
                            NUIDocView.this.onPageLoaded(var1);
                        }

                        public void onSelectionChanged(int var1, int var2) {
                            NUIDocView.this.onSelectionMonitor();
                        }
                    });
                    if (NUIDocView.this.usePagesView()) {
                        float var2 = (float) NUIDocView.this.getResources().getInteger(R.integer.sodk_editor_pagelist_width_percentage) / 100.0F;
                        NUIDocView.this.mDocListPageViews.setScale(var2);
                    }
                } else {
                    label74:
                    {
                        if (NUIDocView.this.mState != null) {
                            if (!NUIDocView.this.mConfigOptions.r()) {
                                throw new UnsupportedOperationException();
                            }

                            var1 = NUIDocView.this;
                            var1.l = var1.mState.getOpenedPath();
//                            var1 = NUIDocView.this;
                            var1.setupForDocType(var1.l);
//                            var1 = NUIDocView.this;
                            var1.setFooterText(var1.mState.getUserPath());
                            NUIDocView.this.showProgressDialog();
//                            var1 = NUIDocView.this;
                            var1.mSOFileState = var1.mState;
                            NUIDocView.this.mSOFileState.openFile(NUIDocView.this.T);
//                            var1 = NUIDocView.this;
                            var1.mSession = new SODocSession(var9, var1.ah);
                            NUIDocView.this.mSession.setFileState(NUIDocView.this.mSOFileState);
                            NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSessionLoadListener() {
                                public void onCancel() {
                                    NUIDocView.this.dismissProgressDialog();
                                }

                                public void onDocComplete() {
                                    if (!NUIDocView.this.mFinished) {
                                        NUIDocView.this.dismissProgressDialog();
                                        NUIDocView.this.onDocCompleted();
                                    }
                                }

                                public void onError(int var1, int var2) {
                                    if (!NUIDocView.this.mFinished) {
                                        NUIDocView.this.setDisableClick();
                                        NUIDocView.this.dismissProgressDialog();
                                        if (!NUIDocView.this.mSession.isCancelled() || var1 != 6) {
                                            String var3 = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), var1);
                                            Utilities.showMessage(var9, NUIDocView.this.getContext().getString(R.string.sodk_editor_error), var3);
                                        }

                                    }
                                }

                                public void onLayoutCompleted() {
                                    NUIDocView.this.onLayoutChanged();
                                }

                                public void onPageLoad(int var1) {
                                    if (!NUIDocView.this.mFinished) {
                                        NUIDocView.this.dismissProgressDialog();
                                        NUIDocView.this.onPageLoaded(var1);
                                    }
                                }

                                public void onSelectionChanged(int var1, int var2) {
                                    NUIDocView.this.onSelectionMonitor();
                                }
                            });

                        } else {
                            Uri var6 = NUIDocView.this.mStartUri;
                            String var3 = var6.getScheme();
                            if (var3 != null && var3.equalsIgnoreCase("content")) {
                                String var7 = com.artifex.solib.a.b(NUIDocView.this.getContext(), var6);
                                if (var7.equals("---fileOpen")) {
                                    Utilities.showMessage(var9, NUIDocView.this.getContext().getString(R.string.sodk_editor_content_error), NUIDocView.this.getContext().getString(R.string.sodk_editor_error_opening_from_other_app));
                                    return;
                                }

                                if (var7.startsWith("---")) {
                                    String var4 = NUIDocView.this.getResources().getString(R.string.sodk_editor_cant_create_temp_file);
                                    var3 = NUIDocView.this.getContext().getString(R.string.sodk_editor_content_error);
                                    String var11 = NUIDocView.this.getContext().getString(R.string.sodk_editor_error_opening_from_other_app) +
                                            ": \n\n" +
                                            var4;
                                    Utilities.showMessage(var9, var3, var11);
                                    return;
                                }

                                NUIDocView.this.l = var7;
                                if (NUIDocView.this.T) {
                                    NUIDocView.this.addDeleteOnClose(var7);
                                }
                            } else {
                                NUIDocView.this.l = var6.getPath();
                                if (NUIDocView.this.l == null) {
                                    Utilities.showMessage(var9, NUIDocView.this.getContext().getString(R.string.sodk_editor_invalid_file_name), NUIDocView.this.getContext().getString(R.string.sodk_editor_error_opening_from_other_app));
                                    String var8 = " Uri has no path: " +
                                            var6;
                                    Log.e("NUIDocView", var8);
                                    return;
                                }
                            }

                            var1 = NUIDocView.this;
                            var1.setupForDocType(var1.l);
//                            var1 = NUIDocView.this;
                            var1.setFooterText(var1.l);
                            NUIDocView.this.showProgressDialog();
                            NUIDocView var9x;
                            SOFileState var10;
                            if (NUIDocView.this.mConfigOptions.r()) {
                                var9x = NUIDocView.this;
                                var10 = var9x.mSoFileDatabase.stateForPath(NUIDocView.this.l, NUIDocView.this.T);
                            } else {
                                var9x = NUIDocView.this;
                                var10 = new SOFileStateDummy(var9x.l);
                            }

                            var9x.mSOFileState = var10;
                            NUIDocView.this.mSOFileState.openFile(NUIDocView.this.T);
                            NUIDocView.this.mSOFileState.setHasChanges(false);
//                            var1 = NUIDocView.this;
                            var1.mSession = new SODocSession(var9, var1.ah);
                            NUIDocView.this.mSession.setFileState(NUIDocView.this.mSOFileState);
                            NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSessionLoadListener() {
                                public void onCancel() {
                                    NUIDocView.this.setDisableClick();
                                    NUIDocView.this.dismissProgressDialog();
                                }

                                public void onDocComplete() {
                                    if (!NUIDocView.this.mFinished) {
                                        NUIDocView.this.dismissProgressDialog();
                                        NUIDocView.this.onDocCompleted();
                                    }
                                }

                                public void onError(int var1, int var2) {
                                    if (!NUIDocView.this.mFinished) {
                                        NUIDocView.this.setDisableClick();
                                        NUIDocView.this.dismissProgressDialog();
                                        if (!NUIDocView.this.mSession.isCancelled() || var1 != 6) {
                                            String var3 = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), var1);
                                            Utilities.showMessage(var9, NUIDocView.this.getContext().getString(R.string.sodk_editor_error), var3);
                                        }

                                    }
                                }

                                public void onLayoutCompleted() {
                                    NUIDocView.this.onLayoutChanged();
                                }

                                public void onPageLoad(int var1) {
                                    if (!NUIDocView.this.mFinished) {
                                        NUIDocView.this.dismissProgressDialog();
                                        NUIDocView.this.onPageLoaded(var1);
                                    }
                                }

                                public void onSelectionChanged(int var1, int var2) {
                                    NUIDocView.this.onSelectionMonitor();
                                }
                            });

                        }
                        NUIDocView.this.mSession.open(NUIDocView.this.mSOFileState.getInternalPath());
                        NUIDocView.this.mDocView.setDoc(NUIDocView.this.mSession.getDoc());
                        if (NUIDocView.this.usePagesView()) {
                            NUIDocView.this.mDocListPageViews.setDoc(NUIDocView.this.mSession.getDoc());
                        }
                        NUIDocView.this.mAdapter.setDoc(NUIDocView.this.mSession.getDoc());
                        if (!NUIDocView.this.usePagesView()) {
                            break label74;
                        }

                        NUIDocView.this.mDocListPageViews.setScale(0.2F);
                    }
                }

                NUIDocView.this.createInputView();
            }
        });
        if (Utilities.isPhoneDevice(this.activity())) {
            this.scaleHeader();
        }

    }

    protected void createRecyclerColor() {
        this.llTextColorContainer = this.findViewById(R.id.text_color_recyclerView);
        this.llBgColorContainer = this.findViewById(R.id.bg_color_recyclerView);

        RecyclerView recyclerBgColor = this.findViewById(R.id.recyclerBgColor);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBgColor.setLayoutManager(layoutManager1);
        recyclerBgColor.setHasFixedSize(true);

        ColorPickAdapter adapterBgColor = new ColorPickAdapter(GlobalConstant.getColorBgList(), 0, new ColorPickAdapter.ColorChangedListener() {
            @Override
            public void onColorChanged(String var1) {
                if (var1.equals("transparent")) {
                    NUIDocView.this.mSession.getDoc().setSelectionBackgroundTransparent();
                } else {
                    NUIDocView.this.mSession.getDoc().setSelectionBackgroundColor(var1);
                }
                Utils.showHideView(getContext(), llBgColorContainer, false, R.dimen.cm_dp_44);
                btnTextBg.setChoose(false);

            }
        });
        recyclerBgColor.setAdapter(adapterBgColor);


        RecyclerView recyclerTextColor = this.findViewById(R.id.recyclerColor);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTextColor.setLayoutManager(layoutManager2);
        recyclerTextColor.setHasFixedSize(true);
        ColorPickAdapter adapterTextColor = new ColorPickAdapter(GlobalConstant.getColorTextList(), 0, new ColorPickAdapter.ColorChangedListener() {
            @Override
            public void onColorChanged(String var1) {
                NUIDocView.this.mSession.getDoc().setSelectionFontColor(var1);
                Utils.showHideView(getContext(), llTextColorContainer, false, R.dimen.cm_dp_44);
                btnTextColor.setChoose(false);

            }
        });
        recyclerTextColor.setAdapter(adapterTextColor);
    }

    public boolean canCanManipulatePages() {
        return false;
    }

    @Override
    public void clickSheetButton(int var1, boolean var2) {
    }

    protected PageAdapter createAdapter() {
        return new PageAdapter(this.getContext(), this, 1);
    }

    protected void createEditButtons() {
        this.btnFontUp = (EditBtn) this.createToolbarButton(R.id.btn_text_size_up);
        this.btnFontDown = (EditBtn) this.createToolbarButton(R.id.btn_text_size_down);
        this.btnTextColor = (EditBtn) this.createToolbarButton(R.id.btn_text_color);
        this.btnTextBg = (EditBtn) this.createToolbarButton(R.id.btn_text_bg);

        this.btnCut = (EditBtn) this.createToolbarButton(R.id.btn_cut);
        this.btnCopy = (EditBtn) this.createToolbarButton(R.id.btn_copy);
        this.btnPaste = (EditBtn) this.createToolbarButton(R.id.btn_paste);

        this.btnTextBold = (EditBtn) this.createToolbarButton(R.id.btn_text_bold);

        this.btnTextItalic = (EditBtn) this.createToolbarButton(R.id.btn_text_italic);
        this.btnTextUnder = (EditBtn) this.createToolbarButton(R.id.btn_text_under);
        this.btnTextStrikeTrough = (EditBtn) this.createToolbarButton(R.id.btn_striketrough);
    }

    protected void createEditButtons2() {
        this.btnListBullets = (EditBtn) this.createToolbarButton(R.id.btn_list_bullets);
        this.btnListNumbers = (EditBtn) this.createToolbarButton(R.id.btn_list_numbers);
        this.btnAlignLeft = (EditBtn) this.createToolbarButton(R.id.btn_align_left);
        this.btnAlignCenter = (EditBtn) this.createToolbarButton(R.id.btn_align_center);
        this.btnAlignRight = (EditBtn) this.createToolbarButton(R.id.btn_align_right);
        this.btnAlignJustify = (EditBtn) this.createToolbarButton(R.id.btn_align_justify);
        this.btnIndentIncrease = (EditBtn) this.createToolbarButton(R.id.btn_indent_increase);
        this.btnIndentDecrease = (EditBtn) this.createToolbarButton(R.id.btn_indent_decrease);
    }

    protected void createInputView() {
        RelativeLayout var1 = this.findViewById(R.id.doc_inner_container);
        InputView var2 = new InputView(this.getContext(), this.mSession.getDoc(), this);
        this.inputView = var2;
        var1.addView(var2);
    }

    protected boolean isRedactionMode() {
        return false;
    }

    protected void createInsertButtons() {
        this.btnInsertImage = (EditBtn) this.createToolbarButton(R.id.btn_insert_image);
        this.btnInsertPhoto = (EditBtn) this.createToolbarButton(R.id.btn_insert_photo);

    }

    protected DocView createMainView(Activity var1) {
        return new DocView(var1);
    }


    protected View createToolbarButton(int idView) {
        View mView = this.findViewById(idView);
        if (mView != null) {
            mView.setOnClickListener(this);
        }

        return mView;
    }

    protected void defocusInputView() {
        InputView var1 = this.inputView;
        if (var1 != null) {
            var1.clearFocus();
        }

    }

    public void doArrowKey(KeyEvent var1) {
        boolean var2 = var1.isShiftPressed();
        boolean var3 = var1.isAltPressed();
        switch (var1.getKeyCode()) {
            case 19:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(2);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(12);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(6);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(14);
                }

                this.onTyping();
                return;
            case 20:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(3);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(13);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(7);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(15);
                }

                this.onTyping();
                return;
            case 21:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(0);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(8);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(4);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(10);
                }

                this.onTyping();
                return;
            case 22:
                if (!var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(1);
                }

                if (var2 && !var3) {
                    this.mSession.getDoc().processKeyCommand(9);
                }

                if (!var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(5);
                }

                if (var2 && var3) {
                    this.mSession.getDoc().processKeyCommand(11);
                }

                this.onTyping();
                return;
            default:
        }
    }

    public void doBold() {
        SODoc var1 = this.mSession.getDoc();
        boolean var2 = !var1.getSelectionIsBold();
        this.btnTextBold.setSelected(var2);
        var1.setSelectionIsBold(var2);
    }

    public void doCopy() {
        this.mSession.getDoc().I();
    }

    public void doCut() {
        this.mSession.getDoc().H();
    }

    public void doInsertImage(String var1) {
        if (!com.artifex.solib.a.b(var1)) {
            Utilities.showMessage((Activity) this.getContext(), this.getContext().getString(R.string.sodk_editor_insert_image_gone_title), this.getContext().getString(R.string.sodk_editor_insert_image_gone_body));
        } else {
            String var2 = Utilities.preInsertImage(this.getContext(), var1);
            this.getDoc().d(var2);
            if (!var1.equalsIgnoreCase(var2)) {
                this.addDeleteOnClose(var2);
            }

        }
    }

    public void doItalic() {
        SODoc var1 = this.mSession.getDoc();
        boolean var2 = !var1.getSelectionIsItalic();
        this.btnTextItalic.setSelected(var2);
        var1.setSelectionIsItalic(var2);
    }

    public boolean doKeyDown(int var1, KeyEvent var2) {
        boolean var3 = var2.isAltPressed();
        boolean var4 = var2.isCtrlPressed();
        boolean var5 = var2.isShiftPressed();
        BaseActivity var6 = (BaseActivity) this.getContext();
        switch (var2.getKeyCode()) {
            case 4:
                if (var6.onKeyDown(var1, var2)) {
                    var6.finish();
                } else {
                    this.onBackPressed();
                }

                return true;
            case 19:
                if (!this.inputViewHasFocus()) {
                    if (!var3 && !var4) {
                        this.y();
                    } else {
                        this.w();
                    }
                    return true;
                }
            case 20:
                if (!this.inputViewHasFocus()) {
                    if (!var3 && !var4) {
                        this.z();
                    } else {
                        this.x();
                    }
                    return true;
                }
            case 21:
            case 22:
                if (this.inputViewHasFocus()) {
                    this.onTyping();
                    this.doArrowKey(var2);
                    return true;
                }
            case 31:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.doCopy();
                return true;
            case 30:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doBold();
                return true;
            case 37:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doItalic();
                return true;
            case 47:
                if (var4 || var3) {
                    this.doSave();
                    return true;
                }
                break;
            case 49:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doUnderline();
                return true;
            case 50:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doPaste();
                return true;
            case 52:
                if (!this.inputViewHasFocus() || !var4 && !var3) {
                    break;
                }

                this.onTyping();
                this.doCut();
                return true;
            case 54:
                if (!var4 && !var3) {
                    break;
                }

                this.onTyping();
                if (var5) {
                    this.doRedo();
                } else {
                    this.doUndo();
                }
                return true;
            case 62:
                if (!this.inputViewHasFocus()) {
                    if (var5) {
                        this.w();
                    } else {
                        this.x();
                    }
                    return true;
                }
                break;
            case 66:
                if (this.inputViewHasFocus()) {
                    this.W = false;
                    if ((var2.getFlags() & 2) != 0) {
                        this.onTyping();
                        return true;
                    }
                }
                break;
            case 67:
                if (this.inputViewHasFocus()) {
                    this.onTyping();
                    this.getDoc().N();
                    return true;
                }
                return false;
        }
        if (this.inputViewHasFocus()) {
            char var7 = (char) var2.getUnicodeChar();
            if (var7 != 0) {
                this.onTyping();
                String var9 = String.valueOf(var7);
                this.getDoc().setSelectionText(var9, 0, true);
            }
        }
        return true;
    }

    public void doPaste() {
        this.mSession.getDoc().a(this.getContext(), this.getTargetPageNumber());
    }

    public void doRedo() {
        int var1 = this.mSession.getDoc().getCurrentEdit();
        if (var1 < this.mSession.getDoc().getNumEdits()) {
            this.resetInputView();
            this.getDoc().clearSelection();
            this.mSession.getDoc().setCurrentEdit(var1 + 1);
        }

    }

    public void doSave() {
        if (this.T) {
            this.a(false);
        } else {
            this.preSaveQuestion(new Runnable() {
                public void run() {
                    final ProgressDialog var1 = Utilities.createAndShowWaitSpinner(NUIDocView.this.getContext());
                    NUIDocView.this.mSession.getDoc().a(NUIDocView.this.mSOFileState.getInternalPath(), new SODocSaveListener() {
                        public void onComplete(int var1x, int var2) {
                            var1.dismiss();
                            if (var1x == 0) {
                                NUIDocView.this.mSOFileState.saveFile();
                                NUIDocView.this.updateUIAppearance();
                                if (NUIDocView.this.n != null) {
                                    NUIDocView.this.n.postSaveHandler(new SOSaveAsComplete() {
                                        public void onComplete(int var1x, String var2) {
                                            NUIDocView.this.reloadFile();
                                        }
                                    });
                                }
                            } else {
                                String var3 = String.format(NUIDocView.this.activity().getString(R.string.sodk_editor_error_saving_document_code), var2);
                                Utilities.showMessage(NUIDocView.this.activity(), NUIDocView.this.activity().getString(R.string.sodk_editor_error), var3);
                            }

                        }
                    });
                }
            }, new Runnable() {
                public void run() {
                }
            });
        }
    }

//    public void doSelectAll() {
//        this.getDocView().selectTopLeft();
//        this.mSession.getDoc().processKeyCommand(6);
//        this.mSession.getDoc().processKeyCommand(15);
//    }

    public void doStrikethrough() {
        SODoc var1 = this.mSession.getDoc();
        boolean var2 = !var1.getSelectionIsLinethrough();
        this.btnTextStrikeTrough.setSelected(var2);
        var1.setSelectionIsLinethrough(var2);
    }

    public void doUnderline() {
        SODoc var1 = this.mSession.getDoc();
        boolean var2 = !var1.getSelectionIsUnderlined();
        this.btnTextUnder.setSelected(var2);
        var1.setSelectionIsUnderlined(var2);
    }

    public void doUndo() {
        int var1 = this.mSession.getDoc().getCurrentEdit();
        if (var1 > 0) {
            this.resetInputView();
            this.getDoc().clearSelection();
            this.mSession.getDoc().setCurrentEdit(var1 - 1);
        }

    }

    public boolean documentHasBeenModified() {
        SODocSession var1 = this.mSession;
        boolean var2;
        var2 = var1 != null && var1.getDoc() != null && this.mSOFileState != null && (this.mSession.getDoc().getHasBeenModified() || this.mSOFileState.hasChanges());

        return var2;
    }

    public void endDocSession(boolean var1) {
        DocView var2 = this.mDocView;
        if (var2 != null) {
            var2.finish();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.mDocListPageViews;
            if (var3 != null) {
                var3.finish();
            }
        }

        SODocSession var4 = this.mSession;
        if (var4 != null) {
            var4.endSession(var1);
        }

        this.dismissProgressDialog();
    }

    protected void focusInputView() {
        if (!ConfigOptions.a().c()) {
            this.defocusInputView();
        } else {
            InputView var1 = this.inputView;
            if (var1 != null) {
                var1.setFocus();
            }

        }
    }

    @Override
    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), R.color.sodk_editor_selected_page_border_color);
    }


    public SODoc getDoc() {
        SODocSession var1 = this.mSession;
        return var1 == null ? null : var1.getDoc();
    }


    public DocListPagesView getDocListPagesView() {
        return this.mDocListPageViews;
    }

    @Override

    public DocView getDocView() {
        return this.mDocView;
    }


    public InputView getInputView() {
        return this.inputView;
    }

    public boolean getIsComposing() {
        return this.W;
    }

    @Override
    public int getKeyboardHeight() {
        return this.mKeyboardHeight;
    }

    protected int getLayoutId() {
        return 0;
    }

    protected int getPageCount() {
        return this.mPageCount;
    }

    protected String getPageNumberText() {
        return (pageNumber + 1) + " / " + this.getPageCount();
    }

    public SODocSession getSession() {
        return this.mSession;
    }

    protected int getStartPage() {
        return this.RS;
    }


    protected int getTargetPageNumber() {
        DocPageView docPageView = this.getDocView().findPageContainingSelection();
        if (docPageView != null) {
            return docPageView.getPageNumber();
        } else {
            Rect var3 = new Rect();
            this.getDocView().getGlobalVisibleRect(var3);
            docPageView = this.getDocView().findPageViewContainingPoint((var3.left + var3.right) / 2, (var3.top + var3.bottom) / 2, true);
            int var2 = 0;
            if (docPageView != null) {
                var2 = docPageView.getPageNumber();
            }
            return var2;
        }
    }

    protected void goBack() {

        boolean headerShow = this.headerContainer !=null && this.headerContainer.getVisibility()==GONE;
        boolean bottomShow = this.bottomContainer!=null&&this.bottomContainer.getVisibility()==GONE;
        if (headerShow|| bottomShow) {
            this.onFullScreenShow();
        } else {
            this.prepareToGoBack();
            if (this.documentHasBeenModified()) {
                this.activity().runOnUiThread(new Runnable() {
                    public void run() {
                        int var2 = R.string.sodk_editor_save;
                        if (NUIDocView.this.k != null) {
                            int var3 = NUIDocView.this.getContext().getResources().getIdentifier("secure_save_upper", "string", NUIDocView.this.getContext().getPackageName());
//                            var2 = var1;
                            if (var3 != 0) {
                                var2 = var3;
                            }
                        }
                        (new Builder(NUIDocView.this.activity(), R.style.sodk_editor_alert_dialog_style)).setTitle(R.string.sodk_editor_document_has_been_modified)
                                .setMessage(R.string.sodk_editor_would_you_like_to_save_your_changes).setCancelable(false)
                                .setPositiveButton(var2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface var1, int var2) {
                                        var1.dismiss();
                                        NUIDocView.this.preSaveQuestion(new Runnable() {
                                            public void run() {
                                                if (NUIDocView.this.k != null) {
                                                    NUIDocView.this.onCustomSaveButton();
                                                } else if (NUIDocView.this.T) {
                                                    NUIDocView.this.a(true);
                                                } else {
                                                    NUIDocView.this.mSession.getDoc().a(NUIDocView.this.mSOFileState.getInternalPath(), new SODocSaveListener() {
                                                        public void onComplete(int var1, int var2) {
                                                            if (var1 == 0) {
                                                                NUIDocView.this.mSOFileState.saveFile();
                                                                if (NUIDocView.this.n != null) {
                                                                    NUIDocView.this.n.postSaveHandler(new SOSaveAsComplete() {
                                                                        public void onComplete(int var1, String var2) {
                                                                            NUIDocView.this.mSOFileState.closeFile();
                                                                            NUIDocView.this.prefinish();
                                                                        }
                                                                    });
                                                                } else {
                                                                    NUIDocView.this.mSOFileState.closeFile();
                                                                    NUIDocView.this.prefinish();
                                                                }
                                                            } else {
                                                                NUIDocView.this.mSOFileState.closeFile();
                                                                String var3 = String.format(NUIDocView.this.activity().getString(R.string.sodk_editor_error_saving_document_code), var2);
                                                                Utilities.showMessage(NUIDocView.this.activity(), NUIDocView.this.activity().getString(R.string.sodk_editor_error), var3);
                                                            }

                                                        }
                                                    });
                                                }

                                            }
                                        }, new Runnable() {
                                            public void run() {
                                            }
                                        });
                                    }
                                }).setNegativeButton(R.string.sodk_editor_discard, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface var1, int var2) {
                                        var1.dismiss();
                                        NUIDocView.this.mSOFileState.closeFile();
                                        NUIDocView.this.e = Boolean.FALSE;
                                        NUIDocView.this.prefinish();
                                    }
                                }).setNeutralButton(R.string.sodk_editor_continue_editing, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface var1, int var2) {
                                        var1.dismiss();
                                    }
                                }).create().show();
                    }
                });
            } else {
                this.e = Boolean.FALSE;
                this.prefinish();
            }
        }


    }

    public void goToPage(int var1) {
        this.goToPage(var1, false);
    }

    public void goToPage(int var1, boolean var2) {
        this.mDocView.scrollToPage(var1, var2);
        if (this.usePagesView()) {
            this.mDocListPageViews.scrollToPage(var1, var2);
        }

    }

    protected void handleStartPage() {
        int var1 = this.getStartPage();
        if (this.getStartPage() > 0 && this.getPageCount() >= this.getStartPage()) {
            this.setStartPage(0);
            this.mDocView.setStartPage(var1);
            this.pageNumber = var1 - 1;
            this.setPageNumberText();
            this.mDocView.requestLayout();
        }

    }

    protected boolean hasRedo() {
        return true;
    }

    protected boolean hasSearch() {
        return true;
    }

    protected boolean hasUndo() {
        return true;
    }

    protected void hidePages() {
        RelativeLayout var1 = this.findViewById(R.id.pages_container);
        if (var1 != null && var1.getVisibility() != GONE) {
            this.mDocView.onHidePages();
            var1.setVisibility(GONE);
        }

    }

    protected boolean inputViewHasFocus() {
        InputView var1 = this.inputView;
        return var1 != null && var1.hasFocus();
    }

    protected boolean isActivityActive() {
        return this.aa;
    }

    public boolean isFullScreen() {
        return this.mConfigOptions.A() && this.aw;
    }

    public boolean isLandscapePhone() {
        boolean var1;
        var1 = this.an == 2 && Utilities.isPhoneDevice(this.getContext());
        return var1;
    }

    public boolean isKeyboardVisible() {
        return this.keyboardShown;
    }

    public boolean isPageListVisible() {
        RelativeLayout var1 = this.findViewById(R.id.pages_container);
        return var1 != null && var1.getVisibility() == VISIBLE;
    }

    protected boolean isSearchVisible() {
        View var1 = this.findViewById(R.id.et_search_text);
        return var1 != null && var1.getVisibility() == VISIBLE && var1.isShown();
    }

    protected void layoutAfterPageLoad() {
        this.layoutNow();
    }

    @Override
    public void layoutNow() {
        DocView var1 = this.mDocView;
        if (var1 != null) {
            var1.layoutNow();
        }

        if (this.mDocListPageViews != null && this.usePagesView() && this.isPageListVisible()) {
            this.mDocListPageViews.layoutNow();
        }

    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        SODataLeakHandlers var4 = this.n;
        if (var4 != null) {
            var4.onActivityResult(var1, var2, var3);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void onAlignCenterButton() {
        this.mSession.getDoc().E();
    }

    public void onAlignJustifyButton() {
        this.mSession.getDoc().G();
    }

    public void onAlignLeftButton() {
        this.mSession.getDoc().D();
    }

    public void onAlignRightButton() {
        this.mSession.getDoc().F();
    }

    public void onBackPressed() {
        this.goBack();
    }

    protected void onShowKeyBoard(Runnable runnable) {
        boolean isKeyBoardShow;
        isKeyBoardShow = this.getKeyboardHeight() > 0;
        label29:
        {
            Utilities.showKeyboard(this.getContext());
            if (isKeyBoardShow) {
                break label29;
            }
            this.mRunnable = runnable;
            return;
        }

        runnable.run();
    }

    public void changeOrientationOnClick() {
        int currentOrientation = getResources().getConfiguration().orientation;

        if (currentOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            this.activity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            this.activity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    public void setEnableBottomBtn(boolean enable) {
        this.btnRotateTab.setEnable(enable);
        this.btnEditTab.setEnable(enable);
        this.btnSearchTab.setEnable(enable);
        this.btnThumbnailTab.setEnable(enable);
    }

    @Override
    public void onClick(View var1) {


        if (var1 != null) {
            if (var1 == this.btnClearSearchText) {
                NUIDocView.this.edtSearchTextInput.setText("");
                onSelectionChanged();
                hidePages();
                layoutNow();
            }
            if (var1 == this.btnFullScreen) {
                if (headerContainer.getVisibility() == VISIBLE) {
                    this.onFullScreenHide();
                } else {
                    this.onFullScreenShow();
                }
            }
            if (var1 == this.btnThumbnailTab) {
                onSelectionChanged();

                RelativeLayout var10 = this.findViewById(R.id.pages_container);
                if (var10 != null && var10.getVisibility() != VISIBLE) {
                    final int var2 = this.mDocView.getMostVisiblePage();
                    this.mDocView.onShowPages();
                    var10.setVisibility(VISIBLE);
                    final ViewTreeObserver var3 = this.mDocView.getViewTreeObserver();
                    var3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            var3.removeOnGlobalLayoutListener(this);
                            NUIDocView.this.mDocListPageViews.setCurrentPage(var2);
                            NUIDocView.this.mDocListPageViews.scrollToPage(var2, false);
                            NUIDocView.this.mDocListPageViews.fitToColumns();
                            NUIDocView.this.mDocListPageViews.layoutNow();
                        }
                    });
                } else if (var10 != null && var10.getVisibility() != GONE) {
                    this.mDocView.onHidePages();
                    var10.setVisibility(GONE);

                }
                layoutNow();
            }
            if (var1 == this.btnEditTab) {
                activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NUIDocView.this.h();

                        onSelectionChanged();
                        bottomToolBar.setClickable(false);
                        bottomToolBar.setFocusable(false);
                        NUIDocView.this.setEnableBottomBtn(false);

//                        Utils.showHideView(getContext(), clBottomEditToolBar, true, R.dimen.cm_dp_215);
                        bottomToolBarEdit.setVisibility(VISIBLE);
                        toolbarSearchContainer.setVisibility(GONE);
                        toolbarContainer.setVisibility(GONE);
                        toolbarEditContainer.setVisibility(VISIBLE);
                        layoutNow();
                    }
                });

            }


            if (var1 == this.btnSearchTab) {
                this.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onSelectionChanged();

                        hidePages();
                        Utilities.showKeyboard(NUIDocView.this.getContext());
                        NUIDocView.this.searchBackForwardContainer.setVisibility(VISIBLE);
                        NUIDocView.this.undoForwardContainer.setVisibility(GONE);
                        NUIDocView.this.toolbarContainer.setVisibility(GONE);
                        NUIDocView.this.toolbarSearchContainer.setVisibility(VISIBLE);
                        NUIDocView.this.edtSearchTextInput.getText().clear();
                        NUIDocView.this.edtSearchTextInput.requestFocus();
                        layoutNow();
                    }
                });
            }
            if (var1 == this.btnRotateTab) {
                NUIDocView.this.h();
                this.changeOrientationOnClick();
                onSelectionChanged();
                hidePages();
                layoutNow();
            }
            if (var1 == this.tvCancelSearch) {
                this.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NUIDocView.this.h();

                        NUIDocView.this.toolbarContainer.setVisibility(VISIBLE);
                        NUIDocView.this.toolbarSearchContainer.setVisibility(GONE);
                        NUIDocView.this.searchBackForwardContainer.setVisibility(GONE);
                        NUIDocView.this.undoForwardContainer.setVisibility(VISIBLE);
                    }
                });
            }
            if (var1 == this.btnCloseEdit) {
                activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NUIDocView.this.toolbarContainer.setVisibility(VISIBLE);
                        bottomToolBar.setVisibility(VISIBLE);
                        bottomToolBar.setFocusable(true);
                        bottomToolBar.setClickable(true);
                        bottomToolBarEdit.setVisibility(GONE);
                        NUIDocView.this.setEnableBottomBtn(true);
                        toolbarSearchContainer.setVisibility(GONE);
                        toolbarEditContainer.setVisibility(GONE);
                    }
                });
            }

            if (var1 == this.btnSave) {
                this.onSaveButton();
            }


            if (var1 == this.btnSaveAsPdf) {
                this.onSavePDFButton();
            }

            if (var1 == this.btnPrint) {
                this.onPrintButton();
            }


            if (var1 == this.btnFontUp) {
                this.onFontUpButton();
            }

            if (var1 == this.btnFontDown) {
                this.onFontDownButton();
            }

            if (var1 == this.btnTextColor) {
                if (llTextColorContainer.getVisibility() == View.VISIBLE) {
                    Utils.showHideView(getContext(), llTextColorContainer, false, R.dimen.cm_dp_44);
                    btnTextColor.setChoose(false);
                    btnTextBg.setEnable(true);

                } else {
                    Utils.showHideView(getContext(), llTextColorContainer, true, R.dimen.cm_dp_44);
                    btnTextColor.setChoose(true);
                    btnTextBg.setEnable(false);
                }

            }

            if (var1 == this.btnTextBg) {
                if (llBgColorContainer.getVisibility() == VISIBLE) {
                    Utils.showHideView(getContext(), llBgColorContainer, false, R.dimen.cm_dp_44);
                    btnTextBg.setChoose(false);

                    btnTextColor.setEnable(true);

                } else {
                    Utils.showHideView(getContext(), llBgColorContainer, true, R.dimen.cm_dp_44);
                    btnTextBg.setChoose(true);
                    btnTextColor.setEnable(false);
                }
            }

            if (var1 == this.btnCut) {
                this.onCutButton();
            }

            if (var1 == this.btnCopy) {
                this.onCopyButton();
            }

            if (var1 == this.btnPaste) {
                this.onPasteButton();
            }

            if (var1 == this.btnTextBold) {
                this.doBold();
            }

            if (var1 == this.btnTextItalic) {
                this.doItalic();
            }

            if (var1 == this.btnTextUnder) {
                this.doUnderline();
            }

            if (var1 == this.btnTextStrikeTrough) {
                this.doStrikethrough();
            }

            if (var1 == this.btnListBullets) {
                this.onListBulletsButton();
            }

            if (var1 == this.btnListNumbers) {
                this.onListNumbersButton();
            }

            if (var1 == this.btnAlignLeft) {
                this.onAlignLeftButton();
            }

            if (var1 == this.btnAlignCenter) {
                this.onAlignCenterButton();
            }

            if (var1 == this.btnAlignRight) {
                this.onAlignRightButton();
            }

            if (var1 == this.btnAlignJustify) {
                this.onAlignJustifyButton();
            }

            if (var1 == this.btnIndentIncrease) {
                this.onIndentIncreaseButton();
            }

            if (var1 == this.btnIndentDecrease) {
                this.onIndentDecreaseButton();
            }

            if (var1 == this.btnUndo) {
                this.onUndoButton(var1);

            }
            if (var1 == this.btnRedo) {
                this.onRedoButton(var1);

            }
            if (var1 == this.btnSearchNext) {
                this.onSearchNext();
            }
            if (var1 == this.btnSearchPrevious) {
                this.onSearchPrevious();
            }
            if (var1 == this.btnBack) {
                this.goBack();
            }
            if (var1 == this.btnInsertImage) {
                this.onInsertImageButton();
            }
            if (var1 == this.btnInsertPhoto) {
                this.onAddCameraClicked();
            }
        }
    }

    public void onConfigurationChange(Configuration var1) {
        if (var1 != null && var1.keyboard != this.V) {
            this.resetInputView();
            this.V = var1.keyboard;
        }

        DocView var2 = this.mDocView;
        if (var2 != null) {
            var2.onConfigurationChange();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.mDocListPageViews;
            if (var3 != null) {
                var3.onConfigurationChange();
            }
        }

    }

    public void onCopyButton() {
        this.doCopy();
    }

    public void onCustomSaveButton() {
        if (this.n != null) {
            this.preSaveQuestion(new Runnable() {
                public void run() {
                    String var1 = NUIDocView.this.mSOFileState.getUserPath();
                    String var2 = var1;
                    if (var1 == null) {
                        var2 = NUIDocView.this.mSOFileState.getOpenedPath();
                    }

                    File var8 = new File(var2);
                    NUIDocView.this.preSave();

                    try {
                        SODataLeakHandlers var10 = NUIDocView.this.n;
                        String var3 = var8.getName();
                        SODoc var9 = NUIDocView.this.mSession.getDoc();
                        String var4 = NUIDocView.this.k;
                        SOCustomSaveComplete var5 = new SOCustomSaveComplete() {
                            public void onComplete(int var1, String var2, boolean var3) {
                                NUIDocView.this.mSOFileState.setHasChanges(false);
                                if (var1 == 0) {
                                    NUIDocView.this.mSOFileState.setHasChanges(false);
                                }

                                if (var3) {
                                    NUIDocView.this.prefinish();
                                }

                            }
                        };
                        var10.customSaveHandler(var3, var9, var4, var5);
                    } catch (UnsupportedOperationException | IOException var6) {
                        var6.printStackTrace();
                    }
                }
            }, new Runnable() {
                public void run() {
                }
            });
        }

    }

    public void onCutButton() {
        this.doCut();
    }

    public void onDestroy() {
        this.q();
        for (int var1 = 0; var1 < this.ai.size(); ++var1) {
            com.artifex.solib.a.e(this.ai.get(var1));
        }

        this.ai.clear();

        SODataLeakHandlers var2 = this.n;
        if (var2 != null) {
            var2.finaliseDataLeakHandlers();
        }

    }

    protected void onDeviceSizeChange() {
        int var3;
        Resources var5;
        if (Utilities.isPhoneDevice(this.activity())) {
            this.scaleHeader();
            var5 = this.getContext().getResources();
            var3 = R.dimen.sodk_editor_after_back_button_phone;
        } else {
            var5 = this.getContext().getResources();
            var3 = R.dimen.sodk_editor_after_back_button;
        }

        var3 = (int) var5.getDimension(var3);
    }

    protected void onDocCompleted() {
        if (!this.mFinished) {
            int var1 = this.mSession.getDoc().r();
            this.mPageCount = var1;
            this.mAdapter.setCount(var1);
            this.layoutNow();
            if (com.artifex.solib.k.e(this.activity())) {
                String var2 = Utilities.getStringPreference(Utilities.getPreferencesObject(this.activity(), "general"), "DocAuthKey", Utilities.getApplicationName(this.activity()));
                this.mSession.getDoc().setAuthor(var2);
            }

        }
    }

    public void onFontDownButton() {
        double var2 = this.mSession.getDoc().getSelectionFontSize();
        if ((int) var2 > 6) {
            this.mSession.getDoc().setSelectionFontSize(var2 - 1.0D);
        }
    }

    public void onFontUpButton() {
        double var2 = this.mSession.getDoc().getSelectionFontSize();
        if ((int) var2 < 72) {
            this.mSession.getDoc().setSelectionFontSize(var2 + 1.0D);
        }
    }

    protected void onFullScreenHide() {
        this.findViewById(R.id.header).setVisibility(GONE);
        this.findViewById(R.id.bottom_container).setVisibility(GONE);
        this.findViewById(R.id.shadow_bottom).setVisibility(GONE);
        this.hidePages();
        this.layoutNow();
    }

    protected void onFullScreenShow() {
        this.findViewById(R.id.header).setVisibility(VISIBLE);
        this.findViewById(R.id.bottom_container).setVisibility(VISIBLE);
        this.findViewById(R.id.shadow_bottom).setVisibility(VISIBLE);
        this.hidePages();
        this.layoutNow();
    }

    public void onIndentDecreaseButton() {
        int[] var2 = this.mSession.getDoc().getIndentationLevel();
        if (var2 != null && var2[0] > 0) {
            this.mSession.getDoc().setIndentationLevel(var2[0] - 1);
        }

    }

    public void onIndentIncreaseButton() {
        int[] var2 = this.mSession.getDoc().getIndentationLevel();
        if (var2 != null && var2[0] < var2[1]) {
            this.mSession.getDoc().setIndentationLevel(var2[0] + 1);
        }

    }

    public void onInsertImageButton() {
        this.onHideKeyBoard(new Runnable() {
            public void run() {
                if (NUIDocView.this.n != null) {
                    try {
                        NUIDocView.this.n.insertImageHandler(NUIDocView.this);
                    } catch (UnsupportedOperationException var2) {
                        var2.printStackTrace();
                    }

                } else {
                    throw new UnsupportedOperationException();
                }
            }
        });
    }

    public void onAddCameraClicked() {
        String[] permissions = {Manifest.permission.CAMERA};
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(activity(), permissionsToRequest.toArray(new String[0]), ViewOfficeActivity.REQUEST_CAMERA_PERMISSIONS);
        } else {
            onInsertPhotoButton();
        }
    }

    public void onInsertPhotoButton() {
        this.onHideKeyBoard(new Runnable() {
            public void run() {
                if (NUIDocView.this.n != null) {
                    try {
                        NUIDocView.this.n.insertPhotoHandler(NUIDocView.this);
                    } catch (UnsupportedOperationException var2) {
                        var2.printStackTrace();
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        });
    }

    public boolean onKeyPreIme(int var1, KeyEvent var2) {
        BaseActivity var3 = (BaseActivity) this.getContext();
        if (!var3.isSlideShow()) {
            View var4 = var3.getCurrentFocus();
            if (var4 != null && var2.getKeyCode() == 4) {
                var4.clearFocus();
                Utilities.hideKeyboard(this.getContext());
                return true;
            }
        }

        return super.onKeyPreIme(var1, var2);
    }


    public void onLayoutChanged() {
        SODocSession var1 = this.mSession;
        if (var1 != null && var1.getDoc() != null && !this.mFinished) {
            this.mDocView.onLayoutChanged();
        }

    }

    public void onListBulletsButton() {
        EditBtn var2;
        if (this.btnListBullets.isSelected()) {
            var2 = this.btnListBullets;
        } else {
            this.btnListBullets.setSelected(true);
            var2 = this.btnListNumbers;

        }

        var2.setSelected(false);
        this.n();
    }

    public void onListNumbersButton() {
        EditBtn var2;
        if (this.btnListNumbers.isSelected()) {
            var2 = this.btnListNumbers;
        } else {
            this.btnListNumbers.setSelected(true);
            var2 = this.btnListBullets;
        }

        var2.setSelected(false);
        this.n();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.a();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onOrientationChange() {
        this.mDocView.onOrientationChange();
        if (this.usePagesView()) {
            this.mDocListPageViews.onOrientationChange();
        }

        if (!this.isFullScreen()) {
            this.showUI(!this.keyboardShown);
        }

        this.onDeviceSizeChange();
    }

    protected void onPageLoaded(int var1) {
        int var2 = this.mPageCount;
        boolean var3 = false;
        boolean var7;
        var7 = var2 == 0;

        this.mPageCount = var1;
        if (var7) {
            this.k();
            this.updateUIAppearance();
        }

        var2 = this.mAdapter.getCount();
        int var5 = this.mPageCount;
        boolean var6 = var3;
        if (var5 != var2) {
            var6 = true;
        }

        if (var5 < var2) {
            this.mDocView.removeAllViewsInLayout();
            if (this.usePagesView()) {
                this.mDocListPageViews.removeAllViewsInLayout();
            }
        }

        this.mAdapter.setCount(this.mPageCount);
        if (var6) {
            final ViewTreeObserver var8 = this.getViewTreeObserver();
            var8.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    var8.removeOnGlobalLayoutListener(this);
                    if (!NUIDocView.this.mFinished) {
                        if (NUIDocView.this.mDocView.getReflowMode()) {
                            NUIDocView.this.onReflowScale();
                        } else {
                            NUIDocView.this.mDocView.scrollSelectionIntoView();
                        }

                    }
                }
            });
            this.layoutAfterPageLoad();
        } else {
            this.g();
        }

        this.handleStartPage();
        if (!this.ap) {
            this.ap = true;
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    NUIDocView.this.setPageNumberText();
                    NUIDocView.this.ap = false;
                }
            }, 1000L);
        }

    }

    public void onPasteButton() {
        this.doPaste();
    }

    public void onPause() {
        this.onPauseCommon();
        DocView var1 = this.mDocView;
        if (var1 == null || !var1.finished()) {
            if (this.mSOFileState != null) {
                var1 = this.mDocView;
                if (var1 != null && var1.getDoc() != null && this.n != null) {
                    SODoc var2 = this.mDocView.getDoc();
                    this.n.pauseHandler(var2, var2.getHasBeenModified());
                }
            }

        }
    }

    protected void onPauseCommon() {
        this.aa = false;
        this.resetInputView();
    }

    public void onPrintButton() {
        SODataLeakHandlers var3 = this.n;
        if (var3 != null) {
            try {
                var3.printHandler(this.mSession.getDoc());
            } catch (UnsupportedOperationException var2) {
                var2.printStackTrace();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }


    public void onRedoButton(View var1) {
        this.doRedo();
    }

    public void onReflowScale() {
        this.mDocView.onReflowScale();
        if (this.usePagesView()) {
            this.mDocListPageViews.onReflowScale();
        }

    }

    public void onResume() {
        this.onResumeCommon();
        this.mKeyboardHeight = 0;
        this.onShowKeyboard(false);
        SOFileState var1 = SOFileState.getAutoOpen(this.getContext());
        if (var1 != null && this.mSOFileState != null && var1.getLastAccess() > this.mSOFileState.getLastAccess()) {
            this.mSOFileState.setHasChanges(var1.hasChanges());
        }

        SOFileState.clearAutoOpen(this.getContext());
        SODataLeakHandlers var2 = this.n;
        if (var2 != null) {
            var2.doInsert();
        }

    }

    protected void onResumeCommon() {
        mNUIDocView = this;
        String var1 = this.l;
        if (var1 != null) {
            this.a(var1);
            this.s();
        }

        if (this.au) {
            this.au = false;
            this.getDoc().a(true);
            this.reloadFile();
        }

        this.aa = true;
        this.focusInputView();
        DocView var2 = this.getDocView();
        if (var2 != null) {
            var2.forceLayout();
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.getDocListPagesView();
            if (var3 != null) {
                var3.forceLayout();
            }
        }

    }

    public void onSaveButton() {
        this.preSave();
        this.doSave();
    }

    public void onSavePDFButton() {
        if (this.n != null) {
            try {
                File var3 = new File(this.mSOFileState.getOpenedPath());
                this.n.saveAsPdfHandler(var3.getName(), this.mSession.getDoc());
            } catch (UnsupportedOperationException var2) {
                var2.printStackTrace();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected void onSearch() {
        this.searchBackForwardContainer.setVisibility(VISIBLE);
        this.undoForwardContainer.setVisibility(GONE);
        this.toolbarContainer.setVisibility(GONE);
        this.toolbarSearchContainer.setVisibility(VISIBLE);
        this.edtSearchTextInput.getText().clear();
        this.edtSearchTextInput.requestFocus();
        Utilities.showKeyboard(this.getContext());
    }

    public void onSearchNext() {
        SODoc var2 = this.getDoc();
        if (var2 != null) {
            if (var2.p()) {
                return;
            }

            var2.d(false);
            this.searchText();
        }

    }

    public void onSearchPrevious() {
        SODoc var2 = this.getDoc();
        if (var2 != null) {
            if (var2.p()) {
                return;
            }

            var2.d(true);
            this.searchText();
        }

    }

    public void onSelectionChanged() {
        try {
            SODocSession var1 = this.mSession;
            if (var1 != null && var1.getDoc() != null && !this.mFinished) {
                this.mDocView.onSelectionChanged();
                if (this.usePagesView() && this.isPageListVisible()) {
                    this.mDocListPageViews.onSelectionChanged();
                }

                this.updateUIAppearance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onSelectionMonitor() {
        this.onSelectionChanged();
        if (this.getDocView().getSelectionLimits() == null) {
            this.defocusInputView();
        } else {
            this.focusInputView();
        }

    }

    @Override
    public void onShowKeyboard(final boolean var1) {
        if (this.isActivityActive()) {
            if (this.getPageCount() > 0) {
                this.keyboardShown = var1;
                this.onShowKeyboardPreventPush();
                if (!this.isFullScreen()) {
                    this.showUI(!var1);
                }

                if (this.usePagesView()) {
                    DocListPagesView var2 = this.getDocListPagesView();
                    if (var2 != null) {
                        var2.onShowKeyboard(var1);
                    }
                }

                final ViewTreeObserver var3 = this.getViewTreeObserver();
                var3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        var3.removeOnGlobalLayoutListener(this);
                        DocView var1x = NUIDocView.this.getDocView();
                        if (var1x != null) {
                            var1x.onShowKeyboard(var1);
                        }

                        if (NUIDocView.this.mRunnable != null) {
                            NUIDocView.this.mRunnable.run();
                            NUIDocView.this.mRunnable = null;
                        }

                    }
                });
            }
        }
    }

    protected void onShowKeyboardPreventPush() {
        boolean var2;
        var2 = (this.activity().getWindow().getAttributes().flags & 1024) != 0;

//        if (var2) {
//            View var3 = ((BaseActivity) this.getContext()).findViewById(16908290);
//
//        }
    }

    public void onTyping() {
        System.currentTimeMillis();
    }

    public void onUndoButton(View var1) {
        this.doUndo();
    }

    public void preSave() {
    }

    protected void preSaveQuestion(Runnable var1, Runnable var2) {
        if (var1 != null) {
            var1.run();
        }

    }

    @Override
    public void prefinish() {
        if (!this.mFinished) {
            this.mFinished = true;
            SODocSession var1 = this.mSession;
            if (var1 != null && var1.getDoc() != null) {
                this.mSession.getDoc().a((p) null);
            }

            this.m = null;
            this.dismissProgressDialog();
            SOFileState var2 = this.mSOFileState;
            if (var2 != null) {
                var2.closeFile();
            }

            Utilities.hideKeyboard(this.getContext());
            DocView var3 = this.mDocView;
            if (var3 != null) {
                var3.finish();
                this.mDocView = null;
            }

            if (this.usePagesView()) {
                DocListPagesView var4 = this.mDocListPageViews;
                if (var4 != null) {
                    var4.finish();
                    this.mDocListPageViews = null;
                }
            }

            var1 = this.mSession;
            if (var1 != null) {
                var1.abort();
            }

            SODoc var5 = this.getDoc();
            if (var5 != null) {
                var5.M();
            }

            PageAdapter var6 = this.mAdapter;
            if (var6 != null) {
                var6.setDoc(null);
            }

            this.mAdapter = null;
            Boolean var7 = this.e;
            if (var7 != null) {
                this.endDocSession(var7);
                this.e = null;
            }

            if (this.mSession != null) {
                final ProgressDialog progressDialog1 = new ProgressDialog(this.getContext(), R.style.sodk_editor_alert_dialog_style);
                progressDialog1.setMessage(this.getContext().getString(R.string.sodk_editor_wait));
                progressDialog1.setCancelable(false);
                progressDialog1.setIndeterminate(true);
                progressDialog1.getWindow().clearFlags(2);
                progressDialog1.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface var1) {
                        (new Handler()).post(new Runnable() {
                            public void run() {
                                if (NUIDocView.this.mSession != null) {
                                    NUIDocView.this.mSession.destroy();
                                }
                                progressDialog1.dismiss();
                                if (NUIDocView.this.mDoneListener != null) {
                                    NUIDocView.this.mDoneListener.done();
                                }

                            }
                        });
                    }
                });
                progressDialog1.show();
            } else {
                OnDoneListener var9 = this.mDoneListener;
                if (var9 != null) {
                    var9.done();
                }
            }

        }
    }

    protected void prepareToGoBack() {
    }

    public void releaseBitmaps() {
        this.setValid(false);
        this.q();
        this.s();
    }

    private void setValid(boolean var1) {
        DocView var2 = this.mDocView;
        if (var2 != null) {
            var2.setValid(var1);
        }

        if (this.usePagesView()) {
            DocListPagesView var3 = this.mDocListPageViews;
            if (var3 != null) {
                var3.setValid(var1);
            }
        }

    }

    public void reloadFile() {
    }

    protected void resetInputView() {
        InputView var1 = this.inputView;
        if (var1 != null) {
            var1.resetEditable();
        }

    }

    protected void scaleHeader() {
    }

    protected void scaleToolbar() {
    }

    @Override
    public void selectionupdated() {
        this.onSelectionChanged();
    }

    public void setConfigurableButtons() {
        if (this.btnSave != null) {
            if (this.mConfigOptions.p()) {
                this.btnSave.setVisibility(VISIBLE);
            } else {
                this.btnSave.setVisibility(GONE);
            }
        }
        if (this.shouldConfigureSaveAsPDFButton() && this.btnSaveAsPdf != null) {
            if (this.mConfigOptions.e()) {
                this.btnSaveAsPdf.setVisibility(VISIBLE);
            } else {
                this.btnSaveAsPdf.setVisibility(GONE);
            }
        }
        if (this.btnPrint != null) {
            if (!this.mConfigOptions.m() && !this.mConfigOptions.n()) {
                this.btnPrint.setVisibility(GONE);
            } else {
                this.btnPrint.setVisibility(VISIBLE);
            }
        }
        if (!this.mConfigOptions.c()) {
            if (this.btnUndo != null) {
                this.btnUndo.setVisibility(GONE);
            }

            if (this.btnRedo != null) {
                this.btnRedo.setVisibility(GONE);
            }
        }

        if (this.btnInsertImage != null) {
            if (this.mConfigOptions.k()) {
                this.btnInsertImage.setVisibility(VISIBLE);
            } else {
                this.btnInsertImage.setVisibility(GONE);
            }
        }
        if (this.btnInsertPhoto != null) {
            if (this.mConfigOptions.l()) {
                this.btnInsertPhoto.setVisibility(VISIBLE);
            } else {
                this.btnInsertPhoto.setVisibility(GONE);
            }
        }


    }

    @Override
    public void setCurrentPage(int var1) {
        if (this.usePagesView()) {
            this.mDocListPageViews.setCurrentPage(var1);
            this.mDocListPageViews.scrollToPage(var1, false);
        }

        this.pageNumber = var1;
        this.setPageNumberText();
        this.mSession.getFileState().setPageNumber(this.pageNumber);
    }

    public void setIsComposing(boolean var1) {
        this.W = var1;
    }

    protected void setPageCount(int var1) {
        this.mPageCount = var1;
        this.mAdapter.setCount(var1);
        this.g();
    }

    protected void setPageNumberText() {
        (new Handler()).post(new Runnable() {
            public void run() {
                NUIDocView.this.tvPageNumber.setText(NUIDocView.this.getPageNumberText());
                NUIDocView.this.tvPageNumber.measure(0, 0);
            }
        });
    }

    public void setSearchStart() {
    }

    protected void setStartPage(int var1) {
        this.RS = var1;
    }


    protected boolean shouldConfigureSaveAsPDFButton() {
        return true;
    }

    protected void onHideKeyBoard(Runnable runnable) {
        boolean isKeyBoardShow;
        isKeyBoardShow = this.getKeyboardHeight() > 0;
        label29:
        {
            Utilities.hideKeyboard(this.getContext());
            if (!isKeyBoardShow) {
                break label29;
            }
            this.mRunnable = runnable;
            return;
        }

        runnable.run();
    }

    @Override
    public boolean showKeyboard() {
        Utilities.showKeyboard(this.getContext());
        return true;
    }

    public void showUI(boolean var1) {

        label46:
        {
//            label45:
            {
                View mHeaderView = this.findViewById(R.id.header);
                if (this.isLandscapePhone()) {
//                    if (!this.isSearchVisible()) {
//                        mHeaderView.setVisibility(GONE);
//                        break label45;
//                    }
                } else if (mHeaderView.getVisibility() == VISIBLE) {
                    break label46;
                }
                mHeaderView.setVisibility(VISIBLE);
            }
            this.layoutNow();
        }
        if (this.mConfigOptions.A() && var1) {
            this.aw = false;
            if (this.getDocView() != null) {
                this.getDocView().onFullscreen(false);
            }
            this.layoutNow();
        }

    }

    public void start(Uri var1, boolean var2, int var3, String var4, OnDoneListener listener) {
        this.T = var2;
        this.mStartUri = var1;
        this.k = var4;
        this.mDoneListener = listener;
        this.c();
        this.e();
        this.d();
    }

    public void start(SODocSession var1, int var2, String var3, OnDoneListener var4) {
        this.mIsSession = true;
        this.mSession = var1;
        this.T = false;
        this.setStartPage(var2);
        this.mDoneListener = var4;
        this.U = var3;
        this.c();
        this.e();
        this.d();
    }

    public void start(SOFileState var1, int var2, OnDoneListener listener) {
        this.mIsSession = false;
        this.T = var1.isTemplate();
        this.mState = var1;
        this.setStartPage(var2);
        this.mDoneListener = listener;
        this.c();
        this.e();
        this.d();
    }

    public void triggerOrientationChange() {
        this.ao = true;
    }

    public void triggerRender() {
        DocView var1 = this.mDocView;
        if (var1 != null) {
            var1.triggerRender();
        }

        if (this.mDocListPageViews != null && this.usePagesView() && this.isPageListVisible()) {
            this.mDocListPageViews.triggerRender();
        }

    }

    protected void updateEditUIAppearance() {
        SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
        boolean var2 = true;
        boolean var3;
        boolean var5;
        boolean var6;
        if (var1 != null) {
            var3 = var1.getIsActive();
            boolean var4;
            var4 = var3 && !var1.getIsCaret();

            var5 = var3;
            var6 = var4;
            if (var3) {
                var1.getIsCaret();
                var5 = true;
                var6 = var4;
            }
        } else {
            var5 = false;
            var6 = false;
        }

        SODoc var9 = this.mSession.getDoc();
        var3 = var6 && !var9.selectionIsAutoshapeOrImage();

        this.btnTextBold.setEnable(var3);
        EditBtn var7 = this.btnTextBold;
        boolean var8;
        var8 = var3 && var9.getSelectionIsBold();

        var7.setSelected(var8);
        this.btnTextItalic.setEnable(var3);
        var7 = this.btnTextItalic;
        var8 = var3 && var9.getSelectionIsItalic();

        var7.setSelected(var8);
        this.btnTextUnder.setEnable(var3);
        var7 = this.btnTextUnder;
        var8 = var3 && var9.getSelectionIsUnderlined();

        var7.setSelected(var8);
        this.btnTextStrikeTrough.setEnable(var3);
        var7 = this.btnTextStrikeTrough;
        var3 = var3 && var9.getSelectionIsLinethrough();

        var7.setSelected(var3);
        this.btnAlignLeft.setEnable(var5);
        var7 = this.btnAlignLeft;
        var3 = var5 && var9.getSelectionIsAlignLeft();

        var7.setSelected(var3);
        this.btnAlignCenter.setEnable(var5);
        var7 = this.btnAlignCenter;
        var3 = var5 && var9.getSelectionIsAlignCenter();

        var7.setSelected(var3);
        this.btnAlignRight.setEnable(var5);
        var7 = this.btnAlignRight;
        var3 = var5 && var9.getSelectionIsAlignRight();

        var7.setSelected(var3);
        this.btnAlignJustify.setEnable(var5);
        var7 = this.btnAlignJustify;
        var3 = var5 && var9.getSelectionIsAlignJustify();

        var7.setSelected(var3);
        this.btnListBullets.setEnable(var5);
        var7 = this.btnListBullets;
        var3 = var5 && var9.getSelectionListStyleIsDisc();

        var7.setSelected(var3);
        this.btnListNumbers.setEnable(var5);
        var7 = this.btnListNumbers;
        var3 = var5 && var9.getSelectionListStyleIsDecimal();

        var7.setSelected(var3);
        EditBtn var10 = this.btnIndentIncrease;
        var3 = var5 && this.o();

        var10.setEnable(var3);
        var10 = this.btnIndentDecrease;
        if (var5 && this.p()) {
            var5 = var2;
        } else {
            var5 = false;
        }

        var10.setEnable(var5);
    }

    protected void updateInsertUIAppearance() {
        SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
        boolean var2;
        var2 = var1 != null && var1.getIsActive() && var1.getIsCaret();

        if (this.btnInsertImage != null && this.mConfigOptions.k()) {
            this.btnInsertImage.setEnable(var2);
        }

        if (this.btnInsertPhoto != null && this.mConfigOptions.l()) {
            this.btnInsertPhoto.setEnable(var2);
        }

    }

    protected void updateReviewUIAppearance() {
    }

    protected void updateSaveUIAppearance() {
        if (this.btnSave != null) {
            boolean var1 = this.documentHasBeenModified();
            boolean var2 = this.mConfigOptions.c();
            boolean var3 = false;
            if (!var2) {
                var1 = false;
            }
            if (this.T) {
                var1 = var3;
            }
        }

    }

    protected void updateUIAppearance() {
        this.updateSaveUIAppearance();
        SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
        boolean var2 = true;
        boolean var3;
        boolean var4;
        boolean var5;
        if (var1 != null) {
            var3 = var1.getIsActive();
            var4 = var3 && !var1.getIsCaret();

            var5 = var3 && var1.getIsCaret();
        } else {
            var5 = false;
            var4 = false;
        }
        if (this.mConfigOptions.c()) {
            this.updateEditUIAppearance();
            this.updateUndoUIAppearance();
            boolean var6 = this.getDoc().selectionIsAutoshapeOrImage();
            var3 = var4 && !var6;
            long var7 = Math.round(this.mSession.getDoc().getSelectionFontSize());
            EditBtn var12;
            if (var3) {
                var12 = this.btnFontUp;
                boolean var10;
                var10 = var7 < 72L;

                var12.setEnable(var10);
                var12 = this.btnFontDown;
                var10 = var7 > 6L;

                var12.setEnable(var10);
            } else {
                this.btnFontUp.setEnable(false);
                this.btnFontDown.setEnable(false);
            }
            this.btnTextColor.setEnable(var3);
            this.btnTextBg.setEnable(var3);
            var3 = var4 && this.mSession.getDoc().getSelectionCanBeDeleted();
            this.btnCut.setEnable(var3);
            var3 = var4 && this.mSession.getDoc().getSelectionCanBeCopied();
            this.btnCopy.setEnable(var3);
            if (!var6 && (var5 || var4) && this.mSession.getDoc().J()) {
                var3 = var2;
            } else {
                var3 = false;
            }

            this.btnPaste.setEnable(var3);
            this.updateInsertUIAppearance();
        }
    }

    protected void updateUndoUIAppearance() {
        SODocSession var1 = this.mSession;
        if (var1 != null && var1.getDoc() != null) {
            int var2 = this.mSession.getDoc().getCurrentEdit();
            int var3 = this.mSession.getDoc().getNumEdits();
            boolean var4 = false;
            boolean var5;
            var5 = var2 > 0;

            this.setEnableButton(this.btnUndo, var5);
            var5 = var4;
            if (var2 < var3) {
                var5 = true;
            }

            this.setEnableButton(this.btnRedo, var5);
        }

    }

    protected boolean usePagesView() {
        return true;
    }

    public boolean wasTyping() {
        boolean var1;
        long ar = 0L;
        var1 = System.currentTimeMillis() - ar < 500L;

        return var1;
    }

    protected class TabData {
        public int contentId;
        public int layoutId;
        public String name;
        public int visibility;

        public TabData(String var2, int var3, int var4, int var5) {
            this.name = var2;
            this.contentId = var3;
            this.layoutId = var4;
            this.visibility = var5;
        }
    }
}
