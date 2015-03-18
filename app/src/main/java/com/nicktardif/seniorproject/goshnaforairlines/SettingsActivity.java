package com.nicktardif.seniorproject.goshnaforairlines;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SettingsActivity extends ActionBarActivity {
    private Button saveButton;
    private String SHARED_PREFS = "goshnaforairlines";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        saveButton = (Button) findViewById(R.id.save_settings_button);

        sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // Set up our button onClick listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read in the submitted values to strings
                String gateString = ((EditText)findViewById(R.id.gate_settings_field)).getText().toString();
                String airportString = ((EditText)findViewById(R.id.airport_settings_field)).getText().toString();

                Log.d("ticknardif", "gate: " + gateString);
                Log.d("ticknardif", "airport: " + airportString);

                // Save them in the shared preferences
                sharedPreferences.edit().putString("airport_string", airportString).apply();
                sharedPreferences.edit().putString("gate_string", gateString).apply();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
