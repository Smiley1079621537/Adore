package me.lemuel.adore.fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

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
        final Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialogview,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.dialog_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false); // 外部点击不取消
        Window window = dialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        mDialogImage = (SimpleDraweeView) dialog.findViewById(R.id.dialog_image);
        mDialogImage.setImageURI(Uri.parse(mImageUrl));
        return dialog;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
