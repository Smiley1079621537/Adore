package me.lemuel.adore.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.SongListAdapter;
import me.lemuel.adore.base.BaseFragment;

public class MusicFragment extends BaseFragment{

    @BindView(R.id.lv_song_list)
    RecyclerView mRecyclerView;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        SongListAdapter adapter = new SongListAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_song_list;
    }

    @Override
    protected void initView(View root) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static Fragment newInstance() {
        return new MusicFragment();
    }
}
