package gz.rmbgysz.ahitat;

/**
 * Created by gzoli on 2017.02.11..
 */

public class Ahitat {
    int id;
    String datum;
    String de_cim;
    String de_ige;
    String de_szoveg;
    String de_szerzo;
    String du_cim;
    String du_ige;
    String du_szoveg;
    String du_szerzo;
    String bibliaora;
    String imaora;

    private String changeLineBreak(String in) {
      String out = in.replaceAll("<br>","\n\n");
      return out;
    };

    public Ahitat(int id,
                  String datum,
                  String de_cim,
                  String de_ige,
                  String de_szoveg,
                  String de_szerzo,
                  String du_cim,
                  String du_ige,
                  String du_szoveg,
                  String du_szerzo,
                  String bibliaora,
                  String imaora) {
        this.id = id;
        this.datum = datum;
        this.de_cim = de_cim;
        this.de_ige = de_ige;
        this.de_szoveg = de_szoveg;
        this.de_szerzo = de_szerzo;
        this.du_cim = du_cim;
        this.du_ige = du_ige;
        this.du_szoveg = du_szoveg;
        this.du_szerzo = du_szerzo;
        this.bibliaora = bibliaora;
        this.imaora = imaora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getDe_cim() {
        return changeLineBreak(de_cim);
    }

    public void setDe_cim(String de_cim) {
        this.de_cim = de_cim;
    }

    public String getDe_ige() {
        return changeLineBreak(de_ige);
    }

    public void setDe_ige(String de_ige) {
        this.de_ige = de_ige;
    }

    public String getDe_szoveg() {
        return changeLineBreak(de_szoveg);
    }

    public void setDe_szoveg(String de_szoveg) {
        this.de_szoveg = de_szoveg;
    }

    public String getDe_szerzo() {
        return changeLineBreak(de_szerzo);
    }

    public void setDe_szerzo(String de_szerzo) {
        this.de_szerzo = de_szerzo;
    }

    public String getDu_cim() {
        return changeLineBreak(du_cim);
    }

    public void setDu_cim(String du_cim) {
        this.du_cim = du_cim;
    }

    public String getDu_ige() {
        return changeLineBreak(du_ige);
    }

    public void setDu_ige(String du_ige) {
        this.du_ige = du_ige;
    }

    public String getDu_szoveg() {
        return changeLineBreak(du_szoveg);
    }

    public void setDu_szoveg(String du_szoveg) {
        this.du_szoveg = du_szoveg;
    }

    public String getDu_szerzo() {
        return changeLineBreak(du_szerzo);
    }

    public void setDu_szerzo(String du_szerzo) {
        this.du_szerzo = du_szerzo;
    }

    public String getBibliaora() {
        return changeLineBreak(bibliaora);
    }

    public void setBibliaora(String bibliaora) {
        this.bibliaora = bibliaora;
    }

    public String getImaora() {
        return changeLineBreak(imaora);
    }

    public void setImaora(String imaora) {
        this.imaora = imaora;
    }


}
