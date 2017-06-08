package me.lemuel.adore.module;

import dagger.Module;
import dagger.Provides;
import me.lemuel.adore.api.ApiManager;
import me.lemuel.adore.api.DBSerivce;
import me.lemuel.adore.api.OnlineMusicService;
import me.lemuel.adore.api.TranslateService;

/**
 * Created by lemuel on 2017/2/28.
 */

@Module
public class AppModule {

    @Provides
    public DBSerivce provideMoviesService() {
        return ApiManager.getDBRetrofit().create(DBSerivce.class);
    }

    @Provides
    public TranslateService provideTranslateService() {
        return ApiManager.getTranslatorRetrofit().create(TranslateService.class);
    }

    @Provides
    public OnlineMusicService provideOnlineMusicService() {
        return ApiManager.getOnlineMusicRetrofit().create(OnlineMusicService.class);
    }
}
