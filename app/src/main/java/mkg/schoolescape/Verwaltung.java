package mkg.schoolescape;

import java.util.Scanner;
import java.util.Random;

class Verwaltung {
    private final Spielfeld s = new Spielfeld();
    private final Laufer l = new Laufer();
    private boolean tuererreicht;
    private int levelnummer;
    
    /** Verwaltung
     * Konstruktor und Einstiegpunkt des ganzen Spieles
     * Legt erste Levelnummer fest und läuft die Level durch, bis keine Leben mehr da sind.
     */
    private Verwaltung() {
        levelnummer = 1;
        while(l.holeLeben() > 1) {
            leveldurchlauf();
        }
    }
    
    private void leveldurchlauf() {
        System.out.println("Level: " + levelnummer);
        initFeld();
        andereRichtung('l');
        tuererreicht = false;
        while(l.holeLeben() > 1 && !tuererreicht) {
            macheZug();
            if(!tuererreicht) {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if(!input.equals("")) {
                    char c = input.charAt(0);
                    andereRichtung(c);
                }
            }
            /*try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/
        }
        if(tuererreicht) {
            System.out.println("Levelup!");
            l.setzeSchlussel(0);
        } else {
            System.out.println("Game over!");        
        }
        
    }
    
    private void leveldaten(int pLevelnummer) {
        switch (pLevelnummer) {
            case 1: 
                s.setzeElement(8,5, "Laufer", l);
                s.setzeElement(2,2, "Tür");
                s.setzeElement(3,2, "Wand");
                s.setzeElement(4,2, "Schlüssel");
                s.setzeElement(5,2, "Wand");
                s.setzeElement(6,2, "Tisch");
                break;
            case 2:
                s.setzeElement(8,5, "Laufer", l);
                s.setzeElement(5,2, "Tür");
                s.setzeElement(5,3, "Wand");
                s.setzeElement(2,2, "Schlüssel");
                s.setzeElement(4,7, "Wand");
                s.setzeElement(6,2, "Tisch");
                // ToDo: Add more Levels
                break;
            default: 
                s.setzeElement(8,5, "Laufer", l);
                s.setzeElement(2,2, "Tür");
                s.setzeElement(3,2, "Wand");
                s.setzeElement(4,2, "Schlüssel");
                s.setzeElement(5,2, "Wand");
                s.setzeElement(6,2, "Tisch");
                break;
        }
    }
    
    private void initFeld() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                s.loescheElement(i, j);
            }
        }
        for(int i = 0; i < 10; i++) {
            s.setzeElement(0,i, "Wand");            
            s.setzeElement(9,i, "Wand");  
        }
        for(int i = 1; i < 9; i++) {
            s.setzeElement(i,0, "Wand");            
            s.setzeElement(i,9, "Wand");  
        }
        leveldaten(levelnummer);
    }
    
    private void getSpielfeld() {
        System.out.println("Leben " + l.holeLeben() + " Schlüssel " + l.holeSchlussel());
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                String element; 
                if(s.holeElement(i,j) == null) {
                  element = " ";
                } else {
                   element = s.holeElement(i,j).holeTyp();
                   if(element == "Wand") {
                       element = "W";
                    } else if (element == "Läufer") {
                     element = "L";   
                    } else if (element == "Schlüssel") {
                     element = "S";   
                    } else if (element == "Tür") {
                     element = "D";   
                    } else if (element == "Tisch") {
                     element = "T";   
                    }
                }
                System.out.print(element);
            }
            System.out.println();
        }
    }

    private void macheZug() {
        char laufrichtung = l.holeLaufrichtung();
        zieheLaufer(laufrichtung);
    }
    
    private void andereRichtung(char richtung) {
        l.setzeLaufrichtung(richtung);
    }

    private void zieheLaufer(char richtung) {
        int x;
        int y;
        int newx = 0;
        int newy = 0;
        if((richtung == 'o') || (richtung == 'u') || (richtung == 'r') || (richtung == 'l')) {
            // Richtung ist okay!
        } else {
            return;
        }
        String koord = holeLauferKoordinaten();
        x = Integer.parseInt(koord.substring(0,koord.indexOf(',')));
        y = Integer.parseInt(koord.substring(koord.indexOf(',')+1,koord.length()));
        switch(richtung) {
            case 'o':
                newx = x - 1;
                newy = y;
                break;
            case 'u':
                newx = x + 1;
                newy = y;  
                break;
            case 'r':
                newx = x;
                newy = y + 1;  
                break;
            case 'l':
                newx = x;
                newy = y - 1;  
                break;
        }
        // Um nicht aus dem Spielfeld zu laufen
        if(newx > 9) {
            newx = newx - 10;
        }
        if(newx < 0) {
            newx = newx + 10;
        }
        if(newy > 9) {
            newy = newy - 10;
        }
        if(newy < 0) {
            newy = newy + 10;
        }
        /*System.out.print("Old:" + x + y + "\n");
        System.out.print("New:" + newx + newy + "\n");*/
        // ToDo: Check if Block im Weg
        if(s.holeElement(newx,newy) == null) {
            // Komplett frei
        } else {
            String typ = s.holeElement(newx,newy).holeTyp();
            if(typ == "Wand") {
                l.setzeLeben(l.holeLeben() - s.holeElement(newx,newy).holeObjekt().holeHaerte());
                newx = x;
                newy = y;
                System.out.println("Wand im Weg! Leben: " + l.holeLeben());
            } else if (typ == "Schlüssel") {
                l.setzeSchlussel(l.holeSchlussel() + 1);
            } else if (typ == "Tür") {
                //System.out.print(s.holeElement(newx,newy).holeObjekt().holeHaerte());
                if(s.holeElement(newx,newy).holeObjekt().holeHaerte() <= l.holeSchlussel()) {
                    // Darf passieren
                    tuererreicht = true;
                    //System.out.println("Level Up!");
                    levelnummer = levelnummer + 1;
                    return;
                } else {
                    newx = x;
                    newy = y;
                }
            } else if (typ == "Tisch") {
                    newx = x;
                    newy = y;                
            }
        }
        Block laufer = s.holeElement(x,y).holeObjekt();
        s.loescheElement(x, y);
        s.setzeElement(newx, newy, "Laufer", laufer);
        getSpielfeld();
    }
    
    private String holeLauferKoordinaten() {
        int xkoord = -1;
        int ykoord = -1;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(s.holeElement(i,j) != null) {
                    if(s.holeElement(i,j).holeTyp() == "Läufer") {
                        xkoord = i;
                        ykoord = j;
                    }
                }
            }
        }
        return xkoord + "," + ykoord;
    }
    
    private void randomLehrer() {
        Random rand = new Random();
        
        int zufallszahl = 0; // = rand.integer();
        // Zufallszahlen generiern
        if(zufallszahl == 37) {
            int zufallx =4;
            int zufally = 4;
            
            if(s.holeElement(zufallx,zufally) == null) {
                // Komplett frei
                s.setzeElement(zufallx,zufally, "Lehrer");
                // Setze Lehrer
            }
            
        } else if(zufallszahl == 38) {
            int xkoord = 0;
            int ykoord = 0;
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    if(s.holeElement(i,j) != null) {
                        if(s.holeElement(i,j).holeTyp() == "Lehrer") {
                            xkoord = i;
                            ykoord = j;
                        }
                    }
                }
            }
            if((xkoord != 0) && (ykoord != 0)) {
                s.loescheElement(xkoord,ykoord);
            }
        }
    }
    
    // Needed for .jar files
    public static void main(String[] args) {
        new Verwaltung();
    }
}
