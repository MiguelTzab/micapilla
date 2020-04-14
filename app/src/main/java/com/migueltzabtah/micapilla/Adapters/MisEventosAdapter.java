package com.migueltzabtah.micapilla.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.migueltzabtah.micapilla.Actividades.ActEvento;
import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.Retrofit.CircleTransform;
import com.migueltzabtah.micapilla.Retrofit.Config;
import com.migueltzabtah.micapilla.models.ClsImageHelper;
import com.migueltzabtah.micapilla.models.Evento_;

import java.io.Serializable;
import java.util.List;

/**
 * Created by migueltzabtah on 08/08/18.
 */

public class MisEventosAdapter extends RecyclerView.Adapter<MisEventosAdapter.ViewHolder> {
    private List<Evento_> EventosList;
    private Context context;
    private String URL_BASE = Config.getURLBASE();

    public MisEventosAdapter(List<Evento_> eventosList, Context c) {
        EventosList = eventosList;
        this.context = c;
    }

    @Override
    public MisEventosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lytmiseventos_item, parent, false);
        MisEventosAdapter.ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MisEventosAdapter.ViewHolder holder, final int position) {
        String Nombre = EventosList.get(position).getEventoNombre();
        String Fecha = "Fecha: " + EventosList.get(position).getEventoFecha();
        String Hora = "Hora: " + EventosList.get(position).getEventoHoraInicio();
        String url_Foto = URL_BASE + EventosList.get(position).getEventoImagen();

        //Asignamos los valores
        holder.evento_nombre.setText(Nombre);
        holder.evento_fecha.setText(Fecha);
        holder.evento_hora.setText(Hora);

        //Obtenemos la imagen con Glide
        Glide.with(this.context)
                .load(url_Foto)
                .asBitmap()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .placeholder(R.drawable.progress)
                .error(R.drawable.error)
                .transform(new CircleTransform(this.context))
                .into(holder.evento_foto);

        //Se implemente el listener para los eventos
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActEvento.class);
                intent.putExtra("Evento", (Serializable) EventosList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return EventosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView evento_nombre;
        private TextView evento_fecha;
        private TextView evento_hora;
        private ImageView evento_foto;
        private CardView cv;

        public ViewHolder(View v) {
            super(v);
            evento_nombre = v.findViewById(R.id.txtEventoNombre);
            evento_fecha = v.findViewById(R.id.txtEventoFecha);
            evento_hora = v.findViewById(R.id.txtEventoHora);
            evento_foto = v.findViewById(R.id.imageView);
            cv = v.findViewById(R.id.cvEvento);
        }
    }
}
