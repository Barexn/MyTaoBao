package com.bawie.yangqingqing.mytaobao.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class UnicodeStreamUtils {
    private static SharedPreferences sharedPreferences = null;
    public static String getStr(String dataStr){
        int index=0;
        StringBuffer buffer=new StringBuffer();
        int len=dataStr.length();
        while (index<len){
            if (index>=len-1||!"\\u".equals(dataStr.substring(index,index+2))){
                buffer.append(dataStr.charAt(index));
                index++;
                continue;
            }
            String charStr="";
            charStr=dataStr.substring(index+2,index+6);
            char letter= (char) Integer.parseInt(charStr,16);
            buffer.append(letter);
            index+=6;
        }
        return buffer.toString();
    }
    //SharedPreferences单例模式
    public static SharedPreferences getSharedPreferencesInstance(Context context){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}
