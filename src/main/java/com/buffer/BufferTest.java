package com.buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/3
 **/
public class BufferTest {
    public static void main(String[] args) throws Exception{
        FileInputStream in = new FileInputStream("D:" + File.separator + "test.txt");
        FileOutputStream out  = new FileOutputStream("D:" + File.separator + "test2.txt");
        FileChannel channel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        ByteBuffer buff  = ByteBuffer.allocate(1024);
        while(true){
            buff.clear();
            int len = channel.read(buff);
            if(len == -1){
                break;
            }
            buff.flip();
            outChannel.write(buff);
        }

        channel.close();
        outChannel.close();
        in.close();
        out.close();

    }
}
