package com.example.bsce19009_lab10;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.Manifest;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String getOverAllWeather, getCity; //defining variables
    Double getTemp, getFeels, getMax, getMin,getHumidity, getVisibility, getWindSpeed,
            getWindDirection;
    Long getSunrise, getSunset;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public String timeFormat(long time) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss:aaa");
        String java_date = sdf.format(date);
        return (java_date);
    }
    int permisisonCode = 0;
    Double longitude;
    Double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button weather = findViewById(R.id.button); //connecting with xml
        EditText city = findViewById(R.id.City);
        TextView Loc = findViewById(R.id.textView);
        TextView overallWeather = findViewById(R.id.textView1a);
        TextView currentTemperature = findViewById(R.id.textView2a);

        TextView feelsTemperature = findViewById(R.id.textView3a);
        TextView maxTemperature = findViewById(R.id.textView4a);
        TextView minTemperature = findViewById(R.id.textView5a);
        TextView humidity = findViewById(R.id.textView6a);
        TextView visibility = findViewById(R.id.textView7a);
        TextView windSpeed = findViewById(R.id.textView8a);
        TextView windDirection = findViewById(R.id.textView9a);
        TextView sunriseTime = findViewById(R.id.textView10a);
        TextView sunsetTime = findViewById(R.id.textView11a);

        overallWeather.setText("-");
        currentTemperature.setText("°C");
        feelsTemperature.setText("°C");
        maxTemperature.setText("°C");
        minTemperature.setText("°C");
        humidity.setText("%");
        visibility.setText("km");
        windSpeed.setText("m/s");
        windDirection.setText("-");
        sunriseTime.setText("-");
        sunsetTime.setText("-");

        int a = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int b = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if( a!= PackageManager.PERMISSION_GRANTED && b!=
                PackageManager.PERMISSION_GRANTED) //checking permissions

        {
            String[] thePermissions = new String[1];
            thePermissions[0]= android.Manifest.permission.ACCESS_FINE_LOCATION;
            this.requestPermissions(thePermissions,permisisonCode);
            return;
        }
        LocationManager lm;
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new
                LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                    @Override
                    public void onProviderDisabled(@NonNull String provider) {
                        Log.d(TAG, "Location provider " + provider + " is disabled");
                        Toast.makeText(MainActivity.this, "Enable Location",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onProviderEnabled(@NonNull String provider) {
                        Log.d(TAG, "Location provider " + provider + " is enabled");
                    }
                    @Override

                    public void onStatusChanged(String provider, int status, Bundle extras) { }
                });
        Log.d("*****","*******Current Location:"+latitude+","+longitude);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try
                        {

                            char[] buffer = new char[5000];
                            String s =

                                    "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&APPID=f5a1252332b7f3cb408eebf4036d362d"; //using weather API

                            URL u = new URL(s);
                            BufferedReader br = new BufferedReader(new

                                    InputStreamReader(u.openStream()));
                            int count = br.read(buffer);

                            String responce = new String(buffer,0,count);
                            Log.d("*******", responce);
                            JSONObject data = new JSONObject(responce);
                            JSONArray weather = data.getJSONArray("weather");
                            JSONObject weatherObj = ((JSONArray) weather).getJSONObject(0);
                            JSONObject main = data.getJSONObject("main");
                            JSONObject wind = data.getJSONObject("wind");

                            JSONObject sys = data.getJSONObject("sys");

                            getOverAllWeather = weatherObj.getString("main");
                            getTemp = main.getDouble("temp");
                            getFeels = main.getDouble("feels_like");
                            getMax = main.getDouble("temp_max");
                            getMin = main.getDouble("temp_min");
                            getHumidity = main.getDouble("humidity");
                            getVisibility = data.getDouble("visibility");
                            getWindSpeed = wind.getDouble("speed");
                            getWindDirection = wind.getDouble("deg");
                            getSunrise = sys.getLong("sunrise");
                            getSunset = sys.getLong("sunset");
                            getCity = data.getString("name");

                            getTemp-=273.15;
                            getFeels-=273.15;
                            getMax-=273.15;
                            getMin-=273.15;
                            getVisibility/=1000;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    city.setText(getCity);
                                    Loc.setText(getCity+" Weather");
                                    overallWeather.setText(getOverAllWeather);

                                    currentTemperature.setText(""+df.format(getTemp)+"°C");
                                    feelsTemperature.setText(""+df.format(getFeels)+"°C");
                                    maxTemperature.setText(""+df.format(getMax)+"°C");
                                    minTemperature.setText(""+df.format(getMin)+"°C");
                                    humidity.setText(""+getHumidity+"%");
                                    visibility.setText(getVisibility+"km");
                                    windSpeed.setText(""+getWindSpeed+"m/s");
                                    windDirection.setText(""+getWindDirection);
                                    sunriseTime.setText(timeFormat(getSunrise));
                                    sunsetTime.setText(timeFormat(getSunset));
                                }
                            });
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                Thread t = new Thread(r); //runnig a thread
                t.start();
            }
        });
    }
}