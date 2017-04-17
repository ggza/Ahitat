package gz.rmbgysz.ahitat;

/**
 * Created by gz on 2017.02.11..
 */

public class DailyDevotion {
    private int id;
    private String dailyDevotionDate;
    private String amTitle;
    private String amVerse;
    private String amDailyDevotion;
    private String amDailyDevotionAuthor;
    private String pmTitle;
    private String pmVerse;
    private String pmDailyDevotion;
    private String pmDailyDevotionAuthor;
    private String bibleLecture;
    private String prayer;

    private String changeLineBreak(String in) {
      return in.replaceAll("<br>","\n\n");
    }

    public DailyDevotion(int id,
                         String dailyDevotionDate,
                         String amTitle,
                         String amVerse,
                         String amDailyDevotion,
                         String amDailyDevotionAuthor,
                         String pmTitle,
                         String pmVerse,
                         String pmDailyDevotion,
                         String pmDailyDevotionAuthor,
                         String bibleLecture,
                         String prayer) {
        this.id = id;
        this.dailyDevotionDate = dailyDevotionDate;
        this.amTitle = amTitle;
        this.amVerse = amVerse;
        this.amDailyDevotion = amDailyDevotion;
        this.amDailyDevotionAuthor = amDailyDevotionAuthor;
        this.pmTitle = pmTitle;
        this.pmVerse = pmVerse;
        this.pmDailyDevotion = pmDailyDevotion;
        this.pmDailyDevotionAuthor = pmDailyDevotionAuthor;
        this.bibleLecture = bibleLecture;
        this.prayer = prayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmTitle() {
        return changeLineBreak(amTitle);
    }

    public String getAmVerse() {
        return changeLineBreak(amVerse);
    }

    public String getAmDailyDevotion() {
        return changeLineBreak(amDailyDevotion);
    }

    public String getAmDailyDevotionAuthor() {
        return changeLineBreak(amDailyDevotionAuthor);
    }

    public String getPmTitle() {
        return changeLineBreak(pmTitle);
    }

    public String getPmVerse() {
        return changeLineBreak(pmVerse);
    }

    public String getPmDailyDevotion() {
        return changeLineBreak(pmDailyDevotion);
    }

    public String getPmDailyDevotionAuthor() {
        return changeLineBreak(pmDailyDevotionAuthor);
    }

    public String getBibleLecture() {
        return changeLineBreak(bibleLecture);
    }

    public String getPrayer() {
        return changeLineBreak(prayer);
    }

}
