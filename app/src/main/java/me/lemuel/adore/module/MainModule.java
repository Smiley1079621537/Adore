package me.lemuel.adore.module;

import dagger.Module;
import dagger.Provides;
import me.lemuel.adore.fragment.MainNowFragment;

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
}