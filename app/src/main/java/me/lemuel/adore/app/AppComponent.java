package me.lemuel.adore.app;

import dagger.Component;
import me.lemuel.adore.api.DBSerivce;
import me.lemuel.adore.api.TranslateService;

/**
 * Created by lemuel on 2017/2/28.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {
    DBSerivce getDoubanService();
    TranslateService getTranslateService();
}
