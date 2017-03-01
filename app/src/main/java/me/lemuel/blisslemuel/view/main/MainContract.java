package me.lemuel.blisslemuel.view.main;

import io.realm.RealmResults;
import me.drakeet.multitype.Items;
import me.lemuel.blisslemuel.BasePresenter;
import me.lemuel.blisslemuel.BaseView;
import me.lemuel.blisslemuel.items.movie.SubjectsBean;

/**
 * Created by lemuel on 2017/2/27.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showToast(String msg);

        void showLoading();

        void hideLoding();

        void loadData(Items items);

        void loadCacheData(RealmResults<SubjectsBean> results);
    }

    interface Presenter extends BasePresenter {
        void onCreate();
    }
}
