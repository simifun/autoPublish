package fun.qsong.autopublish.gif;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import fun.qsong.autopublish.base.BasePresenter;
import fun.qsong.autopublish.main.IMain;
import fun.qsong.autopublish.retrofit.Query;
import fun.qsong.utils.util.T;
import io.reactivex.functions.Consumer;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static fun.qsong.autopublish.retrofit.Server.GIF_SINA;

/**
 * Created by admin on 2018/12/17.
 */

public class GifPresenter extends BasePresenter<IGifView> {
    private int page = 1;
    private static int num = 10;

    @Override
    protected void init() {
        context = mIView.getActivityContext();
    }

    public void getGifFormSina() {
        RetrofitUrlManager.getInstance().putDomain("GIF_SINA", GIF_SINA);
        Query.getInstance().getGifFromSina(page,num)
                .subscribe(new Consumer<GifListBean>() {
                    @Override
                    public void accept(GifListBean gifListBean) throws Exception {
                        Log.d(TAG, "getGifFormSina success!!!!");
                        mIView.refresh(gifListBean);
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable: -->"+throwable);
                        T.showShort("请求失败！请检查网络！");
                    }
                });
    }
}
