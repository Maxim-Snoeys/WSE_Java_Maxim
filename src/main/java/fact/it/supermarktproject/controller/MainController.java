package fact.it.supermarktproject.controller;
//Maxim Snoeys R0792870

import fact.it.supermarktproject.model.Afdeling;
import fact.it.supermarktproject.model.Klant;
import fact.it.supermarktproject.model.Personeelslid;
import fact.it.supermarktproject.model.Supermarkt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class MainController {
    @RequestMapping("/1_nieuweklant")
    public String nieuweKlant(Model model) {
        model.addAttribute("winkellijst", supermarkten);
        return "1_nieuweklant";
    }

    @RequestMapping("/2_welkom")
    public String nieuweKlant(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String achternaam = request.getParameter("achternaam");
        String geboortejaarstr = request.getParameter("geboortejaar");
        String winkel = request.getParameter("winkel");
        int geboortejaar = Integer.parseInt(geboortejaarstr);
        int winkelIndex = Integer.parseInt(winkel);
        Klant k = new Klant(voornaam, achternaam);
        Supermarkt s = supermarkten.get(winkelIndex);
        k.setGeboortejaar(geboortejaar);
        s.registreerKlant(k);
        model.addAttribute("klant", k);
        klanten.add(k);
        return "2_welkom";
    }

    @RequestMapping("/3_nieuwpersoneel")
    public String nieuwPersoneel() {
        return "3_nieuwpersoneel";
    }

    @RequestMapping("/4_personeelsdata")
    public String nieuwPersoneel(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String achternaam = request.getParameter("achternaam");
        String indienstsinds = request.getParameter("indienstsinds");
        LocalDate datum = LocalDate.parse(indienstsinds);
        Personeelslid p = new Personeelslid(voornaam, achternaam);
        p.setInDienstSinds(datum);
        model.addAttribute("personeelslid", p);
        personeelsleden.add(p);
        return "4_personeelsdata";
    }

    @RequestMapping("/5_personeelalle")
    public String allePersoneel(Model model) {
        model.addAttribute("personeelsleden", personeelsleden);
        return "5_personeelalle";
    }

    @RequestMapping("/6_alleklanten")
    public String alleKlanten(Model model) {
        model.addAttribute("klanten", klanten);
        return "6_alleklanten";
    }

    @RequestMapping("/7_nieuwesupermarkt")
    public String nieuwSupermarkt() {
        return "7_nieuwesupermarkt";
    }

    @RequestMapping("/8_overzichtsupermarkt")
    public String overzichtSupermarkten(Model model, HttpServletRequest request) {
        String winkel = request.getParameter("winkel");
        Supermarkt s = new Supermarkt(winkel);
        if (winkel != null) {
            supermarkten.add(s);
        }
        model.addAttribute("overzichtWinkels", supermarkten);
        return "8_overzichtsupermarkt";
    }

    @RequestMapping("/9_nieuweafdeling")
    public String nieuwAfdeling(Model model) {
        model.addAttribute("personeelsleden", personeelsleden);
        model.addAttribute("supermarkten", supermarkten);
        return "9_nieuweafdeling";
    }

    @RequestMapping("/10_overzichtafdeling")
    public String overzichtAfdeling(Model model, HttpServletRequest request) {
        String naam = request.getParameter("naam");
        int supermarktIndex = Integer.parseInt(request.getParameter("supermarkten"));
        Supermarkt supermarkt = supermarkten.get(supermarktIndex);

        if (naam != null) {
            String foto = request.getParameter("foto");
            boolean gekoeld = Boolean.parseBoolean(request.getParameter("gekoeld"));
            int verantwoordelijkeIndex = Integer.parseInt(request.getParameter("verantwoordelijke"));

            if ((supermarktIndex < 0) && (verantwoordelijkeIndex < 0)) {
                model.addAttribute("fout", "Geen verantwoordelijke en supermarkt gekozen");
                return "99_error";
            }

            else if (verantwoordelijkeIndex < 0) {
                model.addAttribute("fout", "Geen verantwoordelijke gekozen");
                return "99_error";
            }
            else if (supermarktIndex < 0) {
                model.addAttribute("fout", "Geen supermarkt gekozen");
                return "99_error";
            }

            Personeelslid personeelslid = personeelsleden.get(verantwoordelijkeIndex);
            Afdeling afdeling = new Afdeling(naam);
            afdeling.setVerantwoordelijke(personeelslid);
            afdeling.setGekoeld(gekoeld);
            afdeling.setFoto(foto);
            supermarkt.voegAfdelingToe(afdeling);
        }
        model.addAttribute("supermarkt", supermarkt);
        return "10_overzichtafdeling";
    }

    @RequestMapping("/11_zoeken")
    public String zoeken(Model model, HttpServletRequest request) {
        boolean voorwaarde = false;
        String afdelingsNaam = request.getParameter("afdeling");
        for (Supermarkt supermarkt: supermarkten) {
            Afdeling afdeling = supermarkt.zoekAfdelingOpNaam(afdelingsNaam);
            if (afdeling != null) {
                model.addAttribute("afdeling", afdeling);
                voorwaarde = true;
            }
            if (voorwaarde == false) {
                model.addAttribute("fout", "Geen afdeling met de naam" + afdelingsNaam + " gevonden!");
                return "99_error";
            }
        }
        return "11_zoeken";
    }


    private ArrayList<Personeelslid> personeelsleden;
    private ArrayList<Klant> klanten;
    private ArrayList<Supermarkt> supermarkten;


    @PostConstruct
    public void vullen() {
        vulPersoneelsledenLijst();
        vulSupermarktenLijst();
        vulKlantenLijst();
    }


    private ArrayList<Personeelslid> vulPersoneelsledenLijst() {
        personeelsleden = new ArrayList<>();
        Personeelslid jitse = new Personeelslid("Jitse", "Verhaegen");
        Personeelslid bert = new Personeelslid("Bert", "De Meulenaere");
        Personeelslid sanne = new Personeelslid("Sanne", "Beckers");
        personeelsleden.add(jitse);
        personeelsleden.add(bert);
        personeelsleden.add(sanne);
        return personeelsleden;
    }

    private ArrayList<Klant> vulKlantenLijst() {
        klanten = new ArrayList<>();
        Klant daan = new Klant("Daan", "Mertens");
        daan.setGeboortejaar(2001);
        Klant wim = new Klant("Wim", "Wijns");
        wim.setGeboortejaar(1956);
        Klant gert = new Klant("Gert", "Pauwels");
        gert.setGeboortejaar(1978);
        Klant maxim = new Klant("Maxim", "Snoeys");
        maxim.setGeboortejaar(2000);
        klanten.add(daan);
        klanten.add(wim);
        klanten.add(gert);
        klanten.add(maxim);
        klanten.get(0).voegToeAanBoodschappenlijst("melk");
        klanten.get(0).voegToeAanBoodschappenlijst("kaas");
        klanten.get(1).voegToeAanBoodschappenlijst("eieren");
        klanten.get(1).voegToeAanBoodschappenlijst("water");
        klanten.get(1).voegToeAanBoodschappenlijst("bloemkool");
        klanten.get(1).voegToeAanBoodschappenlijst("sla");
        klanten.get(2).voegToeAanBoodschappenlijst("tomaten");
        klanten.get(3).voegToeAanBoodschappenlijst("varkenshaasje");
        klanten.get(3).voegToeAanBoodschappenlijst("aardappelen");
        klanten.get(3).voegToeAanBoodschappenlijst("champignon");
        klanten.get(3).voegToeAanBoodschappenlijst("room");
        return klanten;
    }

    private ArrayList<Supermarkt> vulSupermarktenLijst() {
        supermarkten = new ArrayList<>();
        Supermarkt supermarkt1 = new Supermarkt("Colruyt Geel");
        Supermarkt supermarkt2 = new Supermarkt("Okay Meerhout");
        Supermarkt supermarkt3 = new Supermarkt("Colruyt Herentals");
        Afdeling afdeling1 = new Afdeling("Brood");
        Afdeling afdeling2 = new Afdeling("Groenten");
        afdeling2.setGekoeld(true);
        Afdeling afdeling3 = new Afdeling("Fruit");
        afdeling3.setGekoeld(true);
        Afdeling afdeling4 = new Afdeling("Vlees");
        afdeling4.setGekoeld(true);
        Afdeling afdeling5 = new Afdeling("Dranken");
        Afdeling afdeling6 = new Afdeling("Diepvries");
        afdeling1.setFoto("/img/brood.jpg");
        afdeling2.setFoto("/img/groenten.jpg");
        afdeling3.setFoto("/img/fruit.jpg");
        afdeling1.setVerantwoordelijke(personeelsleden.get(0));
        afdeling2.setVerantwoordelijke(personeelsleden.get(1));
        afdeling3.setVerantwoordelijke(personeelsleden.get(2));
        afdeling4.setVerantwoordelijke(personeelsleden.get(0));
        afdeling5.setVerantwoordelijke(personeelsleden.get(1));
        afdeling6.setVerantwoordelijke(personeelsleden.get(2));

        supermarkt1.voegAfdelingToe(afdeling1);
        supermarkt1.voegAfdelingToe(afdeling2);
        supermarkt2.voegAfdelingToe(afdeling3);
        supermarkt2.voegAfdelingToe(afdeling4);
        supermarkt3.voegAfdelingToe(afdeling5);
        supermarkt3.voegAfdelingToe(afdeling6);
        supermarkten.add(supermarkt1);
        supermarkten.add(supermarkt2);
        supermarkten.add(supermarkt3);
        return supermarkten;
    }
}