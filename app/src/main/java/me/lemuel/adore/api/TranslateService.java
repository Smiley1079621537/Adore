package me.lemuel.adore.api;

import io.reactivex.Observable;
import me.lemuel.adore.bean.translate.Word;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lemuel on 2017/03/31.
 */
public interface TranslateService {

    //http://fanyi.youdao.com/openapi.do?keyfrom=AdoreDemo&key=245262782&type=data&doctype=json&version=1.1&q=Good
    @GET("openapi.do?keyfrom=yesheis&key=1280256434&type=data&doctype=json&version=1.1")
    Observable<Word> getResult(@Query("q") String q);
}
