package ua.nure.petrov.Course.db.entity.constants;

import javax.servlet.http.Part;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Владислав on 30.07.2015.
 */
public class Util {

    public static Timestamp getDateTime() {
        java.util.Date utilDate = new java.util.Date();
        return new java.sql.Timestamp(utilDate.getTime());
    }

    public static Date getDate() {
        return new Date(new java.util.Date().getTime());
    }

    public static Date getYesterdaysDate() {
        Calendar cal = Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        cal.setTime(utilDate);
        cal.add(Calendar.DATE, -1);
        utilDate = cal.getTime();
        return new Date(utilDate.getTime());
    }

    public static String changeFormatDate(String date) {
        final String OLD_FORMAT = "dd.MM.yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";

        java.util.Date day = null;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        try {
            day = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf.applyPattern(NEW_FORMAT);

        return sdf.format(day);
    }

    public static boolean isDateValid(String dateToValidate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (sdf.parse(dateToValidate) != null) {
                return true;
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String createNewPath() {
        Random rand = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            boolean flag = rand.nextBoolean();
            Character c;
            if (flag) {
                c = (char) ('a' + rand.nextInt(26));
                c = Character.toUpperCase(c);
            } else {
                c = (char) ('a' + rand.nextInt(26));
            }
            str.append(c);
        }
        return str.toString() + ".mypic";
    }

    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private Util() {
    }
}
