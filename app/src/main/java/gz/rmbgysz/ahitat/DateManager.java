package gz.rmbgysz.ahitat;

/*
  Created by gz on 2017.02.07..
 */

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class DateManager {

    private static int publicationYear = 2017;
    private static int minMonth = 0;
    private static int minDay = 1;
    private static int maxDay = 31;
    private static int defaultHour = 11;
    private static int getDefaultMin = 11;

    private String timezoneString = "Europe/Budapest";
    //TODO: ezekkel lehet megadni, hogy a datepicker milyen tartományt jelenítsen meg most 2018 végére van állítva a vége,
    //TODO: ha kiadjuk a 2019-est akkor ezt át kell írni, esetleg valami konfigba ki lehet tenni
    private String minDateString = "2017-01-01";
    private String maxDateString = "2021-12-31";
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    private static DateManager instance = null;

    private DateManager() {
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance(TimeZone.getTimeZone(timezoneString));
    }

    private boolean isValidDate(int year, int month, int day) {
        return (year >= publicationYear) && (month >= minMonth) && (day >= minDay) && (day <= maxDay);
    }

    public static DateManager getInstance() {
        if (instance == null)
            instance = new DateManager();
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

        if (isValidDate(year, month, day))
            this.setDate(year, month, day);
    }

    public void setDate(int year, int month, int day) throws ParseException {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        //setting default time
        calendar.set(Calendar.HOUR,defaultHour);
        calendar.set(Calendar.MINUTE,getDefaultMin);
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

    public String getFormattedDateWithDayName(Context context) {
        return String.valueOf(calendar.get(Calendar.YEAR)) + ". " +
                context.getResources()
                        .getStringArray(R.array.hungarian_month_names)
                        [calendar.get(Calendar.MONTH)] + " " +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "., " +
                context.getResources()
                .getStringArray(R.array.hungarian_day_names)
                [calendar.get(Calendar.DAY_OF_WEEK) - 1];

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