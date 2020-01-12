package com.companioncar.dal.util;

import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Random;

public class MD5Util {

    public static String getSalt(){
        Random random = new Random();
        Long time = new Date().getTime();
        return String.valueOf(random.nextInt()*time);
    }

    public static String getMd5(String pwd, String salt) {
        String base = pwd + salt;
        //调用spring 的工具类进行加密
        String Md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return Md5;
    }

    public static void main(String[] args) {
        String salt = getSalt();
        System.out.println(salt);
        String md5 = MD5Util.getMd5("123456", salt);
        System.out.println(md5);
    }
}
