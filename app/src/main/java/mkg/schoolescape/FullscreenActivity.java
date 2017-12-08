package mkg.schoolescape;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
     /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        /*mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
        levelnummer = 1;
        init();/*
        while(l.holeLeben() > 1) {
            leveldurchlauf();
        }*/
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };


    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    /**
     * Start of Verwaltung
     */
    private Spielfeld s = new Spielfeld();
    private Laufer l = new Laufer();
    boolean tuererreicht;
    int levelnummer;

    private void init() {
        levelnummer = 1;
        //while(l.holeLeben() > 1) {
            MacheZugTask macheZugTask = new MacheZugTask();
            macheZugTask.execute(new String[] {});

        //}
    }


    private void leveldurchlauf() {
        System.out.println("Level: " + levelnummer);
        initFeld();
        andereRichtung('l');
        tuererreicht = false;
        while(l.holeLeben() > 1 && !tuererreicht) {
            macheZug();
            if(!tuererreicht) {
                //Scanner scanner = new Scanner(System.in);
                //String input = scanner.nextLine();
                String input = "l";
                if(!input.equals("")) {
                    char c = input.charAt(0);
                    andereRichtung(c);
                }
            }
            try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
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

    public void getSpielfeld() {
        System.out.println("Leben " + l.holeLeben() + " Schlüssel " + l.holeSchlussel());
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                String element;
                if(s.holeElement(i,j) == null) {
                    element = " ";
                } else {
                    element = s.holeElement(i,j).holeTyp();
                    int id = getResources().getIdentifier("imageView" + i + j, "id", getPackageName());
                    ImageView iv = (ImageView) findViewById(id);
                    if(element.equals("Wand")) {
                        iv.setImageResource(R.drawable.wand);
                        element = "W";
                    } else if (element.equals("Läufer")) {
                        iv.setImageResource(R.drawable.figur);
                        element = "L";
                    } else if (element.equals("Schlüssel")) {
                        iv.setImageResource(R.drawable.key_0);
                        element = "S";
                    } else if (element.equals("Tür")) {
                        iv.setImageResource(R.drawable.door);
                        element = "D";
                    } else if (element.equals("Tisch")) {
                        iv.setImageResource(R.drawable.tisch);
                        element = "T";
                    } else if (element.equals(" ")) {
                        iv.setImageResource(R.drawable.boden_0);
                        element = "";
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
        int x = 0;
        int y = 0;
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
            switch (typ) {
                case "Wand":
                    l.setzeLeben(l.holeLeben() - s.holeElement(newx, newy).holeObjekt().holeHaerte());
                    newx = x;
                    newy = y;
                    System.out.println("Wand im Weg! Leben: " + l.holeLeben());
                    break;
                case "Schlüssel":
                    l.setzeSchlussel(l.holeSchlussel() + 1);
                    break;
                case "Tür":
                    //System.out.print(s.holeElement(newx,newy).holeObjekt().holeHaerte());
                    if (s.holeElement(newx, newy).holeObjekt().holeHaerte() <= l.holeSchlussel()) {
                        // Darf passieren
                        tuererreicht = true;
                        //System.out.println("Level Up!");
                        levelnummer = levelnummer + 1;
                        return;
                    } else {
                        newx = x;
                        newy = y;
                    }
                    break;
                case "Tisch":
                    newx = x;
                    newy = y;
                    break;
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
                    if(s.holeElement(i,j).holeTyp().equals("Läufer")) {
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
                        if(s.holeElement(i,j).holeTyp().equals("Lehrer")) {
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


/*
// Innere Klasse HoleDatenTask führt den asynchronen Task auf eigenem Arbeitsthread aus
class BewegeTask extends AsyncTask<Zugdaten, Integer, Void> {
    FullscreenActivity activity;

    public void setContext(FullscreenActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Zugdaten... zugdatens) {
        Zugdaten zugdaten = zugdatens[0];
        char richtung = zugdaten.getRichtung();
        Spielfeld s = zugdaten.getSpielfeld();
        Laufer l = zugdaten.getLaufer();
        int x = 0;
        int y = 0;
        int newx = 0;
        int newy = 0;
        if ((richtung == 'o') || (richtung == 'u') || (richtung == 'r') || (richtung == 'l')) {
            // Richtung ist okay!
        } else {
            return null;
        }
        String koord = zugdaten.getKoordinaten();
        x = Integer.parseInt(koord.substring(0, koord.indexOf(',')));
        y = Integer.parseInt(koord.substring(koord.indexOf(',') + 1, koord.length()));
        switch (richtung) {
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
        if (newx > 9) {
            newx = newx - 10;
        }
        if (newx < 0) {
            newx = newx + 10;
        }
        if (newy > 9) {
            newy = newy - 10;
        }
        if (newy < 0) {
            newy = newy + 10;
        }
        /*System.out.print("Old:" + x + y + "\n");
        System.out.print("New:" + newx + newy + "\n");
        // ToDo: Check if Block im Weg
        if (s.holeElement(newx, newy) == null) {
            // Komplett frei
        } else {
            String typ = s.holeElement(newx, newy).holeTyp();
            if (typ == "Wand") {
                l.setzeLeben(l.holeLeben() - s.holeElement(newx, newy).holeObjekt().holeHaerte());
                newx = x;
                newy = y;
                System.out.println("Wand im Weg! Leben: " + l.holeLeben());
            } else if (typ == "Schlüssel") {
                l.setzeSchlussel(l.holeSchlussel() + 1);
            } else if (typ == "Tür") {
                //System.out.print(s.holeElement(newx,newy).holeObjekt().holeHaerte());
                if (s.holeElement(newx, newy).holeObjekt().holeHaerte() <= l.holeSchlussel()) {
                    // Darf passieren
                    activity.tuererreicht = true;
                    //System.out.println("Level Up!");
                    activity.levelnummer = activity.levelnummer + 1;
                    return null;
                } else {
                    newx = x;
                    newy = y;
                }
            } else if (typ == "Tisch") {
                newx = x;
                newy = y;
            }
        }
        Log.d("koords", x + " , " + y);
        Feld f = s.holeElement(x, y);
        Block laufer = f.holeObjekt();
        //Block laufer = s.holeElement(x,y).holeObjekt();
        s.loescheElement(x, y);
        s.setzeElement(newx, newy, "Laufer", laufer);


        // Mit Thread.sleep(600) simulieren wir eine Wartezeit von 600 ms
        try {
            Thread.sleep(600);
        } catch (Exception e) {
            Log.e("Error", "Error ", e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPostExecute(Void strings) {
        activity.getSpielfeld();
    }

    */

    private class MacheZugTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            leveldurchlauf();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
}