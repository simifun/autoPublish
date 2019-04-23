package fun.qsong.autopublish.joke;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2019/4/23.
 */

public class JokeListBean implements Serializable {
    @SerializedName("statusCode")
    public String statusCode;
    @SerializedName("desc")
    public String desc;

    @SerializedName("result")
    public List<Joke> result;

    public static class Joke implements Serializable{
        @SerializedName("id")
        private int id;
        @SerializedName("content")
        private String content;
        @SerializedName("updateTime")
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Joke> getResult() {
        return result;
    }

    public void setResult(List<Joke> result) {
        this.result = result;
    }
}
