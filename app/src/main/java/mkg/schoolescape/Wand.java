package mkg.schoolescape;
public class Wand extends Block {
    // Variabeln
    private final int haerte;

    // Funktionen
    public Wand(int pHaerte, boolean pBlock) {
        haerte = pHaerte;
        super.setzeTyp("Wand");
        super.setzeHaerte(haerte);
    }
    
    public int holeHaerte() {
        return haerte;
    }
}