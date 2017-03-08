package me.lemuel.adore.util;

import java.util.List;

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
}
