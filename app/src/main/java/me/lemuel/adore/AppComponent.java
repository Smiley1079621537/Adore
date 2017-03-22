package me.lemuel.adore;

import dagger.Component;
import io.realm.Realm;
import me.lemuel.adore.api.DBSerivce;

/**
 * Created by lemuel on 2017/2/28.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {

    DBSerivce getDoubanService();

    Realm getRealm();
}
