package gz.rmbgysz.ahitat;

/**
 * Created by gz on 2017.02.14..
 */

public class Favorite {
    private String date;
    private String amTitle;
    private String pmTitle;

    public Favorite(String date, String amTitle, String pmTitle) {
        this.date = date;
        this.amTitle = amTitle;
        this.pmTitle = pmTitle;
    }

    public String getDate() {
        return date;
    }

    public String getFormattedDatum() {
        return date.replace('-','.') + '.';
    }


    public String getAmTitle() {
        return amTitle;
    }

    public String getPmTitle() {
        return pmTitle;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "date='" + date + '\'' +
                ", am_title='" + amTitle + '\'' +
                ", pm_title='" + pmTitle + '\'' +
                '}';
    }
}
