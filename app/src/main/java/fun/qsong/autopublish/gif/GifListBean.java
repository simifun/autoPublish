package fun.qsong.autopublish.gif;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2018/12/17.
 */

public class GifListBean {
    //json数据主体
    @SerializedName("data")
    public ArrayList<Gif> list;

    public static class Gif implements Serializable{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private int name;
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

        public int getName() {
            return name;
        }

        public void setName(int name) {
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

    public ArrayList<Gif> getList() {
        return list;
    }

    public void setList(ArrayList<Gif> list) {
        this.list = list;
    }
}
