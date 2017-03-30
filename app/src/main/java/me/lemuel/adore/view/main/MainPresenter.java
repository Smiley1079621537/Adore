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

import static me.lemuel.adore.app.App.getMoviesRealm;

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
        App.getAppComponent().getDoubanService().getMovies()
                .compose(composer)
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mainView.showLoading();
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        mainView.hideLoding();
                        List<SubjectsBean> subjects = movie.getSubjects();
                        for (SubjectsBean subject : subjects) {
                            subject.setHeight((int) (250 + Math.random() * 200));//瀑布流高度
                            items.add(subject);
                            insertOrUpdate(subject);
                        }
                        mainView.loadData(items);




                    }

                    @Override
                    public void onError(Throwable t) {
                        mainView.hideLoding();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void insertOrUpdate(final SubjectsBean subject) {
        Realm realm = getMoviesRealm();
        realm.beginTransaction();
        realm.copyToRealm(subject);
        realm.commitTransaction();
    }

    public void loadMore() {
        RealmResults<SubjectsBean> all = App.getMoviesRealm().where(SubjectsBean.class).findAll();
        for (SubjectsBean subject : all) {
            items.add(subject);
        }
        mainView.loadData(items);
    }
}
