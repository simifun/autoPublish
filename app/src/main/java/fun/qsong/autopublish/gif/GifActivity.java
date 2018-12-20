package fun.qsong.autopublish.gif;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;
import fun.qsong.autopublish.view.cardswipelayout.CardItemTouchHelperCallback;
import fun.qsong.autopublish.view.cardswipelayout.CardLayoutManager;
import fun.qsong.autopublish.view.cardswipelayout.OnSwipeListener;
import fun.qsong.utils.util.T;

public class GifActivity extends BaseActivity<IGifView, GifPresenter> implements IGifView {
    GifAdapter gifAdapter;
    RecyclerView rcvGif;
    CardItemTouchHelperCallback cardCallback;
    ItemTouchHelper touchHelper;
    List<GifListBean.Gif> gifList = new ArrayList<>();
    boolean isFirstLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        rcvGif = findViewById(R.id.rcv_gif);

    }

    @Override
    protected GifPresenter onCreatePresenter() {
        return new GifPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gifAdapter == null) {
            gifAdapter = new GifAdapter(this);
            gifAdapter.bindToRecyclerView(rcvGif);
        }
        presenter.getGifFormSina();
    }



    @Override
    public void refresh(GifListBean gifListBean) {
        if(gifList.size() == 0){
            Log.e(TAG, "refresh: 0");
            gifList.addAll(gifListBean.data);
            cardCallback = new CardItemTouchHelperCallback(rcvGif.getAdapter(),gifList);
            touchHelper = new ItemTouchHelper(cardCallback);
            touchHelper.attachToRecyclerView(rcvGif);
            rcvGif.setLayoutManager(new CardLayoutManager(rcvGif, touchHelper));
            gifAdapter.setEmptyView(R.layout.layout_empty, rcvGif);
            gifAdapter.setNewData(gifList);
            cardCallback.setOnSwipedListener(new OnSwipeListener<GifListBean.Gif>() {
                @Override
                public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, GifListBean.Gif gif, int direction) {
                }

                @Override
                public void onSwipedClear() {
                }

                @Override
                public void onPreload() {
                    presenter.getGifFormSina();
                }
            });
        }else{
            Log.e(TAG, "refresh: 1");
            gifList.addAll(gifListBean.data);
            rcvGif.getAdapter().notifyDataSetChanged();
        }
    }
}
