package me.lemuel.adore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import me.lemuel.adore.bean.bible.Bible;
import me.lemuel.adore.fragment.BibleFragment;

public class BibleAdapter extends FragmentStatePagerAdapter {

    public BibleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return BibleFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return Bible.ALL_CHAPTER_NUM;
    }
}
