package me.lemuel.blisslemuel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import me.lemuel.blisslemuel.util.ActivityUtils;
import me.lemuel.blisslemuel.view.main.DaggerMainComponent;
import me.lemuel.blisslemuel.view.main.MainModule;
import me.lemuel.blisslemuel.view.main.MainPresenter;
import me.lemuel.blisslemuel.view.main.MainView;


public class MainActivity extends AppCompatActivity {

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainView mainView = (MainView) getSupportFragmentManager()
                .findFragmentById(R.id.content_layout);
        if (mainView == null) {
            mainView = MainView.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mainView, R.id.content_layout);
        }

        DaggerMainComponent.builder().mainModule(new MainModule(mainView))
                .build().inject(this);
    }
}
