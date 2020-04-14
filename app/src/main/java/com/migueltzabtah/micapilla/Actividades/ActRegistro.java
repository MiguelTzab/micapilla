package com.migueltzabtah.micapilla.Actividades;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActRegistro extends AppCompatActivity {

    private EditText nombre;
    private EditText email;
    private EditText pass;
    private Button btnRegistrar;
    private APIService mAPIService;
    private Boolean EMAIL_VALIDADE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lytregistro);
        nombre = findViewById(R.id.editNombre);
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPass);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Registro");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //validación email

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches() && s.length() > 0) {
                    EMAIL_VALIDADE = true;
                }else {
                    email.setError("Correo Invalido");
                }

            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (verificarCampos()){
                    String nombreU = nombre.getText().toString();
                    final String emailU = email.getText().toString();
                    final String passU = pass.getText().toString();
                    mAPIService = ApiUtils.getAPIService();
                    final String[] user_id = {""};
                    mAPIService.createUser(nombreU,emailU,passU).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if (response.body().getSuccess()){
                                user_id[0] = response.body().getUsuarios().getUsuarioId();
                                Intent resultado = new Intent();
                                resultado.putExtra("email",emailU);
                                resultado.putExtra("pass",passU);
                                setResult(RESULT_OK,resultado);
                                finish();
                            }else {
                                Snackbar.make(view,response.body().getMensaje(),Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {

                        }
                    });


                }else {
                    Snackbar.make(view,"Verifica que los campos no estén vacíos y sean correctos",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean verificarCampos(){
        if (nombre.getText().toString().isEmpty() || nombre.getText().toString().length() < 3) return false;
        else if (pass.getText().toString().isEmpty() || pass.getText().toString().length() < 8) return false;
        else if(!EMAIL_VALIDADE) return false;
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("Activity Registro","Terminada;");
        setResult(RESULT_CANCELED,null);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("Activity Registro","Terminada;");
        setResult(RESULT_CANCELED,null);
        finish();
    }
}
