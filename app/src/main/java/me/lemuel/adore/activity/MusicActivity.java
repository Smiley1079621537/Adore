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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.fresco.processors.BlurPostprocessor;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.AdorePresenter;
import me.lemuel.adore.R;
import me.lemuel.adore.base.Extras;
import me.lemuel.adore.bean.music.Music;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;
import me.lemuel.adore.contract.MusicContract;
import me.lemuel.adore.provider.BillboardViewProvider;
import me.lemuel.adore.provider.OnlineMusicViewProvider;
import me.lemuel.adore.service.PlayMusicService;


public class MusicActivity extends AppCompatActivity implements MusicContract.View {

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

    private boolean headerAdded = false;
    private MultiTypeAdapter mAdapter;
    private OnlineMusicViewProvider mMusicViewProvider;
    private AdorePresenter mAdorePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_music);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        SongListInfo songListInfo = getIntent().getParcelableExtra(Extras.MUSIC_LIST_TYPE);
        mAdorePresenter = new AdorePresenter(this);
        mAdorePresenter.requestMusic(songListInfo, 0);
    }

    private void initView() {
        setTranslucent(this);
        ButterKnife.bind(this);
        mMusicViewProvider = new OnlineMusicViewProvider();
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Immanuel");
        }
        mToolbarLayout.setTitleEnabled(false);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(OnlineMusicList.Billboard.class, new BillboardViewProvider());
        mAdapter.register(OnlineMusic.class, mMusicViewProvider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initEvent() {
        mMusicViewProvider.setOnMusicClick(new OnlineMusicViewProvider.OnMusicClick() {
            @Override
            public void onItemMusicClick(OnlineMusic onlineMusic) {
                mAdorePresenter.doPlay(onlineMusic);
            }
        });
    }

    @Override
    public void doPlayMusic(Music music) {
        startService(new Intent(MusicActivity.this, PlayMusicService.class).putExtra("music", music));
        mToolbar.setTitle(music.getSonginfo().getTitle());
    }

    @Override
    public void responseMusicList(OnlineMusicList onlineMusicList) {
        Items items = new Items();
        if (!headerAdded) {
            OnlineMusicList.Billboard billboard = onlineMusicList.getBillboard();
            mIvCover.setImageURI(Uri.parse(billboard.getPic_s640()));
            mIvHeaderBg.setImageURI(Uri.parse(billboard.getPic_s640()));
            mTvTitle.setText(billboard.getName());
            mTvUpdateDate.setText(billboard.getUpdate_date());
            mTvComment.setText(billboard.getComment());
            blurHeaderView(mIvHeaderBg, billboard);
            headerAdded = true;
        }
        items.addAll(onlineMusicList.getSong_list());
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();

    }

    //图片模糊处理
    public void blurHeaderView(SimpleDraweeView draweeView, OnlineMusicList.Billboard billboard) {
        Postprocessor postprocessor = new BlurPostprocessor(draweeView.getContext(), 50);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(billboard.getPic_s640()))
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    public void setTranslucent(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }
}
