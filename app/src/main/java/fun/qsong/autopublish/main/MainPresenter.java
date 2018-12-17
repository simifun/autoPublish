package fun.qsong.autopublish.main;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;

import fun.qsong.autopublish.MyApplication;
import fun.qsong.autopublish.R;
import fun.qsong.autopublish.base.BasePresenter;
import fun.qsong.autopublish.gif.GifListBean;
import fun.qsong.autopublish.retrofit.Query;
import fun.qsong.utils.util.SPUtils;
import fun.qsong.utils.util.T;
import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2018/12/17.
 */

public class MainPresenter extends BasePresenter<IMain> {
    private static Boolean isQuit = false;
    Timer timer = new Timer();
    private Handler handler = new MyHandler();
    /*退出app*/
    private static final int EXIT_APP = 13;
    private int page = 1;

    public void getGifFormSina() {
        Query.getInstance().getGifFromSina2(page)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept: -->"+s);
                        T.showShort("请检查网络");
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable: -->"+throwable);
                        T.showShort("请检查网络");
                    }
                });
    }


    public void RunBack() {
        if (isQuit == false) {
            isQuit = true;
            T.showShort("再按一次返回键退出程序");
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            timer.schedule(task, 2000);
        } else {
            new Thread() {
                public void run() {
                    handler.sendEmptyMessage(EXIT_APP);
                }
            }.start();
        }
    }

    @Override
    protected void init() {
        context = mIView.getActivityContext();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EXIT_APP: {
                    ((Activity) context).finish();
                }
            }
        }
    }
}