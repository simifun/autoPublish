package fun.qsong.autopublish.gif;

import android.util.Log;

import java.util.List;

import fun.qsong.autopublish.base.BasePresenter;
import fun.qsong.autopublish.entity.Itit;
import fun.qsong.autopublish.retrofit.Query;
import fun.qsong.utils.util.GsonUtils;
import fun.qsong.utils.util.T;
import io.reactivex.functions.Consumer;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static fun.qsong.autopublish.retrofit.Server.GIF_SINA;

/**
 * Created by admin on 2018/12/17.
 */

public class GifPresenter extends BasePresenter<IGifView> {
    @Override
    protected void init() {
        context = mIView.getActivityContext();
    }

    public void getGifFormSina(int page,int num) {
        RetrofitUrlManager.getInstance().putDomain("GIF_SINA", GIF_SINA);
        Query.getInstance().getGifFromSina(page,num)
                .subscribe(new Consumer<GifListBean>() {
                    @Override
                    public void accept(GifListBean gifListBean) throws Exception {
                        Log.d(TAG, "getGifFormSina success!!!!");
                        mIView.refresh(gifListBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable: -->"+throwable);
                        T.showShort("请求失败！请检查网络！");
                    }
                });
    }

    //发布文章
    public void publish(List<Itit> itits,String title) {
        if(itits == null || itits.size() == 0){
            T.showShort("发布内容为空！请检查！");
            return;
        }
        try {
            String ititStr = GsonUtils.toJsonString(itits);
            Query.getInstance().postNewImgArticle(ititStr,title,"动图")
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Log.d(TAG, "Object: -->" + o);
                            T.showShort("发布成功！"+o.toString());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(TAG, "throwable: -->" + throwable);
                            T.showShort("请求失败！请检查网络！");
                        }
                    });
        } catch (Exception e) {
            T.showShort("JSON转换出错！");
            e.printStackTrace();
        }
    }
}
