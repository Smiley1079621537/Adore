package me.lemuel.adore.api;

import io.reactivex.Flowable;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusicList;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by lemuel on 2017/05/08.
 */
public interface OnlineMusicService {
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
    @GET("/v1/restserver/ting?method=baidu.ting.billboard.billList")
    Flowable<OnlineMusicList> getSongList(@Query("type") String type, @Query("size") String size, @Query("offset") String offset);

    @Headers("User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
    @GET("/v1/restserver/ting?method=baidu.ting.song.play")
    Flowable<Music> getMusic(@Query("songid") String songId);
}
