package com.migueltzabtah.micapilla.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.migueltzabtah.micapilla.Adapters.MisEventosAdapter;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.models.Evento;
import com.migueltzabtah.micapilla.models.Evento_;
import com.migueltzabtah.micapilla.models.Iglesia;
import com.migueltzabtah.micapilla.models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frg_Eventos extends Fragment {

    private RecyclerView recyclerViewEventos;
    List<Evento_> listaEventos = new ArrayList<>();
    private APIService mAPIService;
    private String IGLESIA_ID;

    public Frg_Eventos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lyteventos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewEventos = view.findViewById(R.id.rvEventosIglesia);
        recyclerViewEventos.setHasFixedSize(true);
        recyclerViewEventos.setLayoutManager( new LinearLayoutManager(getContext()));
        getDatos();
    }

    public void getDatos(){

        String id = this.IGLESIA_ID;
        //Obteniendo dados del usuario
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

        String id_usuario = prefs.getString("Usuario_id", "");

        if (!id.isEmpty()){
            mAPIService = ApiUtils.getAPIService();
            mAPIService.getEventosIglesia(id,id_usuario).enqueue(new Callback<Evento>() {
                @Override
                public void onResponse(Call<Evento> call, Response<Evento> response) {
                    if (response.body().getSuccess()){
                        listaEventos.addAll(response.body().getEventos());
                        MisEventosAdapter adapter = new MisEventosAdapter(listaEventos, getContext());
                        recyclerViewEventos.setAdapter(adapter);
                    }else {
                        //Implementar error
                    }
                }

                @Override
                public void onFailure(Call<Evento> call, Throwable t) {

                }
            });
        }
    }

    public void setIGLESIA_ID(String IGLESIA_ID) {
        this.IGLESIA_ID = IGLESIA_ID;
    }
}
