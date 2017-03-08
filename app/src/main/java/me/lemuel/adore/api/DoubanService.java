package me.lemuel.adore.api;

import io.reactivex.Observable;
import me.lemuel.adore.items.movie.Movie;
import retrofit2.http.GET;

/**
 * Created by lemuel on 2017/2/24.
 */

public interface DoubanService {
    @GET("v2/movie/in_theaters")
    Observable<Movie> getMovies();
}
