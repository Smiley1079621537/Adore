package me.lemuel.adore.view.main;

import dagger.Component;

/**
 * Created by lemuel on 2017/2/24.
 */

@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainNowFragment mainFragment);
}
