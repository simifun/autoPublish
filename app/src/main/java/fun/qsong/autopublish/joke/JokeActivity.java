package fun.qsong.autopublish.joke;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;
import fun.qsong.autopublish.view.EditDialog;
import fun.qsong.utils.util.T;

/**
 * Created by admin on 2019/4/23.
 */

public class JokeActivity extends BaseActivity<IJokeView, JokePresenter> implements IJokeView{
    private RecyclerView rcvJoke;
    JokeAdapter jokeAdapter;
    private String imgUrl;
    List<JokeListBean.Joke> jokeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        rcvJoke = findViewById(R.id.rcv_joke);

        rcvJoke.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected JokePresenter onCreatePresenter() {
        return new JokePresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(jokeAdapter == null){
            jokeAdapter = new JokeAdapter();
            jokeAdapter.bindToRecyclerView(rcvJoke);
            jokeAdapter.setEmptyView(R.layout.layout_empty, rcvJoke);

            jokeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    T.showShort("嘤嘤嘤"+jokeList.get(position).getContent());
                    publicJoke(jokeList.get(position).getContent());
                }
            });

            jokeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    presenter.getJokeFormApi();
                }
            },rcvJoke);
        }
        presenter.getJokeFormApi();
    }

    @Override
    public void refresh(JokeListBean jokeListBean) {
        if (jokeList.size() == 0) {
            Log.e(TAG, "首次加载");
            jokeList.addAll(jokeListBean.result);
            jokeAdapter.setNewData(jokeList);
        } else {
            jokeList.addAll(jokeListBean.result);
            rcvJoke.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void refreshImg(String imgUrl,ImageView imageView) {
        this.imgUrl = imgUrl;
        Glide.with(this)
                .load(imgUrl)
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_broken)
                .signature(new StringSignature(UUID.randomUUID().toString()))  // 重点在这行
                .into(imageView);
    }

    private void publicJoke(final String joke){
        final Dialog dia = new Dialog(JokeActivity.this,R.style.Theme_AppCompat_Dialog);
        dia.setTitle("请选择一张图片(点击刷新/长按选中)");
        dia.setContentView(R.layout.dialog_img);
        dia.setCanceledOnTouchOutside(true);
        final ImageView imageView = dia.findViewById(R.id.iv_img);
        presenter.refreshImgView(imageView);
        dia.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.refreshImgView(imageView);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dia.dismiss();
                return true;
            }
        });

        dia.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                final EditDialog editDialog = new EditDialog(getActivityContext());
                editDialog.setTitle("这篇文章的标题");
                editDialog.setMessage(joke.substring(0,10));
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
                            presenter.publish(imgUrl,inputStr,joke);
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
        });
    }

}
