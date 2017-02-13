package gz.rmbgysz.ahitat;

/**
 * Created by gzoli on 2017.02.07..
 */

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/*TODO:
-kell egy min, maxDate, ezzel vedekezunk majd
*/

public class DateManager {

    private String timezoneString = "Europe/Budapest";
    private String minDateString = "2017-01-01";
    private String maxDateString = "2017-12-31";
    private Context context;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DateManager(Context context) {
        this.context = context;
        calendar = Calendar.getInstance(TimeZone.getTimeZone(timezoneString));

    }

    public long getMinDate() throws ParseException {
        return simpleDateFormat.parse(minDateString).getTime();
    }

    public long getMaxDate() throws ParseException {
        return simpleDateFormat.parse(maxDateString).getTime();
    }

    public DateManager(Context context, Date date) {
        calendar.setTime(date);
        this.context = context;
    }

    public void setDate(Date date) {
        calendar.setTime(date);
    }

    public void setDate(int year, int month, int day) throws ParseException {
        String dateString = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
        Date date = simpleDateFormat.parse(dateString);
        calendar.setTime(date);
    }

    public Date getDate() {
        return calendar.getTime();
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
        return getFormattedDate() + " " + context.getResources()
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