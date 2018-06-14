package mkg.schoolescape;
public class Spielfeld {
    Feld[][] feld = new Feld[10][10];   //erstellen eines zweidimensionalen Arrays aus Feldern der Klasse 'Feld'
     
    public void setzeElement(int x, int y, String typ) {    //einsetzen des jeweiligen Elements (außer des Laufers)
        feld[x][y] = new Feld();    //erstellen eines Objektes der Klasse Feld am Array-Feld [x][y]
        switch(typ) {   //setzen der Eigenschaft 'enthaltenerBlock' des Objektes 'Feld' auf das entsprechende Element, das als Parameter mitgegeben wurde
            case "Wand":
                feld[x][y].setzeEnthaltenenBlock(new Wand(1));
                break;
            case "Superwand":
                feld[x][y].setzeEnthaltenenBlock(new Wand(2));
                break;  
            case "Schlüssel":
                feld[x][y].setzeEnthaltenenBlock(new Schlussel());
                break;  
            case "Tür":
                feld[x][y].setzeEnthaltenenBlock(new Tuer());
                break;  
            case "Tisch":
                feld[x][y].setzeEnthaltenenBlock(new Tisch());
                break;
            case "Lehrer":
                 feld[x][y].setzeEnthaltenenBlock(new Lehrer());
                break;
        }
    }

    // der Laufer muss immer der gleiche bleiben während die anderen Objekte neu erzeugt werden können, deswegen seperate Methode für setzen des Laufers:
    public void setzeLaufer(int x, int y, String typ, Block b) {
        feld[x][y] = new Feld();
        switch(typ) {
            case "Laufer":
                feld[x][y].setzeEnthaltenenBlock(b);
        }
    }


    public void loescheElement(int x, int y) {
         feld[x][y] = null;
    }
    public Feld holeElement(int x, int y) {
         return feld[x][y];
    }

}