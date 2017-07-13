package me.lemuel.adore;

import android.annotation.SuppressLint;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import butterknife.ButterKnife;
import zlc.season.rxdownload2.RxDownload;

public class App extends MultiDexApplication {

    @SuppressLint("StaticFieldLeak")
    private static RxDownload mRxDownload;
    private static App appContext;

    static {
        PlatformConfig.setWeixin("wxb91475a5accdcf0e", "35faf2f10421f56816904376a3aaf209");
    }

    public static App getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        appContext = this;
        Fresco.initialize(this);
        LeakCanary.install(this);
        UMShareAPI.get(this);
        Config.DEBUG = true;
        // initCalligraphy();
        Utils.init(this);
    }

    //字体配置
    /*private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/wyue.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }*/

    public static RxDownload getRxDownload() {
        if (mRxDownload == null) {
            mRxDownload = RxDownload.getInstance(getAppContext())
                    //.retrofit()//若需要自己的retrofit客户端,可在这里指定
                    .maxThread(3)                        //设置最大线程
                    .maxRetryCount(3)                    //设置下载失败重试次数
                    .maxDownloadNumber(5);
        }
        return mRxDownload;
    }

}
