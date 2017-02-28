package me.lemuel.blisslemuel.view.main;

import me.drakeet.multitype.Items;
import me.lemuel.blisslemuel.BasePresenter;
import me.lemuel.blisslemuel.BaseView;

/**
 * Created by lemuel on 2017/2/27.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showToast(String msg);

        void showLoading();

        void hideLoding();

        void loadData(Items items);
    }

    interface Presenter extends BasePresenter {
        void onCreate();
    }
}
