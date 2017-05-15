package me.lemuel.adore.activity;

import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.TabPagerAdapter;
import me.lemuel.adore.fragment.MainNowFragment;
import me.lemuel.adore.fragment.OnlineMusicFragment;
import me.lemuel.adore.util.BitmapUtil;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CAMERA = 0x01;
    private static final int REQUEST_ALBUM = 0X02;

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

    private boolean isCollapsed = false;//是否被折叠
    private static final String TAG = "lemuel";
    private ImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
        mAvatar = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.avatar);
        mAvatar.setOnClickListener(v -> {
            drawer.closeDrawers();
            showAvatarAlert();
        });
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        setAvatar();

        final Fragment[] fragments = new Fragment[2];
        fragments[0] = MainNowFragment.newInstance();

        fragments[1] = OnlineMusicFragment.newInstance();
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
        tabPagerAdapter.setTabTitles(new String[]{getString(R.string.has_released), getString(R.string.online_music)});

        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            isCollapsed = verticalOffset < 0; // 监听AppBar是否被折叠
        });
        fb.setOnClickListener(v -> {
            if (!isCollapsed)
                return;
            String tag = "android:switcher:" + viewPager.getId() + ":" + viewPager.getCurrentItem();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null && fragment instanceof MainNowFragment) {
                ((MainNowFragment) fragment).scrollToTop();
                mAppBar.setExpanded(true, true);
            }
        });
    }

    private void setAvatar() {
        String path = this.getFilesDir() + "/avatar.png";
        if (FileUtils.isFileExists(path)) {
            Bitmap avatarBitmap = BitmapFactory.decodeFile(path);
            mAvatar.setImageBitmap(avatarBitmap);
        }
    }

    private void showAvatarAlert() {
        new AlertDialog.Builder(HomeActivity.this).setTitle("选择来源").setItems(new String[]{"拍照", "图库"},
                (dialog, which) -> {
                    switch (which) {
                        case 0://拍照
                            //打开系统拍照程序，选择拍照图片
                            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera, REQUEST_CAMERA);
                            break;
                        case 1://图库
                            //打开系统图库程序，选择图片
                            Intent picture = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(picture, REQUEST_ALBUM);
                            break;
                    }
                }).create()
                .show();

    }

    private void showAlert(final Activity activity) {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(activity)
                .setImageRecourse(R.drawable.ic_adb_black_24dp)
                .setTextTitle("今日经文")
                .setTextSubTitle("林后5:17")
                .setBody("若有人在基督里，他就是新造的人，\n旧事已过，都变成新的了。")
                .setNegativeColor(R.color.colorAccent)
                .setNegativeButtonText("取消")
                .setOnNegativeClicked((view, dialog) -> dialog.dismiss())
                .setPositiveButtonText("确定")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked((view, dialog)
                        -> Toast.makeText(activity, "Updating", Toast.LENGTH_SHORT).show())
                .setButtonsGravity(FancyAlertDialog.PanelGravity.CENTER)
                .setAutoHide(false)
                .build();
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
            //说明是拍照
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            Bitmap cameraBitmap = (Bitmap) bundle.get("data");
            mAvatar.setImageBitmap(cameraBitmap);
            //将图片保存在本地
            BitmapUtil.saveImage(this, cameraBitmap);
        } else if (requestCode == REQUEST_ALBUM && resultCode == RESULT_OK && data != null) {
            //图库
            Uri selectedImage = data.getData();
            String pathResult = BitmapUtil.getPath(this, selectedImage);
            //加载存储空间中的图片资源并显示
            Bitmap albumBitmap = BitmapFactory.decodeFile(pathResult);
            mAvatar.setImageBitmap(albumBitmap);
            //保存图片到本地
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
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.read:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
                //wxShare();
                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_gallery:
                Toast.makeText(this, "gallery", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //微信分享
    private void wxShare() {
        UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(HomeActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(HomeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        };
        new ShareAction(HomeActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withText("微信分享测试！test!")
                .setCallback(umShareListener)
                .share();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
