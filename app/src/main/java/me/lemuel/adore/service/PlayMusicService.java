package me.lemuel.adore.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.blankj.utilcode.util.ImageUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.lemuel.adore.R;
import me.lemuel.adore.activity.HomeActivity;
import me.lemuel.adore.base.ImmanuelAction;
import me.lemuel.adore.bean.music.Music;

/**
 * Created by Immanuel on 2017/05/09.
 */
public class PlayMusicService extends Service {

    private static final int NOTIFY_ID = 777;
    private MediaPlayer mPlayer = new MediaPlayer();
    private RemoteViews mRemoteViews;
    private NotificationManager mNotificationManager;
    private boolean first = true;
    private Bitmap mBitmap;
    private Notification mNotification;
    private BroadcastReceiver mBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case ImmanuelAction.ACTION_PALY_PAUSE://播放or暂停
                       if (mPlayer.isPlaying()){
                           mPlayer.pause();
                           mRemoteViews.setImageViewResource(R.id.notify_playPause,android.R.drawable.ic_media_play);
                       }else {
                           mPlayer.start();
                           mRemoteViews.setImageViewResource(R.id.notify_playPause,android.R.drawable.ic_media_pause);
                       }
                       break;
                }
                mNotificationManager.notify(NOTIFY_ID,mNotification);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ImmanuelAction.ACTION_PALY_PAUSE);
        registerReceiver(mBroadcastReceiver,filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final Music music = (Music) intent.getSerializableExtra("music");
            mRemoteViews.setTextViewText(R.id.notify_title, music.getSonginfo().getTitle());
            mRemoteViews.setTextViewText(R.id.notify_artist, music.getSonginfo().getAuthor());
            mNotificationManager.notify(NOTIFY_ID, mNotification);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(music.getSonginfo().getPic_radio());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("GET");
                        if (conn.getResponseCode() == 200) {
                            InputStream inputStream = conn.getInputStream();
                            mBitmap = ImageUtils.getBitmap(inputStream);
                            mRemoteViews.setImageViewBitmap(R.id.notify_picture, mBitmap);
                        } else {
                            mRemoteViews.setImageViewResource(R.id.notify_picture, R.drawable.notify);
                        }
                        mNotificationManager.notify(NOTIFY_ID, mNotification);
                    }
                    catch (OutOfMemoryError e){
                        mRemoteViews.setImageViewBitmap(R.id.notify_picture, mBitmap);
                        mNotificationManager.notify(NOTIFY_ID, mNotification);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            try {
                mPlayer.reset();
                mPlayer.setDataSource(music.getBitrate().getFile_link());
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        mRemoteViews.setImageViewResource(R.id.notify_playPause,android.R.drawable.ic_media_pause);
                        mNotificationManager.notify(NOTIFY_ID,mNotification);
                    }
                });
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.pause();
                        mRemoteViews.setImageViewResource(R.id.notify_playPause,android.R.drawable.ic_media_play);
                        mNotificationManager.notify(NOTIFY_ID,mNotification);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initNotification() {
        if (first) {
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mRemoteViews = new RemoteViews(this.getPackageName(),
                    R.layout.notification_layout);
            mRemoteViews.setOnClickPendingIntent(R.id.notify_playPause,PendingIntent.getBroadcast(
                    this,0,new Intent(ImmanuelAction.ACTION_PALY_PAUSE),0));
            Intent intent = new Intent(this, HomeActivity.class);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext())
                    .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                    .setContent(mRemoteViews)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("蓝色鲸鱼")
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(getString(R.string.app_name));
            mNotification = builder.build();
            startForeground(NOTIFY_ID, mNotification);
            first = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
