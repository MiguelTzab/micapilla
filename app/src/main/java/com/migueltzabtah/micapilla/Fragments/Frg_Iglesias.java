package com.migueltzabtah.micapilla.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.migueltzabtah.micapilla.Actividades.ActUbicacion;
import com.migueltzabtah.micapilla.Adapters.IglesiasAdapter;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.Retrofit.Config;
import com.migueltzabtah.micapilla.models.Iglesia;
import com.migueltzabtah.micapilla.models.Iglesia_;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frg_Iglesias extends Fragment {

    private RecyclerView recyclerViewIglesias;
    List<Iglesia_> listaIglesias = new ArrayList<Iglesia_>();
    private Button ubicacion;
    private APIService mAPIService;
    private static Frg_Iglesias INSTANCIA = null;
    private ImageView loading;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    public Frg_Iglesias() {
        // Required empty public constructor
    }

    public static Frg_Iglesias getInstancia(){
        if(INSTANCIA == null )
            INSTANCIA = new Frg_Iglesias();

        return INSTANCIA;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lytiglesias, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewIglesias = view.findViewById(R.id.rvIglesias);

        loading = view.findViewById(R.id.imgVwLoading);

        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        recyclerViewIglesias.setLayoutManager( new GridLayoutManager(getContext(), 2));
        getDatos();

        ubicacion = view.findViewById(R.id.btnUbicacionChange);
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActUbicacion.class);
                startActivity(intent);
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        listaIglesias.clear();
                        getDatos();
                    }
                }
        );

    }

    @Override
    public void onResume() {
        super.onResume();
        if(Config.isConnectedWifi(getContext())){
            getDatos();
        }
    }

    public void getDatos(){
        if (listaIglesias.isEmpty()){
            setLoading(true);
            //Obteniendo dados del usuario
            SharedPreferences prefs = this.getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

            String estado = prefs.getString("Estado", "Null");
            String ciudad = prefs.getString("Ciudad","0");

            mAPIService = ApiUtils.getAPIService();
            mAPIService.getIglesias(ciudad,estado).enqueue(new Callback<Iglesia>() {

                @Override
                public void onResponse(Call<Iglesia> call, Response<Iglesia> response) {
                    if (response.body().getSuccess()){
                        setLoading(false);
                        listaIglesias.addAll(response.body().getIglesias());
                        IglesiasAdapter adapter = new IglesiasAdapter(listaIglesias, getContext());
                        recyclerViewIglesias.setAdapter(adapter);
                        mySwipeRefreshLayout.setRefreshing(false);
                    }else {
                        setLoading(false);
                        //Implementar error
                        Snackbar.make(getView(),"Error al obtener los datos, posiblemente su ciudad no cuente con capillas activas ",Snackbar.LENGTH_SHORT).show();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<Iglesia> call, Throwable t) {
                    //Implementar Error
                    setLoading(false);
                    mySwipeRefreshLayout.setRefreshing(false);
                    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        Snackbar.make(getView(),"Error al obtener los datos, intentelo mas tarde",Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(getView(),"No cuenta con una conexión a internet ",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            IglesiasAdapter adapter = new IglesiasAdapter(listaIglesias, getContext());
            recyclerViewIglesias.setAdapter(adapter);
        }
    }
    public void setLoading(boolean Active){
        if (Active){
            Glide.with(getContext()).load(R.drawable.progress).asGif().into(loading);
            loading.setVisibility(View.VISIBLE);
        }else {
            loading.setVisibility(View.GONE);
        }

    }

    @Override
    public void setUserVisibleHint(boolean visible){
        super.setUserVisibleHint(visible);
        if (visible && isResumed()){
            if (listaIglesias.isEmpty()){
                if(Config.isConnectedWifi(getContext())){
                    getDatos();
                }
            }
        }
    }
}
