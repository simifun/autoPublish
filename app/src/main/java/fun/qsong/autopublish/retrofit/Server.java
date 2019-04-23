package fun.qsong.autopublish.retrofit;


import fun.qsong.autopublish.entity.ImgBean;
import fun.qsong.autopublish.gif.GifListBean;
import fun.qsong.autopublish.img.ImgListBean;
import fun.qsong.autopublish.entity.ReSponseItit;
import fun.qsong.autopublish.joke.JokeListBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
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
    String GIF_SINA = "http://interface.sina.cn/";
    String MY_QSYX = "http://120.24.76.122:8090/";
    String API_SHOP = "http://api.apishop.net/";
    String API_IMG = "https://api.ixiaowai.cn/";

    /**
     * 从新浪GIF趣图获取gif图片信息
     *
     * @param page         页码
     * @param num          每页几个图片
     * @param format       固定值json
     * @param jsoncallback 固定值getDataJson
     * @return
     */
    @Headers({"Domain-Name: GIF_SINA"})
    @FormUrlEncoded
    @GET("tech/gif/album.d.json")
    Observable<ImgListBean> getGifFromSina(@Query("page") int page, @Query("num") int num, @Query("format") String format, @Query("jsoncallback") String jsoncallback);

    @Headers({"Domain-Name: GIF_SINA"})
    @GET("tech/gif/album.d.json")
    Observable<GifListBean> getGifFromSina(@Query("page") int page, @Query("num") int num);

    @Headers({"Domain-Name: MY_QSYX"})
    @Multipart
    @POST("file/savemf")
    Observable<ReSponseItit> uploadImgFile(@Part() MultipartBody.Part[] file, @Part() MultipartBody.Part itits);

    @Headers({"Domain-Name: MY_QSYX"})
    @FormUrlEncoded
    @POST("article/postNewImgArticle")
    Observable<Object> postNewImgArticle(@Field("article.itit") String itits, @Field("article.title") String title, @Field("article.type") String type);

    @Headers({"Domain-Name: MY_QSYX"})
    @FormUrlEncoded
    @POST("article/postNewDzArticle")
    Observable<Object> postNewDz(@Field("article.img") String img, @Field("article.title") String title, @Field("article.content") String content,@Field("article.type") String type,@Field("article.isshow") Boolean isshow);

    @Headers({"Domain-Name: API_SHOP"})
    @GET("common/joke/getJokesByRandom")
    Observable<JokeListBean> getDz(@Query("apiKey") String key, @Query("pageSize") int ps);

    @Headers({"Domain-Name: API_IMG"})
    @GET("api/api.php?return=json")
    Observable<ImgBean> getImg();


}