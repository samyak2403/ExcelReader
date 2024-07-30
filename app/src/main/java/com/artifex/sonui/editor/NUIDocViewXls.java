package com.artifex.sonui.editor;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.supportv1.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.artifex.solib.SODoc;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.SOSelectionTableRange;
import com.arrowwould.excelreader.customviews.EditBtn;
import com.arrowwould.excelreader.R;
public class NUIDocViewXls extends NUIDocView {
    private int E = -1;
    private boolean G = false;
    private boolean H = false;
    private ListPopupWindow I = null;
    private String J = null;
    private boolean K = false;
    //    private EditBtn btnAlignOption;
    private SOTextView btnFx;

    public NUIDocViewXls(Context var1) {
        super(var1);
        this.a(var1);
    }

    public NUIDocViewXls(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public NUIDocViewXls(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    private int a(ListAdapter var1) {
        int var2 = 0;
        int var3 = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int var4 = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int var5 = var1.getCount();
        FrameLayout var6 = null;
        View var7 = null;
        int var8 = 0;

        FrameLayout var12;
        for (int var9 = 0; var2 < var5; var6 = var12) {
            int var10 = var1.getItemViewType(var2);
            int var11 = var9;
            if (var10 != var9) {
                var7 = null;
                var11 = var10;
            }

            var12 = var6;
            if (var6 == null) {
                var12 = new FrameLayout(this.getContext());
            }

            var7 = var1.getView(var2, var7, var12);
            var7.measure(var3, var4);
            var8 += var7.getMeasuredHeight();
            ++var2;
            var9 = var11;
        }

        return var8;
    }

    private void a(int var1) {
        this.setCurrentSheet(var1);
    }

    private void a(Context context) {
    }


    private void a(final String var1) {
        ListPopupWindow var2 = this.I;
        if (var2 != null) {
            var2.dismiss();
        }

        if (this.getKeyboardHeight() > 0) {
            Utilities.hideKeyboard(this.getContext());
            final ViewTreeObserver var3 = this.getViewTreeObserver();
            var3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    var3.removeOnGlobalLayoutListener(this);
                    NUIDocViewXls.this.b(var1);
                }
            });
        } else {
            this.b(var1);
        }

    }

    private void b(String var1) {
        final ListPopupWindow var2 = new ListPopupWindow(this.activity());
        var2.setBackgroundDrawable(ContextCompat.getDrawable(this.activity(), R.drawable.sodk_editor_formula_popup));
        var2.setModal(true);
        var2.setAnchorView(this.btnFx);
        var2.setHorizontalOffset(30);
        var2.setVerticalOffset(30);
        final ChooseFormulaAdapter var3 = new ChooseFormulaAdapter(this.activity(), var1);
        var2.setAdapter(var3);
        Point var4 = Utilities.getScreenSize(this.getContext());
        var2.setContentWidth(var4.x / 2);
        int[] var5 = new int[2];
        this.btnFx.getLocationOnScreen(var5);
        var5 = Utilities.screenToWindow(var5, this.getContext());
        var2.setHeight(Math.min(var4.y - var5[1] - this.btnFx.getHeight() - 60, this.a(var3)));
        var2.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> var1, View var2x, int var3x, long var4) {
                DocExcelView var6;
                String var8;
                label11:
                {
                    var2.dismiss();
                    var6 = (DocExcelView) NUIDocViewXls.this.getDocView();
                    String var7 = var6.getEditText();
                    String var10 = var3.getItem(var3x);
                    if (var7 != null) {
                        var8 = var10;
                        if (!var7.isEmpty()) {
                            break label11;
                        }
                    }

                    var8 = "=" +
                            var10;

                }

                var6.insertEditText(var8);
                var6.copyEditTextToCell();
            }
        });
        var2.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                NUIDocViewXls.this.I = null;
                NUIDocViewXls.this.J = null;
            }
        });
        var2.show();
        this.I = var2;
        this.J = var1;
        var2.getListView().setDivider(null);
        var2.getListView().setVerticalScrollBarEnabled(true);
    }

    private void setUpSheetBar() {
        final LinearLayout llSheetBar = this.findViewById(R.id.excel_sheets_bar);
        llSheetBar.removeAllViews();
        SheetTab2.setEditingEbabled(this.mConfigOptions.c());
        int var2 = this.getDoc().r();
        final Activity mActivity = this.activity();
        int var4 = 1;

        while (true) {
            boolean var5 = false;
            if (var4 > var2) {
                if (this.mConfigOptions.c()) {
                    Button var9 = (Button) this.activity().getLayoutInflater().inflate(R.layout.sodk_editor_sheet_tab_plus, llSheetBar, false);
                    var9.setText("+");
                    var9.setOnClickListener(new OnClickListener() {
                        public void onClick(View var1) {

                            NUIDocViewXls.this.G = true;
                            int var2 = NUIDocViewXls.this.getDoc().r();
                            NUIDocViewXls.this.getDoc().addBlankPage(var2);
                        }
                    });
                    llSheetBar.addView(var9);
                }

                return;
            }

            DocExcelView var6 = (DocExcelView) this.getDocView();
            int var7 = var4 - 1;
            final String var10 = var6.getPageTitle(var7);
            SheetTab2 var8 = new SheetTab2(mActivity);
            var8.setText(var10);
            var8.setSheetNumber(var7);
            var8.setOnClickTab(new OnClickListener() {
                public void onClick(View var1) {
                    int var2 = ((SheetTab2) var1).getSheetNumber();
                    NUIDocViewXls.this.a(var2);
                }
            });
            var8.setOnLongClickTab(new OnLongClickListener() {
                public boolean onLongClick(View var1x) {
                    int var2 = ((SheetTab2) var1x).getSheetNumber();
                    View var3 = llSheetBar.getChildAt(var2);
                    NUIDocViewXls.this.E = var2;
                    ClipData var6 = ClipData.newPlainText(var10, var10);
                    DragShadowBuilder var5 = new DragShadowBuilder(var3);
                    var1x.startDragAndDrop(var6, var5, null, 0);

                    return true;
                }
            });
            llSheetBar.setOnDragListener(new OnDragListener() {
                private int c = -1;

                private void a(int var1x) {
                    this.c = var1x;
                    var1x = 0;

                    while (true) {
                        int var2 = llSheetBar.getChildCount();
                        boolean var3 = true;
                        if (var1x >= var2 - 1) {
                            return;
                        }

                        SheetTab2 var4 = (SheetTab2) llSheetBar.getChildAt(var1x);
                        if (var1x != this.c || var1x == NUIDocViewXls.this.E) {
                            var3 = false;
                        }

                        var4.setHighlight(var3);
                        ++var1x;
                    }
                }

                public boolean onDrag(View var1x, DragEvent var2) {
                    int var5;
                    int var3 = var2.getAction();
                    byte var4 = -1;
                    var5 = var4;
                    label29:
                    switch (var3) {
                        case 1:
                        default:
                            return true;
                        case 2:
                            var3 = 0;

                            while (true) {
                                var5 = var4;
                                if (var3 >= llSheetBar.getChildCount() - 1) {
                                    break label29;
                                }

                                SheetTab2 var6 = (SheetTab2) llSheetBar.getChildAt(var3);
                                if (var2.getX() > (float) var6.getLeft() && var2.getX() < (float) var6.getRight()) {
                                    var5 = var3;
                                    break label29;
                                }

                                ++var3;
                            }
                        case 3:
                            var3 = this.c;
                            if (var3 != -1 && var3 != NUIDocViewXls.this.E) {
                                NUIDocViewXls.this.getDoc().movePage(NUIDocViewXls.this.E, this.c);
                                NUIDocViewXls.this.setUpSheetBar();
                                NUIDocViewXls.this.setCurrentSheet(this.c);
                                NUIDocViewXls.this.setSearchStart();
                            }
                        case 4:
                            NUIDocViewXls.this.E = -1;
                            var5 = var4;
                        case 5:
                        case 6:
                    }

                    this.a(var5);
                    return true;
                }
            });
            if (this.mConfigOptions.c()) {
                var8.setOnClickDelete(new OnClickListener() {
                    public void onClick(View var1) {
                        SheetTab2 mSheetTab2 = (SheetTab2) var1;
                        String mSheetTabName = mSheetTab2.getText();
                        final int mSheetTab2SheetNumber = mSheetTab2.getSheetNumber();
                        String var4 = NUIDocViewXls.this.getContext().getString(R.string.sodk_editor_delete_worksheet_q);
                        String var5 = NUIDocViewXls.this.getContext().getString(R.string.sodk_editor_do_you_want_to_delete_the_sheet) +
                                mSheetTabName +
                                "\" ?";
                        Utilities.yesNoMessage(mActivity, var4, var5, NUIDocViewXls.this.getContext().getString(R.string.sodk_editor_yes), NUIDocViewXls.this.getContext().getString(R.string.sodk_editor_no), new Runnable() {
                            public void run() {
                                NUIDocViewXls.this.H = true;
                                NUIDocViewXls.this.getDoc().clearSelection();
                                NUIDocViewXls.this.getDoc().deletePage(mSheetTab2SheetNumber);
                            }
                        }, null);
                    }
                });
            }

            llSheetBar.addView(var8);
            if (this.getCurrentSheet() == var7) {
                var5 = true;
            }

            var8.setSelected(var5);
            ++var4;
        }
    }

    private void d() {
        ListPopupWindow var1 = this.I;
        if (var1 != null) {
            String var2 = this.J;
            var1.dismiss();
            this.a(var2);
        }

    }

    private void e() {
        SOSelectionTableRange var1 = this.getDoc().selectionTableRange();
        if (var1 != null && var1.rowCount() == 1 && var1.columnCount() == 1) {
            String var2 = this.getDoc().getSelectionAsText();
            ((DocExcelView) this.getDocView()).setEditText(var2);
        }

    }

    private int getCurrentSheet() {
        DocExcelView var1 = (DocExcelView) this.getDocView();
        return var1 != null ? var1.getCurrentSheet() : 0;
    }

    private void setCurrentSheet(int var1) {
        if (var1 != this.getCurrentSheet()) {
            DocExcelView var2 = (DocExcelView) this.getDocView();
            var2.copyEditTextToCell();
            this.getDoc().clearSelection();
            var2.setEditText("");
        }

        LinearLayout var7 = this.findViewById(R.id.excel_sheets_bar);
        int var3 = this.getDoc().r();

        for (int var4 = 0; var4 < var3; ++var4) {
            View var5 = var7.getChildAt(var4);
            if (var5 != null) {
                boolean var6;
                var6 = var4 == var1;

                var5.setSelected(var6);
                if (var3 == 1) {
                    ((SheetTab2) var5).showXView(false);
                }
            }
        }

        ((DocExcelView) this.getDocView()).setCurrentSheet(var1);
        this.onSelectionChanged();
        this.setPageNumberText();
    }

    protected void afterFirstLayoutComplete() {
        super.afterFirstLayoutComplete();
        this.btnFx = (SOTextView) this.createToolbarButton(R.id.fx_button);
    }

    public void clickSheetButton(int var1, boolean var2) {
        LinearLayout var3 = this.findViewById(R.id.excel_sheets_bar);
        if (var3 != null) {
            SheetTab2 var4 = (SheetTab2) var3.getChildAt(var1);
            if (var4 != null) {
                var4.performClick();
            }
        }

        if (!var2) {
            this.setSearchStart();
        }

    }

    protected void createEditButtons2() {
//        this.btnAlignOption = (EditBtn) this.createToolbarButton(R.id.btn_align_options);
    }

    protected void createInputView() {
    }

    protected void createInsertButtons() {
    }

    protected DocView createMainView(Activity var1) {
        DocExcelView var2 = new DocExcelView(var1);
        if (Utilities.isPhoneDevice(var1)) {
            var2.setScale(1.5F);
        }

        return var2;
    }

    public void doCut() {
        super.doCut();
        ((DocExcelView) this.getDocView()).setEditText("");
    }

    public void doPaste() {
        super.doPaste();
        String var1 = this.getDoc().getSelectionAsText();
        ((DocExcelView) this.getDocView()).setEditText(var1);
    }

    public void doRedo() {
        super.doRedo();
        this.e();
        this.K = true;
    }

    public void doUndo() {
        super.doUndo();
        this.e();
        this.K = true;
    }

    protected void focusInputView() {
    }

    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), R.color.sodk_editor_header_xls_color);
    }

    protected int getLayoutId() {
        return R.layout.document_view_excel;
    }

    protected String getPageNumberText() {
        return String.format(this.getContext().getString(R.string.sodk_editor_sheet_d_of_d), this.getCurrentSheet() + 1, this.getPageCount());
    }


    protected void handleStartPage() {
        if (this.getStartPage() > 0 && this.getPageCount() >= this.getStartPage()) {
            this.setCurrentSheet(this.getStartPage() - 1);
            this.setStartPage(0);
        }

    }

    public void onAlignOptionsButton(View var1) {

        (new AlignmentDialog(this.getContext(), this.getDoc(), var1)).show();
    }

    public void onClick(View var1) {
        super.onClick(var1);
//        if (var1 == this.btnAlignOption) {
//            this.onAlignOptionsButton(var1);
//        }
        if (var1 == this.btnFx) {
            this.onClickFunctionButton(var1);
        }

    }

    public void onClickFunctionButton(View var1) {
        Utilities.hideKeyboard(this.getContext());
        View var2 = inflate(this.getContext(), R.layout.sodk_editor_formula_categories, null);
        GridView var3 = var2.findViewById(R.id.grid);
        final ChooseFormulaCategoryAdapter var4 = new ChooseFormulaCategoryAdapter(this.activity());
        var3.setAdapter(var4);
        final NUIPopupWindow var5 = new NUIPopupWindow(var2, -2, -2);
        var5.setFocusable(true);
        var3.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4x) {
                var5.dismiss();
                String var6 = var4.getItem(var3);
                NUIDocViewXls.this.a(var6);
            }
        });
        var5.showAsDropDown(var1, 30, 30);
    }


    protected void onDocCompleted() {
        label30:
        {
            int var1;
            label35:
            {
                super.onDocCompleted();
                this.setPageCount(this.getPageCount());
                this.setUpSheetBar();
                if (this.G) {
                    var1 = this.getDoc().r();
                } else if (this.H) {
                    if (this.getCurrentSheet() == 0) {
                        this.setCurrentSheet(0);
                        break label30;
                    }

                    var1 = this.getCurrentSheet();
                } else {
                    var1 = this.getPageCount();
                    if (var1 <= 0 || this.getCurrentSheet() < var1) {
                        var1 = this.getCurrentSheet();
                        break label35;
                    }
                }

                --var1;
            }

            this.setCurrentSheet(var1);
        }

        this.G = false;
        this.H = false;
    }

    protected void onFullScreenHide() {
        this.findViewById(R.id.fx_bar).setVisibility(GONE);
        super.onFullScreenHide();
    }

    protected void onOrientationChange() {
        super.onOrientationChange();
        this.d();
    }

    protected void onPageLoaded(int var1) {
        this.setUpSheetBar();
        super.onPageLoaded(var1);
    }

    public void onSelectionChanged() {
        super.onSelectionChanged();
        this.setPageCount(this.getPageCount());
        if (this.K) {
            this.setUpSheetBar();
            this.K = false;
        }

    }

    public void onShowKeyboard(final boolean z2) {
        if (isActivityActive() && getPageCount() > 0) {
            this.keyboardShown = z2;
            onShowKeyboardPreventPush();
            DocView docView = getDocView();
            if (docView != null) {
                docView.onShowKeyboard(z2);
            }
            if (isLandscapePhone()) {
                showUI(!z2);
                if (z2) {
                    requestLayout();
                    final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
                    viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            viewTreeObserver.removeOnGlobalLayoutListener(this);
                            ((DocExcelView) NUIDocViewXls.this.getDocView()).scrollSelectedCellAboveKeyboard();
                        }
                    });
                }
            } else if (z2) {
                ((DocExcelView) getDocView()).scrollSelectedCellAboveKeyboard();
            }
            d();
            final ViewTreeObserver viewTreeObserver2 = getViewTreeObserver();
            viewTreeObserver2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    viewTreeObserver2.removeOnGlobalLayoutListener(this);
                    DocView docView = NUIDocViewXls.this.getDocView();
                    if (docView != null) {
                        docView.onShowKeyboard(z2);
                    }
                    NUIDocViewXls.this.layoutNow();
                }
            });
        }
    }

    public void preSave() {
        ((DocExcelView) this.getDocView()).copyEditTextToCell();
    }

    protected void prepareToGoBack() {
        DocView var1 = this.getDocView();
        if (var1 != null && ((DocExcelView) var1).copyEditTextToCell()) {
            SODocSession var2 = this.getSession();
            if (var2 != null) {
                SOFileState var3 = var2.getFileState();
                if (var3 != null) {
                    var3.setHasChanges(true);
                }
            }
        }

    }

    protected void resetInputView() {
    }

    public void setSearchStart() {
        int var1 = this.getCurrentSheet();
        if (var1 >= 0) {
            this.getDoc().a(var1, 0.0F, 0.0F);
        }

    }

    public boolean showKeyboard() {
        return false;
    }

    public void showUI(boolean var1) {
        super.showUI(var1);
        if (this.mConfigOptions.A() && var1 && this.mConfigOptions.c()) {
            this.findViewById(R.id.fx_bar).setVisibility(VISIBLE);
        }

    }

    protected void updateEditUIAppearance() {
        SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
        boolean var2 = true;
        boolean var4;
        boolean var6;
        if (var1 != null) {
            boolean var3 = var1.getIsActive();
            var4 = var3 && !var1.getIsCaret();

            var6 = var4;
            if (var3) {
                var1.getIsCaret();
                var6 = var4;
            }
        } else {
            var6 = false;
        }
        SODoc var8 = this.mSession.getDoc();
        this.btnTextBold.setEnabled(var6);
        EditBtn var7 = this.btnTextBold;
        var4 = var6 && var8.getSelectionIsBold();
        var7.setSelected(var4);
        this.btnTextItalic.setEnabled(var6);
        var7 = this.btnTextItalic;
        var4 = var6 && var8.getSelectionIsItalic();

        var7.setSelected(var4);
        this.btnTextUnder.setEnabled(var6);
        var7 = this.btnTextUnder;
        var4 = var6 && var8.getSelectionIsUnderlined();

        var7.setSelected(var4);
        this.btnTextStrikeTrough.setEnabled(var6);
        var7 = this.btnTextStrikeTrough;
        if (var6 && var8.getSelectionIsLinethrough()) {
            var4 = var2;
        } else {
            var4 = false;
        }

        var7.setSelected(var4);
    }

    protected void updateReviewUIAppearance() {
    }

    protected void updateUIAppearance() {
        super.updateUIAppearance();
        SOSelectionLimits var1 = this.getDocView().getSelectionLimits();
        boolean var2;
        boolean var3;
        boolean var4;
        if (var1 != null) {
            var2 = var1.getIsActive();
            var3 = var2 && !var1.getIsCaret();

            var4 = var2 && var1.getIsCaret();
        } else {
            var4 = false;
            var3 = false;
        }
        var2 = var4 || var3;
//        this.btnAlignOption.setEnabled(var2);
        this.getDocView().onSelectionChanged();
    }

    protected boolean usePagesView() {
        return false;
    }
}
