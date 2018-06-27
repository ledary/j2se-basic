package com.collection;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/24
 **/
public abstract class AbstractSet<E> extends AbstractCollection<E> implements java.util.Set<E> {

    protected AbstractSet(){}

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean removeIf(Predicate filter) {
        return false;
    }

    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) {
            return true;
        }
        if(!(o instanceof  Set)){
            return false;
        }
        Collection<?> c = (Collection<?>)o;
        if(c.size() != size()){
            return false;
        }
        try{
            return containsAll(c);
        }catch (ClassCastException unused){
            return false;
        }catch (NullPointerException unused){
            return false;
        }
    }


    @Override
    public int hashCode(){
        int h = 0;
        Iterator<E> i = iterator();
        while(i.hasNext()){
            E obj = i.next();
            if(obj != null){
                h += obj.hashCode();
            }
        }
        return h;
    }

//    删除c中的元素
    @Override
    public boolean removeAll(Collection<?> c){
        Objects.requireNonNull(c);
        boolean modified = false;
        if(size() > c.size()){
            for(Iterator<?> i = c.iterator();i.hasNext();){
                modified |= remove(i.next());
            }
        }else{
            for(Iterator<?> i = iterator();i.hasNext();){
                if(c.contains(i.next())){
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }
}
