package fun.qsong.autopublish.gif;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;
import fun.qsong.utils.util.T;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;


public class GifActivity extends BaseActivity<IGifView, GifPresenter> implements IGifView {
    //    WebView webView;
    GifAdapter gifAdapter;
    RecyclerView rcvGif;
    CardItemTouchHelperCallback cardCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        rcvGif = findViewById(R.id.rcv_gif);

        rcvGif.setLayoutManager(new LinearLayoutManager(this));
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
            gifAdapter.setEmptyView(R.layout.layout_empty, rcvGif);
        }
        presenter.getGifFormSina();
    }

    @Override
    public void refresh(GifListBean gifListBean) {
        cardCallback = new CardItemTouchHelperCallback(rcvGif.getAdapter(), gifListBean.data);
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        touchHelper.attachToRecyclerView(rcvGif);

        CardLayoutManager cardLayoutManager = new CardLayoutManager(rcvGif, touchHelper);
        rcvGif.setLayoutManager(cardLayoutManager);
        cardCallback.setOnSwipedListener(new OnSwipeListener<GifListBean.Gif>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, GifListBean.Gif gif, int direction) {
            }

            @Override
            public void onSwipedClear() {
                T.showShort("???!!!");
                presenter.getGifFormSina();
            }
        });

        gifAdapter.setNewData(gifListBean.data);
    }
}
