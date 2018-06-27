package com.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;

/**
 * Created by wgp on 2018/6/27.
 */
public class ZkSetData {
    public static void main(String[] args) throws Exception{
//        new ZkSetData().setData();
//        ZooKeeper zk = ZkCreate.zk;
//        byte[] b = zk.getData(ZkCreate.path, null,null);
//        System.out.println(new String(b,"UTF-8"));


        String path = "/MyFirstZnode";
        new ZkSetData().delete(path);
//
        ZooKeeper zk = new ZooKeeper("localhost", 5000, null);
        System.out.println(zk.exists(path,null));
    }

    public void getChildren(){
        String path = ZkCreate.path;

        try{
            Stat  stat = ZkCreate.zoneExists(path);
            if(stat != null){
                List<String> children  = ZkCreate.zk.getChildren(path,false);
                ZkCreate.zkGetData(path);
                for(int i=0;i<children.size();i++){
                    System.out.println(children.get(i));
                }
            }else{
                System.out.println("Node does not exists");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void delete(String path)throws KeeperException,InterruptedException{
        ZooKeeper zk = ZkCreate.zk;
        zk.delete(path,zk.exists(path,true).getVersion());
    }


    public void setData(){
        byte[] data = "换掉数据了".getBytes();
        try{
            String path = ZkCreate.path;
            ZooKeeper zk = ZkCreate.zk;
            zk.setData(path,data,zk.exists(path,true).getVersion());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
