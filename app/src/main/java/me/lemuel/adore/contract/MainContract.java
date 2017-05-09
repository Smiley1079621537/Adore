package me.lemuel.adore.contract;

import me.drakeet.multitype.Items;
import me.lemuel.adore.base.BasePresenter;
import me.lemuel.adore.base.BaseView;
import me.lemuel.adore.presenter.MainPresenter;

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
