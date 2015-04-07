package com.nicktardif.seniorproject.goshnaforairlines;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.AirportResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.GateResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SettingsActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    private Button saveButton;
    private String SHARED_PREFS = "goshnaforairlines";
    SharedPreferences sharedPreferences;

    private List<Airport> airportList;
    private List<Gate> gateList;

    private ArrayAdapter<String> airportAdapter;
    private ArrayAdapter<String> gateAdapter;

    private Spinner airportSpinner;
    private Spinner gateSpinner;

    private GoshnaApiService api;

    private Callback<AirportResponse> getAirportsCallback = new Callback<AirportResponse>() {
        @Override
        public void success(AirportResponse airportReponse, Response response) {

            for(Airport airport :airportReponse.airports) {
                airportList.add(airport);
                airportAdapter.add(airport.airport_short);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("AirportResponse was a failure, error: " + error.toString());
        }
    };

    private Callback<GateResponse> getGatesCallback = new Callback<GateResponse>() {
        @Override
        public void success(GateResponse gateReponse, Response response) {
            gateList.clear();
            gateAdapter.clear();

            for(Gate gate :gateReponse.gates) {
                gateList.add(gate);
                gateAdapter.add(gate.name);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("GateResponse was a failure, error: " + error.toString());
        }
    };

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

                // Get the names and IDs from the selected Spinner items
                int airport_id = airportList.get(airportSpinner.getSelectedItemPosition()).id;
                String airport_short = airportList.get(airportSpinner.getSelectedItemPosition()).airport_short;
                int gate_id = gateList.get(gateSpinner.getSelectedItemPosition()).id;
                String gate_name = gateList.get(gateSpinner.getSelectedItemPosition()).name;

                // Save them in the shared preferences
                sharedPreferences.edit().putString("airport_string", airport_short).apply();
                sharedPreferences.edit().putString("gate_string", gate_name).apply();
                sharedPreferences.edit().putInt("airport_id", airport_id).apply();
                sharedPreferences.edit().putInt("gate_id", gate_id).apply();

                // Make a toast to let the user know the settings were saved
                Toast.makeText(getApplicationContext(), "Settings Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        airportAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        airportList = new ArrayList<Airport>();

        airportSpinner = (Spinner) findViewById(R.id.airport_settings_field);
        airportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        airportSpinner.setAdapter(airportAdapter);
        airportSpinner.setOnItemSelectedListener(this);

        gateAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        gateList = new ArrayList<Gate>();

        gateSpinner = (Spinner) findViewById(R.id.gate_settings_field);
        gateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gateSpinner.setAdapter(gateAdapter);

        // Set up our API service with Retrofit
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://45.55.132.122:5000/goshna/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = restAdapter.create(GoshnaApiService.class);

        api.getAllAirports(getAirportsCallback);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Airport selected = airportList.get(pos);
        System.out.println(selected.airport_full);

        api.getAirportGates(selected, getGatesCallback);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
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
