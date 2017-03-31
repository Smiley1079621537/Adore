package me.lemuel.adore;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import me.lemuel.adore.util.ToastUtils;

/**
 * author : lemuel
 * time   : 2017/03/28
 */
public abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向最后一个滑动，既是否向下滑动
    private boolean isSlidingToLast = false;

    public abstract void onLoadMore();


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
            int lastVisiblePos = getMaxElem(lastVisiblePositions);
            int totalItemCount = manager.getItemCount();

            // 判断是否滚动到底部
            if (lastVisiblePos == (totalItemCount - 1) && isSlidingToLast) {
                //加载更多功能的代码
                ToastUtils.showShortToast("加载更多！");
                this.onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        //大于0表示，正在向下滚动
        //小于等于0 表示停止或向上滚动
        isSlidingToLast = dy > 0;
    }

    private int getMaxElem(int[] arr) {
        int maxVal = Integer.MIN_VALUE;
        for (int anArr : arr) {
            if (anArr > maxVal)
                maxVal = anArr;
        }
        return maxVal;
    }
}
