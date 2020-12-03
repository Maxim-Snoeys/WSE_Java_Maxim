package fact.it.supermarktproject.model;
//Maxim Snoeys R0792870

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Personeelslid extends Persoon{
    private LocalDate inDienstSinds;

    public Personeelslid(String voornaam, String familienaam) {
        super(voornaam, familienaam);
        this.inDienstSinds = LocalDate.now();
    }

    public LocalDate getInDienstSinds() {
        return inDienstSinds;
    }

    public void setInDienstSinds(LocalDate inDienstSinds) {
        this.inDienstSinds = inDienstSinds;
    }

    public String toString() {
        LocalDate datum = getInDienstSinds();
        DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String formattedDate = datum.format(datumFormat);
        return "Personeelslid " + super.toString() + " is in dienst sinds " + formattedDate;
    }
}
