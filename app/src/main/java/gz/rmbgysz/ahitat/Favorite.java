package gz.rmbgysz.ahitat;

/**
 * Created by gzoli on 2017.02.14..
 */

public class Favorite {
    String datum;
    String de_cim;
    String du_cim;
    boolean selected = false;

    public Favorite(String datum, String de_cim, String du_cim) {
        this.datum = datum;
        this.de_cim = de_cim;
        this.du_cim = du_cim;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getDe_cim() {
        return de_cim;
    }

    public void setDe_cim(String de_cim) {
        this.de_cim = de_cim;
    }

    public String getDu_cim() {
        return du_cim;
    }

    public void setDu_cim(String du_cim) {
        this.du_cim = du_cim;
    }

    public boolean isSelected() {return selected;}

    public void setSelected(boolean selected) {this.selected = selected;}

    @Override
    public String toString() {
        return "Favorite{" +
                "datum='" + datum + '\'' +
                ", de_cim='" + de_cim + '\'' +
                ", du_cim='" + du_cim + '\'' +
                '}';
    }
}
