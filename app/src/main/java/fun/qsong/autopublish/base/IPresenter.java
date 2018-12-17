package fun.qsong.autopublish.base;

/**
 * Created by admin on 2018/12/17.
 */

public interface IPresenter<V> {

    void attachView(V view);

    void detachView();
}
