package me.lemuel.adore.component;

import dagger.Component;
import me.lemuel.adore.fragment.MainNowFragment;
import me.lemuel.adore.module.MainModule;

/**
 * Created by lemuel on 2017/2/24.
 */

@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainNowFragment mainFragment);
}
