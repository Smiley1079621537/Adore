package me.lemuel.adore;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by lemuel on 2017/2/24.
 */

public class App extends Application {

    private static AppComponent mAppComponent;

    static {
        PlatformConfig.setWeixin("wxb91475a5accdcf0e", "6b0e4fda3c4f7c2372753846a0d2aa06");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Realm.init(this);
        UMShareAPI.get(this);
        Config.DEBUG = true;
    }

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
        }
        return mAppComponent;
    }
}
