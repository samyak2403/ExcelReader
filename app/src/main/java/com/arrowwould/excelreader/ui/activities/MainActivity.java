package com.arrowwould.excelreader.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arrowwould.excelreader.GlobalConstant;
import com.arrowwould.excelreader.R;
import com.arrowwould.excelreader.SharedPreferenceUtils;
import com.arrowwould.excelreader.Utils;
import com.arrowwould.excelreader.adapter.ViewPagerAdapter;
import com.arrowwould.excelreader.listener.SortByListener;
import com.arrowwould.excelreader.ui.dialog.ExitDialog;
import com.arrowwould.excelreader.ui.dialog.SortByDialog;
import com.arrowwould.excelreader.ui.fragment.AllFileFragment;
import com.arrowwould.excelreader.ui.fragment.FavoriteFragment;
import com.arrowwould.excelreader.ui.fragment.RecentFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
        , NavigationView.OnNavigationItemSelectedListener, SortByListener {
    ViewPager mViewPager2;
    public BottomNavigationView mBottomNavigationView;
    private DrawerLayout drawerLayout;
    private ImageView btnSort;
    private ImageView btnSelect;
    private TextView tvTittle;
    ExitDialog exitDialog;
    Intent intentView;
    Toolbar toolbar;

    private boolean doubleBack = false;
    AppBarLayout appBarLayout;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initViews();
//        initData();
        initListener();
        initViewPager2();
        checkPermission();
        setUpExitDialog();
//        NativeAdsAdmob.loadNativeBanner2(this, null);
        getData();
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int idMenu = item.getItemId();

            if (idMenu == R.id.navigation_home) {
                mViewPager2.setCurrentItem(0);

            } else if (idMenu == R.id.navigation_recent) {
                mViewPager2.setCurrentItem(1);

            } else if (idMenu == R.id.navigation_bookmarks) {
                mViewPager2.setCurrentItem(2);

            }


            return true;
        });
    }

    private void getData() {
        Intent intentData = getIntent();
        if (intentData != null) {
            String st = intentData.getStringExtra(GlobalConstant.KEY_DATA_FROM_OUTSIDE);
            if (st != null) {
                Uri uri = Uri.parse(st);
                try {
                    if (uri.getScheme() != null) {
                        new SendData(this, uri).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        File file = new File(uri.getPath());
                        Utils.openFile(MainActivity.this, file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.addSuppressed(throwable);
                }

            }
        }
    }

    private void setUpExitDialog() {
        exitDialog = new ExitDialog(this);
        Window window = exitDialog.getWindow();
        assert window != null;
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void initViewPager2() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new AllFileFragment(this), getResources().getString(R.string.str_all_pdf));
        viewPagerAdapter.addFrag(new RecentFragment(this), getResources().getString(R.string.str_recent));
        viewPagerAdapter.addFrag(new FavoriteFragment(this), getResources().getString(R.string.str_favorite));
        mViewPager2.setAdapter(viewPagerAdapter);
        mViewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;

                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                        tvTittle.setText(getResources().getString(R.string.str_all_pdf));
                        btnSort.setVisibility(View.VISIBLE);
                        btnSelect.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.navigation_recent).setChecked(true);
                        tvTittle.setText(getResources().getString(R.string.str_recent));
                        btnSort.setVisibility(View.GONE);
                        btnSelect.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.navigation_bookmarks).setChecked(true);
                        tvTittle.setText(getResources().getString(R.string.str_favorite));
                        btnSort.setVisibility(View.GONE);
                        btnSelect.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager2.setOffscreenPageLimit(4);
        mViewPager2.setCurrentItem(0);


    }

    private void initListener() {
        btnSelect.setOnClickListener(this);
        btnSort.setOnClickListener(this);
    }

    private void initViews() {
//        clBrowsePdf = findViewById(R.id.btnBrowsePDF);
        drawerLayout = findViewById(R.id.activity_main_drawer);

        appBarLayout = findViewById(R.id.appBarLayout);
        mBottomNavigationView = findViewById(R.id.bottom_nav);
        mViewPager2 = findViewById(R.id.view_pager);
        tvTittle = findViewById(R.id.tv_all_file);
        TextView tvCount = findViewById(R.id.tv_all_file_count);
        btnSelect = findViewById(R.id.btnSelect);
        btnSort = findViewById(R.id.btnSort);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.findViewById(R.id.nav_browse_pdf).setOnClickListener(this);
        navigationView.findViewById(R.id.nav_rate_app).setOnClickListener(this);
        navigationView.findViewById(R.id.nav_share_app).setOnClickListener(this);
        navigationView.findViewById(R.id.nav_language).setOnClickListener(this);
        navigationView.findViewById(R.id.nav_feedback).setOnClickListener(this);
        navigationView.findViewById(R.id.nav_policy).setOnClickListener(this);

        TextView tvLang = navigationView.findViewById(R.id.tv_language);
        tvLang.setText(SharedPreferenceUtils.getInstance(this).getString(GlobalConstant.LANGUAGE_NAME, "English"));
        TextView tvAppName = navigationView.findViewById(R.id.tv_app_name);
        tvAppName.setText(Utils.setAppName(this));
    }

    private void initToolBar() {

        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle(Utils.setAppName(this));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setTitle(Utils.setAppName(this));
        }
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void checkPermission() {
        if (!Utils.checkPermission(this)) {
            Utils.showPermissionDialog(this);
        }
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if (idView == R.id.btnSort) {
            SortByDialog dialog = new SortByDialog(MainActivity.this, this);
            Window window = dialog.getWindow();
            assert window != null;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        } else if (idView == R.id.btnSelect) {
            if (mPosition == 0) {
                Intent intentAll = new Intent(MainActivity.this, SelectActivity.class);
                intentAll.putExtra(GlobalConstant.RECENT_OR_FAVORITE, GlobalConstant.ALL);
                startActivity(intentAll);
            } else if (mPosition == 1) {
                Intent intentRecent = new Intent(MainActivity.this, SelectActivity.class);
                intentRecent.putExtra(GlobalConstant.RECENT_OR_FAVORITE, GlobalConstant.RECENT);
                startActivity(intentRecent);
            } else if (mPosition == 2) {
                Intent intentFav = new Intent(MainActivity.this, SelectActivity.class);
                intentFav.putExtra(GlobalConstant.RECENT_OR_FAVORITE, GlobalConstant.FAV);
                startActivity(intentFav);
            }
        } else if (idView == R.id.nav_browse_pdf) {
            Utils.openDocument(this);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.nav_share_app) {
            Utils.shareApp(this);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.nav_rate_app) {
            Utils.showRateDialog(this);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.nav_language) {
            Intent intentLang = new Intent(this, LanguageActivity.class);
            startActivity(intentLang);
            finish();
        } else if (idView == R.id.nav_feedback) {
            Utils.feedbackApp(this);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.nav_policy) {
            drawerLayout.closeDrawers();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_search) {
            if (Utils.checkPermission(this)) {
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);

            } else {
                Toast.makeText(this, getResources().getString(R.string.toast_need_permission), Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.item_settings) {
            Intent intentSettings = new Intent(this, SettingsActivity.class);
            startActivity(intentSettings);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GlobalConstant.REQUEST_CODE_PICK_FILE) {
            if (data != null) {
                Uri uri = data.getData();
                new SendData(this, uri).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onSortByDateUp() {
        Intent intent = new Intent(GlobalConstant.ACTION_SORT_BY_DATE_UP);
        sendBroadcast(intent);
    }

    @Override
    public void onSortByDateDown() {

        Intent intent = new Intent(GlobalConstant.ACTION_SORT_BY_DATE_DOWN);
        sendBroadcast(intent);
    }

    @Override
    public void onSortAtoZ() {
        Toast.makeText(this, "sdfsfds", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GlobalConstant.ACTION_SORT_BY_A_Z);
        sendBroadcast(intent);
    }

    @Override
    public void onSortZtoA() {
        Intent intent = new Intent(GlobalConstant.ACTION_SORT_BY_Z_A);
        sendBroadcast(intent);
    }

    @Override
    public void onSortFileSizeUp() {
        Intent intent = new Intent(GlobalConstant.ACTION_SORT_BY_SIZE_UP);
        sendBroadcast(intent);
    }

    @Override
    public void onSortFileSizeDown() {
        Intent intent = new Intent(GlobalConstant.ACTION_SORT_BY_SIZE_DOWN);
        sendBroadcast(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            exitDialog.show();
        } else {
            doubleBack = true;
            Toast.makeText(this, getResources().getString(R.string.toast_exit_app), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBack = false, 2000);
        }
    }

    public static class SendData extends AsyncTask<Void, Void, Void> {
        WeakReference<MainActivity> weakReference;
        Intent intent;
        Uri intentData;


        public SendData(MainActivity activity, Uri uri) {
            this.weakReference = new WeakReference<>(activity);
            this.intentData = uri;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String filename = Utils.getFileNameFromUri(intentData, weakReference.get().getContentResolver());
            File pathFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/DocumentReader/");
            if (!pathFolder.exists()) {
                pathFolder.mkdirs();
            }
            String pathCopy = pathFolder + "/" + filename;
            Utils.copy(weakReference.get(), intentData, pathCopy);
            File file = new File(pathCopy);

            Utils.openFile(weakReference.get(), file);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }

}