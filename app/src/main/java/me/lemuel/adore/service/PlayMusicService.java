package me.lemuel.adore.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import me.lemuel.adore.bean.Music;

/**
 * Created by Immanuel on 2017/05/09.
 */
public class PlayMusicService extends Service{

    private MediaPlayer mPlayer = new MediaPlayer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null){
            Music music = (Music) intent.getSerializableExtra("music");
            try {
                mPlayer.reset();
                mPlayer.setDataSource(music.getBitrate().getFile_link());
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return super.onStartCommand(intent, flags, startId);

    }
}
