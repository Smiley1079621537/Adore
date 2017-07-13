package me.lemuel.adore.presenter;

import me.drakeet.multitype.Items;
import me.lemuel.adore.AdoreCallback;
import me.lemuel.adore.contract.MovieContract;

/**
 * Created by Immanuel on 2017/2/24.
 */

public class MoviePresenter extends AdoreBasePresenter implements MovieContract.Presenter {

    private MovieContract.View mainView;

    public MoviePresenter(MovieContract.View nowFragment) {
        this.mainView = nowFragment;
        requestMovie();
    }

    public void requestMovie() {
        mDepositary.requestMovie(new AdoreCallback<Items>() {
            @Override
            public void onSuccess(Items items) {
                mainView.hideLoding();
                mainView.loadData(items);
            }
        });
    }

    public void loadMore() {
        mainView.showLoading();
        mainView.hideLoding();
    }
}
