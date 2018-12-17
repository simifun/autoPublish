package fun.qsong.autopublish.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Created by admin on 2018/12/17.
 */

public abstract class BaseActivity<V,T extends BasePresenter> extends AppCompatActivity implements IMvpView{
    public final String TAG = this.getClass().getSimpleName();
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = onCreatePresenter();
        if (presenter != null) {
            presenter.attachView((V) this);
        }
    }

    /**
     * @return 如若使用MVP模式，则必须实现presenter层
     */
    protected T onCreatePresenter() {
        return null;
    }

    /**
     * @Description: 响应按键事件
     * @parameters @param keyCode
     * @parameters @param event
     * @returns
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {
            onRunBack();
        }
        return false;
    }

    /**
     * @Description: 编写返回事件
     */
    public void onRunBack() {
        finish();
    }


    @Override
    public Context getActivityContext() {
        return this;
    }
}
