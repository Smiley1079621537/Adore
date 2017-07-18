package me.lemuel.adore.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.FileUtils;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.TabPagerAdapter;
import me.lemuel.adore.base.BaseActivity;
import me.lemuel.adore.fragment.GankFragment;
import me.lemuel.adore.fragment.MovieFragment;
import me.lemuel.adore.fragment.MusicFragment;
import me.lemuel.adore.util.BitmapUtil;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CAMERA = 0x01;
    private static final int REQUEST_ALBUM = 0X02;
    private boolean isCollapsed = false;
    private ImageView mAvatar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fb;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initEvent() {
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCollapsed)
                    return;
                String tag = "android:switcher:" + viewPager.getId() + ":" + viewPager.getCurrentItem();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment != null && fragment instanceof MovieFragment) {
                    ((MovieFragment) fragment).scrollToTop();
                    mAppBar.setExpanded(true, true);
                }
            }
        });
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                isCollapsed = verticalOffset < 0; // 监听AppBar是否被折叠
            }
        });
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                showAvatarAlert();
            }
        });
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        mAvatar = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.avatar);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initData() {
        Fragment[] fragments = new Fragment[2];
        fragments[0] = MovieFragment.newInstance();
        fragments[1] = MusicFragment.newInstance();
        fragments[2] = GankFragment.newInstance();
                TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(
                getSupportFragmentManager(), fragments);
        tabPagerAdapter
                .setTabTitles(new String[]{
                getString(R.string.has_released),
                getString(R.string.online_music)});
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setAvatar();
    }

    private void setAvatar() {
        String path = this.getFilesDir() + "/avatar.png";
        if (FileUtils.isFileExists(path)) {
            Bitmap avatarBitmap = BitmapFactory.decodeFile(path);
            mAvatar.setImageBitmap(avatarBitmap);
        }
    }

    private void showAvatarAlert() {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle(R.string.choose_source)
                .setItems(new String[]{
                                getString(R.string.take_pictures),
                                getString(R.string.gallery)},
                        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera, REQUEST_CAMERA);
                        break;
                    case 1:
                        Intent picture = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, REQUEST_ALBUM);
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            Bitmap cameraBitmap = (Bitmap) bundle.get("data");
            mAvatar.setImageBitmap(cameraBitmap);
            BitmapUtil.saveImage(this, cameraBitmap);
        } else if (requestCode == REQUEST_ALBUM && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String pathResult = BitmapUtil.getPath(this, selectedImage);
            Bitmap albumBitmap = BitmapFactory.decodeFile(pathResult);
            mAvatar.setImageBitmap(albumBitmap);
            BitmapUtil.saveImage(this, albumBitmap);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                break;
            case R.id.read:

                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_bible:
                goToActivity(MainActivity.class);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
