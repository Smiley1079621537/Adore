package me.lemuel.adore.mvp.movie;

import me.drakeet.multitype.Items;
import me.lemuel.adore.AdoreCallback;
import me.lemuel.adore.mvp.presenter.AdoreBasePresenter;

/**
 * Created by Immanuel on 2017/2/24.
 */

public class MoviePresenter extends AdoreBasePresenter implements MovieContract.Presenter {

    private MovieContract.View movieView;

    public MoviePresenter(MovieContract.View movieView) {
        this.movieView = movieView;
    }

    @Override
    public void loadMore() {
        movieView.showLoading();
        movieView.hideLoding();
    }

    @Override
    public void requestMovie() {
        mDepositary.requestMovie(new AdoreCallback<Items>() {
            @Override
            public void onSuccess(Items items) {
                movieView.hideLoding();
                movieView.loadData(items);
            }
        });
    }
}
