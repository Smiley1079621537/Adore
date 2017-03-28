package me.lemuel.adore.app;

import dagger.Module;
import dagger.Provides;
import me.lemuel.adore.api.AppManager;
import me.lemuel.adore.api.DBSerivce;

/**
 * Created by lemuel on 2017/2/28.
 */

@Module
public class AppModule {

    @Provides
    public DBSerivce provideMoviesService() {
        return AppManager.getDBRetrofit().create(DBSerivce.class);
    }
}
