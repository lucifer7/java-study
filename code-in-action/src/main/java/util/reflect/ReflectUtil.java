package util.reflect;

import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Move and refactor form mars, Test before Use!!
 */
public class ReflectUtil {
    private static final Logger LOG = Logger.getLogger(ReflectUtil.class);

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    /**
     * <pre>
     *  将src对象中的属性copy到target中
     *  1. src中不关心其父类的属性
     *  2. target中需要迭代查询其父类属性
     * </pre>
     */
    public static void copyFields(Object src, Object target) {
        //遍历src所有的属性名字
        Class srcClz = src.getClass();
        Field[] fields = srcClz.getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                invokeSetter(target, fieldName, field.get(src));
            }
        } catch (Exception e) {
            Throwables.propagate(e);
        }
    }

    /**     *
     * @param fieldName
     * @param obj
     * @return
     */
    @Deprecated
    public static Object getFieldValueByName(String fieldName, Object obj) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            //Method method = obj.getClass().getMethod(getter, new Class[]{});
            Method method = obj.getClass().getMethod(getter);
            //return method.invoke(obj, new Object[]{});
            return method.invoke(obj);
        } catch (Exception e) {
            LOG.error("getFieldValueByName error", e);
            return null;
        }
    }


    /**
     * 调用Getter方法.
     * 支持多级，如：对象名.对象名.方法
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : StringUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
        }
        return object;
    }

    /**
     * 调用Setter方法, 仅匹配方法名。
     * 支持多级，如：对象名.对象名.方法
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) {
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[]{value});
            }
        }
    }


    /**
     * 直接调用对象方法, 无视private/protected修饰符，
     * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
     * 只匹配函数名，如果有多个同名函数调用第一个。
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用.
     * 同时匹配方法名+参数类型，
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 调用 static 类中的 static 方法
     * @param clz
     * @param methodName
     * @param parameterTypes
     * @param args
     * @return Object
     */
    public static Object invokeStaticMethod(Class<?> clz, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        if (clz == null || methodName == null) {
            throw new IllegalArgumentException("Null class and null method error.");
        }

        Method method;
        try {
            method = clz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
        try {
            return method.invoke(null, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 只匹配函数名。
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }


    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 匹配函数名+参数类型。
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException ignore) {
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }


    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }


    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * <pre>
     *     获取当前类中的所有属性的名字
     * </pre>
     * @param src
     * @return
     */
    public static List<String> getDeclaredFields(Object src) {
        //遍历src所有的属性名字并返回
        Class srcClz = src.getClass();
        Field[] fields = srcClz.getDeclaredFields();
        List<String>fieldNames=new ArrayList<String>();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                fieldNames.add(fieldName);
            }
            return fieldNames;
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        return Collections.emptyList();
    }

    /**
     * Get all fields of object, filter synthetic fields or not
     * @param src               target object
     * @param filterSynthetic   if true, filter synthetic fields
     * @return                  a list of field name
     */
    public static List<String> getDeclaredFields(Object src, boolean filterSynthetic) {
        Class srcClz = src.getClass();
        Field[] fields = srcClz.getDeclaredFields();
        List<String> fieldNames = Lists.newArrayListWithExpectedSize(fields.length);
        for (Field field : fields) {
            if (filterSynthetic && field.isSynthetic()) {       // filter synthetic fields if necessary
                continue;
            }
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    /**
     * Check whether specified fields of entity are null or empty(for string)
     * @param src           instance of entity
     * @param checkFields   specified fields
     * @return              a list of error messages
     */
    public static List<String> checkFieldsNotBlank(Object src, String[] checkFields) {
        List<String> errors = Lists.newLinkedList();
        if (ArrayUtils.isEmpty(checkFields) && null == src) {
            errors.add("Illegal arguments.");
        } else {
            for (String field : checkFields) {
                if (StringUtils.isNotBlank(field)) {
                    Object value = invokeGetter(src, field);
                    if (null == value) {
                        errors.add(field + "cannot be null.");
                    } else if (value instanceof CharSequence && StringUtils.isBlank((CharSequence) value)) {
                        errors.add(field + "cannot be empty string");
                    }
                }
            }
        }
        return errors;
    }

    /**
     * Filter unnecessary fields, like id and synthetic
     * @param fields    field array
     * @param filterId  if true, filter 'id' field
     * @return          filtered field array
     */
    public static Field[] filterFields(Field[] fields, final boolean filterId) {
        //Iterable<Field> filtered = Iterables.filter(Arrays.asList(fields), new Predicate<Field>() {
        Iterable<Field> filtered = Arrays.stream(fields)
                .filter(input -> {
                            assert input != null;
                            return !(filterId && input.getName().equalsIgnoreCase("id")) && !input.isSynthetic();
                        })
                .collect(Collectors.toList());
        return Iterables.toArray(filtered, Field.class);
    }

    /**
     * Generate comma separated lines from list of entity
     * A line for each entity
     * @param list      entity list
     * @param fields    entity fields array
     * @return          line array
     */
    public static String[] genLineFromEntities(List list, Field[] fields) throws IllegalAccessException {
        // 1. Initial
        String[] lines = new String[list.size()];

        // 2. Generate lines, set null value to ""
        String[] item = new String[fields.length];
        for (int i = 0; i < list.size(); i++) {
            Object entity = list.get(i);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                Object obj = fields[j].get(entity);
                item[j] = String.valueOf(null == obj ? "" : obj);
            }
            lines[i] = StringUtil.join(item, ',');
        }

        // 3. Return lines
        return lines;
    }
}
