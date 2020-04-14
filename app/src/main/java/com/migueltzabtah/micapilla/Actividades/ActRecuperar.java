package com.migueltzabtah.micapilla.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.migueltzabtah.micapilla.R;

public class ActRecuperar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lytrecuperar);


        Toolbar toolbar = findViewById(R.id.toolbar6);
        toolbar.setTitle("Recuperar mi cuenta");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("Activity Recuperar","Terminada");
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("Activity Recuperar","Terminada");
        finish();
    }
}
