package com.artifex.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.core.content.ContextCompat;

public class SheetTab2 extends LinearLayout {
    private static boolean a;
    private int b = 0;
    private boolean c = false;

    public SheetTab2(Context var1) {
        super(var1);
        LayoutInflater.from(var1).inflate(R.layout.sodk_editor_sheet_tab, this);
    }

    public SheetTab2(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    private View getOuterView() {
        return this.findViewById(R.id.sheetTab);
    }

    private Space getSpacerView() {
        return (Space) findViewById(R.id.spacer);
    }

    private ImageView getXView() {
        return (ImageView) this.findViewById(R.id.button2);
    }

    public static void setEditingEbabled(boolean var0) {
        a = var0;
    }

    public SOTextView getNameView() {
        return (SOTextView) this.findViewById(R.id.button1);
    }

    public int getSheetNumber() {
        return this.b;
    }

    public String getText() {
        return this.getNameView().getText().toString();
    }

    public void onDraw(Canvas var1) {
        super.onDraw(var1);
        if (this.c) {
            Paint var2 = new Paint();
            var2.setColor(ContextCompat.getColor(this.getContext(), R.color.sodk_editor_excel_sheet_tab_highlight_color));
            var2.setStyle(Paint.Style.STROKE);
            var2.setStrokeWidth((float) Utilities.convertDpToPixel((float) this.getContext().getResources().getInteger(R.integer.sodk_editor_selected_page_border_width)));
            var1.drawRect(new Rect(0, 0, this.getWidth(), this.getHeight()), var2);
        }

    }

    public boolean performClick() {
        return this.getOuterView().performClick();
    }

    public void setHighlight(boolean var1) {
        this.c = var1;
        this.invalidate();
    }

    public void setOnClickDelete(final OnClickListener var1) {
        this.getXView().setOnClickListener(new OnClickListener() {
            public void onClick(View var1x) {
                var1.onClick(SheetTab2.this);
            }
        });
    }

    public void setOnClickTab(final OnClickListener var1) {
        this.getOuterView().setOnClickListener(new OnClickListener() {
            public void onClick(View var1x) {
                var1.onClick(SheetTab2.this);
            }
        });
    }

    public void setOnLongClickTab(final OnLongClickListener var1) {
        this.getOuterView().setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View var1x) {
                var1.onLongClick(SheetTab2.this);
                return true;
            }
        });
    }

    public void setSelected(boolean var1) {
        label13:
        {
            this.getNameView().setSelected(var1);
            this.getXView().setSelected(var1);
            if (var1) {
                this.setBackgroundResource(R.drawable.sodk_editor_sheet_tab_selected);
                if (a) {
                    var1 = true;
                    break label13;
                }
            } else {
                this.setBackgroundResource(R.drawable.sodk_editor_sheet_tab);
            }

            var1 = false;
        }

        this.showXView(var1);
    }

    public void setSheetNumber(int var1) {
        this.b = var1;
    }

    public void setText(String var1) {
        this.getNameView().setText(var1);
    }

    public void showXView(boolean var1) {
        ImageView var2;
        byte var3;
        if (var1) {
            var2 = this.getXView();
            var3 = View.VISIBLE;
        } else {
            var2 = this.getXView();
            var3 = GONE;
        }

        var2.setVisibility(var3);
        this.getSpacerView().setVisibility(var3);
    }
}
