package me.lemuel.blisslemuel.view.main;

import dagger.Component;

/**
 * Created by lemuel on 2017/2/24.
 */

@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainView mainView);
}
