package com.migueltzabtah.micapilla.Actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.Retrofit.Config;
import com.migueltzabtah.micapilla.models.Evento;
import com.migueltzabtah.micapilla.models.Evento_;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActEvento extends AppCompatActivity {

    private ImageView imagen;
    private TextView Fecha;
    private TextView Hora;
    private TextView Descripción;
    private Toolbar toolbar;
    private Evento_ eventoInfo;
    private Button btnAsistir;
    private APIService mAPIService;
    private String URL_BASE = Config.getURLBASE();
    private String USER_ID;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private boolean SWITCH_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lytevento);

        //Configuración general
        toolbar = findViewById(R.id.toolbar7);

        //Refreshing
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        setData();
                    }
                }
        );


        imagen = findViewById(R.id.imgVwEvento);
        Fecha = findViewById(R.id.txtFecha);
        Hora = findViewById(R.id.txtHora);
        Descripción = findViewById(R.id.txtDescripcion);
        btnAsistir = findViewById(R.id.btnAsistir);

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            //obtenemos el evento
            this.eventoInfo = (Evento_) getIntent().getSerializableExtra("Evento");
            Log.i("Activity Evento",this.eventoInfo.getEventoNombre());
            setData();
        }

        btnAsistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(btnAsistir.getText().toString().equalsIgnoreCase("Asistir")){
                    if (USER_ID != "Null"){
                        mAPIService = ApiUtils.getAPIService();
                        mAPIService.asistirEvento(eventoInfo.getEventoId(),USER_ID).enqueue(new Callback<Evento>() {
                            @Override
                            public void onResponse(Call<Evento> call, Response<Evento> response) {
                                if (response.body().getSuccess()){
                                    btnAsistir.setText("Marcado como Asistido");
                                    createEvent();
                                }
                            }

                            @Override
                            public void onFailure(Call<Evento> call, Throwable t) {

                            }
                        });

                    }
                }else {
                    if (USER_ID !="Null"){
                        mAPIService = ApiUtils.getAPIService();
                        mAPIService.eliminarAsistencia(USER_ID,eventoInfo.getEventoId()).enqueue(new Callback<Evento>() {
                            @Override
                            public void onResponse(Call<Evento> call, Response<Evento> response) {
                                if (response.body().getSuccess()){
                                    btnAsistir.setText("Asistir");
                                }
                            }

                            @Override
                            public void onFailure(Call<Evento> call, Throwable t) {
                                Snackbar.make(view,"Error al intentar ejecutar la acción",Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

    }

    private void setData(){
        Descripción.setText(eventoInfo.getEventoDescripcion());
        Fecha.setText(eventoInfo.getEventoFecha());
        Hora.setText(eventoInfo.getEventoHoraInicio());

        //Obteniendo dados del usuario
        SharedPreferences prefs = getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

        this.USER_ID = prefs.getString("Usuario_id", "Null");

        if (eventoInfo.getMarked().equalsIgnoreCase("1")){
            btnAsistir.setText("Cancelar Asistencia");
        }
        if (this.USER_ID.equalsIgnoreCase("Null")){
            btnAsistir.setVisibility(View.INVISIBLE);
        }

        toolbar.setTitle(eventoInfo.getEventoNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Glide.with(this)
                .load(URL_BASE + eventoInfo.getEventoImagen())
                .asBitmap()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .placeholder(R.drawable.progress)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imagen);
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("Activity Evento","Terminada");
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("Activity Evento","Terminada");
        finish();
    }

    public boolean createEvent(){
        //Verificamos si las notificaciones estan activadas
        SharedPreferences prefs = this.getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);
        SWITCH_FLAG = prefs.getBoolean("Notificaciones", false);
        if (SWITCH_FLAG){
            try {
                Calendar beginTime = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                beginTime.setTime(sdf.parse(this.eventoInfo.getEventoFecha()));
                /*
                Calendar endTime = Calendar.getInstance();
                endTime.set(2018, 10, 19, 8, 30);
                */
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        //.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, this.eventoInfo.getEventoNombre())
                        .putExtra(CalendarContract.Events.DESCRIPTION, this.eventoInfo.getEventoDescripcion())
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivity(intent);
            }catch (ParseException e) {
                Log.e("Act Evento", e.getMessage());
            }
        }
        return SWITCH_FLAG;
    }
}
