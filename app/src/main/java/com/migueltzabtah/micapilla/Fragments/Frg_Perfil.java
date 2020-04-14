package com.migueltzabtah.micapilla.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.migueltzabtah.micapilla.Actividades.ActPrincipal;
import com.migueltzabtah.micapilla.R;
import com.subhrajyoti.passwordview.PasswordView;

public class Frg_Perfil extends Fragment {

    private EditText email;
    private PasswordView pass;
    private EditText nombre;
    private Switch notificacion;
    private boolean SWITCH_FLAG;

    public Frg_Perfil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lytperfil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email =  view.findViewById(R.id.editEmail);
        pass = view.findViewById(R.id.editPass);
        nombre = view.findViewById(R.id.editNombre);
        notificacion = view.findViewById(R.id.swchNotificacion);

        nombre.setFocusable(false);
        pass.setFocusable(false);
        pass.useStrikeThrough(false);
        email.setFocusable(false);

        getDatos();

        notificacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences prefs =
                        getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("Notificaciones", b);
                editor.commit();
            }
        });

    }

    public void getDatos(){
        //Obteniendo dados del usuario
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);
        String emailU = prefs.getString("Usuario_mail", "Null");
        String passU = prefs.getString("Usuario_pass","Null");
        String nombreU = prefs.getString("Usuario_nombre","Null");
        SWITCH_FLAG = prefs.getBoolean("Notificaciones", false);
        email.setText(emailU);
        pass.setText(passU);
        nombre.setText(nombreU);
        notificacion.setChecked(SWITCH_FLAG);
    }

}
