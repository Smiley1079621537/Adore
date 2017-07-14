package me.lemuel.adore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.R;
import me.lemuel.adore.bean.movie.SubjectsBean;
import me.lemuel.adore.mvp.movie.MovieContract;
import me.lemuel.adore.listener.OnLoadMoreListener;
import me.lemuel.adore.mvp.movie.MoviePresenter;
import me.lemuel.adore.provider.SubjectProvider;

/**
 * Created by lemuel on 2017/3/7.
 */

public class MovieFragment extends Fragment
        implements MovieContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MultiTypeAdapter listAdapter;
    private MoviePresenter moviePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_now, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        ((DefaultItemAnimator) recyclerView.getItemAnimator())
                .setSupportsChangeAnimations(false);//避免notifyDataSetChanged时闪屏
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        listAdapter = new MultiTypeAdapter();
        listAdapter.register(SubjectsBean.class, new SubjectProvider(getActivity()));
        recyclerView.setAdapter(listAdapter);
        moviePresenter = new MoviePresenter(this);
        moviePresenter.requestMovie();
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                moviePresenter.loadMore();
            }
        });
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoding() {
        refreshLayout.setRefreshing(false);
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
        recyclerView.smoothScrollToPosition(0);
    }
}
