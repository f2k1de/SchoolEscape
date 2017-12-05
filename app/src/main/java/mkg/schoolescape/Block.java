package mkg.schoolescape;

/**
 * Write a description of class Block here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Block {
    String typ;
    int haerte;
    public Block() {
        // initialise instance variables
        haerte = 0;
    }
    
    public void setzeTyp(String ptyp) {
     typ = ptyp;
    }
    
    public void setzeHaerte(int hart) {
        haerte = hart;
    }
    
    public int holeHaerte() {
        return haerte;   
    }
    
    public String holeTyp() {
        return typ;
    }
}