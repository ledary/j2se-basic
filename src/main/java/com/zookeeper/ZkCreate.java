package com.zookeeper;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wgp on 2018/6/27.
 */
public class ZkCreate {
    public static ZooKeeper zk;

    public final static String path= "/MySecondZnode";
    static{
        try{
            zk = new ZkConnection().connect("localhost");
            zk.create(path,"my first zookeeper".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }



    public static   Stat zoneExists(String path)throws KeeperException,InterruptedException{
        return zk.exists(path,true);
    }
    public static   void zkGetData(String path){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try{
            Stat stat = zoneExists(path);
            if(stat != null){
                byte[] b = zk.getData(path, null,null);
                String data = new String(b,"UTF-8");
                System.out.println(data);
                countDownLatch.await();
            }else{
                System.out.println("Node does not exists");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
