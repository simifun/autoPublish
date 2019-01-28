package fun.qsong.autopublish.retrofit;


import android.util.Log;

import fun.qsong.autopublish.gif.GifListBean;
import fun.qsong.autopublish.entity.ReSponseItit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static fun.qsong.autopublish.retrofit.Server.MY_QSYX;


/**
 * Created by sss on 2017/12/7.
 */

public class Query {
    private static Query Instance = null;
    private Server server;

    public Query() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog","retrofitBack = "+message);
            }
        });
        OkHttpClient OkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .addInterceptor(loggingInterceptor)
                .build();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MY_QSYX)
                .client(OkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
                .build();
        server = retrofit.create(Server.class);
    }

    public static Query getInstance() {
        if (Instance == null) {
            synchronized (Query.class) {
                if (Instance == null) {
                    Instance = new Query();
                }
            }
        }
        return Instance;
    }

    public Observable<GifListBean> getGifFromSina(int page, int num) {
        return server.getGifFromSina(page, num)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<ReSponseItit> uploadImgFile(MultipartBody.Part [] files, MultipartBody.Part itits) {
        return server.uploadImgFile(files, itits)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<Object> postNewImgArticle(String itits,String title,String type) {
        return server.postNewImgArticle(itits,title,type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
