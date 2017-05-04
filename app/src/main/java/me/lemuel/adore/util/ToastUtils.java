package me.lemuel.adore.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import me.lemuel.adore.App;

public class ToastUtils {

    private static Toast mToast = Toast.makeText(App.getAppContext(), "", Toast.LENGTH_LONG);

    public static void showShortToast(@StringRes int message) {
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }

    public static void showShortToast(String message) {
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }

    public static void showLongToast(@StringRes int message) {
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(message);
        mToast.show();
    }
    public static void showLongToast(String message) {
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(message);
        mToast.show();
    }

    public static void cancel() {
        mToast.cancel();
    }

}
