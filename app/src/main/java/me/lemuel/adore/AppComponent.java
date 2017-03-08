package me.lemuel.adore;

import dagger.Component;
import io.realm.Realm;
import me.lemuel.adore.api.DoubanService;

/**
 * Created by lemuel on 2017/2/28.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {
    DoubanService getDoubanService();

    Realm getRealm();
}
