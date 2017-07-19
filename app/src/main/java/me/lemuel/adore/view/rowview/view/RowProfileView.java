package me.lemuel.adore.view.rowview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lemuel.adore.R;
import me.lemuel.adore.listener.OnRowViewClickListener;
import me.lemuel.adore.view.rowview.descripter.RowProfileViewDescripter;

public class RowProfileView extends RelativeLayout {

    @BindView(R.id.rowview_profile_avatar)
    ImageView mProfileAvatar;
    @BindView(R.id.rowview_profile_title)
    TextView mProfileTitle;
    @BindView(R.id.rowview_profile_subtitle)
    TextView mProfileSubtitle;
    private View mProfileView;

    public RowProfileView(Context context) {
        super(context);
        initializeView(context);
    }

    public RowProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public RowProfileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        mProfileView = LayoutInflater.from(context).inflate(R.layout.layout_rowview_profile, this);
        ButterKnife.bind(mProfileView);
    }

    public void initializeData(final RowProfileViewDescripter descripter, final OnRowViewClickListener listener){
        mProfileAvatar.setImageResource(descripter.getProfileResId());
        mProfileTitle.setText(descripter.getProfileTitle());
        mProfileSubtitle.setText(descripter.getProfileSubTitle());
        if (listener!=null){
            mProfileView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRowClick(descripter.getAction());
                }
            });
        }
    }
}
