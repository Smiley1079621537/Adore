package me.lemuel.adore.contract;

import me.lemuel.adore.base.BasePresenter;
import me.lemuel.adore.base.BaseView;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusicList;

public interface MusicContract {

    interface View extends BaseView<Presenter>{

        void doPlayMusic(Music music);

        void responseMusicList(OnlineMusicList onlineMusicList);
    }

    interface Presenter extends BasePresenter{

    }
}
