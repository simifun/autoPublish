package fun.qsong.autopublish.gif;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/12/17.
 */

public class GifListBean implements Serializable{
    /*@SerializedName("status")
    public Status status;
    @SerializedName("total")
    public String total;
    @SerializedName("count")
    public String count;*/

    //json数据主体
    @SerializedName("data")
    public List<Gif> data;

    public static class Gif implements Serializable{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("short_name")
        private String short_name;
        @SerializedName("img_url")
        private String img_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class Status implements Serializable{
        public String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
