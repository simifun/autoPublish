package fun.qsong.autopublish.retrofit;


import fun.qsong.autopublish.gif.GifListBean;
import fun.qsong.autopublish.img.Itit;
import fun.qsong.autopublish.img.ReSponseItit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
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
        OkHttpClient OkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .build();
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

    public Observable<ReSponseItit> uploadImgFile(MultipartBody.Part [] files, Itit[] itits) {
        return server.uploadImgFile(files, itits)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }


}
