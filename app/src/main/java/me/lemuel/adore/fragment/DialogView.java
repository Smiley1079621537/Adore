package me.lemuel.adore.fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;

import me.lemuel.adore.R;

/**
 * Created by lemuel on 2017/3/3.
 */

public class DialogView extends DialogFragment {

    SimpleDraweeView mDialogImage;
    private String mImageUrl = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialogview);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        mDialogImage = (SimpleDraweeView) dialog.findViewById(R.id.dialog_image);
        mDialogImage.setImageURI(Uri.parse(mImageUrl));
        return dialog;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
