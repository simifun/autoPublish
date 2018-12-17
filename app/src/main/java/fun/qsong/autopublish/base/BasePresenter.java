package fun.qsong.autopublish.base;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by admin on 2018/12/17.
 */

public abstract class BasePresenter<V> implements IPresenter<V>{
    public final String TAG = this.getClass().getSimpleName();
    protected Context context;
    protected V mIView;
    //定义弱引用接口
    private WeakReference<V> weakReference;

    private ViewHandler viewHandler;

    @Override
    public void attachView(V view) {
        weakReference = new WeakReference<V>(view);
        viewHandler = new ViewHandler((IMvpView) weakReference.get());
        mIView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), viewHandler);
        init();
    }

    protected abstract void init();

    /**
     * @return P层和V层是否关联.
     */
    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    @Override
    public void detachView() {
        if (isViewAttached()) {
            mIView = null;
            weakReference.clear();
            weakReference = null;
        }
    }

    /**
     * 动态代理
     */
    private class ViewHandler implements InvocationHandler {
        private IMvpView iMvpView;

        ViewHandler(IMvpView iMvpView) {
            this.iMvpView = iMvpView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isViewAttached()) {
                return method.invoke(iMvpView, args);
            }
            return null;
        }
    }
}
