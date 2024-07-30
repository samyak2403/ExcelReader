package com.arrowwould.excelreader.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.adapter.FavoriteAdapter;
import com.arrowwould.excelreader.customviews.EmptyRecyclerView;
import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.arrowwould.excelreader.customviews.smartrefresh.header.ClassicsHeader;
import com.arrowwould.excelreader.customviews.smartrefresh.listener.OnRefreshListener;
import com.arrowwould.excelreader.db.DbHelper;
import com.arrowwould.excelreader.listener.OfficeClickListener;
import com.arrowwould.excelreader.model.OfficeModel;
import com.arrowwould.excelreader.ui.activities.MainActivity;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.File;
import java.lang.ref.WeakReference;

public class FavoriteFragment extends Fragment implements OfficeClickListener {
    private MainActivity mActivity;
    private EmptyRecyclerView recyclerView;
    private SpinKitView loadingView;
    private FavoriteAdapter adapter;
    private DbHelper dbHelper;
    //    InterstitialAds interstitialAds;
    OfficeModel mPdfModel;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                if (intent.getAction().equals(GlobalConstant.ACTION_UPDATE_FAVORITE_FRAGMENT)) {
                    new LoadFavorite(FavoriteFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else if (intent.getAction().equals(GlobalConstant.ACTION_UPDATE_ALL_FAVORITE)) {
                    new LoadFavorite(FavoriteFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else if (intent.getAction().equals(GlobalConstant.ACTION_UPDATE_3_FRAGMENT)) {
                    new LoadFavorite(FavoriteFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }
            }

        }
    };


    public FavoriteFragment() {
    }

    public FavoriteFragment(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        initViews(view);
        initListener();

        return view;
    }

    private void initListener() {

    }

    @Override
    public void onPause() {
        super.onPause();
        this.mActivity.unregisterReceiver(receiver);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void registerReceiver(BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalConstant.ACTION_UPDATE_FAVORITE_FRAGMENT);
        intentFilter.addAction(GlobalConstant.ACTION_UPDATE_ALL_FAVORITE);
        intentFilter.addAction(GlobalConstant.ACTION_UPDATE_3_FRAGMENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mActivity.registerReceiver(receiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            mActivity.registerReceiver(receiver, intentFilter);
        }
    }

    private void initViews(View view) {
        loadingView = view.findViewById(R.id.loadingView);
        loadingView.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recyclerFavorite);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(view.findViewById(R.id.empty_layout));
        adapter = new FavoriteAdapter(mActivity, this);
        recyclerView.setAdapter(adapter);
//        recyclerView.setOnTouchListener(new TranslateAnimationUtil(mActivity, mActivity.mBottomNavigationView));
        RefreshLayout refreshLayout = view.findViewById(R.id.smartRefreshLayoutFav);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new LoadFavorite(FavoriteFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                refreshLayout.finishRefresh(1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver);

        new LoadFavorite(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mActivity = (MainActivity) context;
        }
    }

    @Override
    public void onOfficeClick(OfficeModel pdfModel) {

        File file = new File(pdfModel.getAbsolutePath());
        Utils.openFile(mActivity, file);

    }

    public static class LoadFavorite extends AsyncTask<Void, Void, Void> {
        WeakReference<FavoriteFragment> weakReference;

        public LoadFavorite(FavoriteFragment favoriteFragment) {
            this.weakReference = new WeakReference<>(favoriteFragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            weakReference.get().adapter = new FavoriteAdapter(weakReference.get().mActivity, weakReference.get());
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            weakReference.get().recyclerView.setAdapter(weakReference.get().adapter);
            weakReference.get().recyclerView.setVisibility(View.VISIBLE);
            weakReference.get().loadingView.setVisibility(View.GONE);
        }
    }
}
