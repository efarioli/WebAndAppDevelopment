package com.example.webandappdevelopment.Utils;

import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Myhelper {
    public static String CalendarToStringF(Calendar c){
        Date date = c.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
        return format1.format(date);
    }
    public static String getEncoded (String queryString){

        byte[] encodeValue = Base64.encode(queryString.getBytes(), Base64.DEFAULT);
        String strEncode = new String(encodeValue);
        strEncode = "Basic " + strEncode.trim();
        return  strEncode;
    }
}
