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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.MyApplication;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.adapter.ListFileAdapter;
import com.arrowwould.excelreader.customviews.EmptyRecyclerView;
import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.arrowwould.excelreader.customviews.smartrefresh.header.ClassicsHeader;
import com.arrowwould.excelreader.customviews.smartrefresh.listener.OnRefreshListener;
import com.arrowwould.excelreader.listener.OfficeClickListener;
import com.arrowwould.excelreader.model.OfficeModel;
import com.arrowwould.excelreader.ui.activities.MainActivity;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AllFileFragment extends Fragment implements OfficeClickListener {
    private MainActivity mActivity;
    private SpinKitView loadingView;
    private ArrayList<OfficeModel> arrayList;
    private ConstraintLayout clPermission;
    private OfficeModel mPdfModel;
    EmptyRecyclerView recyclerView;
    ListFileAdapter adapter;
        private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals(GlobalConstant.ACTION_SORT_BY_DATE_UP)) {
                    if (adapter != null) {
                        arrayList.sort(OfficeModel.sortDateDescending);
                        adapter.notifyDataSetChanged();
                    }
                } else if (intent.getAction().equals(GlobalConstant.ACTION_SORT_BY_DATE_DOWN)) {
                    if (adapter != null) {
                        arrayList.sort(OfficeModel.sortDateAscending);
                        adapter.notifyDataSetChanged();
                    }
                } else if (intent.getAction().equals(GlobalConstant.ACTION_SORT_BY_A_Z)) {
                    if (adapter != null) {
                        arrayList.sort(OfficeModel.sortNameAscending);
                        adapter.notifyDataSetChanged();
                    }
                } else if (intent.getAction().equals(GlobalConstant.ACTION_SORT_BY_Z_A)) {
                    if (adapter != null) {
                        arrayList.sort(OfficeModel.sortNameDescending);
                        adapter.notifyDataSetChanged();
                    }
                } else if (intent.getAction().equals(GlobalConstant.ACTION_SORT_BY_SIZE_UP)) {
                    if (adapter != null) {
                        arrayList.sort(OfficeModel.sortFileSizeAscending);
                        adapter.notifyDataSetChanged();
                    }
                } else if (intent.getAction().equals(GlobalConstant.ACTION_SORT_BY_SIZE_DOWN)) {
                    if (adapter != null) {
                        arrayList.sort(OfficeModel.sortFileSizeDescending);
                        adapter.notifyDataSetChanged();
                    }
                } else if (intent.getAction().equals(GlobalConstant.ACTION_UPDATE_ALL_FRAGMENT)) {
                    new ListAllPdfFile(AllFileFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else if (intent.getAction().equals(GlobalConstant.ACTION_UPDATE_ALL_FAVORITE)) {
                    new ListAllPdfFile(AllFileFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else if (intent.getAction().equals(GlobalConstant.ACTION_UPDATE_3_FRAGMENT)) {
                    new ListAllPdfFile(AllFileFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        }
    };
//    SortReceiver receiver;

    public AllFileFragment() {
    }

    public AllFileFragment(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_pdf, container, false);
        initViews(view);


        checkPermission();
//        receiver = new SortReceiver();
        return view;
    }

    private void initViews(View view) {
        loadingView = view.findViewById(R.id.loadingView);
        TextView btnGoSet = view.findViewById(R.id.tv_go_to_set);
        btnGoSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.askPermission(mActivity);
            }
        });

        clPermission = view.findViewById(R.id.cly_no_permission_root);

        recyclerView = view.findViewById(R.id.recyclerviewAll);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(view.findViewById(R.id.empty_layout));
        arrayList = new ArrayList<>();
//        recyclerView.setOnTouchListener(new TranslateAnimationUtil(mActivity, mActivity.mBottomNavigationView));
        RefreshLayout refreshLayout = view.findViewById(R.id.smartRefreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new ListAllPdfFile(AllFileFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                refreshLayout.finishRefresh(1);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission();
        registerReceiver(receiver);

    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void registerReceiver(BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalConstant.ACTION_SORT_BY_DATE_UP);
        intentFilter.addAction(GlobalConstant.ACTION_SORT_BY_DATE_DOWN);
        intentFilter.addAction(GlobalConstant.ACTION_SORT_BY_A_Z);
        intentFilter.addAction(GlobalConstant.ACTION_SORT_BY_Z_A);
        intentFilter.addAction(GlobalConstant.ACTION_SORT_BY_SIZE_UP);
        intentFilter.addAction(GlobalConstant.ACTION_SORT_BY_SIZE_DOWN);
        intentFilter.addAction(GlobalConstant.ACTION_UPDATE_ALL_FRAGMENT);
        intentFilter.addAction(GlobalConstant.ACTION_UPDATE_3_FRAGMENT);
        intentFilter.addAction(GlobalConstant.ACTION_UPDATE_ALL_FAVORITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mActivity.registerReceiver(receiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            mActivity.registerReceiver(receiver, intentFilter);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        this.mActivity.unregisterReceiver(receiver);

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void checkPermission() {
        if (Utils.checkPermission(mActivity)) {
            recyclerView.setVisibility(View.VISIBLE);
            clPermission.setVisibility(View.GONE);

            new ListAllPdfFile(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            recyclerView.setVisibility(View.GONE);
            clPermission.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onOfficeClick(OfficeModel officeModel) {
        File file = new File(officeModel.getAbsolutePath());
        Utils.openFile(mActivity, file);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mActivity = (MainActivity) context;
        }
    }

    public static class ListAllPdfFile extends AsyncTask<Void, Void, Void> {

        WeakReference<AllFileFragment> weakReference;

        public ListAllPdfFile(AllFileFragment allFileFragment) {
            this.weakReference = new WeakReference<>(allFileFragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.weakReference.get().arrayList = Utils.getExcel(weakReference.get().mActivity);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            MyApplication.getInstance().setAllPdfList(this.weakReference.get().arrayList);

            weakReference.get().adapter = new ListFileAdapter(weakReference.get().mActivity, weakReference.get().arrayList, weakReference.get());
            weakReference.get().recyclerView.setAdapter(weakReference.get().adapter);
            weakReference.get().loadingView.setVisibility(View.GONE);
        }
    }
}
