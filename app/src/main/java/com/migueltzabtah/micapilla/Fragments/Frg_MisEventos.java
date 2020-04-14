package com.migueltzabtah.micapilla.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.migueltzabtah.micapilla.Adapters.MisEventosAdapter;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.APIService;
import com.migueltzabtah.micapilla.Retrofit.ApiUtils;
import com.migueltzabtah.micapilla.Retrofit.Config;
import com.migueltzabtah.micapilla.models.Evento_;
import com.migueltzabtah.micapilla.models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frg_MisEventos extends Fragment {

    private RecyclerView recyclerViewEventos;
    List<Evento_> listaEventos = new ArrayList<>();
    private APIService mAPIService;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private ImageView imageView;
    private TextView txtLeyenda;

    public Frg_MisEventos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lytmiseventos, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Config.isConnectedWifi(getContext())){
            getDatos();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewEventos = view.findViewById(R.id.rvEventos);
        imageView = view.findViewById(R.id.imgVwLogin);
        txtLeyenda = view.findViewById(R.id.txtMisEventos);
        imageView.setVisibility(View.VISIBLE);
        txtLeyenda.setVisibility(View.VISIBLE);
        recyclerViewEventos.setHasFixedSize(true);
        recyclerViewEventos.setLayoutManager( new LinearLayoutManager(getContext()));
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        listaEventos.clear();
                        getDatos();
                    }
                }
        );
        getDatos();
    }
    public void getDatos(){
        //Obteniendo dados del usuario
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

        String id = prefs.getString("Usuario_id", "");
        if (!id.isEmpty()){
            setLoading(true);
            listaEventos.clear();
            imageView.setVisibility(View.GONE);
            txtLeyenda.setVisibility(View.GONE);
            mAPIService = ApiUtils.getAPIService();
            mAPIService.getEventos(id).enqueue(new Callback<Usuario>() {

                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.body().getSuccess()){
                        setLoading(false);
                        listaEventos.addAll(response.body().getEventos());
                        MisEventosAdapter adapter = new MisEventosAdapter(listaEventos, getContext());
                        recyclerViewEventos.setAdapter(adapter);
                        mySwipeRefreshLayout.setRefreshing(false);
                    }else {
                        //Muestra texto que indique que no tiene eventos guardados
                        setLoading(false);
                        txtLeyenda.setText("No haz marcado ningún evento para asistir");
                        txtLeyenda.setVisibility(View.VISIBLE);
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    setLoading(false);
                    txtLeyenda.setVisibility(View.GONE);
                    mySwipeRefreshLayout.setRefreshing(false);

                    //Verificamos si cuenta con internet

                    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        Snackbar.make(getView(),"Error al obtener los datos ",Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(getView(),"No cuenta con una conexión a internet ",Snackbar.LENGTH_SHORT).show();
                    }

                }
            });
        }else{
            txtLeyenda.setText(R.string.txtIniciarSesionMisEventos);
            mySwipeRefreshLayout.setRefreshing(false);
            setLoading(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean visible){
        super.setUserVisibleHint(visible);
        if (visible && isResumed()){
            //Obteniendo dados del usuario
            SharedPreferences prefs = this.getActivity().getSharedPreferences("MiCapillaConfig", Context.MODE_PRIVATE);

            String id = prefs.getString("Usuario_id", "");

            if (id.isEmpty()){
                listaEventos.clear();
                recyclerViewEventos.removeAllViews();
                imageView.setVisibility(View.VISIBLE);
                txtLeyenda.setVisibility(View.VISIBLE);
            }
            if(Config.isConnectedWifi(getContext())){
                getDatos();
            }
        }
    }

    public void setLoading(boolean Active){
        ImageView loading;
        loading = getView().findViewById(R.id.imgVwLoading);
        if (Active){
            Glide.with(getContext()).load(R.drawable.progress).asGif().into(loading);
            loading.setVisibility(View.VISIBLE);
        }else {
            loading.setVisibility(View.GONE);
        }

    }

}
