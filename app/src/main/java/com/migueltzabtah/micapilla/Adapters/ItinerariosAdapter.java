package com.migueltzabtah.micapilla.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.migueltzabtah.micapilla.R;
import com.migueltzabtah.micapilla.models.Itinerario_;

import java.util.List;

/**
 * Created by migueltzabtah on 16/08/18.
 */

public class ItinerariosAdapter extends RecyclerView.Adapter<ItinerariosAdapter.ViewHolder> {
    private Context context;
    private List<Itinerario_> listaItinerarios;

    public ItinerariosAdapter(Context context, List<Itinerario_> listaItinerarios) {
        this.context = context;
        this.listaItinerarios = listaItinerarios;
    }

    @Override
    public ItinerariosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lytitinerarios_item, parent, false);
        ItinerariosAdapter.ViewHolder viewHolder = new ItinerariosAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItinerariosAdapter.ViewHolder holder, int position) {
        String Nombre =  listaItinerarios.get(position).getItinerarioNombre();
        String Descripcion = listaItinerarios.get(position).getItinerarioDescripcion();
        String Dia = listaItinerarios.get(position).getItinerarioDia();

        //Asignación de Datos
        holder.itinerario_nombre.setText(Nombre);
        holder.itinerario_dia.setText(Dia);
        holder.itinerario_descripcion.setText(Descripcion);
    }

    @Override
    public int getItemCount() {
        return listaItinerarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itinerario_nombre;
        private TextView itinerario_descripcion;
        private TextView itinerario_dia;
        private CardView cv;

        public ViewHolder(View v) {
            super(v);
            itinerario_nombre = v.findViewById(R.id.txtItinerarioNombre);
            itinerario_descripcion = v.findViewById(R.id.txtItinerarioDescripcion);
            itinerario_dia = v.findViewById(R.id.txtItinerarioDia);
            cv = v.findViewById(R.id.cvItinerario);
        }
    }
}
