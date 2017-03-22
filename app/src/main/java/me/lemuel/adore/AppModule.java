package me.lemuel.adore;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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

    @Provides
    public Realm provideRealm(){
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("lemuel.realm")//配置名字
                .encryptionKey(new byte[64])//加密用字段,不是64位会报错
                .schemaVersion(1)//版本号
                .build();
        return Realm.getInstance(configuration);
    }
}
