package me.lemuel.adore.view.rowview.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import me.lemuel.adore.listener.OnRowViewClickListener;
import me.lemuel.adore.view.rowview.descripter.BaseRowViewDescripter;
import me.lemuel.adore.view.rowview.descripter.RowDescripter;
import me.lemuel.adore.view.rowview.descripter.RowSingleDescripter;

public class RowGroupView extends LinearLayout {

    private Context context;

    public RowGroupView(Context context) {
        super(context);
        initializeView(context);
    }

    public RowGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public RowGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
    }

    public void initializeData(ArrayList<BaseRowViewDescripter> descripters, OnRowViewClickListener listener) {
        if (descripters != null && descripters.size() > 0) {
            addRowView(descripters, listener);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    private void addRowView(ArrayList<BaseRowViewDescripter> descripters, OnRowViewClickListener listener) {
        for (int i = 0; i < descripters.size(); i++) {
            BaseRowViewDescripter d = descripters.get(i);
            if (d instanceof RowSingleDescripter) {
                RowSingleView rowSingleView = new RowSingleView(context);
                rowSingleView.initializeData((RowSingleDescripter) d, listener);
                addView(rowSingleView);
            } else if (d instanceof RowDescripter) {
                RowView rowView = new RowView(context);
                rowView.initializeData((RowDescripter) d, listener);
                addView(rowView);
            }
            if (i < descripters.size() - 1) {
                View view = new View(context);
                view.setBackgroundColor(Color.parseColor("#809E9E9E"));
                float density = context.getResources().getDisplayMetrics().density;
                LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (1 * density));
                lp.leftMargin = (int) (8 * density);
                lp.rightMargin = (int) (8 * density);
                addView(view, lp);
            }
        }
    }
}
