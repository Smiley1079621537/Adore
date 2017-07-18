package me.lemuel.adore.view.rowview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.lemuel.adore.R;
import me.lemuel.adore.listener.OnRowViewClickListener;
import me.lemuel.adore.base.RowAction;
import me.lemuel.adore.view.rowview.descripter.RowDescripter;

public class RowView extends LinearLayout implements View.OnClickListener {

    private ImageView mIconImageView;
    private TextView mTitleTextView;
    private ImageButton mMoreImageButton;
    private OnRowViewClickListener listener;
    private RowAction action;

    public RowView(Context context) {
        super(context);
        initializeView(context);
    }

    public RowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public RowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_rowview, this);
        mIconImageView = (ImageView) view.findViewById(R.id.rowview_icon);
        mTitleTextView = (TextView) view.findViewById(R.id.rowview_title);
        mMoreImageButton = (ImageButton) view.findViewById(R.id.rowview_more);
    }

    public void initializeData(RowDescripter descripter, OnRowViewClickListener listener) {
        this.action = descripter.getAction();
        this.listener = listener;
        mIconImageView.setImageResource(descripter.getIconResId());
        mTitleTextView.setText(descripter.getTitle());
        if (listener != null) {
            mMoreImageButton.setOnClickListener(this);
            mMoreImageButton.setVisibility(View.VISIBLE);
        } else {
            mMoreImageButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        listener.onRowClick(action);
    }
}
