package me.lemuel.adore.view.main;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.drakeet.multitype.Items;
import me.lemuel.adore.U;
import me.lemuel.adore.app.App;
import me.lemuel.adore.items.movie.Movie;
import me.lemuel.adore.items.movie.SubjectsBean;

/**
 * Created by lemuel on 2017/2/24.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainNowFragment mainView;
    private U.SchedulersTransformer<Movie> composer;
    private Items items = new Items();

    @Inject
    public MainPresenter(MainNowFragment nowFragment) {
        this.mainView = nowFragment;
        if (composer == null) {
            composer = new U.SchedulersTransformer<>();
        }
    }

    @Inject
    public void setupListeners() {
        mainView.setPresenter(this);
    }

    @Override
    public void onCreate() {
        App.getAppComponent().getDoubanService().getMovies().compose(composer)
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mainView.showLoading();
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        dealMovie(movie);
                    }

                    @Override
                    public void onError(Throwable t) {
                        loadCacheSubjects();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void dealMovie(Movie movie) {
        mainView.hideLoding();
        items.clear();
        Realm realm = App.getMoviesRealm();
        realm.beginTransaction();
        List<SubjectsBean> subjects = movie.getSubjects();
        for (SubjectsBean subject : subjects) {
            subject.setHeight((int) (250 + Math.random() * 200));//瀑布流高度
            items.add(subject);
            realm.copyToRealm(subject);
        }
        realm.commitTransaction();
        mainView.loadData(items);
    }

    void loadMore() {
        loadCacheSubjects();
    }

    private void loadCacheSubjects() {
        mainView.hideLoding();
        RealmResults<SubjectsBean> all = App.getMoviesRealm().where(SubjectsBean.class).findAll();
        if (all != null && all.size() > 0) {
            for (SubjectsBean subject : all) {
                items.add(subject);
            }
            mainView.loadData(items);
        }
    }
}
