package fun.qsong.autopublish.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2019/4/23.
 */

public class ImgBean implements Serializable {
    @SerializedName("code")
    public String code;
    @SerializedName("imgurl")
    public String imgurl;
    @SerializedName("width")
    public String width;
    @SerializedName("height")
    public String height;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
