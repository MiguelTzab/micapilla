package com.migueltzabtah.micapilla.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.migueltzabtah.micapilla.Actividades.ActRecuperar;
import com.migueltzabtah.micapilla.Actividades.ActRegistro;
import com.migueltzabtah.micapilla.Actividades.ActUbicacion;
import com.migueltzabtah.micapilla.Adapters.ViewPagerAdapter;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.models.Usuario;
import com.subhrajyoti.passwordview.PasswordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frg_Login extends Fragment {

    private TextView recuperar;
    private Button login;
    private Button register;
    private EditText email;
    private PasswordView pass;
    private APIService mAPIService;
    private static final int REGISTRO = 1;

    public Frg_Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lytlogin, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recuperar = view.findViewById(R.id.txtLabel3);
        login = view.findViewById(R.id.btnIniciarSesion);
        email = view.findViewById(R.id.editCorreo);
        pass =  view.findViewById(R.id.editPass);
        register = view.findViewById(R.id.btnRegistrar);

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActRecuperar.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActRegistro.class);
                startActivityForResult(intent,REGISTRO);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String emailU = email.getText().toString();
                final String passU = pass.getText().toString();
                if (!emailU.isEmpty() && !passU.isEmpty()){

                    mAPIService = ApiUtils.getAPIService();
                    mAPIService.login(emailU,passU).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if (response.body().getSuccess()){
                                //Guardamos los datos Seleccionados
                                SharedPreferences prefs =
                                        getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("Usuario_id", response.body().getUsuarios().getUsuarioId());
                                editor.putString("Usuario_nombre", response.body().getUsuarios().getUsuarioNombre());
                                editor.putString("Usuario_mail", emailU);
                                editor.putString("Usuario_pass", passU);
                                editor.commit();

                                //Creamos una nueva instancia de la vista
                                Frg_Perfil perfil = new Frg_Perfil();
                                //Cambiamos el fragment
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.root_frame, perfil)
                                        .disallowAddToBackStack()
                                        .commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Snackbar.make(getView(),"Error en el sistema, verifique sus datos e inténtelo mas tarde",Snackbar.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Snackbar.make(getView(),"Los campos no pueden estar vacios",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Verificamos si el usuario logro registrarse, de ser a si se auto logear
        switch(requestCode) {
            case REGISTRO:
                if (resultCode == Activity.RESULT_OK) {
                    String email = data.getStringExtra("email");
                    String pass = data.getStringExtra("pass");
                    autoIniciar(email, pass);
                }
                break;
        }
    }

    public void autoIniciar(final String email, final String pass){
        mAPIService = ApiUtils.getAPIService();
        mAPIService.login(email,pass).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.body().getSuccess()){
                    //Guardamos los datos Seleccionados
                    SharedPreferences prefs =
                            getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Usuario_id", response.body().getUsuarios().getUsuarioId());
                    editor.putString("Usuario_nombre", response.body().getUsuarios().getUsuarioNombre());
                    editor.putString("Usuario_mail", email);
                    editor.putString("Usuario_pass", pass);
                    editor.commit();

                    //Creamos una nueva instancia de la vista
                    Frg_Perfil perfil = new Frg_Perfil();
                    //Cambiamos el fragment
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.root_frame, perfil)
                            .disallowAddToBackStack()
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Frg_Login-auto iniciar", "Mensaje: " + t.toString(), t);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_principal,menu);
    }
}
