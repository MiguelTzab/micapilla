package com.migueltzabtah.micapilla.Actividades;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.models.Ciudad;
import com.migueltzabtah.micapilla.models.Ciudade;
import com.migueltzabtah.micapilla.models.Estado;
import com.migueltzabtah.micapilla.models.Estado_;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActUbicacion extends AppCompatActivity {

    private APIService mAPIService;
    private Spinner spnCiudades;
    private Button btnUbicacion;
    private Spinner spnEstado;
    List<Ciudade> ciudadesList = new ArrayList<>();
    List<Estado_> estadosList = new ArrayList<>();
    private boolean FLAG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lytubicacion);
        spnCiudades = findViewById(R.id.spnCiudad);
        btnUbicacion = findViewById(R.id.btnUbicacion);
        spnEstado = findViewById(R.id.spnEstado);
        getEstados();

        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Mi ubicación");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Manejo del click
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FLAG){

                    Ciudade c = (Ciudade) spnCiudades.getSelectedItem();
                    Estado_ estado = (Estado_) spnEstado.getSelectedItem();

                    //Guardamos los datos Seleccionados
                    SharedPreferences prefs =
                            getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Estado", estado.getEstadoId());
                    editor.putString("Ciudad", c.getCiudadId());
                    editor.commit();
                    finish();
                }else {
                    Snackbar.make(view, "Debe seleccionar un estado y una ciudad", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        spnEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Estado_ estado = (Estado_) adapterView.getSelectedItem();
                getCiudades(estado.getEstadoId());
                FLAG = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnCiudades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FLAG = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("Activity Ubicación","Terminada;");
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("Activity Ubicación","Terminada;");
        finish();
    }

    public void getEstados(){
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getEstados().enqueue(new Callback<Estado>() {
            @Override
            public void onResponse(Call<Estado> call, Response<Estado> response) {
                if (response.body().getSuccess()){
                    estadosList.addAll(response.body().getEstados());
                    ArrayAdapter<Estado_> adapter = new ArrayAdapter<Estado_>(getApplicationContext(),R.layout.spinner_item,estadosList);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    spnEstado.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Estado> call, Throwable t) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    Snackbar.make(findViewById(android.R.id.content),"Error al obtener los datos ",Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content),"No cuenta con una conexión a internet ",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getCiudades(String id){
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getCiudades(id).enqueue(new Callback<Ciudad>() {
            @Override
            public void onResponse(Call<Ciudad> call, Response<Ciudad> response) {
                if (response.body().getSuccess()){
                    ciudadesList.addAll(response.body().getCiudades());
                    //Agregacion de datos al spinner
                    ArrayAdapter<Ciudade> adapter = new ArrayAdapter<Ciudade>(getApplicationContext(), R.layout.spinner_item, ciudadesList);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    spnCiudades.setAdapter(adapter);
                    //Para preseleccionar
                    //spnCiudades.setSelection(adapter.getPosition(myItem));//Optional to set the selected item.
                }else {
                    //Error
                }
            }

            @Override
            public void onFailure(Call<Ciudad> call, Throwable t) {
                //Implementar Error

            }
        });
    }
}
