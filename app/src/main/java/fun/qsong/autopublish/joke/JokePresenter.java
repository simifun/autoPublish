package fun.qsong.autopublish.joke;

import android.util.Log;
import android.widget.ImageView;

import fun.qsong.autopublish.base.BasePresenter;
import fun.qsong.autopublish.entity.ImgBean;
import fun.qsong.autopublish.retrofit.Query;
import fun.qsong.utils.util.T;
import io.reactivex.functions.Consumer;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static fun.qsong.autopublish.retrofit.Server.API_IMG;
import static fun.qsong.autopublish.retrofit.Server.API_SHOP;

/**
 * Created by admin on 2019/4/23.
 */

public class JokePresenter extends BasePresenter<IJokeView> {
    private static final String KEY = "PnXPUVge4ed54afd3d3f6b37270faf44e1ccdc44bcc54e8";
    private static final int PAGE_SIZE = 20;
    @Override
    protected void init() {
        context = mIView.getActivityContext();
    }

    public void getJokeFormApi(){
        RetrofitUrlManager.getInstance().putDomain("API_SHOP", API_SHOP);
        Query.getInstance().getDz(KEY,PAGE_SIZE)
                .subscribe(new Consumer<JokeListBean>() {
                    @Override
                    public void accept(JokeListBean jokeListBean) throws Exception {
                        Log.d(TAG, "getGifFormSina success!!!!");
                        mIView.refresh(jokeListBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable: -->"+throwable);
                        T.showShort("请求失败！请检查网络！");
                    }
                });
    }
    public void refreshImgView(final ImageView imageView){
        RetrofitUrlManager.getInstance().putDomain("API_IMG", API_IMG);
        Query.getInstance().getImg()
                .subscribe(new Consumer<ImgBean>() {
                    @Override
                    public void accept(ImgBean imgBean) throws Exception {
                        String imgUrl = imgBean.getImgurl().replace("large","orj360");
                        imgUrl = imgUrl.replace("https","http");
                        mIView.refreshImg(imgUrl,imageView);
                    }
                },new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "throwable: -->" + throwable);
                        T.showShort("请求失败！请检查网络！");
                    }
                });
    }

    public void publish(String img,String title,String content){
        if(img == ""){
            T.showShort("发布图片不能为空");
            return;
        }
        if(title == ""){
            T.showShort("发布标题不能为空");
            return;
        }
        if(content == ""){
            T.showShort("发布内容不能为空");
            return;
        }
        Query.getInstance().postNewDz(img,title,content,"段子",false)
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
    }
}
