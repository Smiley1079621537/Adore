package me.lemuel.adore.app;

import dagger.Module;
import dagger.Provides;
import me.lemuel.adore.api.AppManager;
import me.lemuel.adore.api.DBSerivce;
import me.lemuel.adore.api.TranslateService;

/**
 * Created by lemuel on 2017/2/28.
 */

@Module
public class AppModule {
    @Provides
    public DBSerivce provideMoviesService() {
        return AppManager.getDBRetrofit().create(DBSerivce.class);
    }

    @Provides
    public TranslateService provideTranslateService() {
        return AppManager.getTranslatorRetrofit().create(TranslateService.class);
    }
}
