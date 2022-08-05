package com.nebulacompanies.ibo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetTime {
    static SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

    static SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);



    public static String SetDateFormat(String inputDate) {
        Date inDate;
        String outDate = "";

        try {
            inDate = inputFormat.parse(inputDate);
            outDate = outputFormat.format(inDate);
            // System.out.println(outDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return outDate;
    }


    public static Integer SetDateFormats(String inputDate) {
        Date inDate;
        int minutes = 0;
        try {
            inDate = inputFormat.parse(inputDate);
            //outDate = outputFormat.format(inDate);
            String [] hou=inputDate.split(" ");
            String [] time =(hou[1].split(":"));
            //when app is live then use seconf line commited and one set
            //hours
            minutes=Integer.parseInt(time[0]);
            // mins
            //minutes=Integer.parseInt(time[1]);
            System.out.println(minutes);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return minutes;
    }
}
