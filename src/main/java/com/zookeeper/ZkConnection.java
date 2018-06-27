package com.zookeeper;




import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


/**
 * 连接zookeeper类
 * Created by wgp on 2018/6/27.
 */
public class ZkConnection {

    //声明 zk实例，连接到Zookeeper集群组
    private ZooKeeper zoo;
    final CountDownLatch countDownLatch =  new CountDownLatch(1);
    public ZooKeeper connect(String host ) throws IOException,InterruptedException{
        zoo = new ZooKeeper(host, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return  zoo;
    }


    //断开连接 方法
    public void close()throws InterruptedException{
        zoo.close();
    }
}
