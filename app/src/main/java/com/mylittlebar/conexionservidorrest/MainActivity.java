package com.mylittlebar.conexionservidorrest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create(); 

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.132:8080/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                MyApiEndpointInterface gerritAPI = retrofit.create(MyApiEndpointInterface.class);
                Call<List<Ciudad>> call = gerritAPI.getCiudades();
                call.enqueue(new Callback<List<Ciudad>>() {
                    @Override
                    public void onResponse(Call<List<Ciudad>> call, Response<List<Ciudad>> response) {
                        /*final TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(response.body().toString());*/
                        final TextView textView = (TextView) findViewById(R.id.textView);
                        if(response.isSuccessful()) {
                            List<Ciudad> ciudades = response.body();
                            System.out.println("RESPONSE 1"+response.body());
                            for (Ciudad temp : ciudades) {
                                System.out.println(temp.getName());
                            }
                            String json = new Gson().toJson(ciudades);
                            System.out.println("RESULTADO CIUDADES: "+json);
                            textView.setText(json);

                            //changesList.forEach(change -> System.out.println(change.subject));
                        } else {
                            System.out.println("RESPONSE 2");
                            System.out.println(response.errorBody());
                        }

                    }
                    @Override
                    public void onFailure(Call<List<Ciudad>> call, Throwable t) {
                        final TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText("Something went wrong: " + t.getMessage());
                    }
                });
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
