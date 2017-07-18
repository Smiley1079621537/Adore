package me.lemuel.adore.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.lemuel.adore.R;
import me.lemuel.adore.base.BaseActivity;
import me.lemuel.adore.bean.translate.Word;
import me.lemuel.adore.mvp.movie.MovieActivityContract;
import me.lemuel.adore.mvp.movie.MovieActivityPersenter;
import me.lemuel.adore.view.DialogView;

public class MovieActivity extends BaseActivity implements MovieActivityContract.View {

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
    @BindView(R.id.notification)
    RichPathView mNotification;

    private String mImgUrl;
    private MovieActivityPersenter mPersenter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_movie;
    }

    @Override
    protected void initData() {
        mImgUrl = getIntent().getStringExtra("image");
        mMovieImg.setImageURI(mImgUrl);
        mPersenter = new MovieActivityPersenter(this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Lemuel");
        }
        collapsingToolbarLayout.setTitleEnabled(false);
    }

    @Override
    protected void initEvent() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog(mImgUrl);
            }
        });
        RxTextView.textChanges(mSearchTranslate).debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(@NonNull CharSequence charSequence) throws Exception {
                        mPersenter.translate(charSequence.toString().trim());
                    }
                });
        pathAnimator();
    }

    private void pathAnimator() {
        RichPath top = mNotification.findRichPathByName("top");
        RichPath bottom = mNotification.findRichPathByName("bottom");
        RichPathAnimator.animate(top)
                .interpolator(new DecelerateInterpolator())
                .rotation(0, 20, -20, 10, -10, 5, -5, 2, -2, 0)
                .duration(4000)
                .andAnimate(bottom)
                .interpolator(new DecelerateInterpolator())
                .rotation(0, 10, -10, 5, -5, 2, -2, 0)
                .startDelay(50)
                .duration(4000)
                .start();
    }

    private void showBottomDialog(String imgUrl) {
        DialogView dialogView = new DialogView();
        dialogView.setImageUrl(imgUrl);
        dialogView.show(getSupportFragmentManager(), "fragment_bottom_dialog");
    }

    @Override
    public void translateResponse(Word word) {
        mTranslateResult.setText(word.getTranslation().get(0));
    }
}
