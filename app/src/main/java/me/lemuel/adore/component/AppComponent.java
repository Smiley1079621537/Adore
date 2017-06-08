package me.lemuel.adore.component;

import dagger.Component;
import me.lemuel.adore.api.DBSerivce;
import me.lemuel.adore.api.OnlineMusicService;
import me.lemuel.adore.api.TranslateService;
import me.lemuel.adore.module.AppModule;

/**
 * Created by lemuel on 2017/2/28.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {

    DBSerivce getDoubanService();
    TranslateService getTranslateService();
    OnlineMusicService getOnlineMusicService();
}
