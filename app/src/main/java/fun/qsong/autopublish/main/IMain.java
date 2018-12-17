package fun.qsong.autopublish.main;

import java.util.List;

import fun.qsong.autopublish.base.IMvpView;
import fun.qsong.autopublish.gif.GifListBean;

/**
 * Created by admin on 2018/12/17.
 */

public interface IMain extends IMvpView{
    void openGif(GifListBean gifListBeans);
}
