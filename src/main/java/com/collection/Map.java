package com.collection;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author WGP
 * @description
 * @date 2018/5/24
 **/

//参考本篇博客：https://blog.csdn.net/oqkdws/article/details/79977115
public interface Map<K, V> {

    //返回key -value 的数量
    int size();

    //判断集合是否为空，根据key value 的数量判断，为0则为true fouze false
    boolean isEmpty();

    //对于给定的key
    boolean containsKey(Object o);

    boolean containsValue(Object o);

    //    根据给定的key  返回对应的value
    V get(Object key);

    //    将一个key value 放入map中
    V put(K key, V value);

    //对于给定的key  首先找到
    V remove(Object key);

    //    给定一个map集合，将m中所有的key value放入map中
    void putAll(Map<? extends K, ? extends V> m);

    //清楚
    void clear();

    //返回Map中所有的key集合
    Set<K> keySet();

    //返回Map中所有的value集合
    Collection<V> values();

    //    因为map的接口不对
    Set<Map.Entry<K, V>> entrySet();

    //key -value对象。封装了键值，value字段
    interface Entry<K, V> {
        K getKey();

        V getValue();

        V setValue(V value);

        @Override
        boolean equals(Object o);

        @Override
        int hashCode();

        /*----------以下均为jdk8新增内容-----------*/

        /**
         * 返回一个比较map.entry的比较器,按照key的自然顺序排序.
         * 返回的比较器支持序列化.
         * 如果map中的entry有key=null情况,则抛出空指针异常(因为返回结果要按照key排序)
         * 注意:传入参数k必须支持Comparable接口,因为需要按照key排序.
         *
         * @see java.lang.Comparable
         * @since 1.8
         */
        public static <K extends java.lang.Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        /**
         * 返回一个map.entry的比较器,按照value的自然顺序排序.
         * 返回的比较器支持序列化.
         * 如果map中的entry有value=null情况,则抛出空指针异常(因为返回结果要按照value排序)
         * 注意:传入参数value必须支持Comparable接口,因为按照value排序.
         *
         * @see java.lang.Comparable
         * @since 1.8
         */
        public static <K, V extends java.lang.Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        /**
         * 返回一个map.entry的比较器,根据传入比较器对key排序.
         * 如果传入的比较器支持序列化,则返回的结果比较器也支持序列化.
         *
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        /**
         * 返回一个map.entry的比较器,根据传入比较器对value排序.
         * 如果传入的比较器支持序列化,则返回的结果比较器也支持序列化.
         *
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }

    //返回key对应的value如果没有对应的映射，返回传入的参数

    default V getOrDefault(Object key, V defaultValue) {
        V v;
//        如果map里存在key,并且keu对应的值不为null或者map里包含对应的value，就返回V，否则返回传入的参数
        return ((v = get(key)) != null) || containsValue(defaultValue) ? v : defaultValue;
    }

    /**
     * 对map中每一个entry执行action中定义对操作,直到全部entry执行完成or执行中出现异常为止.
     * 除非map的实现类有规定,否则forEach的执行顺序为entrySet中entry的顺序.
     * 执行中的异常最终抛给方法的调用者.
     *
     * @implSpec The default implementation is equivalent to, for this {@code map}:
     * 本方法的默认实现和下面的代码是等价的:
     * for (Map.Entry<K, V> entry : map.entrySet())
     * action.accept(entry.getKey(), entry.getValue());
     * }
     * <p>
     * 此默认方法对于线程同步和执行过程的原子性并不能保证.
     * 任何实施提供原子性保证必须重写此方法并记录其方法.
     * @since 1.8
     */
    //所以foreach可以使用lambda表达式，传递 没有参数的表达式。因为这是一个消费型function
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
                action.accept(k, v);
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
        }
    }

    //传递有参数的function

    /**
     * 对于map中每一个entry,将其value替换成BiFunction接口返回的值.直到所有entry替换完or出现异常为止.
     * 如果执行过程中出现异常,则抛给调用者.
     *
     * @implSpec 本方法的默认执行和下面代码等价:
     * for (Map.Entry<K, V> entry : map.entrySet())
     * entry.setValue(function.apply(entry.getKey(), entry.getValue()));
     * }
     * <p>
     * 此默认方法对于线程同步和执行过程的原子性并不能保证.
     * 任何实施提供原子性保证必须重写此方法并记录其方法.
     * @since 1.8
     */
    default void replaceAll(java.util.function.BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException e) {
                throw new ConcurrentModificationException(e);
            }
            v = function.apply(k, v);
            try {
                entry.setValue(v);
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
        }
    }

    default V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }
        return v;
    }

    default boolean remove(Object key, Object value) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, value) || (curValue == null && !containsKey(key))) {
            return false;
        }
        remove(key);
        return true;
    }

    default boolean replace(K key, V oldValue, V newValue) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, oldValue) || (curValue == null && !containsKey(key))) {
            return false;
        }
        put(key, newValue);
        return true;
    }

    //这个返回的是旧职
    default V replace(K key, V value) {
        V curValue;
        if ((curValue = get(key)) != null || containsKey(key)) {
            curValue = put(key, value);
        }
        return curValue;
    }

    //    如果指定key在map中没有对应的value,则使用输入参数,即函数接口mappingfunction为其计算一个value.
//     * 如果计算value不为null,则将value插入map中.
    default V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }
        return v;
    }

    //    如果map中存在指定key对应的value,且不为null,则本方法会尝试使用function,并利用key生成一个新的value.
//             * 如果function接口返回null,map中原entry则被移除.如果function本身抛出异常,则当前map不会发生改变.
    default V computeIfPresent(K key, java.util.function.BiFunction<? super K, ? super V, ? extends V> remappintFunction) {
        Objects.requireNonNull(remappintFunction);
        V oldValue;
//        如果存在对应key的value值，则利用旧值生成新值，放入map里。返回新值
//        如果旧值存在，但是无法生成新值，则删除旧值，返回 null
        if ((oldValue = get(key)) != null) {
            V newValue = remappintFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        } else {
            return null;
        }
    }

    default V compute(K key, java.util.function.BiFunction<? super K, ? super V, ? extends V> remappintFunction) {
        Objects.requireNonNull(remappintFunction);
        V oldValue = get(key);
//        只要新值有值，无论旧值如何，都存入map里，返回新值。新值无值，就删除旧值，返回null
        V newValue = remappintFunction.apply(key, oldValue);
        if (newValue == null) {
            if (oldValue != null || containsKey(key)) {
                remove(key);
                return null;
            } else {
                return null;
            }
        } else {
            put(key, newValue);
            return newValue;
        }

    }

//    如果指定key没有value,或者其value为null,则将其改为给定的非null的value.
//     * 否则,用给定的function返回值替换原value.
//     * 如果给定参数value和function返回结果都为null,则删除map中这个entry.
//     * 这一方法常用于:对一个key合并多个映射的value时.
//     * 比如:要创建或追加一个String给一个值映射.
    default V compute(K key, V value, java.util.function.BiFunction<? super V, ? super V, ? extends V> remappintFunction) {
        Objects.requireNonNull(remappintFunction);
        Objects.requireNonNull(value);
//        获取旧值
        V oldValue  = get(key);
//        如果旧值为null,则新值为传入的参数
//         如果旧值不为null,则新值为生产型function产生的值
        V newValue = (oldValue == null)?value:remappintFunction.apply(oldValue,value);
//        如果新值为null的话，删除旧值，否则插入新值
        if(newValue == null){
            remove(key);
        }else{
            put(key,newValue);
        }
        return newValue;
    }
}
