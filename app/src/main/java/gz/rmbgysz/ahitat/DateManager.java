package gz.rmbgysz.ahitat;

/**
 * Created by gzoli on 2017.02.07..
 */

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/*TODO:
-kell egy min, maxDate, ezzel vedekezunk majd
-localtime-ot is figyelembe kell majd venni
*/

public class DateManager {

    private Date mDate;
    private Context context;
    private Calendar calendar = Calendar.getInstance();

    public DateManager(Context context) {
        mDate = new Date();
        this.context = context;
    }

    public DateManager(Context context, Date date) {
        mDate = date;
        this.context = context;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public void stepToNextDay() {
        calendar.setTime(mDate);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        mDate = calendar.getTime();
    }

    public void stepToPreviousDay() {
        calendar.setTime(mDate);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        mDate = calendar.getTime();
    }

    public String getFormattedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        return context.getResources().getStringArray(R.array.hungarian_month_names)
                [calendar.get(calendar.MONTH)] + " " +
                String.valueOf(calendar.get(calendar.DAY_OF_MONTH)) + ".";

    }

    public int getMonth() {
        calendar.setTime(mDate);
        return calendar.MONTH;
    }

    public int getDay() {
        calendar.setTime(mDate);
        return calendar.DAY_OF_MONTH;
    }
}