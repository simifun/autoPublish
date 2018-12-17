package fun.qsong.autopublish.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fun.qsong.utils.util.T;

import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BaseActivity;


public class MainActivity extends BaseActivity<IMain,MainPresenter> implements IMain,View.OnClickListener{
    private Button gifBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifBtn = findViewById(R.id.gif_btn);
        gifBtn.setOnClickListener(this);
    }

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gif_btn:
                presenter.getGifFormSina();
                break;
        }
    }

    @Override
    public void onRunBack() {
        presenter.RunBack();
    }
}
