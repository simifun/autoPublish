package fun.qsong.autopublish.img;

import com.google.gson.JsonObject;

/**
 * Created by admin on 2018/12/19.
 */

public class Itit {
    private String imgId;
    private String text;
    private int tag;

    public Itit(String text, int tag) {
        this.imgId = "";
        this.text = text;
        this.tag = tag;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
