package me.lemuel.adore.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.lemuel.adore.App;
import me.lemuel.adore.R;
import me.lemuel.adore.bean.translate.Word;
import me.lemuel.adore.fragment.DialogView;
import me.lemuel.adore.util.CommentUtil;
import solid.ren.skinlibrary.base.SkinBaseActivity;

public class MovieActivity extends SkinBaseActivity {

    @BindView(R.id.movie_img)
    SimpleDraweeView mMovieImg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.search_translate)
    EditText mSearchTranslate;
    @BindView(R.id.translate_result)
    TextView mTranslateResult;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.app_bar)
    AppBarLayout mBarLayout;
    private Disposable mDisposable;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        CommentUtil.setTransparentStatusbar(this);
        setSupportActionBar(mToolbar);
        //显示左上角的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lemuel");
        //不使用左下角的大标题
        collapsingToolbarLayout.setTitleEnabled(false);
        final String imgUrl = getIntent().getStringExtra("image");
        mMovieImg.setImageURI(imgUrl);
        mFab.setOnClickListener(v -> showBottomDialog(imgUrl));

        RxTextView.textChanges(mSearchTranslate)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> doTranslateSearch(charSequence.toString().trim()));
    }

    private void showBottomDialog(String imgUrl) {
        DialogView dialogView = new DialogView();
        dialogView.setImageUrl(imgUrl);
        dialogView.show(getSupportFragmentManager(), "fragment_bottom_dialog");
    }

    //执行单词翻译
    private void doTranslateSearch(String searchText) {
        App.getAppComponent().getTranslateService()
                .getResult(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Word>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Word word) {
                        if (null != word) {
                            int errorCode = word.getErrorCode();
                            if (errorCode == 20) {
                                ToastUtils.showShortSafe("要翻译的文本过长");
                            } else if (errorCode == 40) {
                                ToastUtils.showShortSafe("不支持该语言");
                            } else if (errorCode == 0) {
                                mTranslateResult.setText("");
                                mTranslateResult.setText(word.getTranslation().get(0));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
