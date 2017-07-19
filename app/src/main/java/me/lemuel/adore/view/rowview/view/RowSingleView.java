package me.lemuel.adore.view.rowview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lemuel.adore.R;
import me.lemuel.adore.base.RowAction;
import me.lemuel.adore.listener.OnRowViewClickListener;
import me.lemuel.adore.view.rowview.descripter.RowSingleDescripter;

public class RowSingleView extends LinearLayout{

    @BindView(R.id.rowview_icon)
    ImageView mIconImageView;
    @BindView(R.id.rowview_title)
    TextView mTitleTextView;
    @BindView(R.id.rowview_switcher)
    Switch mSwitcher;

    private OnRowViewClickListener listener;
    private RowAction action;

    public RowSingleView(Context context) {
        super(context);
        initializeView(context);
    }

    public RowSingleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public RowSingleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_single_rowview, this);
        ButterKnife.bind(view);
    }

    public void initializeData(final RowSingleDescripter descripter, final OnRowViewClickListener listener) {
        mIconImageView.setImageResource(descripter.getIconResId());
        mTitleTextView.setText(descripter.getTitle());
        mSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && listener!=null){
                    listener.onRowClick(descripter.getAction());
                }
            }
        });
    }
}
