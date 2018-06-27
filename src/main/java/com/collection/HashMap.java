//package com.collection;
//
//import javax.swing.tree.TreeNode;
//import java.io.Serializable;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.LinkedHashMap;
//import java.util.Objects;
//import java.util.Set;
//import java.util.function.BiConsumer;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
///**
// * @author WGP
// * @descriptioncrude-trade
// * @date 2018/5/25
// **/
//public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
//       private java.util.HashMap
//    private static final long serialVersionUID = 362498820763181265L;
//    //初始容量为 16,且初始容量必须为2的幂次
//    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
//
//
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
//
//    //一个桶的元素数超过8，由链表变为红黑树
//    static final int TREEIFY_THRESHOLD = 8;
//
//    //一个桶中的元素少于6时会从红黑树转换为链表
//    static final int UNTREEIFY_THRESHOLD = 6;
//
//    static final int MIN_TREEIFY_CAPACITY = 64;
//
//
//    static class Node<K, V> implements Map.Entry<K, V> {
//        final int hash;
//        final K key;
//        V value;
//        Node<K, V> next;
//
//        Node(int hash, K key, V value, Node<K, V> next) {
//            this.hash = hash;
//            this.key = key;
//            this.value = value;
//            this.next = next;
//        }
//
//        @Override
//        public final K getKey() {
//            return key;
//        }
//
//        @Override
//        public final V getValue() {
//            return value;
//        }
//
//        @Override
//        public final String toString() {
//            return key + "=" + value;
//        }
//
//        ;
//
//        @Override
//        public final int hashCode() {
//            return Objects.hashCode(key) ^ Objects.hashCode(value);
//        }
//
//        @Override
//        public final V setValue(V newValue) {
//            V oldValue = value;
//            value = newValue;
//            return oldValue;
//        }
//
//        @Override
//        public final boolean equals(Object o) {
//            if (o == this) {
//                return true;
//            }
//            if (o instanceof Map.Entry) {
//                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
////                return (a == b) || (a != null && a.equals(b));
//                if (Objects.equals(key, e.getKey())
//                        && Objects.equals(value, e.getValue())) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//    //利用异或操作，高位和低位相互掺入。减少碰撞 https://www.zhihu.com/question/20733617
//    static final int hash(Object key) {
//        int h;
//        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
//    }
//
//    //        todo:该方法有什么作用
////        具体解释详见该文章：https://blog.csdn.net/qpzkobe/article/details/79533237
//    static Class<?> comparableClassFor(Object x) {
//        //判断该对象是否实现了 Comparable接口
//        if (x instanceof Comparable) {
//            Class<?> c;
//            Type[] ts, as;
//            Type t;
//            ParameterizedType p;
//            //如果是String 类型，直接返回String.class
//            if ((c = x.getClass()) == String.class) {
//                return c;
//            }
////                判断是否有直接实现接口
//            if ((ts = c.getGenericInterfaces()) != null) {
//                //有的话，循环实现的接口
//                for (int i = 0; i < ts.length; ++i) {
//                    //是否实现泛型，获取接口不带参数的类型对象
//                    if ((t = ts[i]) instanceof ParameterizedType &&
//                            ((p = (ParameterizedType) t).getRawType() == Comparable.class) &&
//                            (as = p.getActualTypeArguments()) != null && as.length == 1 && as[0] == c) {
//                        return c;
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    //        todo
//    static int compareComparables(Class<?> kc, Object k, Object x) {
//        return (x == null || x.getClass() != kc) ? 0 : ((Comparable) k).compareTo(x);
//    }
//
//    //        https://blog.csdn.net/fan2012huan/article/details/51097331
//    static final int tableSizeFor(int cap) {
////            -1操作，防止新的cap为原有的cap的2倍，数太大
//        int n = cap - 1;
//        n |= n >>> 1;
//        n |= n >>> 2;
//        n |= n >>> 4;
//        n |= n >>> 8;
//        n |= n >>> 16;
//        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
//    }
//
//    //存放元素的数组  table通常设置为2的整数次幂
//    transient Node<K, V>[] table;
//
//    //存放具体元素的集
//    transient Set<Entry<K, V>> entrySet;
//    //map里包含键值对的数量
//    transient int size;
//    //对HashMap操作的次数：  有特殊的作用 具体忘了。
//    transient int modCount;
//
//
//    //Hashtable resize 的门限值 一旦HashMap的size超过了这个值
//    //就需要对HashMap进行扩容
//    //The next size value at which to resize (capacity * load factor).
//    int threshold;
//    //        Hashtable的装载因子
//    final float loadFactor;
//
//    public HashMap(int initialCapacity, float loadFactor) {
//        if (initialCapacity < 0) {
//            throw new IllegalArgumentException();
//        }
//        if (initialCapacity > MAXIMUM_CAPACITY) {
//            initialCapacity = MAXIMUM_CAPACITY;
//        }
//        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
//            throw new IllegalArgumentException();
//        }
//        this.loadFactor = loadFactor;
//        //最大的容量  总是2的幂次
//        this.threshold = tableSizeFor(initialCapacity);
//    }
//
//    public HashMap(int initialCapacity) {
//        this(initialCapacity, DEFAULT_LOAD_FACTOR);
//    }
//
//    public HashMap() {
//        this.loadFactor = DEFAULT_LOAD_FACTOR;
//    }
//
//    public HashMap(Map<? extends K, ? extends V> m) {
//        this.loadFactor = DEFAULT_LOAD_FACTOR;
//        putMapEntries(m,false);
//    }
//
//    //将m的所有元素存入本HashMap实例中，evict为false时 表示构造初始HashMap
//    final void putMapEntries(Map<? extends K,? extends V>m,boolean evict){
//        int s = m.size();
//        if(s > 0){
//            //table为初始化
//            if(table == null){
////                计算初始容量
//                float ft = (float)s/loadFactor + 1.0F;
//                int t = ((ft<(float)MAXIMUM_CAPACITY)?(int)ft:MAXIMUM_CAPACITY);
//                if(t<threshold){
//                    //保存容量到threshold
//                    threshold = tableSizeFor(t);
//                }
//            }//已经初始化，并且m元素个数大于阈值，进行扩容
//            else if(s > threshold){
//                resize();
//            }
//            for(Map.Entry<? extends  K,? extends  V>e: m.entrySet()){
//                K key = e.getKey();
//                V value = e.getValue();
//                putVal(hash(key),key,value,false,evict);
//            }
//        }
//    }
//
//    //对当前的HashMap进行扩容
//    final Node<K,V>[] resize(){
//        //保存当前的table
//        Node<K,V>[] oldTable = table;
//        //保存当前table大小
//        int oldCap = (oldTable == null)?0:oldTable.length;
//        //保存当前阈值
//        int oldThr = threshold;
//        int newCap,newThr = 0;
//        //之前table大于0，表示已经初始化
//        if(oldCap > 0){
//            //大于最大值，只设置阈值
//            if(oldCap >= MAXIMUM_CAPACITY){
//                threshold = Integer.MAX_VALUE;
//                return oldTable;
//            }//        容量翻倍
//            else if((newCap = oldCap <<1)<MAXIMUM_CAPACITY){
//                newThr = oldThr<<1;
//            }
//        }
//        //初始容量已经存在于threshold中
//        else if(oldThr >0){
//            newCap = oldThr;
//        }else{
//            newCap = DEFAULT_INITIAL_CAPACITY;
//            newThr = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
//        }
////        计算新阈值
//        if(newThr ==0){
//            float ft = (float)newCap * loadFactor;
//            newThr = (newCap > MAXIMUM_CAPACITY && ft<(float)MAXIMUM_CAPACITY?(int)ft:Integer.MAX_VALUE);
//        }
//        threshold = newThr;
////        初始化table
//        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
//        table = newTab;
//        if(oldTable != null){
//            for(int j = 0;j<oldCap;j++){
//                Node<K,V> e;
//                if((e = oldTable[j])!=null){
//                    oldTable[j] = null;
//                }
//                if(e.next == null){
//                    newTab[e.hash & (newCap-1)] = e;
//                }else if(e instanceof TreeNode){
//                    ((TreeNode<K,V>)e).
//                }
//            }
//        }
//
//    }
//
//
//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        return null;
//    }
//
//    @Override
//    public V get(Object key) {
//        return null;
//    }
//
//    @Override
//    public V getOrDefault(Object key, V defaultValue) {
//        return null;
//    }
//
//    @Override
//    public void forEach(BiConsumer<? super K, ? super V> action) {
//
//    }
//
//    @Override
//    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
//
//    }
//
//    @Override
//    public V putIfAbsent(K key, V value) {
//        return null;
//    }
//
//    @Override
//    public boolean remove(Object key, Object value) {
//        return false;
//    }
//
//    @Override
//    public boolean replace(K key, V oldValue, V newValue) {
//        return false;
//    }
//
//    @Override
//    public V replace(K key, V value) {
//        return null;
//    }
//
//    @Override
//    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
//        return null;
//    }
//
//    @Override
//    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappintFunction) {
//        return null;
//    }
//
//    @Override
//    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappintFunction) {
//        return null;
//    }
//
//    @Override
//    public V compute(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappintFunction) {
//        return null;
//    }
//
//static final class TreeNode<K,V> {
//        TreeNode<K,V> parent;
//        TreeNode<K,V>left;
//        TreeNode<K,V>right;
//        TreeNode<K,V>prev;
//        boolean red;
//        K key;
//        V value;
//        Node<K,V> next;
//
//        int hash;
//        TreeNode(int hash,K key,V val,Node<K,V> next){
//            this.hash = hash;
//            this.key = key;
//            this.value = val;
//            this.next = next;
//        }
//
//        final TreeNode<K,V> root(){
//            for(TreeNode<K,V> r = this,p;;){
//                if((p = r.parent) == null){
//                    return r;
//                }
//                r = p;
//            }
//        }
//
//    static <K,V> void moveRootToFront(Node<K,V>[] tab, TreeNode<K,V> root) {
//        int n;
//        if (root != null && tab != null && (n = tab.length) > 0) {
//            int index = (n - 1) & root.hash;
//            TreeNode<K,V> first = (TreeNode<K,V>)tab[index];
//            if (root != first) {
//                Node<K,V> rn;
//                tab[index] = root;
//                TreeNode<K,V> rp = root.prev;
//                if ((rn = root.next) != null)
//                    ((TreeNode<K,V>)rn).prev = rp;
//                if (rp != null)
//                    rp.next = rn;
//                if (first != null)
//                    first.prev = root;
//                root.next = first;
//                root.prev = null;
//            }
//            assert checkInvariants(root);
//        }
//    }
//
//    final TreeNode<K,V> find(int h, Object k, Class<?> kc) {
//        TreeNode<K,V> p = this;
//        do {
//            int ph, dir; K pk;
//            TreeNode<K,V> pl = p.left, pr = p.right, q;
//            if ((ph = p.hash) > h)
//                p = pl;
//            else if (ph < h)
//                p = pr;
//            else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                return p;
//            else if (pl == null)
//                p = pr;
//            else if (pr == null)
//                p = pl;
//            else if ((kc != null ||
//                    (kc = comparableClassFor(k)) != null) &&
//                    (dir = compareComparables(kc, k, pk)) != 0)
//                p = (dir < 0) ? pl : pr;
//            else if ((q = pr.find(h, k, kc)) != null)
//                return q;
//            else
//                p = pl;
//        } while (p != null);
//        return null;
//    }
//}
//    final TreeNode<K,V> getTreeNode(int h, Object k) {
//        return ((parent != null) ? root() : this).find(h, k, null);
//    }
//
//    /**
//     * Tie-breaking utility for ordering insertions when equal
//     * hashCodes and non-comparable. We don't require a total
//     * order, just a consistent insertion rule to maintain
//     * equivalence across rebalancings. Tie-breaking further than
//     * necessary simplifies testing a bit.
//     */
//    static int tieBreakOrder(Object a, Object b) {
//        int d;
//        if (a == null || b == null ||
//                (d = a.getClass().getName().
//                        compareTo(b.getClass().getName())) == 0)
//            d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
//                    -1 : 1);
//        return d;
//    }
//
//    /**
//     * Forms tree of the nodes linked from this node.
//     * @return root of tree
//     */
//    final void treeify(Node<K,V>[] tab) {
//        TreeNode<K,V> root = null;
//        for (TreeNode<K,V> x = this, next; x != null; x = next) {
//            next = (TreeNode<K,V>)x.next;
//            x.left = x.right = null;
//            if (root == null) {
//                x.parent = null;
//                x.red = false;
//                root = x;
//            }
//            else {
//                K k = x.key;
//                int h = x.hash;
//                Class<?> kc = null;
//                for (TreeNode<K,V> p = root;;) {
//                    int dir, ph;
//                    K pk = p.key;
//                    if ((ph = p.hash) > h)
//                        dir = -1;
//                    else if (ph < h)
//                        dir = 1;
//                    else if ((kc == null &&
//                            (kc = comparableClassFor(k)) == null) ||
//                            (dir = compareComparables(kc, k, pk)) == 0)
//                        dir = tieBreakOrder(k, pk);
//
//                    TreeNode<K,V> xp = p;
//                    if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                        x.parent = xp;
//                        if (dir <= 0)
//                            xp.left = x;
//                        else
//                            xp.right = x;
//                        root = balanceInsertion(root, x);
//                        break;
//                    }
//                }
//            }
//        }
//        moveRootToFront(tab, root);
//    }
//
//    /**
//     * Returns a list of non-TreeNodes replacing those linked from
//     * this node.
//     */
//    final Node<K,V> untreeify(HashMap<K,V> map) {
//        Node<K,V> hd = null, tl = null;
//        for (Node<K,V> q = this; q != null; q = q.next) {
//            Node<K,V> p = map.replacementNode(q, null);
//            if (tl == null)
//                hd = p;
//            else
//                tl.next = p;
//            tl = p;
//        }
//        return hd;
//    }
//
//    /**
//     * Tree version of putVal.
//     */
//    final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
//                                   int h, K k, V v) {
//        Class<?> kc = null;
//        boolean searched = false;
//        TreeNode<K,V> root = (parent != null) ? root() : this;
//        for (TreeNode<K,V> p = root;;) {
//            int dir, ph; K pk;
//            if ((ph = p.hash) > h)
//                dir = -1;
//            else if (ph < h)
//                dir = 1;
//            else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                return p;
//            else if ((kc == null &&
//                    (kc = comparableClassFor(k)) == null) ||
//                    (dir = compareComparables(kc, k, pk)) == 0) {
//                if (!searched) {
//                    TreeNode<K,V> q, ch;
//                    searched = true;
//                    if (((ch = p.left) != null &&
//                            (q = ch.find(h, k, kc)) != null) ||
//                            ((ch = p.right) != null &&
//                                    (q = ch.find(h, k, kc)) != null))
//                        return q;
//                }
//                dir = tieBreakOrder(k, pk);
//            }
//
//            TreeNode<K,V> xp = p;
//            if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                Node<K,V> xpn = xp.next;
//                TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
//                if (dir <= 0)
//                    xp.left = x;
//                else
//                    xp.right = x;
//                xp.next = x;
//                x.parent = x.prev = xp;
//                if (xpn != null)
//                    ((TreeNode<K,V>)xpn).prev = x;
//                moveRootToFront(tab, balanceInsertion(root, x));
//                return null;
//            }
//        }
//    }
//
//    /**
//     * Removes the given node, that must be present before this call.
//     * This is messier than typical red-black deletion code because we
//     * cannot swap the contents of an interior node with a leaf
//     * successor that is pinned by "next" pointers that are accessible
//     * independently during traversal. So instead we swap the tree
//     * linkages. If the current tree appears to have too few nodes,
//     * the bin is converted back to a plain bin. (The test triggers
//     * somewhere between 2 and 6 nodes, depending on tree structure).
//     */
//    final void removeTreeNode(HashMap<K,V> map, Node<K,V>[] tab,
//                              boolean movable) {
//        int n;
//        if (tab == null || (n = tab.length) == 0)
//            return;
//        int index = (n - 1) & hash;
//        TreeNode<K,V> first = (TreeNode<K,V>)tab[index], root = first, rl;
//        TreeNode<K,V> succ = (TreeNode<K,V>)next, pred = prev;
//        if (pred == null)
//            tab[index] = first = succ;
//        else
//            pred.next = succ;
//        if (succ != null)
//            succ.prev = pred;
//        if (first == null)
//            return;
//        if (root.parent != null)
//            root = root.root();
//        if (root == null || root.right == null ||
//                (rl = root.left) == null || rl.left == null) {
//            tab[index] = first.untreeify(map);  // too small
//            return;
//        }
//        TreeNode<K,V> p = this, pl = left, pr = right, replacement;
//        if (pl != null && pr != null) {
//            TreeNode<K,V> s = pr, sl;
//            while ((sl = s.left) != null) // find successor
//                s = sl;
//            boolean c = s.red; s.red = p.red; p.red = c; // swap colors
//            TreeNode<K,V> sr = s.right;
//            TreeNode<K,V> pp = p.parent;
//            if (s == pr) { // p was s's direct parent
//                p.parent = s;
//                s.right = p;
//            }
//            else {
//                TreeNode<K,V> sp = s.parent;
//                if ((p.parent = sp) != null) {
//                    if (s == sp.left)
//                        sp.left = p;
//                    else
//                        sp.right = p;
//                }
//                if ((s.right = pr) != null)
//                    pr.parent = s;
//            }
//            p.left = null;
//            if ((p.right = sr) != null)
//                sr.parent = p;
//            if ((s.left = pl) != null)
//                pl.parent = s;
//            if ((s.parent = pp) == null)
//                root = s;
//            else if (p == pp.left)
//                pp.left = s;
//            else
//                pp.right = s;
//            if (sr != null)
//                replacement = sr;
//            else
//                replacement = p;
//        }
//        else if (pl != null)
//            replacement = pl;
//        else if (pr != null)
//            replacement = pr;
//        else
//            replacement = p;
//        if (replacement != p) {
//            TreeNode<K,V> pp = replacement.parent = p.parent;
//            if (pp == null)
//                root = replacement;
//            else if (p == pp.left)
//                pp.left = replacement;
//            else
//                pp.right = replacement;
//            p.left = p.right = p.parent = null;
//        }
//
//        TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement);
//
//        if (replacement == p) {  // detach
//            TreeNode<K,V> pp = p.parent;
//            p.parent = null;
//            if (pp != null) {
//                if (p == pp.left)
//                    pp.left = null;
//                else if (p == pp.right)
//                    pp.right = null;
//            }
//        }
//        if (movable)
//            moveRootToFront(tab, r);
//    }
//
//    /**
//     * Splits nodes in a tree bin into lower and upper tree bins,
//     * or untreeifies if now too small. Called only from resize;
//     * see above discussion about split bits and indices.
//     *
//     * @param map the map
//     * @param tab the table for recording bin heads
//     * @param index the index of the table being split
//     * @param bit the bit of hash to split on
//     */
//    final void split(HashMap<K,V> map, Node<K,V>[] tab, int index, int bit) {
//        TreeNode<K,V> b = this;
//        // Relink into lo and hi lists, preserving order
//        TreeNode<K,V> loHead = null, loTail = null;
//        TreeNode<K,V> hiHead = null, hiTail = null;
//        int lc = 0, hc = 0;
//        for (TreeNode<K,V> e = b, next; e != null; e = next) {
//            next = (TreeNode<K,V>)e.next;
//            e.next = null;
//            if ((e.hash & bit) == 0) {
//                if ((e.prev = loTail) == null)
//                    loHead = e;
//                else
//                    loTail.next = e;
//                loTail = e;
//                ++lc;
//            }
//            else {
//                if ((e.prev = hiTail) == null)
//                    hiHead = e;
//                else
//                    hiTail.next = e;
//                hiTail = e;
//                ++hc;
//            }
//        }
//
//        if (loHead != null) {
//            if (lc <= UNTREEIFY_THRESHOLD)
//                tab[index] = loHead.untreeify(map);
//            else {
//                tab[index] = loHead;
//                if (hiHead != null) // (else is already treeified)
//                    loHead.treeify(tab);
//            }
//        }
//        if (hiHead != null) {
//            if (hc <= UNTREEIFY_THRESHOLD)
//                tab[index + bit] = hiHead.untreeify(map);
//            else {
//                tab[index + bit] = hiHead;
//                if (loHead != null)
//                    hiHead.treeify(tab);
//            }
//        }
//    }
//
//        /* ------------------------------------------------------------ */
//    // Red-black tree methods, all adapted from CLR
//
//    static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
//                                          TreeNode<K,V> p) {
//        TreeNode<K,V> r, pp, rl;
//        if (p != null && (r = p.right) != null) {
//            if ((rl = p.right = r.left) != null)
//                rl.parent = p;
//            if ((pp = r.parent = p.parent) == null)
//                (root = r).red = false;
//            else if (pp.left == p)
//                pp.left = r;
//            else
//                pp.right = r;
//            r.left = p;
//            p.parent = r;
//        }
//        return root;
//    }
//
//    static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
//                                           TreeNode<K,V> p) {
//        TreeNode<K,V> l, pp, lr;
//        if (p != null && (l = p.left) != null) {
//            if ((lr = p.left = l.right) != null)
//                lr.parent = p;
//            if ((pp = l.parent = p.parent) == null)
//                (root = l).red = false;
//            else if (pp.right == p)
//                pp.right = l;
//            else
//                pp.left = l;
//            l.right = p;
//            p.parent = l;
//        }
//        return root;
//    }
//
//    static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
//                                                TreeNode<K,V> x) {
//        x.red = true;
//        for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
//            if ((xp = x.parent) == null) {
//                x.red = false;
//                return x;
//            }
//            else if (!xp.red || (xpp = xp.parent) == null)
//                return root;
//            if (xp == (xppl = xpp.left)) {
//                if ((xppr = xpp.right) != null && xppr.red) {
//                    xppr.red = false;
//                    xp.red = false;
//                    xpp.red = true;
//                    x = xpp;
//                }
//                else {
//                    if (x == xp.right) {
//                        root = rotateLeft(root, x = xp);
//                        xpp = (xp = x.parent) == null ? null : xp.parent;
//                    }
//                    if (xp != null) {
//                        xp.red = false;
//                        if (xpp != null) {
//                            xpp.red = true;
//                            root = rotateRight(root, xpp);
//                        }
//                    }
//                }
//            }
//            else {
//                if (xppl != null && xppl.red) {
//                    xppl.red = false;
//                    xp.red = false;
//                    xpp.red = true;
//                    x = xpp;
//                }
//                else {
//                    if (x == xp.left) {
//                        root = rotateRight(root, x = xp);
//                        xpp = (xp = x.parent) == null ? null : xp.parent;
//                    }
//                    if (xp != null) {
//                        xp.red = false;
//                        if (xpp != null) {
//                            xpp.red = true;
//                            root = rotateLeft(root, xpp);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    static <K,V> TreeNode<K,V> balanceDeletion(TreeNode<K,V> root,
//                                               TreeNode<K,V> x) {
//        for (TreeNode<K,V> xp, xpl, xpr;;)  {
//            if (x == null || x == root)
//                return root;
//            else if ((xp = x.parent) == null) {
//                x.red = false;
//                return x;
//            }
//            else if (x.red) {
//                x.red = false;
//                return root;
//            }
//            else if ((xpl = xp.left) == x) {
//                if ((xpr = xp.right) != null && xpr.red) {
//                    xpr.red = false;
//                    xp.red = true;
//                    root = rotateLeft(root, xp);
//                    xpr = (xp = x.parent) == null ? null : xp.right;
//                }
//                if (xpr == null)
//                    x = xp;
//                else {
//                    TreeNode<K,V> sl = xpr.left, sr = xpr.right;
//                    if ((sr == null || !sr.red) &&
//                            (sl == null || !sl.red)) {
//                        xpr.red = true;
//                        x = xp;
//                    }
//                    else {
//                        if (sr == null || !sr.red) {
//                            if (sl != null)
//                                sl.red = false;
//                            xpr.red = true;
//                            root = rotateRight(root, xpr);
//                            xpr = (xp = x.parent) == null ?
//                                    null : xp.right;
//                        }
//                        if (xpr != null) {
//                            xpr.red = (xp == null) ? false : xp.red;
//                            if ((sr = xpr.right) != null)
//                                sr.red = false;
//                        }
//                        if (xp != null) {
//                            xp.red = false;
//                            root = rotateLeft(root, xp);
//                        }
//                        x = root;
//                    }
//                }
//            }
//            else { // symmetric
//                if (xpl != null && xpl.red) {
//                    xpl.red = false;
//                    xp.red = true;
//                    root = rotateRight(root, xp);
//                    xpl = (xp = x.parent) == null ? null : xp.left;
//                }
//                if (xpl == null)
//                    x = xp;
//                else {
//                    TreeNode<K,V> sl = xpl.left, sr = xpl.right;
//                    if ((sl == null || !sl.red) &&
//                            (sr == null || !sr.red)) {
//                        xpl.red = true;
//                        x = xp;
//                    }
//                    else {
//                        if (sl == null || !sl.red) {
//                            if (sr != null)
//                                sr.red = false;
//                            xpl.red = true;
//                            root = rotateLeft(root, xpl);
//                            xpl = (xp = x.parent) == null ?
//                                    null : xp.left;
//                        }
//                        if (xpl != null) {
//                            xpl.red = (xp == null) ? false : xp.red;
//                            if ((sl = xpl.left) != null)
//                                sl.red = false;
//                        }
//                        if (xp != null) {
//                            xp.red = false;
//                            root = rotateRight(root, xp);
//                        }
//                        x = root;
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Recursive invariant check
//     */
//    static <K,V> boolean checkInvariants(TreeNode<K,V> t) {
//        TreeNode<K,V> tp = t.parent, tl = t.left, tr = t.right,
//                tb = t.prev, tn = (TreeNode<K,V>)t.next;
//        if (tb != null && tb.next != t)
//            return false;
//        if (tn != null && tn.prev != t)
//            return false;
//        if (tp != null && t != tp.left && t != tp.right)
//            return false;
//        if (tl != null && (tl.parent != t || tl.hash > t.hash))
//            return false;
//        if (tr != null && (tr.parent != t || tr.hash < t.hash))
//            return false;
//        if (t.red && tl != null && tl.red && tr != null && tr.red)
//            return false;
//        if (tl != null && !checkInvariants(tl))
//            return false;
//        if (tr != null && !checkInvariants(tr))
//            return false;
//        return true;
//    }
//}
//
//}