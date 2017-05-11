package me.lemuel.adore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.SongListAdapter;
import me.lemuel.adore.bean.music.SongListInfo;

/**
 * Created by lemuel on 2017/05/08.
 */
public class OnlineMusicFragment extends Fragment {

    @BindView(R.id.lv_song_list)
    RecyclerView mLvSongList;
    private ArrayList<SongListInfo> mSongListInfos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!NetworkUtils.isAvailableByPing()) {
            ToastUtils.showShortSafe("网络开小差了~~");
            return;
        }
        if (mSongListInfos.isEmpty()) {
            String[] titles = getResources().getStringArray(R.array.online_music_list_title);
            String[] types = getResources().getStringArray(R.array.online_music_list_type);
            for (int i = 0; i < titles.length; i++) {
                SongListInfo info = new SongListInfo();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                mSongListInfos.add(info);
            }
        }
        mLvSongList.setLayoutManager(new LinearLayoutManager(getActivity()));
        SongListAdapter adapter = new SongListAdapter(mSongListInfos);
        mLvSongList.setAdapter(adapter);
    }

    public static Fragment newInstance() {
        return new OnlineMusicFragment();
    }
}
