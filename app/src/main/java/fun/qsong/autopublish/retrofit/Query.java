package fun.qsong.autopublish.retrofit;

import com.google.gson.JsonObject;


import fun.qsong.autopublish.gif.GifListBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sss on 2017/12/7.
 */

public class Query {
    private static Query Instance = null;
    private Server server;

    public Query() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Server.HOST_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
                .build();
        server = retrofit.create(Server.class);
    }

    public static Query getInstance() {
        if(Instance == null){
            synchronized (Query.class){
                if (Instance == null){
                    Instance = new Query();
                }
            }
        }
        return Instance;
    }


    public Observable<GifListBean> getGifFromSina(int page){
        return server.getGifFromSina(page,10,"json","getDataJson")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<String> getGifFromSina2(int page){
        return server.getGifFromSina2(page,10,"json","getDataJson")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }


}
