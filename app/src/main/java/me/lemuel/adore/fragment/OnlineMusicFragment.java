package me.lemuel.adore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.SongListAdapter;
import me.lemuel.adore.bean.SongList;
import me.lemuel.adore.util.NetworkUtils;

/**
 * Created by lemuel on 2017/05/08.
 */
public class OnlineMusicFragment extends Fragment {

    @BindView(R.id.lv_song_list)
    RecyclerView mLvSongList;
    private ArrayList<SongList> mSongLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            return;
        }
        //mSongLists = AppCache.getSongListInfos();
        if (mSongLists.isEmpty()) {
            String[] titles = getResources().getStringArray(R.array.online_music_list_title);
            String[] types = getResources().getStringArray(R.array.online_music_list_type);
            for (int i = 0; i < titles.length; i++) {
                SongList info = new SongList();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                mSongLists.add(info);
            }
        }
        mLvSongList.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(SongList.class, new SongListViewProvider());
        mLvSongList.setAdapter(adapter);
        adapter.setItems(mSongLists);
        adapter.notifyDataSetChanged();*/
        SongListAdapter adapter = new SongListAdapter(mSongLists);
        mLvSongList.setAdapter(adapter);
    }

    public static Fragment newInstance() {
        return new OnlineMusicFragment();
    }
}
