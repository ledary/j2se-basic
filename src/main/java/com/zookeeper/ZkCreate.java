package com.zookeeper;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wgp on 2018/6/27.
 */
public class ZkCreate {
    private static ZooKeeper zk;
    private static ZkConnection conn;

    public static  void create(String path ,byte[] data)throws KeeperException,
            InterruptedException{
        zk.create(path,data, ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    public static Stat zoneExists(String path)throws KeeperException,InterruptedException{
        return zk.exists(path,true);
    }

    public static void zkGetData(String path){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try{
            conn  = new ZkConnection();
            zk = conn.connect("localhost");
            Stat stat = zoneExists(path);
            if(stat != null){
                byte[] b = zk.getData(path, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        if(watchedEvent.getType() == Event.EventType.None){
                            switch (watchedEvent.getState()){
                                case Expired:
                                    countDownLatch.countDown();
                                    break;
                            }

                        }else{
                            String path = "";
                            try{
                                byte[] bn = zk.getData(path,false,null);
                                String data = new String(bn,"UTF-8");
                                System.out.println(data);
                                countDownLatch.countDown();
                            }catch (Exception ex){
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                },null);
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

    public static void main(String[] args) {
        String path = "/MyFirstZnode";
        zkGetData(path);
//        String path = "/MyFirstZnode";
//
////        byte[] data = "my first zookeeper app".getBytes();
//        try{
//            conn = new ZkConnection();
//            zk = conn.connect("localhost");
//            System.out.println(zk.exists(path,true));
////            create(path,data);
//            conn.close();
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
    }
}
