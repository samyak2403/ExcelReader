package com.arrowwould.excelreader.customviews.smartrefresh.api;


import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arrowwould.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.arrowwould.excelreader.customviews.smartrefresh.listener.OnLoadMoreListener;
import com.arrowwould.excelreader.customviews.smartrefresh.listener.OnMultiPurposeListener;
import com.arrowwould.excelreader.customviews.smartrefresh.listener.OnRefreshListener;
import com.arrowwould.excelreader.customviews.smartrefresh.listener.OnRefreshLoadMoreListener;


@SuppressWarnings({"UnusedReturnValue", "SameParameterValue", "unused"})
public interface RefreshLayout {


    RefreshLayout setFooterHeight(float dp);



    RefreshLayout setHeaderHeight(float dp);

    RefreshLayout setHeaderInsetStart(float dp);


    RefreshLayout setFooterInsetStart(float dp);


    RefreshLayout setDragRate(@FloatRange(from = 0,to = 1) float rate);


    RefreshLayout setHeaderMaxDragRate(@FloatRange(from = 1,to = 10) float rate);

    RefreshLayout setFooterMaxDragRate(@FloatRange(from = 1,to = 10) float rate);


    RefreshLayout setHeaderTriggerRate(@FloatRange(from = 0,to = 1.0) float rate);

    RefreshLayout setFooterTriggerRate(@FloatRange(from = 0,to = 1.0) float rate);


    RefreshLayout setReboundInterpolator(@NonNull Interpolator interpolator);


    RefreshLayout setReboundDuration(int duration);

    /**
     * Set the footer of RefreshLayout.
     * 设置指定的 Footer
     * @param footer RefreshFooter 刷新尾巴
     * @return RefreshLayout
     */
    RefreshLayout setRefreshFooter(@NonNull RefreshFooter footer);

    /**
     * Set the footer of RefreshLayout.
     * 设置指定的 Footer
     * @param footer RefreshFooter 刷新尾巴
     * @param width the width in px, can use MATCH_PARENT and WRAP_CONTENT.
     *              宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height the height in px, can use MATCH_PARENT and WRAP_CONTENT.
     *               高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return RefreshLayout
     */
    RefreshLayout setRefreshFooter(@NonNull RefreshFooter footer, int width, int height);

    /**
     * Set the header of RefreshLayout.
     * 设置指定的 Header
     * @param header RefreshHeader 刷新头
     * @return RefreshLayout
     */
    RefreshLayout setRefreshHeader(@NonNull RefreshHeader header);

    /**
     * Set the header of RefreshLayout.
     * 设置指定的 Header
     * @param header RefreshHeader 刷新头
     * @param width the width in px, can use MATCH_PARENT and WRAP_CONTENT.
     *              宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height the height in px, can use MATCH_PARENT and WRAP_CONTENT.
     *               高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return RefreshLayout
     */
    RefreshLayout setRefreshHeader(@NonNull RefreshHeader header, int width, int height);

    /**
     * Set the content of RefreshLayout（Suitable for non-XML pages, not suitable for replacing empty layouts）。
     * 设置指定的 Content（适用于非XML页面，不适合用替换空布局）
     * @param content View 内容视图
     * @return RefreshLayout
     */
    RefreshLayout setRefreshContent(@NonNull View content);

    /**
     * Set the content of RefreshLayout（Suitable for non-XML pages, not suitable for replacing empty layouts）.
     * 设置指定的 Content（适用于非XML页面，不适合用替换空布局）
     * @param content View 内容视图
     * @param width the width in px, can use MATCH_PARENT and WRAP_CONTENT.
     *              宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height the height in px, can use MATCH_PARENT and WRAP_CONTENT.
     *               高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return RefreshLayout
     */
    RefreshLayout setRefreshContent(@NonNull View content, int width, int height);

    /**
     * Whether to enable pull-down refresh (enabled by default).
     * 是否启用下拉刷新（默认启用）
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableRefresh(boolean enabled);

    /**
     * Set whether to enable pull-up loading more (enabled by default).
     * 设置是否启用上拉加载更多（默认启用）
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableLoadMore(boolean enabled);

    /**
     * Sets whether to listen for the list to trigger a load event when scrolling to the bottom (default true).
     * 设置是否监听列表在滚动到底部时触发加载事件（默认true）
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableAutoLoadMore(boolean enabled);

    /**
     * Set whether to pull down the content while pulling down the header.
     * 设置是否启在下拉 Header 的同时下拉内容
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableHeaderTranslationContent(boolean enabled);

    /**
     * Set whether to pull up the content while pulling up the header.
     * 设置是否启在上拉 Footer 的同时上拉内容
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableFooterTranslationContent(boolean enabled);

    /**
     * Set whether to enable cross-border rebound function.
     * 设置是否启用越界回弹
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableOverScrollBounce(boolean enabled);

    /**
     * Set whether to enable the pure scroll mode.
     * 设置是否开启纯滚动模式
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnablePureScrollMode(boolean enabled);

    /**
     * Set whether to scroll the content to display new data after loading more complete.
     * 设置是否在加载更多完成之后滚动内容显示新数据
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableScrollContentWhenLoaded(boolean enabled);

    /**
     * Set whether to scroll the content to display new data after the refresh is complete.
     * 是否在刷新完成之后滚动内容显示新数据
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableScrollContentWhenRefreshed(boolean enabled);

    /**
     * Set whether to pull up and load more when the content is not full of one page.
     * 设置在内容不满一页的时候，是否可以上拉加载更多
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableLoadMoreWhenContentNotFull(boolean enabled);

    /**
     * Set whether to enable cross-border drag (imitation iphone effect).
     * 设置是否启用越界拖动（仿苹果效果）
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableOverScrollDrag(boolean enabled);

    /**
     * Set whether or not Footer follows the content after there is no more data.
     * 设置是否在没有更多数据之后 Footer 跟随内容
     * @deprecated use {@link RefreshLayout#setEnableFooterFollowWhenNoMoreData(boolean)}
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    @Deprecated
    RefreshLayout setEnableFooterFollowWhenLoadFinished(boolean enabled);

    /**
     * Set whether or not Footer follows the content after there is no more data.
     * 设置是否在没有更多数据之后 Footer 跟随内容
     * @param enabled 是否启用
     * @return RefreshLayout
     */
    RefreshLayout setEnableFooterFollowWhenNoMoreData(boolean enabled);


    RefreshLayout setEnableClipHeaderWhenFixedBehind(boolean enabled);


    RefreshLayout setEnableClipFooterWhenFixedBehind(boolean enabled);

    RefreshLayout setEnableNestedScroll(boolean enabled);


    RefreshLayout setDisableContentWhenRefresh(boolean disable);


    RefreshLayout setDisableContentWhenLoading(boolean disable);


    RefreshLayout setOnRefreshListener(OnRefreshListener listener);


    RefreshLayout setOnLoadMoreListener(OnLoadMoreListener listener);

    RefreshLayout setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener);


    RefreshLayout setOnMultiPurposeListener(OnMultiPurposeListener listener);


    RefreshLayout setScrollBoundaryDecider(ScrollBoundaryDecider boundary);


    RefreshLayout setPrimaryColors(@ColorInt int... primaryColors);


    RefreshLayout setPrimaryColorsId(@ColorRes int... primaryColorId);


    RefreshLayout finishRefresh();


    RefreshLayout finishRefresh(int delayed);


    RefreshLayout finishRefresh(boolean success);


    RefreshLayout finishRefresh(int delayed, boolean success, Boolean noMoreData);


    RefreshLayout finishRefreshWithNoMoreData();


    RefreshLayout finishLoadMore();


    RefreshLayout finishLoadMore(int delayed);


    RefreshLayout finishLoadMore(boolean success);


    RefreshLayout finishLoadMore(int delayed, boolean success, boolean noMoreData);


    RefreshLayout finishLoadMoreWithNoMoreData();


    RefreshLayout closeHeaderOrFooter();


    RefreshLayout setNoMoreData(boolean noMoreData);


    RefreshLayout resetNoMoreData();


    @Nullable
    RefreshHeader getRefreshHeader();

    @Nullable
    RefreshFooter getRefreshFooter();

    @NonNull
    RefreshState getState();


    @NonNull
    ViewGroup getLayout();


    boolean autoRefresh();


    boolean autoRefresh(int delayed);


    boolean autoRefreshAnimationOnly();


    boolean autoRefresh(int delayed, int duration, float dragRate, boolean animationOnly);


    boolean autoLoadMore();


    boolean autoLoadMoreAnimationOnly();


    boolean autoLoadMore(int delayed, int duration, float dragRate, boolean animationOnly);




}
