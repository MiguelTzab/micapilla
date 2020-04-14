package com.migueltzabtah.micapilla.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.migueltzabtah.micapilla.Actividades.ActIglesia;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.Config;
import com.migueltzabtah.micapilla.models.Iglesia_;

import java.io.Serializable;
import java.util.List;

/**
 * Created by migueltzabtah on 07/08/18.
 */

public class IglesiasAdapter extends RecyclerView.Adapter<IglesiasAdapter.ViewHolder>{

    private List<Iglesia_> IglesiaList;
    private Context context;
    private String URL_BASE = Config.getURLBASE();

    public IglesiasAdapter(List<Iglesia_> iglesias, Context c) {
        this.IglesiaList = iglesias;
        this.context = c;
    }

    @Override
    public IglesiasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lytiglesias_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IglesiasAdapter.ViewHolder holder, final int position) {
        String iglesia_nombre = IglesiaList.get(position).getIglesiaNombre();
        String url_Foto = this.URL_BASE + IglesiaList.get(position).getIglesiaImagen();
        holder.iglesia_nombre.setText(iglesia_nombre);

        //Obtenemos la imagen con Glide
        Glide.with(this.context)
                .load(url_Foto)
                .asBitmap()
                .placeholder(R.drawable.progress)
                .error(R.drawable.error)
                .into(holder.iglesia_foto);

        //Se implemente el listener para los eventos
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActIglesia.class);
                intent.putExtra("Iglesia", IglesiaList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return IglesiaList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView iglesia_nombre;
        private ImageView iglesia_foto;
        private CardView cv;

        public ViewHolder(View v) {
            super(v);
            iglesia_nombre = v.findViewById(R.id.txtIglesiaNombre);
            iglesia_foto = v.findViewById(R.id.imgIglesiaFoto);
            cv = v.findViewById(R.id.cvIglesia);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
