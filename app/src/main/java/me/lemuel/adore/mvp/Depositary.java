package me.lemuel.adore.mvp;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.lemuel.adore.base.AdoreCallback;
import me.lemuel.adore.base.AdoreSubscriver;
import me.lemuel.adore.api.ApiManager;
import me.lemuel.adore.base.AdoreToast;
import me.lemuel.adore.bean.movie.Movie;
import me.lemuel.adore.bean.movie.SubjectsBean;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;
import me.lemuel.adore.bean.translate.Word;

public class Depositary implements AdoreToast{
    private Items items = new Items();

    private static class DepositaryHolder {
        private static Depositary depositary = new Depositary();
    }

    public static Depositary getInstance() {
        return DepositaryHolder.depositary;
    }

    public void translate(String content, final AdoreCallback<Word> callback) {
        ApiManager.getTranslateService().getResult(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdoreSubscriver<Word>() {
                    @Override
                    public void onNext(Word word) {
                        if (null != word) {
                            int errorCode = word.getErrorCode();
                            if (errorCode == 20) {
                                ToastUtils.showShortSafe(TOAST_TRANSLATE_TEXT_TOO_LONG);
                            } else if (errorCode == 40) {
                                ToastUtils.showShortSafe(TOAST_DONT_SUPPORT_LANGUAGE);
                            } else if (errorCode == 0) {
                                callback.onSuccess(word);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });

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

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        callback.onError(t);
                    }
                });
    }

    public void requestMusic(SongListInfo songListInfo, int offset,
                             final AdoreCallback<OnlineMusicList> callback) {
        ApiManager.getMusicService().getSongList(songListInfo.getType(),
                String.valueOf(20), String.valueOf(offset))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdoreSubscriver<OnlineMusicList>() {
                    @Override
                    public void onNext(OnlineMusicList musicList) {
                        super.onNext(musicList);
                        callback.onSuccess(musicList);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void doPlay(OnlineMusic onlineMusic, final AdoreCallback<Music> callback) {
        ApiManager.getMusicService().getMusic(onlineMusic.getSong_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AdoreSubscriver<Music>() {
                    @Override
                    public void onNext(Music music) {
                        super.onNext(music);
                        callback.onSuccess(music);
                        LogUtils.i(music.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

}
