package com.lemonjun.mysql.orm.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author WangYazhou
 * @date 2016年6月8日 上午10:56:12
 * @see
 */
public class ReflectionUtils {

    /**
     * 得到所有field , 包括 父类
     *
     * @param clazz
     * @return
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    public static Field[] findFields(Class clazz) throws IllegalAccessException {
        final List<Field> fieldList = new ArrayList<Field>();

        doWithDeclaredFields(clazz, new FieldCallback() {
            public void doWith(Field field) {
                fieldList.add(field);
            }
        });
        return fieldList.toArray(new Field[fieldList.size()]);
    }

    @SuppressWarnings("rawtypes")
    public static Field findField(Class clazz, String name) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), name);
            }
            throw e;
        }
    }

    @SuppressWarnings("rawtypes")
    private static void doWithDeclaredFields(Class clazz, FieldCallback fieldCallback) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldCallback.doWith(field);
        }
        if (clazz.getSuperclass() != null) {
            doWithDeclaredFields(clazz.getSuperclass(), fieldCallback);
        }
    }

    protected interface FieldCallback {

        void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
    }
}
