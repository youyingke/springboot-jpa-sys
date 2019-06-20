package com.hawk.demo.util;

/**
 * Created by Lenovo on 2019-04-02.
 */
public class StrReverse {



    public String strReverse(String str)
    {
        StringBuffer stringBuffer=new StringBuffer();
        String result=stringBuffer.append(str).reverse().toString();
        return result;
    }
}
