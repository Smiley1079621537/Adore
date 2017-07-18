package me.lemuel.adore.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.R;
import me.lemuel.adore.base.BaseFragment;
import me.lemuel.adore.bean.movie.SubjectsBean;
import me.lemuel.adore.listener.OnLoadMoreListener;
import me.lemuel.adore.mvp.movie.MovieContract;
import me.lemuel.adore.mvp.movie.MoviePresenter;
import me.lemuel.adore.provider.SubjectProvider;

public  class MovieFragment extends BaseFragment
        implements MovieContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private MultiTypeAdapter listAdapter;
    private MoviePresenter moviePresenter;

    @Override
    protected void initEvent() {
        mRecyclerview.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                moviePresenter.loadMore();
            }
        });
    }

    @Override
    protected void initData() {
        listAdapter = new MultiTypeAdapter();
        listAdapter.register(SubjectsBean.class, new SubjectProvider(getActivity()));
        mRecyclerview.setAdapter(listAdapter);
        moviePresenter = new MoviePresenter(this);
        moviePresenter.requestMovie();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_now;
    }

    @Override
    protected void initView(View root) {
        ((DefaultItemAnimator) mRecyclerview.getItemAnimator())
                .setSupportsChangeAnimations(false);//避免notifyDataSetChanged时闪屏
        mRefresh.setOnRefreshListener(this);
        mRefresh.setRefreshing(true);
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoding() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void loadData(Items items) {
        listAdapter.setItems(items);
        listAdapter.notifyDataSetChanged();
    }

    public static Fragment newInstance() {
        return new MovieFragment();
    }

    public void scrollToTop() {
        mRecyclerview.smoothScrollToPosition(0);
    }
}
