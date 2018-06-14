package mkg.schoolescape;

public class Block {
    String typ;
    int haerte;

    public Block() {
        haerte = 0;
    }

    // get-/set-methoden
    public void setzeTyp(String pTyp) {
     typ = pTyp;
    }
    public String holeTyp() {
        return typ;
    }
    public void setzeHaerte(int phaerte) {
        haerte = phaerte;
    }
    public int holeHaerte() {
        return haerte;   
    }
}