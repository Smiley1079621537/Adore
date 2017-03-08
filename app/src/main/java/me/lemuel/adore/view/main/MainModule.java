package me.lemuel.adore.view.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lemuel on 2017/2/27.
 */

@Module
public class MainModule {

    private final MainNowFragment mView;

    public MainModule(MainNowFragment view) {
        mView = view;
    }

    @Provides
    MainNowFragment provideMainView() {
        return mView;
    }

   /* @Provides
    MainPresenter getMainPresenter(MainNowFragment fragment){
        return new MainPresenter(fragment);
    }*/
}