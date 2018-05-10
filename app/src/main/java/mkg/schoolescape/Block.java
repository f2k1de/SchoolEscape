package mkg.schoolescape;

/**
 * Write a description of class Block here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Block {
    private String typ;
    private int haerte;
    Block() {
        // initialise instance variables
        haerte = 0;
    }
    
    void setzeTyp(String ptyp) {
     typ = ptyp;
    }
    
    void setzeHaerte(int hart) {
        haerte = hart;
    }
    
    public int holeHaerte() {
        return haerte;   
    }
    
    public String holeTyp() {
        return typ;
    }
}