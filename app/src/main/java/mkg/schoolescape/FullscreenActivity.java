package mkg.schoolescape;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/** FullscreenActivitz
 * Dies ist die Hauptactivity des Spiels.
 * Hier findet die ganze Spielelogik statt
 */

public class FullscreenActivity extends AppCompatActivity {
    private FrameLayout myLayout = null;
    private float x1, x2;
    private float y1, y2;
    private boolean AsyncTaskCancel = false;

    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setze Layout
        setContentView(R.layout.activity_fullscreen);
        mContentView = findViewById(R.id.fullscreen_content);

        // Prüfe, ob Musik eingeschaltet ist
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String music = prefs.getString("music", null);
        if(music == null) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("music", "on");
            edit.apply();
            mediaPlayer = MediaPlayer.create(this, R.raw.bakercat);
            mediaPlayer.start();
        } else {
            if(music.equals("on")) {
                mediaPlayer = MediaPlayer.create(this, R.raw.bakercat);
                mediaPlayer.start();
            }
        }

        Button btn = (Button) findViewById(R.id.pause);
        btn.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          AsyncTaskCancel = true;
                                          // Code here executes on main thread after user presses button
                                      }
                                  });

                                      // Steuerung
        myLayout = (FrameLayout) findViewById(R.id.MyLayout);

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch( View v, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        break;
                }

                if(x1 < x2 && (Math.abs(x2-x1) > Math.abs(y1-y2))){
                    l.setzeLaufrichtung('r');
                }else if(x1 > x2 && (Math.abs(x1-x2) > Math.abs(y1-y2))){
                    l.setzeLaufrichtung('l');
                }else if(y1 < y2 && (Math.abs(y2-y1) > Math.abs(x1-x2))){
                    l.setzeLaufrichtung('o');
                }else if(y1 > y2 && (Math.abs(y1-y2) > Math.abs(x1-x2))){
                    l.setzeLaufrichtung('u');
                }
                return true;
            }
        });
    }

    /**
     * Starte hier den Spieleablauf nach erstellen der Activity
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
        levelnummer = 1;
        init();
    }

    /**
     * Halte Musik an, wenn Pause, damit sie nicht im Hingrund weiterläuft
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null) {
            mediaPlayer.pause();
        }
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
     * Start der Verwaltung
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
        if(AsyncTaskCancel) {
            return;
        }
        System.out.println("Level: " + levelnummer);
        initFeld();
        //andereRichtung('l');
        tuererreicht = false;
        getSpielfeld();
        while(l.holeLeben() > 1 && !tuererreicht) {
            macheZug();
            if(!tuererreicht) {
                //Scanner scanner = new Scanner(System.in);
                //String input = scanner.nextLine();
                String input = Character.toString(l.holeLaufrichtung());
                if(!input.equals("")) {
                    char c = input.charAt(0);
                    andereRichtung(c);
                }
            }
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        if(tuererreicht) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView leben = (TextView) findViewById(R.id.tvleben);
                    leben.setText("Level up!");
                }
            });
            System.out.println("Levelup!");
            l.setzeSchlussel(0);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView leben = (TextView) findViewById(R.id.tvleben);
                    leben.setText("Game over!");
                    if(mediaPlayer != null) {
                        mediaPlayer.pause();
                    }
                }
            });
            System.out.println("Game over!");
        }

    }

    /** Leveldaten
     * Hier werden die Leveldaten definiert.
     * @param pLevelnummer - Levelnummer, für welches Level die Daten gesetzt werden sollen.
     */
    private void leveldaten(int pLevelnummer) {
        switch (pLevelnummer) {
            case 3:
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
            case 1:
                s.setzeElement(8,5, "Laufer", l);
                s.setzeElement(3,2, "Tür");
                s.setzeElement(2,3, "Wand");
                s.setzeElement(2,2, "Schlüssel");
                s.setzeElement(8,7, "Wand");
                s.setzeElement(8,2, "Tisch");
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

    /** initFeld
     * Räume das ganze Spielfeld frei und setze die Wandelemente.
     */
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

    /** getSpielfeld
     * Schreibt das Spielfeld von s auf die UI
     */
    public void getSpielfeld() {
        System.out.println("Leben " + l.holeLeben() + " Schlüssel " + l.holeSchlussel());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView leben = (TextView) findViewById(R.id.tvleben);
                leben.setText("Leben: " + l.holeLeben() + "\n" + "Schlüssel: " + l.holeSchlussel());
            }
        });
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                String element;
                if(s.holeElement(i,j) == null) {
                    element = " ";
                } else {
                    element = s.holeElement(i, j).holeTyp();
                }
                int id = getResources().getIdentifier("imageView" + i + j, "id", getPackageName());
                final ImageView iv = (ImageView) findViewById(id);
                switch (element) {
                    case "Wand":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageResource(R.drawable.wand);
                            }
                        });
                        break;
                    case "Läufer":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageResource(R.drawable.figur);
                            }
                        });
                        break;
                    case "Schlüssel":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageResource(R.drawable.key_0);
                            }
                        });
                        break;
                    case "Tür":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageResource(R.drawable.door);
                            }
                        });
                        break;
                    case "Tisch":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageResource(R.drawable.tisch);
                            }
                        });
                        switch (element = "T") {
                        }
                        break;
                    case " ":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageResource(R.drawable.boden_0);
                            }
                        });
                        break;
                }
            }
        }

    }

    /** macheZug
     * Ziehe Läufer in die zuvor festgelegte Laufrichtung
     */
    private void macheZug() {
        char laufrichtung = l.holeLaufrichtung();
        zieheLaufer(laufrichtung);
    }

    /** andereRichtung
     * Setze dem Läufer eine andere Laufrichutng
     * @param richtung – char 'l', 'r', 'o', 'u'
     */
    private void andereRichtung(char richtung) {
        l.setzeLaufrichtung(richtung);
    }

    /** zieheLaufer
     * Ziehe den Laufer in eine andere Richtung
     * @param richtung – char 'l', 'r', 'o', 'u'
     */
    private void zieheLaufer(char richtung) {
        int x;
        int y;
        int newx = 0;
        int newy = 0;
        if((richtung == 'o') || (richtung == 'u') || (richtung == 'r') || (richtung == 'l')) {
            // Richtung ist okay!
            // ToDo: Fix!
        } else {
            return;
        }
        String koord = holeLauferKoordinaten();
        x = Integer.parseInt(koord.substring(0,koord.indexOf(',')));
        y = Integer.parseInt(koord.substring(koord.indexOf(',')+1,koord.length()));
        switch(richtung) {
            case 'u':
                newx = x - 1;
                newy = y;
                break;
            case 'o':
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
                        levelnummer = levelnummer + 1;
                        l.setzeSchlussel(0);
                        leveldurchlauf();
                        return;
                    } else {
                        // Bleibe vor Türe stehen
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

    /** holeLauferKoordinaten
     * gibt die Koordinaten des Läufers zurück
     * @return String: "xkoord,ykoord"
     */
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

    /** randomLehrer
     * Soll irgendwann mal zufällige Hindernisse erzeugen.
     */
    // Todo: Implement to use this function.
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

    /** MacheZugTask
     * Dies ist ein AsyncTask, der den Leveldurchlauf im Hintergrund ausführt, um den Haupt-Thread
     * nicht zu belagern
     */
    private class MacheZugTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            leveldurchlauf();
            return null;
        }
    }
}