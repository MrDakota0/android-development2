package com.example.firstappforpractice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar is defined in the layout file
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        CheckBox box = findViewById(R.id.checkBox);
        EditText editText = findViewById(R.id.editText);
        if (!box.isChecked()) {
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
        else {
            String message = "You Checked the BOX!";
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

    public void startProgressBar(View view) {
        final Button button = findViewById(R.id.progressButton);
        button.setText("Processing");
        button.setClickable(false);
        progressStatus = 0;
        progressBar = findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            private Handler handler = new Handler();

            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            if (progressStatus == 100){
                                progressBar.setProgress(0);
                                button.setText("Start Processing");
                            }
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void resetProgressBar(View view){
        Button resetButton = findViewById(R.id.resetButton);
        Button progressButton = findViewById(R.id.progressButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressButton.setText("Start Processing");
        progressButton.setClickable(true);
    }
}
