package fun.qsong.autopublish.video;

import android.os.Bundle;
import android.view.View;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;

public class VideoActivity extends BaseActivity<IVideoView, VideoPresenter> implements IVideoView, View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        initListener();
    }

    @Override
    protected VideoPresenter onCreatePresenter() {
        return new VideoPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initListener(){

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pages:
                break;
            case R.id.tv_publish:
                break;
        }
    }
}
