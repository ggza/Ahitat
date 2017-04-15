package gz.rmbgysz.ahitat;

/**
 * Created by gzoli on 2017.02.07..
 */

import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class DateManager {

    private String timezoneString = "Europe/Budapest";
    private String minDateString = "2017-01-01";
    private String maxDateString = "2017-12-31";
    private Context context;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    private static DateManager instance = null;

    private DateManager(Context context) {
        this.context = context;
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance(TimeZone.getTimeZone(timezoneString));
    }

    public static DateManager getInstance(Context context) {
        if (instance == null)
            instance = new DateManager(context);
        return instance;
    }

    public long getMinDate() throws ParseException {
        return simpleDateFormat.parse(minDateString).getTime();
    }

    public long getMaxDate() throws ParseException {
        return simpleDateFormat.parse(maxDateString).getTime();
    }

    public void setActualDate() {
        calendar = Calendar.getInstance(TimeZone.getTimeZone(timezoneString));
    }

    public void setDate(String dateString) throws ParseException {
        String[] result = dateString.split("-");
        int year = Integer.valueOf(result[0]);
        int month = Integer.valueOf(result[1]) - 1;
        int day = Integer.valueOf(result[2]);

        //FIXME: ezt lehetne szebben is majd írok rá egy osztályt
        if ((year == 2017) && (month >= 0) && (day > 1 ) && (day < 32 ))
            this.setDate(year, month, day);
    }


    public void setDate(int year, int month, int day) throws ParseException {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        //setting default
        calendar.set(Calendar.HOUR,11);
        calendar.set(Calendar.MINUTE,11);
    }

    public String getDateString() {

        String formattedMonth = String.format("%02d",calendar.get(Calendar.MONTH) +1);
        String formattedDay = String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));

        return String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                formattedMonth + "-" + formattedDay;
    }


    public void stepToNextDay() {
        calendar.add(Calendar.DAY_OF_YEAR,1);
    }

    public void stepToPreviousDay() {
        calendar.add(Calendar.DAY_OF_YEAR,-1);
    }

    public String getFormattedDate() {
        return context.getResources()
                .getStringArray(R.array.hungarian_month_names)
                [calendar.get(calendar.MONTH)] + " " +
                String.valueOf(calendar.get(calendar.DAY_OF_MONTH)) + ".";
    }

    public String getFormattedDateWithDayName() {
        return String.valueOf(calendar.get(calendar.YEAR)) + ". " +
                context.getResources()
                        .getStringArray(R.array.hungarian_month_names)
                        [calendar.get(calendar.MONTH)] + " " +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "., " +
                context.getResources()
                .getStringArray(R.array.hungarian_day_names)
                [calendar.get(calendar.DAY_OF_WEEK) - 1];

    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}