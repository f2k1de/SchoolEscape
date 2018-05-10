package mkg.schoolescape;
class Spielfeld {
     private final Feld[][] feld = new Feld[10][10];
     
     public void setzeElement(int x, int y, String typ) {
         feld[x][y] = new Feld();
         switch(typ) {
             case "Wand": 
                feld[x][y].setzeObjekt(new Wand(1, true));
                break;  
             case "Superwand": 
                feld[x][y].setzeObjekt(new Wand(2, true));
                break;  
             case "Schlüssel": 
                feld[x][y].setzeObjekt(new Schlussel());
                break;  
             case "Tür": 
                feld[x][y].setzeObjekt(new Tuer(1));
                break;  
             case "Tisch": 
                feld[x][y].setzeObjekt(new Tisch());
                break;
             case "Lehrer":
                feld[x][y].setzeObjekt(new Lehrer());
                break;                  
        }
     }
     
       public void setzeElement(int x, int y, String typ, Block b) {
         feld[x][y] = new Feld();
         switch(typ) {
             case "Laufer":
                feld[x][y].setzeObjekt(b);
        }
     }
     
     public void loescheElement(int x, int y) {
         feld[x][y] = null;
    }
     
     public Feld holeElement(int x, int y) {
         return feld[x][y];
    }
     
}