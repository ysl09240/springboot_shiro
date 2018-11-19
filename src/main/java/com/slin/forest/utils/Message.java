package com.slin.forest.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类
 *
 * @author yangsonglin
 * @create 2018-07-17 9:01
 **/
public class Message {


    public static String waveData(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datatype", 0);
        map.put("imei", "123456789131313");
        map.put("sleepy",0);
        map.put("lrhand",0);
        map.put("filesize","1078650");
        map.put("datanum","90000");
        map.put("starttime",System.currentTimeMillis()/1000);
        map.put("uploadtime",System.currentTimeMillis()/1000);
        map.put("istestbpbs",0);
        map.put("sportstatus",0);
        String str = FileUtils.readFile("E://tempDownload//41c7cfd93e6a467db6986440d0a1915a_1542080559950.txt");
        map.put("data",str);
        String mapJson = JSON.toJSONString(map);
        String returnStr = ZipUtils.gzip(mapJson);
        return returnStr;
    }

    public static String bloodBaseIsValid(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datatype", 8);
        map.put("imei", "861319031125629");

        String mapJson = JSON.toJSONString(map);



        return mapJson;
    }

    public static String sedentarySave(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datatype", 1);
        map.put("imei", "1314");

        map.put("sittime",System.currentTimeMillis());
        map.put("sitduration",60);
        String mapJson = JSON.toJSONString(map);

        return mapJson;
    }

    public static String stepSave(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datatype", 5);
        map.put("imei", "1314");
        map.put("stepcount",1234);
        map.put("uploadtime",System.currentTimeMillis());
        String mapJson = JSON.toJSONString(map);

        return mapJson;
    }

    public static String heartRateSave(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datatype", 6);
        map.put("imei", "1314");

        map.put("heart",75);
        map.put("uploadtime",System.currentTimeMillis());
        String mapJson = JSON.toJSONString(map);

        return mapJson;
    }


    public static String heartbeat(){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datatype", 4);
        map.put("imei", "1314");
        String mapJson = JSON.toJSONString(map);

        return mapJson;
    }


}
