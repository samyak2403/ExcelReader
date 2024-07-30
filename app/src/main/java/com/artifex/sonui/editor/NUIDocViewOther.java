package com.artifex.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.supportv1.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.arrowwould.excelreader.customviews.BottomBtn;
import com.arrowwould.excelreader.R;

public class NUIDocViewOther extends NUIDocView {
    private BottomBtn btnPrintTab;


    public NUIDocViewOther(Context var1) {
        super(var1);
        this.a();
    }

    public NUIDocViewOther(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a();
    }

    public NUIDocViewOther(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a();
    }

    private void a() {
    }

    protected void updateUndoUIAppearance() {
        this.btnUndo.setVisibility(GONE);
        this.btnRedo.setVisibility(GONE);
    }

    private String getFileExtension() {
        if (this.mStartUri != null) {
            return com.artifex.solib.a.a(this.getContext(), this.mStartUri);
        } else {
            String var1;
            if (this.mSession != null) {
                var1 = this.mSession.getUserPath();
            } else if (this.mState != null) {
                var1 = this.mState.getInternalPath();
            } else {
                var1 = "";
            }

            return com.artifex.solib.a.g(var1);
        }
    }

    protected void afterFirstLayoutComplete() {
        super.afterFirstLayoutComplete();
        this.btnPrintTab = (BottomBtn) this.createToolbarButton(R.id.btnPrintTab);
    }

    protected PageAdapter createAdapter() {
        return new PageAdapter(this.activity(), this, 1);
    }

    protected void createEditButtons() {
    }

    protected void createEditButtons2() {
    }

    protected void createInputView() {
    }

    protected void createInsertButtons() {
    }

    protected DocView createMainView(Activity var1) {
        return new DocView(var1);
    }

    public int getBorderColor() {
        return ContextCompat.getColor(this.getContext(), R.color.sodk_editor_selected_page_border_color);
    }

    protected int getLayoutId() {
        return R.layout.document_view_other;
    }

    protected boolean hasRedo() {
        return false;
    }

    protected boolean hasSearch() {
        String var1 = this.getFileExtension();
        return var1 != null && var1.compareToIgnoreCase("txt") == 0;
    }

    protected boolean hasUndo() {
        return false;
    }


    protected boolean inputViewHasFocus() {
        return false;
    }

    public void onClick(View var1) {
        super.onClick(var1);
        if (var1 == btnPrintTab) {
            this.onPrintButton();
        }

    }

    protected void onFullScreenHide() {

        this.layoutNow();
    }

    public void onPause() {
        this.onPauseCommon();
    }

    public void onRedoButton(View var1) {
        super.onRedoButton(var1);
    }


    public void onResume() {
        this.onResumeCommon();
    }

    public void onUndoButton(View var1) {
        super.onUndoButton(var1);
    }

    protected void scaleHeader() {
        this.scaleToolbar();

    }

    public void showUI(boolean var1) {


        this.layoutNow();
    }

    @Override
    protected void createRecyclerColor() {

    }

    protected void updateUIAppearance() {
        this.updateUndoUIAppearance();

    }

    protected boolean usePagesView() {
        return false;
    }
}
