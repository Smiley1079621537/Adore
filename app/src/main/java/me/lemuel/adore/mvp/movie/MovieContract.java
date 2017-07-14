package me.lemuel.adore.mvp.movie;

import me.drakeet.multitype.Items;
import me.lemuel.adore.base.BasePresenter;
import me.lemuel.adore.base.BaseView;

/**
 * Created by lemuel on 2017/2/27.
 */

public interface MovieContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoding();

        void loadData(Items items);
    }

    interface Presenter extends BasePresenter {

        void loadMore();

        void requestMovie();
    }
}
