package net.megx;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatTest {

    public static void main(final String[] args) throws ParseException {
        System.out.println(System.getProperty("java.version"));
        final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSX");
        final String string = "2014-03-07 02:03:47.287822+00";
        final Date date = format.parse(string);
        //final java.sql.Date sqlDate = format.parse(string);
        
        System.out.println(date);
        
    }
} 