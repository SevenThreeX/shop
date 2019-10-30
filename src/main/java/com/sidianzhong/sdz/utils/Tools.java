package com.sidianzhong.sdz.utils;

import java.util.UUID;

public class Tools {

    public  static  String getUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("_", "");
        return  uuid;
    };
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
}
