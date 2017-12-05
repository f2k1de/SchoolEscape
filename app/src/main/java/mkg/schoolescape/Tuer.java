package mkg.schoolescape;
public class Tuer extends Block {
    // Variabeln
    private int schlussel = 0;
    // Funktionen
    public Tuer(int pschlussel) {
        super.setzeTyp("Tür");
        super.setzeHaerte(1);
        schlussel = pschlussel;
    }
    
    public int holeAnzahlSchlussel() {
        return schlussel;
    }
}