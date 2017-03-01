package me.lemuel.blisslemuel;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by lemuel on 2017/2/24.
 */

public class App extends Application {

    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Realm.init(this);
    }

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
        }
        return mAppComponent;
    }
}
