package fun.qsong.autopublish.img;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/12/19.
 */

public class ReSponseItit {
    /**
     * {
     "code": 200,
     "data": {
     "itit": [
     {
     "imgId": "e655afa7412fa74bac52a2e2023acf39.png",
     "text": "这个傻猫",
     "tag": 0
     }
     ]
     },
     "errMessage": "请求成功",
     "success": true
     }
     */
    private int code;
    private Data data;

    public static class Data{
        private List<Itit> itit;

        public List<Itit> getItit() {
            return itit;
        }

        public void setItit(List<Itit> itit) {
            this.itit = itit;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
