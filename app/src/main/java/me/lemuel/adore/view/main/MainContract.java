package me.lemuel.adore.view.main;

import io.realm.RealmResults;
import me.drakeet.multitype.Items;
import me.lemuel.adore.BasePresenter;
import me.lemuel.adore.BaseView;
import me.lemuel.adore.items.movie.SubjectsBean;

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

        void setPresenter(MainPresenter presenter);
    }

    interface Presenter extends BasePresenter {
        void onCreate();
    }
}
