package me.lemuel.adore.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import me.lemuel.adore.R;

/**
 * Created by lemuel on 2017/2/27.
 */

public class ActivityUtils {

    public static void addFragmentToActivity(
            FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


}
