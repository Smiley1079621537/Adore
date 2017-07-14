package me.lemuel.adore.mvp.movie;

import me.lemuel.adore.base.BasePresenter;
import me.lemuel.adore.base.BaseView;
import me.lemuel.adore.bean.translate.Word;

public interface MovieActivityContract {

    interface View extends BaseView<Persenter>{
       void translateResponse(Word word);
    }

    interface Persenter extends BasePresenter{
        void translate(String content);
    }
}
