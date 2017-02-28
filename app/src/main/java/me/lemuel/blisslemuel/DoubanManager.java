package me.lemuel.blisslemuel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lemuel on 2017/2/28.
 */

public class DoubanManager {

    private static Retrofit mRetrofit = null;
    private final static String BASE_URL = "https://api.douban.com/";

    public static Retrofit getDoubanManager() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

}
