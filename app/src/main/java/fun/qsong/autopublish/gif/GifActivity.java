package fun.qsong.autopublish.gif;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;
import fun.qsong.autopublish.entity.Itit;
import fun.qsong.autopublish.view.EditDialog;
import fun.qsong.autopublish.view.cardswipelayout.CardConfig;
import fun.qsong.autopublish.view.cardswipelayout.CardItemTouchHelperCallback;
import fun.qsong.autopublish.view.cardswipelayout.CardLayoutManager;
import fun.qsong.autopublish.view.cardswipelayout.OnSwipeListener;
import fun.qsong.utils.util.T;

public class GifActivity extends BaseActivity<IGifView, GifPresenter> implements IGifView, View.OnClickListener {
    GifAdapter gifAdapter;
    RecyclerView rcvGif;
    LinearLayout pageBtn;
    EditText pageEdt;
    TextView numTv;
    TextView publishTv;
    CardItemTouchHelperCallback cardCallback;
    ItemTouchHelper touchHelper;
    List<GifListBean.Gif> gifList = new ArrayList<>();
    List<Itit> itits = new ArrayList<>();
    private int page = 1;
    private int num = 10;
    private int tag = 0;
    private int bannerIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        rcvGif = findViewById(R.id.rcv_gif);
        numTv = findViewById(R.id.tv_num);
        publishTv = findViewById(R.id.tv_publish);
        pageBtn = findViewById(R.id.ll_pages);
        pageEdt = findViewById(R.id.et_pages);
        initListener();
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
            cardCallback = new CardItemTouchHelperCallback(rcvGif.getAdapter());
            touchHelper = new ItemTouchHelper(cardCallback);
            touchHelper.attachToRecyclerView(rcvGif);
            rcvGif.setLayoutManager(new CardLayoutManager(rcvGif, touchHelper));
            gifAdapter.setEmptyView(R.layout.layout_empty, rcvGif);


            cardCallback.setOnSwipedListener(new OnSwipeListener<GifListBean.Gif>() {
                @Override
                public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, GifListBean.Gif gif, int direction) {
                    if(direction == CardConfig.SWIPING_LEFT){

                    }else{
                        itits.add(new Itit(gif.getImg_url(),gif.getName(),tag++));
                        if(gif.isBannser()){
                            bannerIndex = itits.size()-1;
                        }
                        numTv.setText("已选"+tag+"张图片");
                    }
                }

                @Override
                public void onSwipedClear() {
                }

                @Override
                public void onPreload() {
                    presenter.getGifFormSina(++page, num);
                }
            });
        }
        presenter.getGifFormSina(page, num);
    }

    private void initListener(){
        pageBtn.setOnClickListener(this);
        publishTv.setOnClickListener(this);
        pageEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    toPage();
                }
                return false;
            }
        });
    }


    @Override
    public void refresh(GifListBean gifListBean) {
        if (gifList.size() == 0) {
            Log.e(TAG, "首次加载");
            gifList.addAll(gifListBean.data);
            cardCallback.setDataList(gifList);
            gifAdapter.setNewData(gifList);
        } else {
            Log.e(TAG, "加载第"+page+"页");
            pageEdt.setText(page+"");
            gifList.addAll(gifListBean.data);
            rcvGif.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pages:
                toPage();
                break;
            case R.id.tv_publish:
                publishGif();
                break;
        }
    }

    private void publishGif(){
        if (itits.size() == 0){
            T.showShort("先选择几张图片好不好？！");
            return;
        }

        final EditDialog editDialog = new EditDialog(getActivityContext());
        editDialog.setTitle("这篇文章的标题");
        if(bannerIndex>-1){
            // 将选中的封面移到list第一个
            Itit banner = itits.get(bannerIndex);
            itits.remove(bannerIndex);
            itits.add(0,banner);
            editDialog.setMessage(banner.getText());
        }
        editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String inputStr) {
                if (TextUtils.isEmpty(inputStr) && editDialog.getMessage() =="") {
                    T.showShort("文章标题不能为空！");
                } else {
                    //让软键盘隐藏
                    InputMethodManager imm = (InputMethodManager) getActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editDialog.getCurrentFocus().getApplicationWindowToken(), 0);
                    editDialog.dismiss();
                    inputStr = TextUtils.isEmpty(inputStr)? editDialog.getMessage() :inputStr;
                    presenter.publish(itits,inputStr);
                }
            }
        });
        editDialog.setNoOnclickListener("取消", new EditDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }
    private void toPage(){
        //让软键盘隐藏
        InputMethodManager imm = (InputMethodManager) getActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pageEdt.getWindowToken(), 0);
        pageEdt.clearFocus();

        if (TextUtils.isEmpty(pageEdt.getText())) {
            return;
        }
        try {
            int n = Integer.parseInt(pageEdt.getText().toString());
            if (n <= 0) {
                T.showShort("请输入大于1的整数！");
                return;
            }
            this.page = n;
            gifList.clear();
            rcvGif.getAdapter().notifyDataSetChanged();
            presenter.getGifFormSina(page,num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            T.showShort("请输入数字！");
        }
    }
}
