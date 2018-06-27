package com.basicdatabook.tree;

import com.basicdatabook.Node;

/**
 * @author WGP
 * @description 二叉树存储
 * @date 2018/6/2
 **/
public class BinTreeNode implements Node {
//    数据域
    private Object data;
//    父结点
    private BinTreeNode parent;
    //左孩子
    private BinTreeNode lChild;
    //右孩子
    private BinTreeNode rChild;
    //以该结点为根的子树的高度
    private int height;
    //该结点子孙数
    private int size;


    public BinTreeNode(){this(null);}
    public BinTreeNode(Object e){
        data = e;
        height = 0;
        size = 1;
        parent = lChild= rChild = null;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object obj) {
        data = obj;
    }

    public boolean hasParent(){
        return parent!=null;
    }

    public boolean hasLChild(){
        return lChild != null;
    }

    public boolean hasRchild(){
        return rChild != null;
    }

    public boolean isLeaf(){
        return !hasLChild() && !hasRchild();
    }

    public boolean isLChild(){
        return (hasParent() && this == parent.lChild);
    }

    public boolean isRChild(){
        return (hasParent() && this == parent.rChild);
    }

    public int getHeight(){
        return height;
    }
    public void updateHeight(){
        int newHeight = 0;
        if(hasLChild()){
            newHeight = Math.max(newHeight,1 + getLChild().getHeight() );
        }
        if(hasRchild()){
            newHeight = Math.max(newHeight,1+getRChild().getHeight());
        }
        if(newHeight != height){
            height = newHeight;
        }
        if(hasParent()){
            getParent().updateHeight();
        }
    }

   public int getSize(){
        return size;
   }
   public void updateSize(){
       size = 1;
        if(hasLChild()) {
            size += getLChild().getSize();
        }
        if(hasRchild()){
            size += getRChild().getSize();
        }
        if(hasParent()){
            getParent().updateSize();
        }
   }

   public BinTreeNode getParent(){return parent;}

   //断开该结点与父节点的关系
   public void sever(){
       if(!hasParent()){
           return;
       }
       if(isLChild()){
           parent.lChild = null;
       }else{
           parent.rChild = null;
       }
       parent.updateHeight();
       parent.updateSize();
       parent = null;
   }

   public BinTreeNode getLChild(){
       return lChild;
   }

   //设置当前结点的左孩子，返回原左孩子
    /*
    先过去旧的左孩子，如果存在左孩子与结点的关系，则断开与左孩子的关系。
    把传入的结点断开关系。然后相互确定父，左子关系。
    最后更新高度和节点个数
     */
   public BinTreeNode setLChild(BinTreeNode lc){
       BinTreeNode oldLC = this.lChild;
       if(hasLChild()){
           lChild.sever();
       }
       if(lc != null){
           lc.sever();
           this.lChild = lc;
           lc.parent = this;
           this.updateSize();
           this.updateHeight();
       }
       return oldLC;
   }

   public BinTreeNode getRChild(){
       return this.rChild;
   }
   //设置当前的右孩子，返回原先的右孩子
    /*
    获取原先的右孩子
    然后断开当前结点和右孩子之间的关系

     */
   public BinTreeNode setRChild(BinTreeNode rc){
       BinTreeNode oldRC = this.rChild;
       if(hasRchild()){
           rChild.sever();
       }
       if(rc!=null){
           rc.sever();
           this.rChild = rc;
           rc.parent = this;
           this.updateHeight();
           this.updateSize();
       }
       return  oldRC;
   }
}
