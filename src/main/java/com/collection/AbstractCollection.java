package com.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/24
 **/
public abstract class AbstractCollection<E> implements Collection<E> {

    protected AbstractCollection() {
    }

    @Override
    public abstract Iterator iterator();

    @Override
    public abstract int size();

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    //分为两种判断，null和非null值
    @Override
    public boolean contains(Object obj) {
        Iterator it = iterator();
        if (obj == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     *  此方法是同步的，即使在遍历集合的过程中发现集合已经改变，任然可以返回
     * 正确的结果
     * 此方法等同于:
     * List<E> list = new ArrayList<E>(size());
     * for (E e : this)
     *     list.add(e);
     * return list.toArray();
     * @return
     */
    @Override
    public Object[] toArray(){
        Object[] r = new Object[size()];
        Iterator it = iterator();
        for(int i =0;i<r.length;i++){
            //可以防止在多线程操作时迭代时出错
            if(!it.hasNext()){
                return Arrays.copyOf(r,i);
            }
            r[i]=it.next();
        }
        //如果发现iterator里仍旧有数据，把剩余的数据放入数组里。
        return it.hasNext()?finishToArray(r,it):r;
    }

    @Override
    public <T> T[] toArray(T[] a) {
//        初始化数组的长度 = 集合的长度
      int size = size();
//        通过反射创建相应数据类型的数组
      T[] r = a.length >=size?a:(T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(),size);
      //创造迭代器，为循环做准备
      Iterator<E> it = iterator();
      for(int i = 0;i>r.length;i++){
          //如果集合的数组减少了。为数组附上null值
          if(!it.hasNext()){
              //通过集合的长度和数组初始化的疮毒判断
              if(a == r){
                  r[i] =null;
              }else  if(a.length<i){
                  return Arrays.copyOf(r,i);
              }else{
                  System.arraycopy(r,0,a,0,i);
                  if(a.length>i){
                      a[i] = null;
                  }
              }
              return a;
          }
          r[i] = (T)it.next();
      }
      return it.hasNext()?finishToArray(r,it):r;
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     *把剩余的数组元素，放入数组里。
     */
    private static <T> T[] finishToArray(T[] r,Iterator<?> it){
        int i = r.length;
        while(it.hasNext()){
            int cap = r.length;
            //未重新分配数组长度
            if(i == cap){
                //约扩大0.5倍
                int newCap = cap + (cap>>1) +1;
                //检验新数组最大长度，不能超过最大长度  Integer.MAX_VALUE
                if(newCap -MAX_ARRAY_SIZE>0){
                    newCap = hugeCapacity(cap +1);
                }
                r = Arrays.copyOf(r,newCap);
            }
            r[i++] = (T)it.next();
        }
        return r;
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0){
            throw new OutOfMemoryError
                    ("Required array size too large");
        }
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    @Override
    public boolean add(E e){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o){
        Iterator it = iterator();
        if(o == null){
            while(it.hasNext()){
                if(it.next() == null) {
                    it.remove();
                    return true;
                }
            }
        }else{
            while(it.hasNext()){
                if(o.equals(it.next())){
                    it.remove();
                    return  true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection c){
        for(Object e:c){
            if(!contains(e)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c){
        boolean modified = false;
        for(E e:c){
            if(add(e)){
                modified = true;
            }
        }
        return modified;
    }

    //取与其他集合的非集
    @Override
    public boolean removeAll(Collection<?> c){
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while(it.hasNext()){
            if(c.contains(it.next())){
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    //去与其他集合的交集
    @Override
    public boolean retainAll(Collection<?> c){
        boolean modified = false;
        Iterator<E> e = iterator();
        while(e.hasNext()){
            if(!c.contains(e.next())){
                e.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear(){
        Iterator<E> e = iterator();
        while(e.hasNext()){
            e.next();
            e.remove();
        }
    }

    @Override
    public String toString(){
        Iterator<E> i = iterator();
        if(!i.hasNext()){
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(;;){
            E e = i.next();
            //不知道加上这个判断有啥意思耶
            sb.append(e == this ?"(this Collection":e);
            if(!i.hasNext()){
                return sb.append(']').toString();
            }
            sb.append(",");
        }
    }

}

