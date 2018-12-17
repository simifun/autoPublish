package fun.qsong.autopublish.retrofit;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import fun.qsong.autopublish.gif.GifListBean;
import io.reactivex.Observable;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sss on 2017/12/7.
 */

public interface Server {
    final String HOST_PATH ="http://interface.sina.cn/";

    /**
     * 从新浪GIF趣图获取gif图片信息
     * @param page 页码
     * @param num 每页几个图片
     * @param format 固定值json
     * @param jsoncallback 固定值getDataJson
     * @return
     */
    @FormUrlEncoded
    @POST("tech/gif/album.d.json")
    Observable<GifListBean> getGifFromSina2(@Query("page") int page,@Query("num") int num, @Query("format") String format,@Query("jsoncallback") String jsoncallback);

    @GET("tech/gif/album.d.json")
    Observable<GifListBean> getGifFromSina(@Query("page") int page);

}
