package me.lemuel.adore.activity;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import me.lemuel.adore.R;
import me.lemuel.adore.base.BaseActivity;
import me.lemuel.adore.base.RowAction;
import me.lemuel.adore.listener.OnRowViewClickListener;
import me.lemuel.adore.view.rowview.RowContainerViewSetting;
import me.lemuel.adore.view.rowview.descripter.RowContainerDescripter;
import me.lemuel.adore.view.rowview.view.RowContainerView;

public class ProfileActivity extends BaseActivity implements OnRowViewClickListener {

    @BindView(R.id.rowContainerView)
    RowContainerView rowContainerView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initData() {
        RowContainerDescripter containerDescripter = RowContainerViewSetting.getRowContainerDescripter();
        rowContainerView.initializeData(containerDescripter,this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onRowClick(RowAction action) {
        ToastUtils.showShort("rowView click : " + action.name());
    }
}
