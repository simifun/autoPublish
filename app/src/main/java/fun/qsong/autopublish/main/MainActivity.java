package fun.qsong.autopublish.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fun.qsong.autopublish.gif.GifActivity;
import fun.qsong.autopublish.img.ImgActivity;
import fun.qsong.autopublish.web.WebActivity;
import fun.qsong.utils.util.ActivityUtils;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;
import fun.qsong.utils.util.T;


public class MainActivity extends BaseActivity<IMain,MainPresenter> implements IMain,View.OnClickListener{
    private Button gifBtn;
    private Button imgBtn;
    private Button videoBtn;
    private Button dzbtn;
    private Button noticeBtn;
    private Button webBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifBtn = findViewById(R.id.gif_btn);
        imgBtn = findViewById(R.id.img_btn);
        videoBtn = findViewById(R.id.video_btn);
        dzbtn = findViewById(R.id.dz_btn);
        noticeBtn = findViewById(R.id.notice_btn);
        webBtn = findViewById(R.id.web_btn);

        gifBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        dzbtn.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);
        webBtn.setOnClickListener(this);
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
            case R.id.video_btn:
                T.showShort("该功能暂不开放，请使用web发布");
                break;
            case R.id.dz_btn:
                T.showShort("该功能暂不开放，请使用web发布");
                break;
            case R.id.notice_btn:
                T.showShort("该功能暂不开放，请使用web发布");
                break;
            case R.id.web_btn:
                ActivityUtils.startActivity(WebActivity.class);
                break;
        }
    }

    @Override
    public void onRunBack() {
        presenter.RunBack();
    }
}
