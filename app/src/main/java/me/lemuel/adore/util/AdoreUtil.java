package me.lemuel.adore.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;

import java.util.List;

import me.lemuel.adore.R;
import me.lemuel.adore.bean.movie.DirectorsBean;

/**
 * Created by lemuel on 2017/3/1.
 */

public class AdoreUtil {

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

    //透明状态栏
    public static void setTransparentStatusbar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//透明状态栏
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //全屏沉浸
    public static void setImmersiveStatusbar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showAlert(final Activity activity) {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(activity)
                .setImageRecourse(R.drawable.ic_adb_black_24dp)
                .setTextTitle("今日经文")
                .setTextSubTitle("林后5:17")
                .setBody("若有人在基督里，他就是新造的人，\n旧事已过，都变成新的了。")
                .setNegativeColor(R.color.colorAccent)
                .setNegativeButtonText("取消")
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonText("确定")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        Toast.makeText(activity, "Updating", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButtonsGravity(FancyAlertDialog.PanelGravity.CENTER)
                .build();
        alert.show();
    }
}
