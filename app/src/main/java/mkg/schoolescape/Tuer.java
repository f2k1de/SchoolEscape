package mkg.schoolescape;
class Tuer extends Block {
    // Variabeln
    private final int schlussel;
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