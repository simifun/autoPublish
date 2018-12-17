package fun.qsong.utils.util;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewUtils {

    private ViewUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取view的所有子view
     */
    public static List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<>();
        if (view == null) {
            return allchildren;
        }
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);

                //再次 调用本身（递归）
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    /**
     * 获取view的子view，不是所有的view
     */
    public static List<View> getChildViews(View view) {
        List<View> allchildren = new ArrayList<>();
        if (view == null) {
            return allchildren;
        }
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
            }
        }
        return allchildren;
    }

    /**
     * 获取view的所有子view
     */
    public static List<View> getAllChildViews(View view, Class c) {
        List<View> allchildren = new ArrayList<>();
        if (view == null || c == null) {
            return allchildren;
        }
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (c.isInstance(viewchild)) {
                    allchildren.add(viewchild);
                }
                //再次 调用本身（递归）
                allchildren.addAll(getAllChildViews(viewchild, c));
            }
        }
        return allchildren;
    }

    /**
     *  获取view的子view，不是所有的view
     */
    public static List<View> getChildViews(View view, Class c) {
        List<View> allchildren = new ArrayList<>();
        if (view == null || c == null) {
            return allchildren;
        }
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (c.isInstance(viewchild)) {
                    allchildren.add(viewchild);
                }
            }
        }
        return allchildren;
    }

}
