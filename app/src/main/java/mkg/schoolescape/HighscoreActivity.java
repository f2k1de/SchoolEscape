package mkg.schoolescape;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        SharedPreferences pref = getSharedPreferences("score", Context.MODE_PRIVATE);
        String oldscore = pref.getString("score", null);
        String score;
        if(oldscore != null) {
            score = oldscore;
        } else {
            score = "0";
        }
        TextView textView = findViewById(R.id.myscore);
        textView.setText(String.format("Mein Highscore: %s", score));
    }
}
