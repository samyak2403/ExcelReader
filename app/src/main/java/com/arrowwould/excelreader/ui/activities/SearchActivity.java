package com.arrowwould.excelreader.ui.activities;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.arrowwould.excelreader.BaseActivity;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.adapter.ListFileAdapter;
import com.arrowwould.excelreader.ads.NativeAdsAdmob;
import com.arrowwould.excelreader.customviews.EmptyRecyclerView;
import com.arrowwould.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.arrowwould.excelreader.customviews.smartrefresh.header.ClassicsHeader;
import com.arrowwould.excelreader.listener.OfficeClickListener;
import com.arrowwould.excelreader.model.OfficeModel;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends BaseActivity implements OfficeClickListener {
    private EmptyRecyclerView recyclerView;
    private ListFileAdapter adapter;
    private ArrayList<OfficeModel> arrayList;
    private Activity mActivity;
    private AppCompatImageView btnClear;

    private ConstraintLayout clToolbar;
    private AppCompatEditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mActivity = this;
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_excel));
        initViews();
        new GetDocument(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        NativeAdsAdmob.loadNativeBanner1(mActivity, null);

    }

    private void initViews() {

        btnClear = findViewById(R.id.iv_clear);

        clToolbar = findViewById(R.id.cl_toolbar);
        edtSearch = findViewById(R.id.et_search_text);

        RefreshLayout refreshLayout = findViewById(R.id.smartRefreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setEmptyView(findViewById(R.id.list_empty));
        arrayList = new ArrayList<>();


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    btnClear.setVisibility(View.VISIBLE);
                    Utils.searchDocument(charSequence.toString(), arrayList, adapter);

                } else if (charSequence.length() == 0) {
                    btnClear.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnClear.setOnClickListener(view -> Objects.requireNonNull(edtSearch.getText()).clear());
        findViewById(R.id.tv_back).setOnClickListener(view -> finish());

    }

    @Override
    public void onOfficeClick(OfficeModel officeModel) {
        File file = new File(officeModel.getAbsolutePath());
        Utils.openFile(mActivity, file);
    }

    public static class GetDocument extends AsyncTask<Void, Void, Void> {
        WeakReference<SearchActivity> weakReference;

        public GetDocument(SearchActivity allFragment) {
            this.weakReference = new WeakReference<>(allFragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.weakReference.get().arrayList = Utils.getExcel(weakReference.get());
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            weakReference.get().adapter = new ListFileAdapter(weakReference.get(), weakReference.get().arrayList, weakReference.get());
            weakReference.get().recyclerView.setAdapter(weakReference.get().adapter);
            weakReference.get().recyclerView.setVisibility(View.VISIBLE);
//            weakReference.get().progressBar.setVisibility(View.GONE);
        }
    }
}