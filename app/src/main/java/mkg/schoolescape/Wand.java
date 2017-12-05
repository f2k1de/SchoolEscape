package mkg.schoolescape;
public class Wand extends Block {
    // Variabeln
    public int haerte;
    public boolean block;
    // Funktionen
    public Wand(int pHaerte, boolean pBlock) {
        haerte = pHaerte;
        block = pBlock;
        super.setzeTyp("Wand");
        super.setzeHaerte(haerte);
    }
    
    public int holeHaerte() {
        return haerte;
    }
}