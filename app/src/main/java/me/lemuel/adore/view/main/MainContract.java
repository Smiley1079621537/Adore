package me.lemuel.adore.view.main;

import me.drakeet.multitype.Items;
import me.lemuel.adore.BasePresenter;
import me.lemuel.adore.BaseView;

/**
 * Created by lemuel on 2017/2/27.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showToast(String msg);

        void showLoading();

        void hideLoding();

        void loadData(Items items);

        void setPresenter(MainPresenter presenter);
    }

    interface Presenter extends BasePresenter {
        void onCreate();
    }
}
