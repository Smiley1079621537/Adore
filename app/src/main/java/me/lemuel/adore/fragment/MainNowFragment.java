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
import android.widget.Toast;

import javax.inject.Inject;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.R;
import me.lemuel.adore.bean.movie.SubjectsBean;
import me.lemuel.adore.component.DaggerMainComponent;
import me.lemuel.adore.contract.MainContract;
import me.lemuel.adore.listener.OnLoadMoreListener;
import me.lemuel.adore.module.MainModule;
import me.lemuel.adore.presenter.MainPresenter;
import me.lemuel.adore.provider.SubjectProvider;

/**
 * Created by lemuel on 2017/3/7.
 */

public class MainNowFragment extends Fragment
        implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MultiTypeAdapter listAdapter;
    @Inject
    MainPresenter mainPresenter;

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
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        mainPresenter.onCreate();
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mainPresenter.loadMore();
            }
        });
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void setPresenter(MainPresenter presenter) {
        mainPresenter = presenter;
    }

    public static Fragment newInstance() {
        return new MainNowFragment();
    }

    public void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }
}
