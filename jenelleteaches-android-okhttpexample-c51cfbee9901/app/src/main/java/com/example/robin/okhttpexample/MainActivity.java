package com.example.robin.okhttpexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    // used for Log.d statements
    // -----------
    final static String TAG = "YOUR_NAME_HERE";


    // outlets
    // ---------
    TextView tv;


    // 1. OKHttp variable
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Setup the outlet
        tv = (TextView) findViewById(R.id.textview1);
    }
    public void getJSONPressed(View view) {


        // 2. Setup the URL
        String URL = "https://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400";
        //String URL = "https://dog.ceo/api/breeds/image/random";
        Request request = new Request.Builder()
                .url(URL)
                .build();


        // 3. Get the data from the url
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "an error occured!");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    // 4. Get the data from the API and convert it to a JSON dictionary
                    String dataFromAPI = responseBody.string();
                    JSONObject obj = new JSONObject(dataFromAPI);

                    // 5. Do your parsing here
                    // -- This example parses out the status, sunrise, and sunset time out of the API

                    // get status
                    final String status = obj.getString("status");

                    // get sunrise / sunset.
                    // Sunrise/sunset is inside a dictionary!
                    JSONObject r = obj.getJSONObject("results");
                    final String sunrise = r.getString("sunrise");
                    final String sunset = r.getString("sunset");

                    // output this status, sunrise and sunset time to the Terminal
                    Log.d(TAG, "Sunrise: " + sunrise);
                    Log.d(TAG, "Sunset: " + sunset);
                    Log.d(TAG, "Status: " + status);
                    Log.d(TAG, "--------");

                    // output this status, sunrise and sunset time to the user interface
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String abc = "Sunrise: " + sunrise + "\n"
                                    + "Sunset: " + sunset + "\n"
                                    + "Status: " + status + "\n";


                            tv.setText(abc);
                        }
                    });


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
