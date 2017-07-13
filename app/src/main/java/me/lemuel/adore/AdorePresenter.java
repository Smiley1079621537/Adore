package me.lemuel.adore;

import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;
import me.lemuel.adore.contract.MusicContract;
import me.lemuel.adore.presenter.AdoreBasePresenter;

public class AdorePresenter extends AdoreBasePresenter implements MusicContract.Presenter {

    private  MusicContract.View musicView;

    public AdorePresenter(MusicContract.View view) {
        musicView = view;
    }

    public void requestMusic(SongListInfo listInfo,int offset){
        mDepositary.requestMusic(listInfo, offset, new AdoreCallback<OnlineMusicList>() {
            @Override
            public void onSuccess(OnlineMusicList onlineMusicList) {
                musicView.responseMusicList(onlineMusicList);
            }
        });
    }

    public void doPlay(OnlineMusic music){
        mDepositary.doPlay(music, new AdoreCallback<Music>() {
            @Override
            public void onSuccess(Music music) {
                musicView.doPlayMusic(music);
            }
        });
    }

    public void getAllNote(){
        mLocalDepositary.getNote(new AdoreCallback<NoteDao>() {
            @Override
            public void onSuccess(NoteDao noteDao) {

            }
        });
    }
}
