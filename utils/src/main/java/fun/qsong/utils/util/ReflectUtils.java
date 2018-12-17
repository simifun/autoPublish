package fun.qsong.utils.util;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *     author: Yanghan
 *     time  : 2017/10/25
 *     desc  : 反射相关工具类
 * </pre>
 */
public class ReflectUtils {

    private ReflectUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 通过反射获取符合list<String>的dataList
     *
     * @param oriDataList 数据源
     * @param list        判断的list
     * @param fieldname   需要判断的字段（在数据源中）
     */
    public static List<Object> getWholeObjectList(List<? extends Object> oriDataList, List<String> list,
                                                  String fieldname) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        List<Object> objectList = new ArrayList<>();
        if (EmptyUtils.isNotEmpty(oriDataList) && EmptyUtils.isNotEmpty(list)) {
            for (Object obj : oriDataList) {
                Field field = obj.getClass().getDeclaredField(fieldname);
                field.setAccessible(true);
                String val = field.get(obj).toString();
                if (list.contains(val)) {
                    objectList.add(obj);
                }
            }
        }
        return objectList;
    }

    /**
     * 通过反射获取符合list<String>的dataList
     *
     * @param oriDataList 数据源
     * @param fieldvalue  字段的值
     * @param fieldname   需要判断的字段（在数据源中）
     */
    public static List<Object> getWholeObjectList(List<? extends Object> oriDataList, String fieldvalue,
                                                  String fieldname) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        List<Object> objectList = new ArrayList<>();
        if (EmptyUtils.isNotEmpty(oriDataList) && EmptyUtils.isNotEmpty(fieldvalue)) {
            for (Object obj : oriDataList) {
                Field field = obj.getClass().getDeclaredField(fieldname);
                field.setAccessible(true);
                String val = field.get(obj).toString();
                if (fieldvalue.equals(val)) {
                    objectList.add(obj);
                }
            }
        }
        return objectList;
    }

    /**
     * 通过反射获取符合fieldvalue的第一个对象
     *
     * @param oriDataList 数据源
     * @param fieldvalue  字段的值
     * @param fieldname   需要判断的字段（在数据源中）
     */
    public static Object getOneObject(List<? extends Object> oriDataList, String fieldvalue,
                                      String fieldname) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        List<Object> objectList = new ArrayList<>();
        if (EmptyUtils.isNotEmpty(oriDataList) && EmptyUtils.isNotEmpty(fieldvalue)) {
            for (Object obj : oriDataList) {
                Field field = obj.getClass().getDeclaredField(fieldname);
                field.setAccessible(true);
                String val = field.get(obj).toString();
                if (fieldvalue.equals(val)) {
                    return obj;
                }
            }
        }
        return objectList;
    }

    /**
     * @Description: 处理一组list，并将其元素的指定属性放在list返回
     * @author 书杰 2016年5月17日 上午10:56:46
     * @parameters @param list
     * @parameters @param filedname
     * @parameters @return
     * @parameters @throws NoSuchFieldException
     * @parameters @throws IllegalAccessException
     * @parameters @throws IllegalArgumentException
     * @returns
     */

    public static List<String> getSpecifiedFieldList(List<? extends Object> list, String fieldname)
            throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        List<String> fieldList = new ArrayList<String>();
        if (EmptyUtils.isNotEmpty(list)) {
            for (Object obj : list) {
                Field field = obj.getClass().getDeclaredField(fieldname);
                field.setAccessible(true);
                Object valObj = field.get(obj);
                fieldList.add(valObj.toString());
            }
        }
        return fieldList;
    }

    public static String getConnectString(List<? extends Object> list, String fieldName, String split) {
        StringBuilder builder = new StringBuilder();
        for (Object obj : list) {
            Object o = getReflect(obj, fieldName);
            /**
             * 这里做特殊处理一下，为null的数据以空字符串代替
             * */
            if (o != null) {
                builder.append(o).append(split);
            }
        }
        if (!TextUtils.isEmpty(builder.toString())) {
            return builder.substring(0, builder.length() - 1);
        }
        return "";
    }


    /**
     * 对象去重操作
     *
     * @return 返回的是去重后的数据
     */
    public static List<? extends Object> getDistinctList(List<? extends Object> list, String... fieldNames)
            throws IllegalArgumentException {
        List<Object> objectList = new ArrayList<>();
        Set<String> setValueList = new HashSet<>();
        for (Object obj : list) {
            String values = "";
            for (String fieldName : fieldNames) {
                Object o = getReflect(obj, fieldName);
                /**
                 * 这里做特殊处理一下，为null的数据以空字符串代替
                 * */
                if (o == null) {
                    values += "" + ",";
                } else {
                    values += o + ",";
                }
            }

            // 字段集去重
            if (!setValueList.contains(values)) {
                objectList.add(obj);
                setValueList.add(values);
            }
        }
        return objectList;
    }

    /**
     * 通过反射获取对象里key对应的set方法，用于存值
     *
     * @param bean  实体对象
     * @param key   属性名
     * @param value 保存值
     */
    public static <T> void setReflect(T bean, String key, Object value) {
        if (bean == null) {
            return;
        }
        Class cls = bean.getClass();
        try {
            Field f = cls.getDeclaredField(key);
            f.setAccessible(true);// 设置属性是可以访问的
            f.set(bean, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取对象里key对应的get方法，用于取值
     *
     * @param bean 实体对象
     * @param key  属性名
     * @return 返回值
     */
    public static <T> Object getReflect(T bean, String key) {
        Object object = null;
        if (bean == null) {
            return null;
        }
        Class cls = bean.getClass();
        try {
            Field f = cls.getDeclaredField(key);
            f.setAccessible(true);// 设置属性是可以访问的
            object = f.get(bean);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 通过反射获取方法名对应的方法，用于存值
     *
     * @param bean          实体对象
     * @param methodName    方法名
     * @param value         需要存的值
     * @param parameterType 该值的类型，与value相互对应 例如：String.class、Integer.class、Boolean.class等
     */
    public static <T> void setReflectName(T bean, String methodName, Object value, Class parameterType) {
        if (bean == null) {
            return;
        }
        Class cls = bean.getClass();
        try {
            Method method = cls.getDeclaredMethod(methodName, parameterType);
            method.setAccessible(true);
            method.invoke(bean, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取方法名对应的方法，用于取值
     *
     * @param bean       实体对象
     * @param methodName 方法名
     * @return 返回值
     */
    public static <T> Object getReflectName(T bean, String methodName) {
        Object object = null;
        if (bean == null) {
            return null;
        }
        Class cls = bean.getClass();
        try {
            Method method = cls.getDeclaredMethod(methodName);
            method.setAccessible(true);// 设置属性是可以访问的
            object = method.invoke(bean);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
}
