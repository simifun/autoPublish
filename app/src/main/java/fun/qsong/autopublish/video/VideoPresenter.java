package fun.qsong.autopublish.video;

import android.util.Log;

import java.util.List;

import fun.qsong.autopublish.base.BasePresenter;
import fun.qsong.autopublish.entity.Itit;
import fun.qsong.autopublish.retrofit.Query;
import fun.qsong.utils.util.GsonUtils;
import fun.qsong.utils.util.T;
import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2018/12/17.
 */

public class VideoPresenter extends BasePresenter<IVideoView> {
    @Override
    protected void init() {
        context = mIView.getActivityContext();
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
