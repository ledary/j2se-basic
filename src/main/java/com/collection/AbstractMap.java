package com.collection;


import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/24
 **/
public abstract class AbstractMap<K,V> implements Map<K,V> {
    protected AbstractMap(){}

    @Override
    public int size(){
        return entrySet().size();
    }

    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public boolean containsValue(Object value){
        Iterator<Entry<K,V>> i = entrySet().iterator();
//        检测是否存在null值
        if(value == null){
            while(i.hasNext()){
                Entry<K,V> e = i.next();
                if(e.getValue() == null){
                    return true;
                }
            }
        }else{
//            不是null值时，是否包含value值
            while(i.hasNext()){
                Entry<K,V> entry = i.next();
                if(value.equals(entry.getValue())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(Object key){
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();
        if(key == null){
            while(i.hasNext()){
                Entry<K,V> e = i.next();
                if(e.getKey() == null){
                    return true;
                }
            }
        }else{
            while(i.hasNext()){
                Entry<K,V> e = i.next();
                if(e.getKey() == key){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V put(K key,V value){
        throw new UnsupportedOperationException();
    }

//    如果存在一个键的映射关系，则将其从此映射中移除 返回旧值
    @Override
    public V remove(Object key){
        Iterator<Entry<K,V>> i = entrySet().iterator();
        Entry<K,V> correctEntry = null;
        if(key == null){
            while (correctEntry == null && i.hasNext()){
                Entry<K,V> e = i.next();
                if(e.getKey() == null){
                    correctEntry = e;
                }
            }
        }else{
            while (correctEntry == null && i.hasNext()){
                Entry<K,V> e = i.next();
                if(key.equals(e.getKey())){
                    correctEntry = e;
                }
            }
        }
        V oldValue = null;
        if(correctEntry != null){
            oldValue = correctEntry.getValue();
            i.remove();
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends  K,? extends V> m){
        for(Map.Entry<? extends K,? extends V> e:m.entrySet()){
            put(e.getKey(),e.getValue());
        }
    }

    @Override
    public void clear(){
        entrySet().clear();
    }

//    Views 视图

    transient Set<K> keySet;
    transient Collection<V> values;

    @Override
    public Set<K> keySet(){
        Set<K> ks = keySet;
        if(ks == null){
            ks = new AbstractSet<K>() {
                @Override
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();
                        @Override
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        @Override
                        public K next() {
                            return i.next().getKey();
                        }
                        @Override
                        public void remove(){
                            i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
                @Override
                public boolean isEmpty(){
                    return AbstractMap.this.isEmpty();
                }
                @Override
                public void clear(){
                    AbstractMap.this.clear();
                }
            };
            keySet = ks;
        }
        return ks;
    }

    @Override
    public Collection<V> values(){
        Collection<V> vals = values;
        if(vals == null){
            vals = new AbstractCollection<V>() {
                @Override
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();
                        @Override
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        @Override
                        public V next() {
                            return i.next().getValue();
                        }
                        @Override
                        public void remove(){
                            i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }
                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }
                @Override
                public boolean contains(Object v) {
                    return AbstractMap.this.containsValue(v);
                }


            };
            values = vals;
        }
        return vals;
    }

    @Override
    public abstract Set<Entry<K,V>> entrySet();

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Map)){
            return false;
        }
        Map<?,?> m = (Map<?,?>)o;
        if(m.size()!=size()){
            return false;
        }
        try{
            Iterator<Entry<K,V>> i = entrySet().iterator();
            //存在两种情况，null值 和非null值
            while(i.hasNext()){
                Entry<K,V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if(value == null){
                    if(!(m.get(key)== null && m.containsKey(key))){
                        return false;
                    }
                }else{
                    if(!value.equals(m.get(key))){
                        return false;
                    }
                }
            }
        }catch (ClassCastException unused){
            return false;
        }catch (NullPointerException e){
            return false;
        }
        return true;

    }

    @Override
    public int hashCode(){
        int h = 0;
        Iterator<Entry<K,V>> i = entrySet().iterator();
        while(i.hasNext()){
            h += i.next().hashCode();
        }
        return h;
    }

    @Override
    public String toString(){
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if(!i.hasNext()){
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for(;;){
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V v = e.getValue();
            sb.append(key == this?"(this map)":key);
            if(!i.hasNext()){
                return sb.append('}').toString();
            }
            sb.append(',').append(' ');
        }
    }



}
