package me.lemuel.blisslemuel;

import dagger.Module;
import dagger.Provides;
import me.lemuel.blisslemuel.api.DoubanService;

/**
 * Created by lemuel on 2017/2/28.
 */

@Module
public class AppModule {

    @Provides
    public DoubanService provideMoviesService() {
        return DoubanManager.getDoubanManager().create(DoubanService.class);
    }
}
