package me.lemuel.adore.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lemuel on 2017/2/28.
 */

public class ApiManager {

    private static Retrofit MovieRetrofit = null;
    private final static String MOVIE_URL = "https://api.douban.com/";
    private final static String TRANSLATE_URL = "http://fanyi.youdao.com/";
    private static final String ONLINE_MUSIC_URL = "http://tingapi.ting.baidu.com";
    private static Retrofit translatorRetrofit;
    private static Retrofit onlineMusicRetrofit;

    public static Retrofit getDBRetrofit() {
        if (MovieRetrofit == null) {
            MovieRetrofit = new Retrofit.Builder()
                    .baseUrl(MOVIE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return MovieRetrofit;
    }

    public static Retrofit getTranslatorRetrofit() {
        if (translatorRetrofit == null) {
            translatorRetrofit = new Retrofit.Builder()
                    .baseUrl(TRANSLATE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return translatorRetrofit;
    }

    public static Retrofit getOnlineMusicRetrofit() {
        if (onlineMusicRetrofit == null) {
            onlineMusicRetrofit = new Retrofit.Builder()
                    .baseUrl(ONLINE_MUSIC_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder().build())
                    .build();
        }
        return onlineMusicRetrofit;
    }

}
