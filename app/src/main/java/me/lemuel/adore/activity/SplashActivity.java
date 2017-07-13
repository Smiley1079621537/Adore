package me.lemuel.adore.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.lemuel.adore.R;
import me.lemuel.adore.base.BaseActivity;
import me.lemuel.adore.base.Constants;
import me.lemuel.adore.service.SplashDownLoadService;
import me.lemuel.adore.util.DownLoadUtils;
import me.lemuel.adore.util.SerializableUtils;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static me.lemuel.adore.base.Constants.SPLASH_FILE_NAME;

public class SplashActivity extends BaseActivity
        implements View.OnClickListener, EasyPermissions.PermissionCallbacks ,DownLoadUtils.DownLoadInterFace{

    private static final String TAG = SplashActivity.class.getName();
    @BindView(R.id.sp_bg)
    ImageView mSpBg;
    @BindView(R.id.sp_jump_btn)
    Button mSpJumpBtn;

    private Splash mSplash;

    private CountDownTimer countDownTimer = new CountDownTimer(3200, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSpJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mSpJumpBtn.setText("跳过(" + 0 + "s)");
            gotoLoginOrMainActivity();
        }
    };
    private Splash mLocalSplash;


    private void gotoLoginOrMainActivity() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSDCardPermission();
        mSpBg.setOnClickListener(this);
        mSpJumpBtn.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public static final int RC_PERMISSION = 123;

    private void checkSDCardPermission() {
        if (EasyPermissions.hasPermissions(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //initSplashImage();
           // startImageDownLoad();
            String path = this.getFilesDir() + "/splash.png";
            if (FileUtils.isFileExists(path)) {
                Bitmap splashBitmap = BitmapFactory.decodeFile(path);
                mSpBg.setImageBitmap(splashBitmap);
            }else {
                startImageDownLoad();
            }
        } else {
            EasyPermissions.requestPermissions(this, "需要您提供【**】App 读写内存卡权限来确保应用更好的运行",
                    RC_PERMISSION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    private void startImageDownLoad() {
        SplashDownLoadService.startDownLoadSplashImage(this, Constants.DOWNLOAD_SPLASH);
    }

    private void initSplashImage() {
        mSplash = getLocalSplash();
        //如果取出本地序列化的对象成功 则进行图片加载和倒计时
        if (mSplash != null && !TextUtils.isEmpty(mSplash.savePath)) {
            LogUtils.d("SplashActivity 获取本地序列化成功" + mSplash);
            mSpBg.setImageURI(Uri.parse(mSplash.savePath));
            startClock();//加载成功 开启倒计时
        } else {
            // 如果本地没有 直接跳转
            mSpJumpBtn.setVisibility(View.INVISIBLE);
            mSpJumpBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoLoginOrMainActivity();
                }
            },4000);
        }
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_splash;
    }

    private void startClock() {
        mSpJumpBtn.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp_bg:
                gotoWebActivity();
                break;
            case R.id.sp_jump_btn:
                gotoLoginOrMainActivity();
                break;
        }
    }

    private void gotoWebActivity() {
        if (mSplash != null && mSplash.click_url != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("url", mSplash.click_url);
            intent.putExtra("title", mSplash.title);
            intent.putExtra("fromSplash", true);
            intent.putExtra("needShare", false);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    public Splash getLocalSplash() {
        Splash splash = null;
        try {
            File serializableFile = SerializableUtils
                    .getSerializableFile(Constants.SPLASH_PATH, SPLASH_FILE_NAME);
            splash = (Splash) SerializableUtils.readObject(serializableFile);
        } catch (IOException e) {
            LogUtils.e("SplashActivity 获取本地序列化闪屏失败" + e.getMessage());
        }
        return splash;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void afterDownLoad(ArrayList<String> savePaths) {
        if (savePaths.size() == 1) {
            LogUtils.d("闪屏页面下载完成" + savePaths);
            if (mSplash != null) {
                mSplash.savePath = savePaths.get(0);
            }
            // 序列化 Splash 到本地
            SerializableUtils.writeObject(mSplash, Constants.SPLASH_PATH + "/" + SPLASH_FILE_NAME);
        } else {
            LogUtils.d("闪屏页面下载失败" + savePaths);
        }
    }
}
