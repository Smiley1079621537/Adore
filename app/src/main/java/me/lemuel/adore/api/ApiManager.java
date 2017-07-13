package me.lemuel.adore.api;

import android.support.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final int TYPE_MOVIE = 0x01;
    private static final int TYPE_ONLINE_MUSIC = 0x02;
    private static final int TYPE_TRANSLATOR = 0x03;
    private final static String URL_MOVIE = "https://api.douban.com/";
    private final static String URL_TRANSLATE = "http://fanyi.youdao.com/";
    private static final String URL_MUSIC = "http://tingapi.ting.baidu.com";
    private static MovieSerivce movieService;
    private static MusicService musicService;
    private static TranslateService translateService;

    public static MovieSerivce getMovieService(){
        if (movieService == null) {
            movieService = getRetrofit(TYPE_MOVIE).create(MovieSerivce.class);
        }
        return movieService;
    }

    public static TranslateService getTranslateService(){
        if (translateService == null) {
            translateService = getRetrofit(TYPE_TRANSLATOR).create(TranslateService.class);
        }
        return translateService;
    }

    public static MusicService getMusicService(){
        if (musicService == null) {
            musicService = getRetrofit(TYPE_ONLINE_MUSIC).create(MusicService.class);
        }
        return musicService;
    }

    private static Retrofit getRetrofit(int type) {
        Retrofit retrofit = null;
        switch (type) {
            case TYPE_MOVIE:
                retrofit = buildRetrofit(URL_MOVIE);
                break;
            case TYPE_ONLINE_MUSIC:
                retrofit = buildRetrofit(URL_MUSIC);
                break;
            case TYPE_TRANSLATOR:
                retrofit = buildRetrofit(URL_TRANSLATE);
                break;
        }
        return retrofit;
    }

    @NonNull
    private static Retrofit buildRetrofit(String url) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }
}
