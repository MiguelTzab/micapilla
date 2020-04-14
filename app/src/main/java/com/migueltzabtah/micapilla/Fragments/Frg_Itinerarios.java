package com.migueltzabtah.micapilla.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.migueltzabtah.micapilla.Adapters.ItinerariosAdapter;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.models.Itinerario;
import com.migueltzabtah.micapilla.models.Itinerario_;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frg_Itinerarios extends Fragment {

    private String IGLESIA_ID;
    private RecyclerView recyclerViewItinerarios;
    private APIService mAPIService;
    List<Itinerario_> listaItinerarios = new ArrayList<>();

    public Frg_Itinerarios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lytitinerarios, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewItinerarios = view.findViewById(R.id.rvItinerariosIglesia);
        recyclerViewItinerarios.setHasFixedSize(true);
        recyclerViewItinerarios.setLayoutManager( new LinearLayoutManager(getContext()));
        getDatos();
    }

    public void setIGLESIA_ID(String IGLESIA_ID) {
        this.IGLESIA_ID = IGLESIA_ID;
    }

    public void getDatos(){
        if (!this.IGLESIA_ID.isEmpty()){
            mAPIService = ApiUtils.getAPIService();
            mAPIService.getItinerarios(this.IGLESIA_ID).enqueue(new Callback<Itinerario>() {
                @Override
                public void onResponse(Call<Itinerario> call, Response<Itinerario> response) {
                    if (response.body().getSuccess()){
                        listaItinerarios.addAll(response.body().getItinerarios());
                        ItinerariosAdapter adapter = new ItinerariosAdapter(getContext(),listaItinerarios);
                        recyclerViewItinerarios.setAdapter(adapter);
                    }else {
                        //Implementar error
                    }
                }

                @Override
                public void onFailure(Call<Itinerario> call, Throwable t) {

                }
            });
        }
    }

}
