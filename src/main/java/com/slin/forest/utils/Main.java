package com.slin.forest.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Main {


//    public static final String IP_ADDR = "127.0.0.1";//服务器地址
//    public static final String IP_ADDR = "192.168.1.136";//服务器地址
//    public static final String IP_ADDR = "113.200.89.198";//服务器地址
    public static final String IP_ADDR = "seerwatch.seer-health.com";//服务器地址
//    public static final String IP_ADDR = "192.168.184.132";//服务器地址
//    public static final int PORT = 3389;//服务器端口号
    public static final int PORT = 9997;//服务器端口号

    public static final String FUHAd="$_";
    public static void main(String[] args) {
        start2();

//        for (int i = 0; i < 10 ; i++) {
//            System.out.println(IdUtils.uuid());
//        }

    }


    public static void start1(){
        System.out.println("客户端启动...");
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
        Socket socket = null;
        BufferedReader input =null;
        DataOutputStream out = null;
        try {
            //创建一个流套接字并将其连接到指定主机上的指定端口号
            socket = new Socket(IP_ADDR, PORT);
            //读取服务器端数据
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            //向服务器端发送数据
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {

                //                Map<String, Object> map = new HashMap<String, Object>();
                ////                map.put("name", "admin");
                ////                map.put("age", "23");
                ////                String mapJson = JSON.toJSONString(map);
                ////
                ////                out.writeBytes(mapJson);

                StringBuilder result = new StringBuilder("");
                do {
                    result.append(input.readLine());
                }while (input.ready());

                Scanner scanner = new Scanner(System.in);
                String p = scanner.nextLine();
//                        String p = "{"name":"slin","channelId":"12345678","age":"35","email":"345978511@qq.com","phone":"15193118591"}"$_;
                if (p.equals("bye")) {
                    socket.close();
                    break;
                }
                out.writeBytes(p);
                out.flush();


                String ret = result.toString();
                System.out.println("服务器端返回过来的是: " + ret);
                // 如接收到 "OK" 则断开连接
                if ("OK".equals(ret)) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(100);
                }

            }

            out.close();
            input.close();
        } catch (Exception e) {
            System.out.println("客户端异常:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }
    }


    public static void start2(){
        System.out.println("客户端启动...");
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
        Socket socket = null;
        BufferedReader input =null;
        DataOutputStream out = null;
        String str = "";
        try {
            //创建一个流套接字并将其连接到指定主机上的指定端口号
            socket = new Socket(IP_ADDR, PORT);
            //读取服务器端数据
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            //向服务器端发送数据
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                str = "";
                StringBuilder result = new StringBuilder("");
                while (true){
                    if(input.ready()){
                        result.append(input.readLine());
                        continue;
                    }
                    break;
                }
                String ret = result.toString();
                System.out.println("服务器端返回过来的是: " + ret);
                //波型数据
                str = Message.waveData()+FUHAd;
                //血糖是否有效
//                str = Message.bloodBaseIsValid()+FUHAd;
                //久坐提醒
//                str = Message.sedentarySave()+FUHAd;
                //心率上传
//                str = Message.heartRateSave()+FUHAd;
                //计步
//                str = Message.stepSave()+FUHAd;
                //心跳保持
//                str = Message.heartbeat()+FUHAd;
                System.out.println("-----------"+str);
                out.writeBytes(str);
                out.flush();

                Thread.sleep(100000);

                // 如接收到 "OK" 则断开连接

                if ("OK".equals(ret)) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(100);
                }

            }
//            out.close();
//            input.close();
        } catch (Exception e) {
            System.out.println("客户端异常:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }
    }


}
