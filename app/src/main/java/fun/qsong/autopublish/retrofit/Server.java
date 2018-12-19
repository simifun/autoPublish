package fun.qsong.autopublish.retrofit;


import fun.qsong.autopublish.gif.GifListBean;
import fun.qsong.autopublish.img.ImgListBean;
import fun.qsong.autopublish.img.Itit;
import fun.qsong.autopublish.img.ReSponseItit;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sss on 2017/12/7.
 */

public interface Server {
     String GIF_SINA ="http://interface.sina.cn/";
     String MY_QSYX = "http://192.168.1.218:8090/";
//    article/postNewImgArticle

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
    Observable<ImgListBean> getGifFromSina(@Query("page") int page, @Query("num") int num, @Query("format") String format, @Query("jsoncallback") String jsoncallback);

    @Headers({"Domain-Name: GIF_SINA"})
    @GET("tech/gif/album.d.json")
    Observable<GifListBean> getGifFromSina(@Query("page") int page, @Query("num") int num);

    @Headers({"Domain-Name: MY_QSYX"})
    @Multipart
    @POST("file/savemf")
    Observable<ReSponseItit> uploadImgFile(@Part() MultipartBody.Part [] file, @Part("itit[]") Itit... itits);
}
