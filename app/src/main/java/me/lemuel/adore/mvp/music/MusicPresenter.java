package me.lemuel.adore.mvp.music;

import me.lemuel.adore.base.AdoreCallback;
import me.lemuel.adore.NoteDao;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;
import me.lemuel.adore.mvp.presenter.AdoreBasePresenter;

public class MusicPresenter extends AdoreBasePresenter implements MusicContract.Presenter {

    private  MusicContract.View musicView;

    public MusicPresenter(MusicContract.View view) {
        musicView = view;
    }

    @Override
    public void requestMusic(SongListInfo listInfo,int offset){
        mDepositary.requestMusic(listInfo, offset, new AdoreCallback<OnlineMusicList>() {
            @Override
            public void onSuccess(OnlineMusicList onlineMusicList) {
                musicView.responseMusicList(onlineMusicList);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    @Override
    public void doPlay(OnlineMusic music){
        mDepositary.doPlay(music, new AdoreCallback<Music>() {
            @Override
            public void onSuccess(Music music) {
                musicView.doPlayMusic(music);
            }

            @Override
            public void onError(Throwable t) {

            }

        });
    }

    @Override
    public void getAllNote(){
        mLocalDepositary.getNote(new AdoreCallback<NoteDao>() {
            @Override
            public void onSuccess(NoteDao noteDao) {

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
