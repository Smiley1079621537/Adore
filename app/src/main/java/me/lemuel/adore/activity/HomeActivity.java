package me.lemuel.adore.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.TabPagerAdapter;
import me.lemuel.adore.fragment.MovieFragment;
import me.lemuel.adore.fragment.MusicFragment;
import me.lemuel.adore.util.BitmapUtil;
import me.lemuel.adore.util.CommentUtil;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CAMERA = 0x01;
    private static final int REQUEST_ALBUM = 0X02;
    private boolean isCollapsed = false;//是否被折叠
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CommentUtil.setTransparentStatusbar(this);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
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

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        mAvatar = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.avatar);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        Fragment[] fragments = new Fragment[2];
        fragments[0] = MovieFragment.newInstance();
        fragments[1] = MusicFragment.newInstance();
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
        tabPagerAdapter.setTabTitles(new String[]{getString(R.string.has_released),
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
        new AlertDialog.Builder(HomeActivity.this).setTitle("选择来源")
                .setItems(new String[]{"拍照", "图库"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0://拍照
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera, REQUEST_CAMERA);
                        break;
                    case 1://图库
                        Intent picture = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, REQUEST_ALBUM);
                        break;
                }
            }
        });
    }

    private void showAlert(final Activity activity) {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(activity)
                .setImageRecourse(R.drawable.ic_adb_black_24dp)
                .setTextTitle("今日经文")
                .setTextSubTitle("林后5:17")
                .setBody("若有人在基督里，他就是新造的人，\n旧事已过，都变成新的了。")
                .setNegativeColor(R.color.colorAccent)
                .setNegativeButtonText("取消")
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonText("确定")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        Toast.makeText(activity, "Updating", Toast.LENGTH_SHORT).show();
                    }
                })
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

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
               // wxShare();
                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_gallery:

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //微信分享
    private void wxShare() {
        new ShareAction(HomeActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withText("Immanuel分享测试！")
                .share();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
