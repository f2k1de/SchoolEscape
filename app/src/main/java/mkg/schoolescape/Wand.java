package mkg.schoolescape;
public class Wand extends Block {
    public int haerte;

    public Wand(int pHaerte) { //Konstruktor
        haerte = pHaerte;
        super.setzeTyp("Wand");
        super.setzeHaerte(haerte);
    }
    
    public int holeHaerte() {
        return haerte;
    }
}