package me.lemuel.blisslemuel.view.main;

import dagger.Component;
import me.lemuel.blisslemuel.MainActivity;

/**
 * Created by lemuel on 2017/2/24.
 */

@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
