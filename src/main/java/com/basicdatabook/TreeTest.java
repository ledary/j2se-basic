package com.basicdatabook;

import com.basicdatabook.stack.Stack;
import com.basicdatabook.stack.StackSLinked;
import com.basicdatabook.tree.BinTreeNode;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/3
 **/
public class TreeTest {
    private BinTreeNode root;

    {
        root = new BinTreeNode();
        root.setData("A");

        BinTreeNode binC = new BinTreeNode();
        binC.setData("C");
        BinTreeNode binD = new BinTreeNode();
        binD.setData("D");
        BinTreeNode binB = new BinTreeNode();
        binB.setData("B");
        binB.setLChild(binC);
        binB.setRChild(binD);

        BinTreeNode binE = new BinTreeNode();
        binE.setData("E");
        root.setRChild(binE);
        root.setLChild(binB);
    }

    public Iterator preOrder() {
        LinkedList list = new LinkedListDLNode();
        preOrderTraverse(root, list);
        return list.elements();
    }

    //先序遍历
    private void preOrderTraverse(BinTreeNode rt, LinkedList list) {
        if (rt == null) {
            return;
        }
        BinTreeNode p = rt;
        Stack s = new StackSLinked();
        while (p != null) {
            while (p != null) {
                list.insertLast(p);
                if (p.hasRchild()) {
                    s.push(p.getRChild());
                }
                p = p.getLChild();
            }
            if (!s.isEmpty()) {
                p = (BinTreeNode) s.pop();
            }

        }
    }

    public Iterator endOrder() {
        LinkedList list = new LinkedListDLNode();
        backOrderTravers(root, list);
        return list.elements();
    }

    private void backOrderTravers(BinTreeNode root, LinkedList list) {
        if (root == null) {
            return;
        }
        BinTreeNode p = root;
        Stack s = new StackSLinked();
        while (p != null) {
            while(p!=null){
                s.push(p);
                if(p.hasLChild()){
                    p = p.getLChild();
                }else{
                    p = p.getRChild();
                }
            }
            list.insertLast(s.pop());

        }


    }

    public Iterator inOrder() {
        LinkedList list = new LinkedListDLNode();
        inOrderTravers(root, list);
        return list.elements();
    }

    private void inOrderTravers(BinTreeNode root, LinkedList list) {
        if (root == null) {
            return;
        }
        BinTreeNode p = root;
        Stack s = new StackSLinked();
        while (p != null || !s.isEmpty()) {
            while (p != null) {
                s.push(p);
                p = p.getLChild();
            }
            while (!s.isEmpty()) {
                p = (BinTreeNode) s.pop();
                list.insertLast(p);
                p = p.getRChild();
            }
        }
    }


    public static void main(String[] args) {
        TreeTest tt = new TreeTest();
        Iterator it = tt.preOrder();
        while (it.hasNext()) {
//            System.out.println(it.currentItem()==null?"为空":it.currentItem());
            BinTreeNode node = (BinTreeNode) it.currentItem();
            System.out.println(node == null ? "为空" : node.getData());
            it.next();

        }
    }
}
