package me.lemuel.adore.app;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import butterknife.ButterKnife;
import me.lemuel.adore.BuildConfig;
import me.lemuel.adore.R;
import me.lemuel.adore.api.AppManager;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import zlc.season.rxdownload2.RxDownload;

public class App extends Application {

    private static AppComponent mAppComponent;
    @SuppressLint("StaticFieldLeak")
    private static RxDownload mRxDownload;
    private static App appContext;

    static {
        PlatformConfig.setWeixin("wxb91475a5accdcf0e", "6b0e4fda3c4f7c2372753846a0d2aa06");
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
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/app.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
        }
        return mAppComponent;
    }

    public static RxDownload getRxDownload(Context context) {
        if (mRxDownload == null) {
            mRxDownload = RxDownload.getInstance(context)
                    .retrofit(AppManager.getDBRetrofit())               //若需要自己的retrofit客户端,可在这里指定
                    .maxThread(3)                                       //设置最大线程
                    .maxRetryCount(3)                                   //设置下载失败重试次数
                    .maxDownloadNumber(5);
        }
        return mRxDownload;
    }
}
