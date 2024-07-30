package com.artifex.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.supportv1.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;

import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOHyperlink;
import com.artifex.solib.SOPage;
import com.artifex.solib.SOPageListener;
import com.artifex.solib.SORender;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.o;

public class DocPageView extends View implements SOPageListener, AnimatableView {
    private static Paint F;
    private final Rect A = new Rect();
    private boolean B = false;
    private boolean C = true;
    private Bitmap D = null;
    private Point E = null;
    private DocView G = null;
    private final Rect H = new Rect();
    Path a = null;
    private SODoc b;
    private int c = -1;
    private SORender d = null;
    private boolean e = false;
    private SODataLeakHandlers f;
    private final Rect g = new Rect();
    private final Rect h = new Rect();
    private final int[] i = new int[2];
    private SOBitmap j = null;
    private final Rect k = new Rect();
    private float l;
    private SOBitmap m = null;
    protected int mLayer = -2;
    protected SOPage mPage;
    protected Rect mPageRect = new Rect();
    protected PointF mRenderOrigin = new PointF();
    protected float mScale = 1.0F;
    protected Point mSize;
    protected double mZoom = 1.0D;
    private final Rect n = new Rect();
    private float o;
    private int p = -1;
    private SOBitmap q = null;
    private final Rect r = new Rect();
    private float s;
    private int t = -1;
    private final Paint u;
    private final Rect v = new Rect();
    private final Rect w = new Rect();
    private final Paint x;
    private final Paint y;
    private final Paint z;

    public DocPageView(Context var1, SODoc var2) {
        super(var1);
        this.setLayoutParams(new LayoutParams(-2, -2));
        this.b = var2;
        this.u = new Paint();
        Paint var4 = new Paint();
        this.x = var4;
        var4.setStyle(Style.FILL);
        var4.setColor(this.t);
        var4 = new Paint();
        this.z = var4;
        var4.setColor(ContextCompat.getColor(this.getContext(), R.color.sodk_editor_page_border_color));
        var4.setStyle(Style.STROKE);
        var4.setStrokeWidth((float) Utilities.convertDpToPixel(2.0F));
        var4 = new Paint();
        this.y = var4;
        this.setSelectedBorderColor(ContextCompat.getColor(this.getContext(), R.color.sodk_editor_selected_page_border_color));
        var4.setStyle(Style.STROKE);
        var4.setStrokeWidth((float) Utilities.convertDpToPixel((float) var1.getResources().getInteger(R.integer.sodk_editor_selected_page_border_width)));
        if (F == null) {
            Paint var3 = new Paint();
            F = var3;
            var3.setAntiAlias(true);
            F.setFilterBitmap(true);
            F.setDither(true);
        }

        this.getDataLeakHandlers();
    }

    private int a(SOBitmap var1) {
        if (!this.C) {
            return 0;
        } else if (var1 == null) {
            return -1;
        } else if (var1.b() != null && var1.a() != null) {
            int var2 = var1.b().left + 5;
            int var3 = var1.b().top + 5;
            int var4 = var1.b().right - 5;
            int var5 = var1.b().bottom - 5;
            return this.a(new int[]{var1.a().getPixel(var2, var3), var1.a().getPixel(var4, var3), var1.a().getPixel(var2, var5), var1.a().getPixel(var4, var5)});
        } else {
            return -1;
        }
    }

    private int a(int[] var1) {
        int var2 = 0;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;

        int var6;
        for (var6 = 0; var2 < var1.length; ++var2) {
            int var7 = var1[var2];
            var4 += var7 >> 16 & 255;
            var5 += var7 >> 8 & 255;
            var6 += var7 & 255;
            var3 += var7 >>> 24;
        }

        return Color.argb(var3 / var1.length, var4 / var1.length, var5 / var1.length, var6 / var1.length);
    }

    private Point a(float var1, float var2) {
        return this.screenToPage((int) var1, (int) var2);
    }

    private void a(SOBitmap var1, final o var2, Rect var3, Rect var4) {
        try {
            this.g.set(var4);
            this.g.offset(NUIDocView.OVERSIZE_MARGIN, NUIDocView.OVERSIZE_MARGIN);
            this.h.set(var3);
            this.setPageRect();
            int var5 = Math.min(Math.max(this.g.top - this.mPageRect.top, 0), NUIDocView.OVERSIZE_MARGIN);
            int var6 = Math.min(Math.max(this.mPageRect.bottom - this.g.bottom, 0), NUIDocView.OVERSIZE_MARGIN);
            int var7 = Math.min(Math.max(this.g.left - this.mPageRect.left, 0), NUIDocView.OVERSIZE_MARGIN);
            int var8 = Math.min(Math.max(this.mPageRect.right - this.g.right, 0), NUIDocView.OVERSIZE_MARGIN);
            int var9 = var7;
            int var10 = var8;
            if (this.b()) {
                if (this.getParent() instanceof DocListPagesView) {
                    var9 = 0;
                    var10 = var8;
                } else {
                    var10 = 0;
                    var9 = var7;
                }
            }

            var3 = this.g;
            var3.top -= var5;
            var3 = this.g;
            var3.bottom += var6;
            var3 = this.g;
            var3.left -= var9;
            var3 = this.g;
            var3.right += var10;
            var3 = this.h;
            var3.top -= var5;
            var3 = this.h;
            var3.bottom += var6;
            var3 = this.h;
            var3.left -= var9;
            var3 = this.h;
            var3.right += var10;
            if (this.g.left < 0) {
                var10 = -this.g.left;
                var3 = this.g;
                var3.left += var10;
                var3 = this.h;
                var3.left += var10;
            }

            if (this.g.right > var1.c()) {
                var10 = this.g.right - var1.c();
                var3 = this.g;
                var3.right -= var10;
                var3 = this.h;
                var3.right -= var10;
            }

            if (this.g.top < 0) {
                var10 = -this.g.top;
                var3 = this.g;
                var3.top += var10;
                var3 = this.h;
                var3.top += var10;
            }

            if (this.g.bottom > var1.d()) {
                var10 = this.g.bottom - var1.d();
                var3 = this.g;
                var3.bottom -= var10;
                var3 = this.h;
                var3.bottom -= var10;
            }

            this.k.set(this.h);
            this.l = this.mScale;
            this.j = new SOBitmap(var1, this.g.left, this.g.top, this.g.right, this.g.bottom);
            this.setOrigin();
            SOPage var15 = this.mPage;
            var10 = this.mLayer;
            double var11 = this.mScale;
            double var13 = this.mZoom;
            Double.isNaN(var11);
            Double.isNaN(var11);
            Double.isNaN(var11);
            this.d = var15.a(var10, var11 * var13, this.mRenderOrigin.x, this.mRenderOrigin.y, this.j, null, new o() {
                public void a(int var1) {
                    if (!DocPageView.this.e) {
                        DocPageView.this.stopRender();
                        if (var1 == 0) {
                            DocPageView var2x = DocPageView.this;
                            var2x.m = var2x.j;
                            DocPageView.this.n.set(DocPageView.this.k);
                            var2x = DocPageView.this;
                            var2x.o = var2x.l;
                            var2x = DocPageView.this;
                            var2x.p = var2x.a(var2x.m);
                        } else {
                            System.out.printf("render error %d for page %d  %n", var1, DocPageView.this.c);
                        }

                        var2.a(var1);
                    }
                }
            }, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private boolean a() {
        DocView var1 = this.getDocView();
        return var1 != null && var1.getReflowMode();
    }

    private boolean b() {
        DocView var1 = this.getDocView();
        return var1 != null && var1.pagesShowing();
    }

    private void getDataLeakHandlers() {
        String str;
        try {
            SODataLeakHandlers dataLeakHandlers = Utilities.getDataLeakHandlers();
            this.f = dataLeakHandlers;
            if (dataLeakHandlers == null) {
                throw new ClassNotFoundException();
            }
        } catch (ExceptionInInitializerError e2) {
            str = String.format("getDataLeakHandlers() experienced unexpected exception [%s]", "ExceptionInInitializerError");
            Log.e("DocPageView", str);
        } catch (LinkageError e3) {
            str = String.format("getDataLeakHandlers() experienced unexpected exception [%s]", "LinkageError");
            Log.e("DocPageView", str);
        } catch (SecurityException e4) {
            str = String.format("getDataLeakHandlers() experienced unexpected exception [%s]", "SecurityException");
            Log.e("DocPageView", str);
        } catch (ClassNotFoundException e5) {
            Log.i("DocPageView", "DataLeakHandlers implementation unavailable");
        }
    }

    protected boolean canDoubleTap(int var1, int var2) {
        return true;
    }

    protected void changePage(int var1) {
        if (!this.isFinished()) {
            if (this.C) {
                if (this.b != null) {
                    if (var1 != this.c || this.mPage == null) {
                        this.c = var1;
                        this.dropPage();
                        SOPage var2 = this.b.getPage(this.c, this);
                        this.mPage = var2;
                        this.b.a(var2);
                    }

                }
            }
        }
    }

    public void clearContent() {
        this.q = null;
        this.invalidate();
    }

    protected void dropPage() {
        SOPage var1 = this.mPage;
        if (var1 != null) {
            this.b.b(var1);
            this.mPage.m();
        }

        this.mPage = null;
    }

    public void endRenderPass() {
        this.q = this.m;
        this.r.set(this.n);
        this.s = this.o;
        this.t = this.p;
    }

    public void finish() {
        this.e = true;
        this.stopRender();
        SOPage var1 = this.mPage;
        if (var1 != null) {
            var1.n();
            this.mPage = null;
        }

        this.q = null;
        this.m = null;
        this.j = null;
        this.b = null;
    }

    public Rect getChildRect() {
        return this.H;
    }

    public Path getClipPath() {
        return this.a;
    }

    protected SODoc getDoc() {
        return this.b;
    }

    protected DocView getDocView() {
        return this.G;
    }

    public double getFactor() {
        double var1 = this.mZoom;
        double var3 = this.mScale;
        Double.isNaN(var3);
        Double.isNaN(var3);
        Double.isNaN(var3);
        return var1 * var3;
    }

    public float[] getHorizontalRuler() {
        return this.e ? null : this.mPage.getHorizontalRuler();
    }

    public SOPage getPage() {
        return this.mPage;
    }

    public int getPageNumber() {
        return this.c;
    }

    public int getReflowWidth() {
        return this.mPage.sizeAtZoom(1.0D).x;
    }

    SOSelectionLimits getSelectionLimits() {
        SOPage var1 = this.mPage;
        return var1 == null ? null : var1.selectionLimits();
    }

    public Point getSize() {
        return this.mSize;
    }

    public int getUnscaledHeight() {
        Point var1;
        if (this.a()) {
            var1 = this.mPage.sizeAtZoom(this.mZoom);
        } else {
            var1 = this.mSize;
        }

        return var1.y;
    }

    public int getUnscaledWidth() {
        Point var1;
        if (this.a()) {
            var1 = this.mPage.sizeAtZoom(this.mZoom);
        } else {
            var1 = this.mSize;
        }

        return var1.x;
    }

    public float[] getVerticalRuler() {
        return this.e ? null : this.mPage.getVerticalRuler();
    }

    public double getZoomScale() {
        double var1 = this.mZoom;
        double var3 = (double) this.mScale;
        Double.isNaN(var3);
        Double.isNaN(var3);
        Double.isNaN(var3);
        return var1 * var3;
    }

    protected boolean handleFullscreenTap(int var1, int var2) {
        return this.tryHyperlink(new Point(var1, var2), null);
    }

    public boolean isCurrent() {
        return this.B;
    }

    protected boolean isFinished() {
        return this.e;
    }

    protected boolean isValid() {
        return this.C;
    }

    public void onDoubleTap(int var1, int var2) {
        Point var3 = this.screenToPage(var1, var2);
        this.mPage.select(2, var3.x, var3.y);
        NUIDocView.currentNUIDocView().showUI(true);
    }

    public void onDraw(@NonNull Canvas var1) {
        if (!this.e) {
            if (this.isShown()) {
                if (this.mPage != null) {
                    Bitmap var2 = this.D;
                    Rect var3;
                    Rect var6;
                    if (var2 == null) {
                        if (this.C) {
                            this.x.setColor(this.t);
                            var6 = new Rect();
                            this.getLocalVisibleRect(var6);
                            var1.drawRect(var6, this.x);
                            if (this.a != null) {
                                var1.save();
                            }

                            SOBitmap var7 = this.q;
                            if (var7 != null && !var7.a().isRecycled()) {
                                this.v.set(var7.b());
                                this.w.set(this.r);
                                if (this.s != this.mScale) {
                                    var3 = this.w;
                                    var3.left = (int) ((float) var3.left * (this.mScale / this.s));
                                    var3 = this.w;
                                    var3.top = (int) ((float) var3.top * (this.mScale / this.s));
                                    var3 = this.w;
                                    var3.right = (int) ((float) var3.right * (this.mScale / this.s));
                                    var3 = this.w;
                                    var3.bottom = (int) ((float) var3.bottom * (this.mScale / this.s));
                                }

                                Path var9 = this.a;
                                if (var9 != null) {
                                    var1.clipPath(var9);
                                }

                                var1.drawBitmap(var7.a(), this.v, this.w, this.u);
                                this.A.set(0, 0, this.getWidth(), this.getHeight());
                                Paint var8;
                                if (this.B) {
                                    var3 = this.A;
                                    var8 = this.y;
                                } else {
                                    var3 = this.A;
                                    var8 = this.z;
                                }

                                var1.drawRect(var3, var8);
                                if (this.a != null) {
                                    var1.restore();
                                }
                            }

                        }
                    } else {
                        var3 = new Rect(0, 0, var2.getWidth(), this.D.getHeight());
                        Point var5 = Utilities.getScreenSize(this.getContext());
                        Point var4 = this.E;
                        if (var4 == null || var4.x == var5.x && this.E.y == var5.y) {
                            var6 = this.r;
                        } else {
                            var6 = new Rect();
                            this.getLocalVisibleRect(var6);
                        }

                        var1.drawBitmap(this.D, var3, var6, this.u);
                    }
                }
            }
        }
    }

    public void onFullscreen(boolean var1) {
    }

    public void onReflowScale(DocPageView var1) {
        this.mZoom = var1.mZoom;
        this.mScale = var1.mScale;
        this.mSize.x = var1.mSize.x;
        this.mSize.y = var1.mSize.y;
        this.requestLayout();
    }

    public boolean onSingleTap(int var1, int var2, boolean var3, ExternalLinkListener var4) {
        Point var5 = this.screenToPage(var1, var2);
        if (this.tryHyperlink(var5, var4)) {
            return true;
        } else {
            if (var3) {
                this.getDoc().clearSelection();
                this.mPage.select(3, (double) var5.x, (double) var5.y);
            }

            return false;
        }
    }

    public Point pageToScreen(Point var1) {
        double var2 = this.getFactor();
        double var4 = var1.x;
        Double.isNaN(var4);
        Double.isNaN(var4);
        Double.isNaN(var4);
        int var6 = (int) (var4 * var2);
        var4 = var1.y;
        Double.isNaN(var4);
        Double.isNaN(var4);
        Double.isNaN(var4);
        int var7 = (int) (var4 * var2);
        int[] var8 = new int[2];
        this.getLocationOnScreen(var8);
        return new Point(var6 + var8[0], var7 + var8[1]);
    }

    public Rect pageToScreen(RectF var1) {
        double var2 = this.getFactor();
        double var4 = var1.left;
        Double.isNaN(var4);
        Double.isNaN(var4);
        Double.isNaN(var4);
        int var6 = (int) (var4 * var2);
        var4 = var1.top;
        Double.isNaN(var4);
        Double.isNaN(var4);
        Double.isNaN(var4);
        int var7 = (int) (var4 * var2);
        var4 = var1.right;
        Double.isNaN(var4);
        Double.isNaN(var4);
        Double.isNaN(var4);
        int var8 = (int) (var4 * var2);
        var4 = var1.bottom;
        Double.isNaN(var4);
        Double.isNaN(var4);
        Double.isNaN(var4);
        int var9 = (int) (var4 * var2);
        int[] var10 = new int[2];
        this.getLocationOnScreen(var10);
        return new Rect(var6 + var10[0], var7 + var10[1], var8 + var10[0], var9 + var10[1]);
    }

    public int pageToView(int var1) {
        double var2 = this.getFactor();
        double var4 = var1;
        Double.isNaN(var4);
        Double.isNaN(var4);
        Double.isNaN(var4);
        return (int) (var4 * var2);
    }

    public Point pageToView(int var1, int var2) {
        return new Point(this.pageToView(var1), this.pageToView(var2));
    }

    protected Rect pageToView(RectF var1) {
        double var2 = this.getFactor();
        Rect var4 = new Rect();
        double var5 = var1.left;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        var4.left = (int) Math.round(var5 * var2);
        var5 = var1.top;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        var4.top = (int) Math.round(var5 * var2);
        var5 = var1.right;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        var4.right = (int) Math.round(var5 * var2);
        var5 = var1.bottom;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        var4.bottom = (int) Math.round(var5 * var2);
        return var4;
    }

    protected void pageToView(PointF var1, PointF var2) {
        double var3 = this.getFactor();
        float var5 = var1.x;
        float var6 = (float) var3;
        var2.set(var5 * var6, var1.y * var6);
    }

    public void pageToView(Rect var1, Rect var2) {
        double var3 = this.getFactor();
        double var5 = var1.left;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        int var7 = (int) (var5 * var3);
        var5 = var1.top;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        int var8 = (int) (var5 * var3);
        var5 = var1.right;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        int var9 = (int) (var5 * var3);
        var5 = var1.bottom;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        var2.set(var7, var8, var9, (int) (var5 * var3));
    }

    public void render(SOBitmap var1, o var2) {
        try {
            if (!this.e) {
                Rect var3 = new Rect();
                if (!this.getLocalVisibleRect(var3)) {
                    var2.a(0);
                } else {
                    Rect var4 = new Rect();
                    if (!this.getGlobalVisibleRect(var4)) {
                        var2.a(0);
                    } else {
                        this.a(var1, var2, var3, var4);
                    }
                }
            }
        } catch (Exception exception) {
            Log.e("render", "render_Exception: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void resize(int var1, int var2) {
        SOPage var3 = this.mPage;
        if (var3 != null) {
            PointF var6 = var3.zoomToFitRect(var1, var2);
            double var4 = (double) Math.max(var6.x, var6.y);
            this.mZoom = var4;
            this.mSize = this.mPage.sizeAtZoom(var4);
        }
    }

    public Rect screenRect() {
        int[] var1 = new int[2];
        this.getLocationOnScreen(var1);
        Rect var2 = new Rect();
        var2.set(var1[0], var1[1], var1[0] + this.getChildRect().width(), var1[1] + this.getChildRect().height());
        return var2;
    }

    protected Point screenToPage(int var1, int var2) {
        int[] var3 = new int[2];
        this.getLocationOnScreen(var3);
        var3 = Utilities.screenToWindow(var3, this.getContext());
        int var4 = var3[0];
        int var5 = var3[1];
        double var6 = this.getFactor();
        double var8 = var1 - var4;
        Double.isNaN(var8);
        Double.isNaN(var8);
        Double.isNaN(var8);
        var1 = (int) (var8 / var6);
        var8 = (double) (var2 - var5);
        Double.isNaN(var8);
        Double.isNaN(var8);
        Double.isNaN(var8);
        return new Point(var1, (int) (var8 / var6));
    }

    protected Point screenToPage(Point var1) {
        return this.screenToPage(var1.x, var1.y);
    }

    protected PointF screenToPage(PointF var1) {
        return new PointF(this.a(var1.x, var1.y));
    }

    public void selectTopLeft() {
        this.mPage.select(2, 0.0D, 0.0D);
    }

    public SOSelectionLimits selectionLimits() {
        return this.e ? null : this.mPage.selectionLimits();
    }

    public void setCaret(int var1, int var2) {
        Point var3 = this.screenToPage(var1, var2);
        SOHyperlink var4 = this.mPage.objectAtPoint((float) var3.x, (float) var3.y);
        if ((var4 == null || var4.url == null) && var4.pageNum == -1) {
            this.mPage.select(3, (double) var3.x, (double) var3.y);
        }
    }

    public void setChildRect(Rect var1) {
        this.H.set(var1);
    }

    public void setClipPath(Path var1) {
        this.a = var1;
    }

    public void setCurrent(boolean var1) {
        if (var1 != this.B) {
            this.B = var1;
            this.invalidate();
        }

    }

    public void setDocView(DocView var1) {
        this.G = var1;
    }

    public void setLayer(int var1) {
        this.mLayer = var1;
    }

    public void setNewScale(float var1) {
        this.mScale = var1;
    }

    protected void setOrigin() {
        this.mRenderOrigin.set((float) (-this.h.left), (float) (-this.h.top));
    }

    protected void setPageRect() {
        this.getLocationOnScreen(this.i);
        Rect var1 = this.mPageRect;
        int[] var2 = this.i;
        var1.set(var2[0], var2[1], var2[0] + this.getChildRect().width(), this.i[1] + this.getChildRect().height());
        this.mPageRect.offset(NUIDocView.OVERSIZE_MARGIN, NUIDocView.OVERSIZE_MARGIN);
    }

    public void setSelectedBorderColor(int var1) {
        this.y.setColor(var1);
    }

    public void setSelectionEnd(Point var1) {
        var1 = this.screenToPage(var1);
        PointF var2 = new PointF((float) var1.x, (float) var1.y);
        this.mPage.a(1, var2);
    }

    public void setSelectionStart(Point var1) {
        var1 = this.screenToPage(var1);
        PointF var2 = new PointF((float) var1.x, (float) var1.y);
        this.mPage.a(0, var2);
    }

    public void setValid(boolean var1) {
        if (var1 != this.C) {
            this.C = var1;
            if (!var1) {
                if (this.isShown()) {
                    SOBitmap var2 = this.q;
                    if (var2 != null && !var2.a().isRecycled()) {
                        this.E = Utilities.getScreenSize(this.getContext());
                        int var3 = this.q.c() / 2;
                        int var4 = this.q.d() / 2;
                        Rect var5 = new Rect(0, 0, var3, var4);
                        this.D = Bitmap.createBitmap(var3, var4, Config.ARGB_8888);
                        (new Canvas(this.D)).drawBitmap(this.q.a(), this.q.b(), var5, F);
                    }
                }

                this.q = null;
                this.m = null;
                this.j = null;
            } else {
                Bitmap var6 = this.D;
                if (var6 != null) {
                    var6.recycle();
                }

                this.D = null;
            }

            this.invalidate();
        }
    }

    public void setupPage(int var1, int var2, int var3) {
        if (!this.isFinished()) {
            if (this.C) {
                this.changePage(var1);
                this.resize(var2, 1);
            }
        }
    }

    public boolean sizeViewToPage() {
        SOPage var1 = this.mPage;
        boolean var2 = false;
        if (var1 == null) {
            return false;
        } else {
            Point var5 = this.mSize;
            if (var5 == null) {
                return false;
            } else {
                int var3 = var5.x;
                int var4 = this.mSize.y;
                var5 = this.mPage.sizeAtZoom(this.mZoom);
                this.mSize = var5;
                if (var5 == null) {
                    return false;
                } else {
                    if (var5.x != var3 || this.mSize.y != var4) {
                        var2 = true;
                    }

                    return var2;
                }
            }
        }
    }

    public void startRenderPass() {
    }

    public void stopRender() {
        SORender var1 = this.d;
        if (var1 != null) {
            var1.abort();
            this.d.destroy();
            this.d = null;
        }

    }

    protected boolean tryHyperlink(Point var1, ExternalLinkListener var2) {
        SOHyperlink var4 = this.mPage.objectAtPoint((float) var1.x, (float) var1.y);
        if (var4 != null) {
            if (var4.url != null) {
                if (ConfigOptions.a().o()) {
                    SODataLeakHandlers var5 = this.f;
                    if (var5 == null) {
                        throw new UnsupportedOperationException();
                    }

                    try {
                        var5.launchUrlHandler(var4.url);
                    } catch (UnsupportedOperationException var3) {
                        var3.printStackTrace();
                    }
                }

                return true;
            }

            if (var4.pageNum != -1) {
                if (var2 != null) {
                    var2.handleExternalLink(var4.pageNum, var4.bbox);
                }

                return true;
            }
        }

        return false;
    }

    public void update(RectF var1) {
        if (!this.e) {
            if (this.isShown()) {
                ((Activity) this.getContext()).runOnUiThread(new Runnable() {
                    public void run() {
                        NUIDocView var1 = NUIDocView.currentNUIDocView();
                        if (var1 != null) {
                            var1.triggerRender();
                        }

                    }
                });
            }
        }
    }

    public int viewToPage(int var1) {
        double var2 = this.getFactor();
        Double.isNaN(var1);
        Double.isNaN(var1);
        Double.isNaN(var1);
        return (int) ((double) var1 / var2);
    }

    public Point viewToPage(int var1, int var2) {
        double var3 = this.getFactor();
        double var5 = var1;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        var1 = (int) (var5 / var3);
        var5 = var2;
        Double.isNaN(var5);
        Double.isNaN(var5);
        Double.isNaN(var5);
        return new Point(var1, (int) (var5 / var3));
    }

    public interface ExternalLinkListener {
        void handleExternalLink(int var1, Rect var2);
    }
}
