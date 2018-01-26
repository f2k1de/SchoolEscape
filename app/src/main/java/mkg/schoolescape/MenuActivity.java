package mkg.schoolescape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


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
                        intent = new Intent(MenuActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
    }