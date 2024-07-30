package com.arrowwould.excelreader.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arrowwould.excelreader.BaseActivity;
import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.MyApplication;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.adapter.ChooseFileAdapter;
import com.arrowwould.excelreader.customviews.EmptyRecyclerView;
import com.arrowwould.excelreader.db.DbHelper;
import com.arrowwould.excelreader.listener.MergeChooseListener;
import com.arrowwould.excelreader.model.OfficeModel;
import com.arrowwould.excelreader.ui.dialog.ConfirmDialog;
import com.arrowwould.excelreader.ui.dialog.ProgressLockingDialog;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class SelectActivity extends BaseActivity implements View.OnClickListener, MergeChooseListener {
    private EmptyRecyclerView recyclerView;
    private ChooseFileAdapter adapter;
    private Toolbar toolbar;
    private LinearLayout btnShare;
    private LinearLayout btnMove;
    private LinearLayout btnDelete;
    private AppCompatImageView imgShare;
    private AppCompatImageView imgMove;
    private AppCompatImageView imgDelete;
    private AppCompatTextView tvShare;
    private AppCompatTextView tvMove;
    private AppCompatTextView tvDelete;
    private ArrayList<OfficeModel> arrayList;
    String typeArraylist;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initToolBar();

        getData();

        initViews();
        activeButton(false);
    }

    private void getData() {
        DbHelper dbHelper = DbHelper.getInstance(this);
        Intent intent = getIntent();
        if (intent != null) {

            typeArraylist = intent.getStringExtra(GlobalConstant.RECENT_OR_FAVORITE);
            if (typeArraylist != null) {
                if (typeArraylist.equals(GlobalConstant.ALL)) {
                    arrayList = MyApplication.getInstance().getArrayListAll();
                } else if (typeArraylist.equals(GlobalConstant.RECENT)) {
                    arrayList = dbHelper.getRecentPDFs();
                } else if (typeArraylist.equals(GlobalConstant.FAV)) {
                    arrayList = dbHelper.getStarredPdfs();
                }
            }
        }

    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getString(R.string.x_selected, String.valueOf(0)));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
    }

    private void activeButton(boolean onOff) {
        btnShare.setClickable(onOff);
        btnShare.setFocusable(onOff);
        btnDelete.setClickable(onOff);
        btnDelete.setFocusable(onOff);
        btnMove.setClickable(onOff);
        btnMove.setFocusable(onOff);
        if (onOff) {
            imgShare.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryText));
            imgMove.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryText));
            imgDelete.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryText));

            tvShare.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText));
            tvMove.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText));
            tvDelete.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText));

        } else {
            imgShare.setColorFilter(ContextCompat.getColor(this, R.color.color_disable_button));
            imgMove.setColorFilter(ContextCompat.getColor(this, R.color.color_disable_button));
            imgDelete.setColorFilter(ContextCompat.getColor(this, R.color.color_disable_button));

            tvShare.setTextColor(ContextCompat.getColor(this, R.color.color_disable_button));
            tvMove.setTextColor(ContextCompat.getColor(this, R.color.color_disable_button));
            tvDelete.setTextColor(ContextCompat.getColor(this, R.color.color_disable_button));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.menu_activity_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.item_select_all) {

            if (adapter.isSelectedAll) {
                adapter.setUnSelectedAll();
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_select_all));
                activeButton(false);
            } else {
                adapter.setSelectedAll();
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_unselect_all));
                activeButton(true);
            }
            toolbar.setTitle(getString(R.string.x_selected, String.valueOf(adapter.getSelected().size())));

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {

        btnShare = findViewById(R.id.ll_share);
        btnShare.setOnClickListener(this);
        btnMove = findViewById(R.id.ll_remove);
        btnMove.setOnClickListener(this);
        btnDelete = findViewById(R.id.ll_delete);
        btnDelete.setOnClickListener(this);
        tvShare = findViewById(R.id.tv_share);
        tvMove = findViewById(R.id.tv_remove);
        tvDelete = findViewById(R.id.tv_delete);
        imgShare = findViewById(R.id.iv_share);
        imgMove = findViewById(R.id.iv_remove);
        imgDelete = findViewById(R.id.iv_delete);


        if (typeArraylist.equals(GlobalConstant.ALL)) {
            btnMove.setVisibility(View.GONE);
        } else if (typeArraylist.equals(GlobalConstant.FAV)) {
            imgMove.setImageResource(R.drawable.ic_un_bookmark);
            tvMove.setText(getResources().getString(R.string.str_remove_favorite));
        } else if (typeArraylist.equals(GlobalConstant.RECENT)) {
            imgMove.setImageResource(R.drawable.ic_remove);
            tvMove.setText(R.string.str_move_out);

        }

        SpinKitView loadingView = findViewById(R.id.loadingView);
        recyclerView = findViewById(R.id.rcv_select_file_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(findViewById(R.id.emptyView));
//        arrayList = MyApplication.getInstance().getArrayListAll();
        adapter = new ChooseFileAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
        loadingView.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View view) {
        int idView = view.getId();

        if (idView == R.id.ll_share) {
            if (adapter.getSelected().size() < 50) {
                ArrayList<Uri> arrayList = new ArrayList<>();
                for (int i = 0; i < adapter.getSelected().size(); i++) {
                    arrayList.add(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", new File(adapter.getSelected().get(i).getFileUri())));
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.str_share_via_photo));
                intent.setType("application/pdf");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.toast_maximum_file_share), Toast.LENGTH_SHORT).show();
            }
        } else if (idView == R.id.ll_delete) {
            ConfirmDialog confirmDialog = new ConfirmDialog(this, GlobalConstant.DIALOG_CONFIRM_DELETE, () -> new DeleteMultiFile(SelectActivity.this).execute());
            Window window1 = confirmDialog.getWindow();
            assert window1 != null;
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window1.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            confirmDialog.show();
        } else if (idView == R.id.ll_remove) {
            if (typeArraylist.equals(GlobalConstant.RECENT)) {
                ConfirmDialog confirmDialog1 = new ConfirmDialog(this, GlobalConstant.DIALOG_CONFIRM_REMOVE_RECENT, () -> new RemoveRecent(SelectActivity.this).execute());
                Window window3 = confirmDialog1.getWindow();
                assert window3 != null;
                confirmDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                window3.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                confirmDialog1.show();
            } else if (typeArraylist.equals(GlobalConstant.FAV)) {
                ConfirmDialog confirmDialog1 = new ConfirmDialog(this, GlobalConstant.DIALOG_CONFIRM_REMOVE_FAV, () -> new RemoveFavorite(SelectActivity.this).execute());
                Window window4 = confirmDialog1.getWindow();
                assert window4 != null;
                confirmDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                window4.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                confirmDialog1.show();
            }
        }
    }

    @Override
    public void onMergeChoose(int position) {
        activeButton(adapter.getSelected().size() > 0);
        toolbar.setTitle(getString(R.string.x_selected, String.valueOf(adapter.getSelected().size())));
        if (adapter.getSelected().size() == arrayList.size()) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_unselect_all));
            adapter.isSelectedAll = true;
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_select_all));
            adapter.isSelectedAll = false;
        }

    }


    public static class RemoveFavorite extends AsyncTask<Void, Integer, Void> {
        WeakReference<SelectActivity> weakReference;
        ProgressLockingDialog dialog;
        int progress;

        public RemoveFavorite(SelectActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = 0;
            dialog = new ProgressLockingDialog(weakReference.get());
            dialog.setTvTittle(weakReference.get().getString(R.string.removing));
            Window window7 = dialog.getWindow();
            assert window7 != null;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window7.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<OfficeModel> arrayListRemove = weakReference.get().adapter.getSelected();
            for (int i = 0; i < arrayListRemove.size(); i++) {
                OfficeModel pdfModel = arrayListRemove.get(i);
                if (DbHelper.getInstance(weakReference.get()).isStared(pdfModel.getFileUri())) {
                    DbHelper.getInstance(weakReference.get()).removeStaredPDF(pdfModel.getFileUri());
                }
            }
            for (int i = 0; i < 100; i++) {
                SystemClock.sleep(10);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            dialog.dismiss();
            this.weakReference.get().arrayList = DbHelper.getInstance(weakReference.get()).getStarredPdfs();

            if (weakReference.get().arrayList.size() == 0) {
                weakReference.get().finish();
            } else {
                weakReference.get().adapter = new ChooseFileAdapter(weakReference.get(), weakReference.get().arrayList, weakReference.get());
                weakReference.get().recyclerView.setAdapter(weakReference.get().adapter);
                weakReference.get().activeButton(weakReference.get().adapter.getSelected().size() > 0);
                weakReference.get().toolbar.setTitle(weakReference.get().getString(R.string.x_selected, String.valueOf(weakReference.get().adapter.getSelected().size())));
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int number = values[0];
            dialog.setProgress(number);
            dialog.setTvPercent(number + "%");
        }
    }

    public static class RemoveRecent extends AsyncTask<Void, Integer, Void> {
        WeakReference<SelectActivity> weakReference;
        ProgressLockingDialog dialog;
        int progress;

        public RemoveRecent(SelectActivity activity) {
            this.weakReference = new WeakReference<>(activity);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = 0;
            dialog = new ProgressLockingDialog(weakReference.get());
            dialog.setTvTittle(weakReference.get().getString(R.string.removing));
            Window window6 = dialog.getWindow();
            assert window6 != null;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window6.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<OfficeModel> arrayListRemove = weakReference.get().adapter.getSelected();
            for (int i = 0; i < arrayListRemove.size(); i++) {
                OfficeModel pdfModel = arrayListRemove.get(i);
                if (DbHelper.getInstance(weakReference.get()).isRecent(pdfModel.getFileUri())) {
                    DbHelper.getInstance(weakReference.get()).removeRecentPDF(pdfModel.getFileUri());
                }
            }
            for (int i = 0; i < 100; i++) {
                SystemClock.sleep(10);
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void
        onPostExecute(Void unused) {
            super.onPostExecute(unused);
            dialog.dismiss();
            this.weakReference.get().arrayList = DbHelper.getInstance(weakReference.get()).getRecentPDFs();

            if (weakReference.get().arrayList.size() == 0) {
                weakReference.get().finish();
            } else {
                weakReference.get().adapter = new ChooseFileAdapter(weakReference.get(), weakReference.get().arrayList, weakReference.get());
                weakReference.get().recyclerView.setAdapter(weakReference.get().adapter);
                weakReference.get().activeButton(weakReference.get().adapter.getSelected().size() > 0);
                weakReference.get().toolbar.setTitle(weakReference.get().getString(R.string.x_selected, String.valueOf(weakReference.get().adapter.getSelected().size())));
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int number = values[0];
            dialog.setProgress(number);
            dialog.setTvPercent(number + "%");
        }
    }

    public static class DeleteMultiFile extends AsyncTask<Void, Integer, Void> {


        WeakReference<SelectActivity> weakReference;
        ProgressLockingDialog dialog;
        int progress;

        public DeleteMultiFile(SelectActivity activity) {
            this.weakReference = new WeakReference<>(activity);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = 0;
            dialog = new ProgressLockingDialog(weakReference.get());
            dialog.setTvTittle(weakReference.get().getString(R.string.deleting));
            Window window5 = dialog.getWindow();
            assert window5 != null;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window5.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<OfficeModel> arrayList1 = weakReference.get().adapter.getSelected();
            for (int i = 0; i < arrayList1.size(); i++) {
                OfficeModel pdfModel = arrayList1.get(i);
                File file = new File(pdfModel.getFileUri());
                file.delete();
                MediaScannerConnection.scanFile(weakReference.get(), new String[]{pdfModel.getFileUri()}, null, null);
                if (DbHelper.getInstance(weakReference.get()).isStared(pdfModel.getFileUri())) {
                    DbHelper.getInstance(weakReference.get()).removeStaredPDF(pdfModel.getFileUri());

                }
                if (DbHelper.getInstance(weakReference.get()).isRecent(pdfModel.getFileUri())) {
                    DbHelper.getInstance(weakReference.get()).removeRecentPDF(pdfModel.getFileUri());

                }
            }
            for (int i = 0; i < 100; i++) {
                SystemClock.sleep(10);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            dialog.dismiss();

            if (weakReference.get().typeArraylist.equals(GlobalConstant.ALL)) {
                this.weakReference.get().arrayList = Utils.getExcel(weakReference.get());

            } else if (weakReference.get().typeArraylist.equals(GlobalConstant.FAV)) {
                this.weakReference.get().arrayList = DbHelper.getInstance(weakReference.get()).getStarredPdfs();

            } else if (weakReference.get().typeArraylist.equals(GlobalConstant.RECENT)) {
                this.weakReference.get().arrayList = DbHelper.getInstance(weakReference.get()).getRecentPDFs();

            }


            MyApplication.getInstance().setAllPdfList(this.weakReference.get().arrayList);

            weakReference.get().adapter = new ChooseFileAdapter(weakReference.get(), weakReference.get().arrayList, weakReference.get());
            weakReference.get().recyclerView.setAdapter(weakReference.get().adapter);

            weakReference.get().activeButton(weakReference.get().adapter.getSelected().size() > 0);
            weakReference.get().toolbar.setTitle(weakReference.get().getString(R.string.x_selected, String.valueOf(weakReference.get().adapter.getSelected().size())));


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int number = values[0];
            dialog.setProgress(number);
            dialog.setTvPercent(number + "%");
        }
    }

}