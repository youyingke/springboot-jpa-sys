package com.hawk.demo.util;

import java.util.Calendar;

/**
 * Created by Lenovo on 2019-04-20.
 */
public class GetAgeByBirthday {

    public static int getAgeBybirthday(String birthday)
    {
        int age=0;
        String day_arr[]=birthday.split("-");
        Calendar cal=Calendar.getInstance();
        int yearNow=cal.get(Calendar.YEAR);
        age=yearNow-Integer.parseInt(day_arr[0]);
        return age;
    }

public static void main(String args[])
{
  System.out.println(  GetAgeByBirthday.getAgeBybirthday("2017-2-9"));
}
}

