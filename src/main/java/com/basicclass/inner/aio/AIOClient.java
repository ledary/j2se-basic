package com.basicclass.inner.aio;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/6/3
 **/
public class AIOClient {

    final static String UTF_8 = "utf_8";
    final static int PORT =30000;

    JFrame mainWin = new JFrame("多人聊天");
    JTextArea jta = new JTextArea(16,48);
    JTextField jtf = new JTextField(40);
    JButton sendBn = new JButton("发送");
    AsynchronousSocketChannel clientChannel;
    public void init(){
        mainWin.setLayout(new BorderLayout());
        jta.setEditable(false);
        mainWin.add(new JScrollPane(jta),BorderLayout.CENTER);
        JPanel jp = new JPanel();
        jp.add(sendBn);
        Action sendAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = jtf.getText();
                if(content.trim().length() >0){
                    try{

                        clientChannel.write(ByteBuffer
                                .wrap(content.trim().getBytes(UTF_8))).get();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                jtf.setText("");
            }
        };
        sendBn.addActionListener(sendAction);
        jtf.getInputMap().put(KeyStroke.getKeyStroke('\n', InputEvent.CTRL_DOWN_MASK),"send");
    }

    public static void main(String[] args)throws Exception {
//        InputStreamReader a = new InputStreamReader();
//        BufferedReader
    }
}
