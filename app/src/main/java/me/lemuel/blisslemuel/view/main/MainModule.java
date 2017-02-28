package me.lemuel.blisslemuel.view.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lemuel on 2017/2/27.
 */

@Module
public class MainModule {

    private final MainContract.View mView;

    public MainModule(MainView view) {
        mView = view;
    }

    @Provides
    MainContract.View provideMainView() {
        return mView;
    }
}