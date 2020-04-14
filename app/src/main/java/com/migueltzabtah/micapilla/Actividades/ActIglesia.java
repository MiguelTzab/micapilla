package com.migueltzabtah.micapilla.Actividades;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.migueltzabtah.micapilla.Adapters.ViewPagerAdapter;
import com.migueltzabtah.micapilla.Adapters.ViewPagerAdapterIglesias;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.Config;
import com.migueltzabtah.micapilla.models.Iglesia_;

public class ActIglesia extends AppCompatActivity {

    private Iglesia_ iglesiaInfo;
    private TextView Descripción;
    private Toolbar toolbar;
    private ImageView ImagenIglesia;
    private ViewPager viewPager;
    private String URL_BASE = Config.getURLBASE();
    private SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lytiglesia);

        //Configuración general
        toolbar = findViewById(R.id.toolbar5);
        Descripción =  findViewById(R.id.txtDescripcion);
        ImagenIglesia = findViewById(R.id.imgVwIglesia);
        TabLayout tb = (TabLayout) findViewById(R.id.tabIglesia);
        tb.addTab(tb.newTab().setText("Itinerario"));
        tb.addTab(tb.newTab().setText("Eventos"));

        //Obtenemos los datos de la iglesia

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

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            //recibimos la iglesia
            this.iglesiaInfo = (Iglesia_) getIntent().getSerializableExtra("Iglesia");
            Log.i("Activity Iglesia",this.iglesiaInfo.getIglesiaNombre());
            setData();
        }

        //Configuración del ViewPager
        viewPager = findViewById(R.id.viewpIglesia);


        //set viewpager adapter
        ViewPagerAdapterIglesias pagerAdapter = new ViewPagerAdapterIglesias(getSupportFragmentManager(), iglesiaInfo.getIglesiaId());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tb));

        //change ViewPager page when tab selected
        tb.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setData(){
        Descripción.setText(iglesiaInfo.getIglesiaDescripcion());
        toolbar.setTitle(iglesiaInfo.getIglesiaNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Glide.with(this)
                .load(URL_BASE + iglesiaInfo.getIglesiaImagen())
                .asBitmap()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .placeholder(R.drawable.progress)
                .error(R.drawable.error)
                .into(ImagenIglesia);
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("Activity Iglesia","Terminada");
        finish();
        return true;
    }
    @Override
    protected void onDestroy(){
        Glide.clear(ImagenIglesia);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("Activity Iglesia","Terminada");
        finish();
    }



}
