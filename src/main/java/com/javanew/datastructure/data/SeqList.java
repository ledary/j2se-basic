package com.javanew.datastructure.data;

/**
 * @author WGP
 * @description
 * @date 2018/5/28
 **/

/*
 从上面发现，在插入和删除的时候都是需要大量元素的移动，
 因为在各个位置插入元素的概率相同，所以平均插入一个元素要移动的元素是2/n。
 那么它的时间复杂度就是O(n)，如果要容器满了，
 那么就需要申请更大的容器来存储新加入的元素，那么效率会更加的低下。
 所以顺序表的静态性好，动态性就很差了，所以在选择的时候就要注意一下。
 */
public class SeqList<T> implements IList<T> {
    private Object[] element;
    private int len;
    public SeqList(){this(64);}

    public SeqList(int size){
        this.element = new Object[size];
        this.len = 0;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public T get(int i) {
        return null;
    }

    @Override
    public void set(int i, T x) {

    }

    //移除某位置的元素
    @Override
    public T remove(int i) {
        if(i<0 || i>this.len -1){
            throw new IndexOutOfBoundsException();
        }
        T temp = (T)this.element[i];
        if(i == this.len -1){
            this.element[this.len-1] = null;
            this.len --;
            return temp;
        }
        for(int j=i;j<this.len-1;j++){
            this.element[j] = this.element[j+1];
        }
        this.len --;
        return temp;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public void append(T x) {

    }

    @Override
    public T search(T key) {
        return null;
    }

    /**
     * 在i位置插入元素X
     * @param i
     * @param x
     */
    @Override
    public void insert(int i, T x) {
        //先判断插入的索引是否合法
        if(i<0){
            throw new IndexOutOfBoundsException();
        }
        //数组已经满了，进行扩容
        if(this.len == this.element.length){
            Object[] temp = this.element;
            this.element = new Object[2*this.len];
            for(int m =0;m<this.len;m++){
                this.element[m] = temp[m];
            }
        }
        //i位置后的元素往后移位，给i位置腾出地
        for(int j =i;j<this.len;j++){
            this.element[j+1] = this.element[j];
        }
        this.element[i] = x;
        this.len ++;
    }

    @Override
    public void insert(T x) {

    }

    @Override
    public void remove(T x) {

    }
}
