package me.lemuel.adore.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import me.drakeet.multitype.Items;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.TabPagerAdapter;

/**
 * Created by lemuel on 2017/2/24.
 */
public class MainFragment extends Fragment implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    @Inject
    MainPresenter mMainPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.view_main, container, false);
        tabLayout = (TabLayout) root.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) root.findViewById(R.id.main_viewpager);
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Fragment[] fragments = new Fragment[2];
        fragments[0] = MainNowFragment.newInstance();
        fragments[1] = MainNowFragment.newInstance();
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        tabPagerAdapter.setTabTitles(new String[]{getString(R.string.has_released), getString(R.string.going_to_release)});
        viewPager.setAdapter(tabPagerAdapter);
    }

    public MainFragment(){

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoding() {

    }

    @Override
    public void loadData(Items items) {

    }


    @Override
    public void setPresenter(MainPresenter presenter) {

    }

    @Override
    public void onRefresh() {

    }
}
