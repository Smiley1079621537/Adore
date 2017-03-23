package me.lemuel.adore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
        BibleFragment bibleFragment = BibleFragment.newInstance(position);
        return bibleFragment;
    }

    @Override
    public int getCount() {
        return Bible.ALL_CHAPTER_NUM;
    }
}
