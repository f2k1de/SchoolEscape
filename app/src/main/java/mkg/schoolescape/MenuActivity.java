package mkg.schoolescape;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        findViewById(R.id.button).setOnClickListener(onClickListenerMenu);
        findViewById(R.id.button2).setOnClickListener(onClickListenerMenu);
        findViewById(R.id.button3).setOnClickListener(onClickListenerMenu);
    }

    private View.OnClickListener onClickListenerMenu = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (view.getId()) {
                    case R.id.button:
                        intent = new Intent(MenuActivity.this, FullscreenActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.button2:
                        //intent = new Intent(MenuActivity.this, FullscreenActivity.class);
                        //startActivity(intent);
                        break;
                }
            }
        };
    }