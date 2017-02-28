package me.lemuel.blisslemuel;

import dagger.Component;
import me.lemuel.blisslemuel.api.DoubanService;

/**
 * Created by lemuel on 2017/2/28.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {
    DoubanService getDoubanService();
}
