package fun.qsong.autopublish;

import android.app.Application;
import fun.qsong.utils.util.SPUtils;
import fun.qsong.utils.util.Utils;

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null)
                    instance = new MyApplication();
            }
        }
        return instance;
    }



    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Utils.init(this);
        SPUtils.init();
    }
}
