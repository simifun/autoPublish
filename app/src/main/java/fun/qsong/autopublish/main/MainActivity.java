package fun.qsong.autopublish.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fun.qsong.autopublish.gif.GifActivity;
import fun.qsong.autopublish.gif.GifListBean;
import fun.qsong.autopublish.img.ImgActivity;
import fun.qsong.autopublish.img.ImgListBean;
import fun.qsong.utils.util.ActivityUtils;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;


public class MainActivity extends BaseActivity<IMain,MainPresenter> implements IMain,View.OnClickListener{
    private Button gifBtn;
    private Button imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifBtn = findViewById(R.id.gif_btn);
        imgBtn = findViewById(R.id.img_btn);

        gifBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);
    }

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gif_btn:
                ActivityUtils.startActivity(GifActivity.class);
                break;
            case R.id.img_btn:
                ActivityUtils.startActivity(ImgActivity.class);
                break;
        }
    }

    @Override
    public void onRunBack() {
        presenter.RunBack();
    }
}
