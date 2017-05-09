package me.lemuel.adore.presenter;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import me.drakeet.multitype.Items;
import me.lemuel.adore.App;
import me.lemuel.adore.fragment.MainNowFragment;
import me.lemuel.adore.bean.movie.Movie;
import me.lemuel.adore.bean.movie.SubjectsBean;
import me.lemuel.adore.contract.MainContract;
import me.lemuel.adore.util.ToastUtils;

/**
 * Created by Immanuel on 2017/2/24.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainNowFragment mainView;
    private Items items = new Items();

    @Inject
    public MainPresenter(MainNowFragment nowFragment) {
        this.mainView = nowFragment;
    }

    @Inject
    public void setupListeners() {
        mainView.setPresenter(this);
    }

    @Override
    public void onCreate() {
        App.getAppComponent().getDoubanService().getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        ToastUtils.showShortToast(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Immanuel","完成！");
                    }
                });
    }

    private void dealMovie(Movie movie) {
        mainView.hideLoding();
        items.clear();
        Realm realm = App.getMoviesRealm();
        realm.beginTransaction();
        Log.d("Immanuel",movie.getSubjects().toString());
        List<SubjectsBean> subjects = movie.getSubjects();
        for (SubjectsBean subject : subjects) {
            subject.setHeight((int) (250 + Math.random() * 200));//瀑布流高度
            items.add(subject);
            realm.copyToRealm(subject);
        }
        realm.commitTransaction();
        mainView.loadData(items);
    }

    public void loadMore() {
        mainView.showLoading();
        RealmResults<SubjectsBean> all = App.getMoviesRealm().where(SubjectsBean.class).findAll();
        if (all != null && all.size() > 0) {
            for (SubjectsBean subject : all) {
                items.add(subject);
            }
            mainView.loadData(items);
        }
        mainView.hideLoding();
    }
}
