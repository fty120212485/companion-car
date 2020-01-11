package com.companioncar.dal.util;

public class UUIDUtil {
    public static String getUUID(){
        return java.util.UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtil.getUUID());
    }
}
