package me.lemuel.adore.util;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.List;

import me.lemuel.adore.R;
import me.lemuel.adore.items.movie.DirectorsBean;

/**
 * Created by lemuel on 2017/3/1.
 */

public class CommentUtil {

    public static String formatDirector(List<DirectorsBean> list) {
        StringBuilder builder = new StringBuilder();
        for (DirectorsBean director : list) {
            builder.append(director.getName()).append(" / ");
        }
        return builder.substring(0, builder.length() - 2);
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return toolbarHeight;
    }
}
