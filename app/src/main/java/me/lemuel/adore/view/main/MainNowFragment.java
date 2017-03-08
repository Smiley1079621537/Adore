package me.lemuel.adore.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import io.realm.RealmResults;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.R;
import me.lemuel.adore.items.movie.MovieViewProvider;
import me.lemuel.adore.items.movie.SubjectsBean;

/**
 * Created by lemuel on 2017/3/7.
 */

public class MainNowFragment extends Fragment implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MultiTypeAdapter listAdapter;
    @Inject
    MainPresenter mainPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_now, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        listAdapter = new MultiTypeAdapter();
        listAdapter.register(SubjectsBean.class, new MovieViewProvider(getActivity()));
        recyclerView.setAdapter(listAdapter);
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        mainPresenter.onCreate();
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
    public void loadCacheData(RealmResults<SubjectsBean> results) {
        Items items = new Items();
        for (SubjectsBean subject : results) {
            items.add(subject);
        }
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
}