package mkg.schoolescape;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        if(getStatus()) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    musicon();
                } else {
                    musicoff();
                }
            }
        });
    }

    /** getStatus
     * @return booloan Wert, ob Musik aktiviert
     */
    private boolean getStatus() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String music = prefs.getString("music", null);
        if(music == null) {
            return false;
        } else {
            return music.equals("on");
        }
    }

    /** musicon
     * Schaltet Musik ein
     */
    private void musicon() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("music", "on");
        editor.apply();
    }

    /** musicoff
     * Schaltet Musik aus
     */
    private void musicoff() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("music", "off");
        editor.apply();
    }
}
