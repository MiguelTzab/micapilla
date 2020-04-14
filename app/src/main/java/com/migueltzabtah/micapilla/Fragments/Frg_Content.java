package com.migueltzabtah.micapilla.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.models.Usuario;
import com.subhrajyoti.passwordview.PasswordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frg_Content extends Fragment {

    private Menu menu;
    private APIService mAPIService;

    public Frg_Content() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        verificarEstado();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lytcontent, container, false);
    }



    public void verificarEstado(){
        //Obteniendo dados del usuario
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

        String usuario = prefs.getString("Usuario_id","Null");
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();

        if (usuario.contains("Null")){
            transaction.replace(R.id.root_frame, new Frg_Login());

        }else {
            transaction.replace(R.id.root_frame, new Frg_Perfil());
            modeEdicion(false);
        }
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_perfil,menu);
        modeEdicion(false);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrarSesion){
            cerrarSesion();
        }
        if (id == R.id.editarPerfil){
            modeEdicion(true);
        }
        if (id == R.id.guardarPerfil){
            guardarPerfil();
        }
        if (id == R.id.cancelarEdicion){
            modeEdicion(false);
        }
        return super.onOptionsItemSelected(item);
    }

    public void cerrarSesion(){
        SharedPreferences prefs = getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);
        prefs.edit().remove("Usuario_id").commit();
        prefs.edit().remove("Usuario_nombre").commit();
        prefs.edit().remove("Usuario_mail").commit();
        prefs.edit().remove("Usuario_pass").commit();


        //Cambiamos el fragment
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.root_frame, new Frg_Login());
        transaction.disallowAddToBackStack();
        transaction.commit();

    }

    public void modeEdicion(boolean b){
        try{
            //Ponemos los editext en modo edición
            EditText n = getActivity().findViewById(R.id.editNombre);
            EditText email =  getActivity().findViewById(R.id.editEmail);
            PasswordView pass = getActivity().findViewById(R.id.editPass);
            n.setFocusable(b);
            n.setFocusableInTouchMode(b);
            email.setFocusable(b);
            email.setFocusableInTouchMode(b);
            pass.setFocusable(b);
            pass.setFocusableInTouchMode(b);
            pass.useStrikeThrough(!b);
            if (b){
                n.requestFocus();
            }

            //Ocultamos la opcion editar
            menu.findItem(R.id.editarPerfil).setVisible(!b);
            menu.findItem(R.id.cancelarEdicion).setVisible(b);
            menu.findItem(R.id.guardarPerfil).setVisible(b);

        }catch (Exception e){
            Log.e("Content","Error ->" + e.toString());
            Log.e("Content","Error : " + e.toString());
        }
    }


    public void guardarPerfil(){
        if (verificar()){
            EditText n = getActivity().findViewById(R.id.editNombre);
            EditText email =  getActivity().findViewById(R.id.editEmail);
            PasswordView pass = getActivity().findViewById(R.id.editPass);
            String nombre = n.getText().toString();
            String correo = email.getText().toString();
            String password = pass.getText().toString();

            //Obtenemos el id del usuario
            SharedPreferences prefs = this.getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

            String id = prefs.getString("Usuario_id", "Null");

            if (!id.equalsIgnoreCase("Null")){
                mAPIService = ApiUtils.getAPIService();
                mAPIService.updatePerfil(id,nombre,correo,password).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.body().getSuccess()){
                            modeEdicion(false);
                            Snackbar.make(getView(),"Perfil actualizado correctamente",Snackbar.LENGTH_SHORT).show();
                        }else {
                            Snackbar.make(getView(),"Lo sentimos, su perfil no fue actualizado",Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Snackbar.make(getView(),"Error, verifique que cuenta con señal de internet",Snackbar.LENGTH_SHORT).show();
                        Log.e("Actualizar Perfil","Error: " + t.toString());
                    }
                });
            }
        }else {
            Log.e("Actualizar Perfil","Error: la verificación dio false");
        }
    }

    public boolean verificar(){
        EditText n = getActivity().findViewById(R.id.editNombre);
        EditText email =  getActivity().findViewById(R.id.editEmail);
        PasswordView pass = getActivity().findViewById(R.id.editPass);

        if (n.getText().toString().isEmpty() || n.getText().toString().length() < 6) return false;
        if (pass.getText().toString().isEmpty() || pass.getText().length() < 8) return false;
        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && email.getText().toString().length() > 0) return true;
        return false;

    }
    @Override
    public void setUserVisibleHint(boolean visible){
        super.setUserVisibleHint(visible);
        if (!visible && isResumed()){
            modeEdicion(false);
        }
    }
}
