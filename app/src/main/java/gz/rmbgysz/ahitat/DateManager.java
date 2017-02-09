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
-localtime-ot is figyelembe kell majd venni
*/

public class DateManager {

    private Date mDate;
    private Context context;
    private Calendar calendar;

    public DateManager(Context context) {
        this.context = context;
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Budapest"));
        mDate = calendar.getTime();
    }

    public DateManager(Context context, Date date) {
        mDate = date;
        this.context = context;
    }

    public void setDate(Date date) {
        mDate = date;
        calendar.setTime(mDate);
    }

    public void setDate(int year, int month, int day) throws ParseException {
        String dateString = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateString);
        calendar.setTime(date);
        mDate = calendar.getTime();
    }


    public Date getDate() {
        return mDate;
    }

    public void stepToNextDay() {
        calendar.add(Calendar.DAY_OF_YEAR,1);
        mDate = calendar.getTime();
    }

    public void stepToPreviousDay() {
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        mDate = calendar.getTime();
    }

    public String getFormattedDate() {
        return context.getResources().getStringArray(R.array.hungarian_month_names)
                [calendar.get(calendar.MONTH)] + " " +
                String.valueOf(calendar.get(calendar.DAY_OF_MONTH)) + ".";

    }

    public int getMonth() {
        return calendar.MONTH;
    }

    public int getDay() {
        return calendar.DAY_OF_MONTH;
    }

}