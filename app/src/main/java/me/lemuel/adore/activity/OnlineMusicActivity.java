package me.lemuel.adore.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.fresco.processors.BlurPostprocessor;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.App;
import me.lemuel.adore.R;
import me.lemuel.adore.base.Extras;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;
import me.lemuel.adore.fragment.DialogView;
import me.lemuel.adore.provider.BillboardViewProvider;
import me.lemuel.adore.provider.OnlineMusicViewProvider;
import me.lemuel.adore.service.PlayMusicService;

public class OnlineMusicActivity extends AppCompatActivity {

    private static final int MUSIC_LIST_SIZE = 20;
    @BindView(R.id.lv_online_music_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_header_bg)
    SimpleDraweeView mIvHeaderBg;
    @BindView(R.id.iv_cover)
    SimpleDraweeView mIvCover;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_update_date)
    TextView mTvUpdateDate;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private int mOffset = 0;
    private boolean headerAdded = false;
    private MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_music);
        ButterKnife.bind(this);
        setTranslucent(this);
        initView();
        requestOnlineMusic();
    }

    private void requestOnlineMusic() {
        SongListInfo songListInfo = getIntent().getParcelableExtra(Extras.MUSIC_LIST_TYPE);
        App.getAppComponent().getOnlineMusicService()
                .getSongList(songListInfo.getType(), String.valueOf(MUSIC_LIST_SIZE), String.valueOf(mOffset))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OnlineMusicList>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(OnlineMusicList onlineMusicList) {
                        Items items = new Items();
                        if (!headerAdded) {
                            OnlineMusicList.Billboard billboard = onlineMusicList.getBillboard();
                            mIvCover.setImageURI(Uri.parse(billboard.getPic_s640()));
                            mIvHeaderBg.setImageURI(Uri.parse(billboard.getPic_s640()));
                            mTvTitle.setText(billboard.getName());
                            mTvUpdateDate.setText(billboard.getUpdate_date());
                            mTvComment.setText(billboard.getComment());
                            blurHeaderView(billboard);
                            headerAdded = true;
                        }
                        items.addAll(onlineMusicList.getSong_list());
                        mAdapter.setItems(items);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("imm", t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        OnlineMusicViewProvider musicViewProvider = new OnlineMusicViewProvider();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Immanuel");
        mToolbarLayout.setTitleEnabled(false);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(OnlineMusicList.Billboard.class, new BillboardViewProvider());
        mAdapter.register(OnlineMusic.class, musicViewProvider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        musicViewProvider.setOnMusicClick(onlineMusic -> {
            Log.i("LM - onItemMusicClick:",onlineMusic.toString());
            App.getAppComponent().getOnlineMusicService().getMusic(onlineMusic.getSong_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Music>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                            s.request(Long.MAX_VALUE);
                        }

                        @Override
                        public void onNext(Music music) {
                            Log.i("LM - onNext:",music.toString());
                            startService(new Intent(OnlineMusicActivity.this, PlayMusicService.class).putExtra("music",music));
                            mToolbar.setTitle(music.getSonginfo().getTitle());
                        }

                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });
    }

    private void showBottomDialog(String imgUrl) {
        DialogView dialogView = new DialogView();
        dialogView.setImageUrl(imgUrl);
        dialogView.show(getSupportFragmentManager(), "fragment_bottom_dialog");
    }

    /**
     * 榜单背景图片模糊处理
     *
     * @param billboard
     */
    private void blurHeaderView(OnlineMusicList.Billboard billboard) {
        Postprocessor postprocessor = new BlurPostprocessor(OnlineMusicActivity.this, 50);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(billboard.getPic_s640()))
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(mIvHeaderBg.getController())
                .build();
        mIvHeaderBg.setController(controller);

    }

    /**
     * 使状态栏透明
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        // 设置状态栏透明
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置根布局的参数
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }
}
