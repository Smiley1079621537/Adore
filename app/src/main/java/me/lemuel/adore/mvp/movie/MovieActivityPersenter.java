package me.lemuel.adore.mvp.movie;

import me.lemuel.adore.base.AdoreCallback;
import me.lemuel.adore.bean.translate.Word;
import me.lemuel.adore.mvp.presenter.AdoreBasePresenter;

public class MovieActivityPersenter
        extends AdoreBasePresenter implements MovieActivityContract.Persenter {

    private MovieActivityContract.View view;

    public MovieActivityPersenter(MovieActivityContract.View view){
        this.view = view;
    }

    @Override
    public void translate(String content) {
        mDepositary.translate(content, new AdoreCallback<Word>() {
            @Override
            public void onSuccess(Word word) {
                view.translateResponse(word);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
