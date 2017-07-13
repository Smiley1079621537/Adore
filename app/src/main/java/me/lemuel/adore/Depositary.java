package me.lemuel.adore;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.lemuel.adore.api.ApiManager;
import me.lemuel.adore.bean.movie.Movie;
import me.lemuel.adore.bean.movie.SubjectsBean;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;

public class Depositary {
    private Items items = new Items();

    private static class DepositaryHolder {
        private static Depositary depositary = new Depositary();
    }

    public static Depositary getInstance() {
        return DepositaryHolder.depositary;
    }

    public void requestMovie(final AdoreCallback<Items> callback) {
        ApiManager.getMovieService().getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdoreSubscriver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        items.clear();
                        List<SubjectsBean> subjects = movie.getSubjects();
                        for (SubjectsBean subject : subjects) {
                            subject.setHeight((int) (250 + Math.random() * 200));//瀑布流高度
                            items.add(subject);
                        }
                        callback.onSuccess(items);
                    }
                });
    }

    public void requestMusic(SongListInfo songListInfo, int offset,
                             final AdoreCallback<OnlineMusicList> callback) {
        ApiManager.getMusicService()
                .getSongList(songListInfo.getType(),
                        String.valueOf(20),
                        String.valueOf(offset))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdoreSubscriver<OnlineMusicList>() {
                    @Override
                    public void onNext(OnlineMusicList musicList) {
                        super.onNext(musicList);
                        callback.onSuccess(musicList);
                    }
                });
    }

    public void doPlay(OnlineMusic onlineMusic, final AdoreCallback<Music> callback) {
        ApiManager.getMusicService()
                .getMusic(onlineMusic.getSong_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdoreSubscriver<Music>() {
                    @Override
                    public void onNext(Music music) {
                        super.onNext(music);
                        callback.onSuccess(music);
                        LogUtils.i(music.toString());
                    }
                });
    }

}
