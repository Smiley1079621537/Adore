package me.lemuel.adore;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import attr.BackgroundAttr;
import attr.CollapsingToolbarLayoutAttr;
import attr.FabButtonAttr;
import attr.NavigationViewAttr;
import attr.TabLayoutAttr;
import attr.TabLayoutIndicatorAttr;
import butterknife.ButterKnife;
import me.lemuel.adore.api.ApiManager;
import me.lemuel.adore.component.AppComponent;
import me.lemuel.adore.component.DaggerAppComponent;
import me.lemuel.adore.module.AppModule;
import solid.ren.skinlibrary.SkinConfig;
import solid.ren.skinlibrary.base.SkinBaseApplication;
import zlc.season.rxdownload2.RxDownload;

public class App extends SkinBaseApplication {

    private static AppComponent mAppComponent;
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
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.setCanChangeFont(true);
        SkinConfig.setDebug(false);
        /**
         * 换肤默认只支持 android 的常用控件，对于支持库的控件和自定义控件的换肤需要动态添加（
         * 例如：
         * dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimaryDark);），
         * 在布局文件中使用skin:enable="true"是无效的。
         */
        SkinConfig.addSupportAttr("tabLayoutIndicator", new TabLayoutIndicatorAttr());
        SkinConfig.addSupportAttr("background",new BackgroundAttr());
        SkinConfig.addSupportAttr("navigationView",new NavigationViewAttr());
        SkinConfig.addSupportAttr("tabLayout",new TabLayoutAttr());
        SkinConfig.addSupportAttr("collapsingToolbarLayout",new CollapsingToolbarLayoutAttr());
        SkinConfig.addSupportAttr("fabButton",new FabButtonAttr());
        SkinConfig.enableGlobalSkinApply();
    }

    //字体配置
    /*private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/wyue.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }*/

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
        }
        return mAppComponent;
    }

    public static RxDownload getRxDownload() {
        if (mRxDownload == null) {
            mRxDownload = RxDownload.getInstance(getAppContext())
                    .retrofit(ApiManager.getDBRetrofit())//若需要自己的retrofit客户端,可在这里指定
                    .maxThread(3)                        //设置最大线程
                    .maxRetryCount(3)                    //设置下载失败重试次数
                    .maxDownloadNumber(5);
        }
        return mRxDownload;
    }

}
