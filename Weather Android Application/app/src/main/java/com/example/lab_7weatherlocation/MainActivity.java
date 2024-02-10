package com.example.lab_7weatherlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    private static final DecimalFormat var_decimal_covert = new DecimalFormat("0.00"); //converting decimal format
    Button btn1;
    EditText city;
    TextView desc, temp, feel_like, max_temp, min_temp, humidity, visibility, wind_speed, wind_dir, sunrise, sunset ,Latitude,Longitude;
    String description;
    Double var_temp, var_feel_like, var_max_temp, var_min_temp, var_humidity, var_visibility, var_wind_speed, var_wind_dir;
    long var_sunrise_time, var_sunset_time;
    String cityName;

    int permisisonCode = 999999;
    double longi;
    double lati;

    Location loc=null;
    public String date_change(long t) {
        Date date = new Date(t * 1000L); //the variable t is a Unix timestamp in seconds. It is multiplied by 1000L to convert it to milliseconds, which is the unit of time used by the Date constructor.
        SimpleDateFormat jvar_decimal_covert = new SimpleDateFormat("hh:mm:ss:aaa");
        String java_date = jvar_decimal_covert.format(date);
        return (java_date);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button);
        city = (EditText) findViewById(R.id.editText_cityname);
        desc = findViewById(R.id.textView_discription);
        temp = findViewById(R.id.textView_temp);
        feel_like = findViewById(R.id.textView_feellike);
        max_temp = findViewById(R.id.textView_maxtemp);
        min_temp = findViewById(R.id.textView_mintemp);
        humidity = findViewById(R.id.textView_humidity);
        visibility = findViewById(R.id.textView_visibility);
        wind_speed = findViewById(R.id.textView_windspeed);
        wind_dir = findViewById(R.id.textView_winddir);
        sunrise = findViewById(R.id.textView_sunrise);
        sunset = findViewById(R.id.textView_sunset);
        Longitude= findViewById(R.id.textView_Latitude);
        Latitude= findViewById(R.id.textView_Longitude);


        city.setEnabled(false); //used to disable the input functionality of an Android EditText view object named city.


        int a = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int b = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        try {
            if (a != PackageManager.PERMISSION_GRANTED && b != PackageManager.PERMISSION_GRANTED) {
                String[] thepermissions = new String[1];
                thepermissions[0] = android.Manifest.permission.ACCESS_FINE_LOCATION;
                MainActivity.this.requestPermissions(thepermissions, permisisonCode); //requesting permission

            }
        }

        catch (Exception e )
        {
            e.getStackTrace();
        }

        LocationManager lm;

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);



        Location networkLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (gpsLocation != null) {

            loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Toast.makeText(MainActivity.this, "GPS availible", Toast.LENGTH_SHORT).show();



        } else if (networkLocation != null) {
            loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Toast.makeText(MainActivity.this, "Network provider avaivible", Toast.LENGTH_SHORT).show();

        }

        lati = loc.getLatitude();
        longi = loc.getLongitude();

        lm.requestLocationUpdates(loc.getProvider(), 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();


            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                lati = loc.getLatitude();
                longi = loc.getLongitude();

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {

                            char[] buffer = new char[100000];
                            URL u = null;
                            BufferedReader br;
                            int count = 0;
                            u = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lati + "&lon="+ longi+"&appid=0a709485bd666a983e6a18e309411e4c");
                            br = new BufferedReader(new InputStreamReader(u.openStream()));
                            count = br.read(buffer);
                            String s = new String(buffer, 0, count);
                            JSONObject j = new JSONObject(s);
                            cityName = j.getString("name");
                            JSONArray jsonarray = j.getJSONArray("weather");
                            JSONObject descrip = jsonarray.getJSONObject(0);
                            JSONObject main = j.getJSONObject("main");
                            JSONObject wind = j.getJSONObject("wind");
                            JSONObject sys = j.getJSONObject("sys");
                            description = descrip.getString("main");
                            var_temp = main.getDouble("temp");
                            var_feel_like = main.getDouble("feels_like");
                            var_max_temp = main.getDouble("temp_max");
                            var_min_temp = main.getDouble("temp_min");
                            var_humidity = main.getDouble("humidity");
                            var_visibility = j.getDouble("visibility");
                            var_wind_speed = wind.getDouble("speed");
                            var_wind_dir = wind.getDouble("deg");
                            var_sunrise_time = sys.getLong("sunrise");
                            var_sunset_time = sys.getLong("sunset");
                            cityName= cityName+","+ sys.getString("country");
                            var_temp = var_temp - 273.15;
                            var_feel_like = var_feel_like - 273.15;
                            var_max_temp = var_max_temp - 273.15;
                            var_min_temp = var_min_temp - 273.15;
                            var_visibility = var_visibility / 1000;
                            runOnUiThread(new Runnable() {
                                @Override

                                public void run() {

                                    Latitude.setText(""+var_decimal_covert.format(lati));
                                    Longitude.setText(""+var_decimal_covert.format(longi));
                                    desc.setText(description);
                                    temp.setText("" + var_decimal_covert.format(var_temp) + "°C");
                                    feel_like.setText("" + var_decimal_covert.format(var_feel_like) + "°C");
                                    max_temp.setText("" + var_decimal_covert.format(var_max_temp) + "°C");
                                    min_temp.setText("" + var_decimal_covert.format(var_min_temp) + "°C");
                                    humidity.setText("" + var_humidity + "%");
                                    visibility.setText(var_visibility + "km");
                                    wind_speed.setText("" + var_wind_speed + "m/s");
                                    wind_dir.setText("" + var_wind_dir);
                                    sunrise.setText("" + date_change(var_sunrise_time));
                                    sunset.setText("" + date_change(var_sunset_time));
                                    city.setText(cityName);

                                }
                            });

                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                };
                Thread t = new Thread(r);
                t.start();

            }
        });

    }
}