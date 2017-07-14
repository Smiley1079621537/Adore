package me.lemuel.adore.mvp.music;

import me.lemuel.adore.base.BasePresenter;
import me.lemuel.adore.base.BaseView;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;

public interface MusicContract {

    interface View extends BaseView<Presenter>{

        void doPlayMusic(Music music);

        void responseMusicList(OnlineMusicList onlineMusicList);
    }

    interface Presenter extends BasePresenter{

        void requestMusic(SongListInfo listInfo, int offset);

        void doPlay(OnlineMusic music);

        void getAllNote();
    }
}
