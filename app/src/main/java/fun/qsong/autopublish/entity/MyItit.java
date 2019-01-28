package fun.qsong.autopublish.entity;

import android.net.Uri;

/**
 * Created by admin on 2018/12/19.
 */

public class MyItit {
    private Uri imgUri;
    private String text;
    private int tag;

    public MyItit(Uri imgUri,String text, int tag) {
        this.imgUri = imgUri;
        this.text = text;
        this.tag = tag;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
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
