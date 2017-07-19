package me.lemuel.adore.view.rowview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.lemuel.adore.listener.OnRowViewClickListener;
import me.lemuel.adore.view.rowview.descripter.RowContainerDescripter;
import me.lemuel.adore.view.rowview.descripter.RowGroupDescripter;
import me.lemuel.adore.view.rowview.descripter.RowProfileViewDescripter;

public class RowContainerView extends LinearLayout {

    private Context context;

    public RowContainerView(Context context) {
        super(context);
        initializeView(context);
    }

    public RowContainerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public RowContainerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
    }

    public void initializeData(RowContainerDescripter descripters, OnRowViewClickListener listener) {
        addProfileView(descripters, listener);
        addRowGroupView(descripters, listener);
    }

    /**
     * 添加RowGroupView
     * @param descripters
     * @param listener
     */
    private void addRowGroupView(RowContainerDescripter descripters, OnRowViewClickListener listener) {
        if (descripters.getGroupDescripters().size() > 0) {
            addRowView(descripters, listener);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    private void addRowView(RowContainerDescripter descripters, OnRowViewClickListener listener) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        float density = context.getResources().getDisplayMetrics().density;
        layoutParams.topMargin = (int) (25 * density);
        for (RowGroupDescripter descripter : descripters.getGroupDescripters()) {
            RowGroupView groupView = new RowGroupView(context);
            groupView.initializeData(descripter.getRowDescripters(), listener);
            addView(groupView, layoutParams);
        }
    }

    /**
     * 添加头部
     * @param descripters
     * @param listener
     */
    private void addProfileView(RowContainerDescripter descripters, OnRowViewClickListener listener) {
        if (descripters != null && descripters.getProfileViewDescripter() != null) {
            RowProfileViewDescripter profileDescripter = descripters.getProfileViewDescripter();
            RowProfileView rowProfileView = new RowProfileView(context);
            rowProfileView.initializeData(profileDescripter, listener);
            addView(rowProfileView);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }
}
