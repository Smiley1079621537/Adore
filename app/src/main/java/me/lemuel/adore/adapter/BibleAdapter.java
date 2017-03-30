package me.lemuel.adore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.lemuel.adore.items.bible.Bible;
import me.lemuel.adore.items.bible.BibleFragment;

/**
 * author : lemuel
 * time   : 2017/03/23
 */
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
