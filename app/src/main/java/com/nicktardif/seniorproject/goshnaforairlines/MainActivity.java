package com.nicktardif.seniorproject.goshnaforairlines;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
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

import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.IdResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.MessageResponse;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private int REQUEST_CODE = 1129;
    private Button clearButton, approveButton, recordButton;
    private EditText resultsText;
    private String SHARED_PREFS = "goshnaforairlines";

    private int airport_id;
    private int gate_id;

    private GoshnaApiService api;

    private Callback<IdResponse> idResponseCallback = new Callback<IdResponse>() {
        @Override
        public void success(IdResponse idResponse, Response response) {
            System.out.println(idResponse.toString());

            showToast("Message saved to the server!");
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("IdResponse was a failure, error: " + error.toString());
        }
    };

    private Callback<MessageResponse> getMessagesCallback = new Callback<MessageResponse>() {
        @Override
        public void success(MessageResponse messageResponse, Response response) {
            System.out.println(messageResponse.toString());
            resultsText.setText("");

            showToast("Success: Message was sent to the server!");
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("MessageResponse was a failure");

            showToast("ERROR: Message was not sent to the server");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = (Button) findViewById(R.id.recordButton);
        clearButton = (Button) findViewById(R.id.clearText);
        approveButton = (Button) findViewById(R.id.approveText);
        resultsText = (EditText) findViewById(R.id.speechResults);

        // Set up our API service with Retrofit
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://45.55.132.122:5000/goshna/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = restAdapter.create(GoshnaApiService.class);

        // Set up our button onClick listeners
        setOnClickListeners();

        // Configure the Informational Bar text
        refreshStatusBarText();

        Log.d("ticknardif", "onCreate was called");
    }

    public void onResume() {
        super.onResume();

        // Configure the Informational Bar text
        refreshStatusBarText();

        Log.d("ticknardif", "onResume was called");
    }

    public void refreshStatusBarText() {
        TextView airportText = (TextView) findViewById(R.id.airport_id_text);
        TextView gateText = (TextView) findViewById(R.id.gate_id_text);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String airportString = sharedPreferences.getString("airport_string", "Airport Not Saved");
        String gateString = sharedPreferences.getString("gate_string", "Gate Not Saved");
        airport_id = sharedPreferences.getInt("airport_id", -1);
        gate_id = sharedPreferences.getInt("gate_id", -1);

        airportText.setText(airportString);
        gateText.setText(gateString);
    }

    // This function sets up the onclick listeners for our button pushes
    private void setOnClickListeners() {

        // Set up the listener to start recording your voice
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // Set up the listener to clear the textbox
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsText.setText("");
            }
        });

        // Set up the listener to send the Message to the server
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speech = ((EditText) findViewById(R.id.speechResults)).getText().toString();

                // Get the minute of the day
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                int time = hour * 60 + minute;

                GateMessage message = new GateMessage(speech, time, gate_id, airport_id);
                api.sendMessage(message, idResponseCallback);
            }
        });
    }

    private void showToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
