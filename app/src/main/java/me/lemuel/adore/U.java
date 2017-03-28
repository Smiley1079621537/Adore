package me.lemuel.adore;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lemuel
 * time   : 2017/03/28
 */
public class U {
    public static class SchedulersTransformer<T> implements FlowableTransformer<T, T> {
        @Override
        public Publisher<T> apply(Flowable<T> upstream) {
            return upstream.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}