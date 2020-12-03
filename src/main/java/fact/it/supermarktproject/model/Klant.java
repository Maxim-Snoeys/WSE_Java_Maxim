package fact.it.supermarktproject.model;
//Maxim Snoeys R0792870

import java.time.LocalDate;
import java.util.ArrayList;

public class Klant extends Persoon {
    private ArrayList<String> boodschappenlijst = new ArrayList<>();
    private int klantenkaartnr;

    public Klant(String voornaam, String familienaam) {
        super(voornaam, familienaam);
        this.klantenkaartnr = -1;
    }

    public ArrayList<String> getBoodschappenlijst() {
        return boodschappenlijst;
    }

    public int getKlantenkaartnr() {
        return klantenkaartnr;
    }

    public void setKlantenkaartnr(int klantenkaartnr) {
        this.klantenkaartnr = klantenkaartnr;
    }

    public boolean voegToeAanBoodschappenlijst(String product) {
        if (getAantalOpBoodschappenlijst() < 5) {
            boodschappenlijst.add(product);
            return true;
        }
        else {
            return false;
        }
    }

    public int getAantalOpBoodschappenlijst() {
        return boodschappenlijst.size();
    }

    public String toString() {
        return "Klant " + super.toString() + " met klantenkaartnr " + getKlantenkaartnr();
    }

}
