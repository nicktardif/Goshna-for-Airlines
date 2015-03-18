package com.nicktardif.seniorproject.goshnaforairlines;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private int REQUEST_CODE = 1129;
    private Button clearButton, approveButton, recordButton;
    private EditText resultsText;
    private String SHARED_PREFS = "goshnaforairlines";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = (Button) findViewById(R.id.recordButton);
        clearButton = (Button) findViewById(R.id.clearText);
        approveButton = (Button) findViewById(R.id.approveText);
        resultsText = (EditText) findViewById(R.id.speechResults);

        // Set up our button onClick listeners
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsText.setText("");
            }
        });

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Set up an approval process
            }
        });

        // Configure the Informational Bar text
        refreshStatusBarText();
    }

    protected void onResume(Bundle savedInstanceState) {
        // Configure the Informational Bar text
        refreshStatusBarText();
    }

    public void refreshStatusBarText() {
        TextView airportText = (TextView) findViewById(R.id.airport_id_text);
        TextView gateText = (TextView) findViewById(R.id.gate_id_text);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String airportString = sharedPreferences.getString("airport_string", "Airport Not Saved");
        String gateString = sharedPreferences.getString("gate_string", "Gate Not Saved");

        airportText.setText(airportString);
        gateText.setText(gateString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if(speech.size() > 0) {
                resultsText.setText(speech.get(0));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
