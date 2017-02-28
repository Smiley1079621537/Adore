package me.lemuel.blisslemuel.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.blisslemuel.R;
import me.lemuel.blisslemuel.items.movie.Movie;
import me.lemuel.blisslemuel.items.movie.MovieViewProvider;

/**
 * Created by lemuel on 2017/2/24.
 */
public class MainView extends Fragment implements MainContract.View {

    private MainContract.Presenter mMainPresenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MultiTypeAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.view_main, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        return root;
    }

    public MainView() {
        //
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new MultiTypeAdapter();
        listAdapter.register(Movie.SubjectsBean.class, new MovieViewProvider());
        recyclerView.setAdapter(listAdapter);
        mMainPresenter.onCreate();
    }

    public static MainView newInstance() {
        return new MainView();
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
    public void setPresenter(MainContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}
