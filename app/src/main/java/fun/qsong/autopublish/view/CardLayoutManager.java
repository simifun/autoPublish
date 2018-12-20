package fun.qsong.autopublish.view;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2018/12/20.
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //先移除所有的view
        removeAllViews();
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        int position = (itemCount > CardConfig.DEFAULT_SHOW_ITEM) ? itemCount : itemCount - 1;
        for (; position >= 0; position--) {
            final View view = recycler.getViewForPosition(position);
            addView(view);

            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view), heightSpace / 2 + getDecoratedMeasuredHeight(view));
            if (position == CardConfig.DEFAULT_SHOW_ITEM) {
                view.setScaleX(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                view.setScaleY(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                view.setTranslationY((position - 1) * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
            } else if (position > 0) {
                view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
            } else {
                // 设置 mTouchListener 的意义就在于我们想让处于顶层的卡片是可以随意滑动的
                // 而第二层、第三层等等的卡片是禁止滑动的
                view.setOnTouchListener(mOnTouchListener);
            }
        }
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /*RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            // 把触摸事件交给 mItemTouchHelper，让其处理卡片滑动事件
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mItemTouchHelper.startSwipe(childViewHolder);
            }*/
            return false;
        }
    };

    static class CardConfig {
        static int DEFAULT_SHOW_ITEM = 4;
        static float DEFAULT_SCALE = 0.1f;
        static int DEFAULT_TRANSLATE_Y = 14;
    }
}
