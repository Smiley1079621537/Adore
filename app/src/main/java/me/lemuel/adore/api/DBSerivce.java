package me.lemuel.adore.api;

import io.reactivex.Flowable;
import me.lemuel.adore.items.movie.Movie;
import retrofit2.http.GET;

/**
 * Created by lemuel on 2017/2/24.
 */

public interface DBSerivce {
    //https://api.douban.com/v2/movie/in_theaters
    @GET("v2/movie/in_theaters")
    Flowable<Movie> getMovies();
}
