package mkg.schoolescape;

public class Laufer extends Block
{
    private int leben;
    private char laufrichtung;
    private int schlussel;
    public Laufer() {
        super.setzeTyp("Läufer");
        setzeLeben(6);
        setzeSchlussel(0);
    }
   
    public void setzeLeben(int pLeben) {
        leben = pLeben;
    }
    
    public int holeLeben() {
        return leben;
    }
    
    public void setzeSchlussel(int pSchlussel) {
        schlussel = pSchlussel;
    }
    
    public int holeSchlussel() {
        return schlussel;
    }
    
    public void setzeLaufrichtung(char pLr) {
        laufrichtung = pLr;
    }
    
    public char holeLaufrichtung() {
        return laufrichtung;
    }
}
