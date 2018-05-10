package mkg.schoolescape;

/**
 * Created by freddy on 26.11.17.
 */

class Zugdaten {
    private final Laufer l;
    private final Spielfeld s;
    private final char richtung;
    private final String koordinaten;
    Zugdaten(Laufer pL, Spielfeld pS, char pRichtung, String pKoord) {
        l = pL;
        s = pS;
        richtung = pRichtung;
        koordinaten = pKoord;
    }
    Laufer getLaufer() {
        return l;
    }
    Spielfeld getSpielfeld() {
        return s;
    }
    char getRichtung() {
        return richtung;
    }
    String getKoordinaten() {
        return koordinaten;
    }
}