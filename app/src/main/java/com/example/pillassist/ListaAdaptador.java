package com.example.pillassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class ListaAdaptador extends RecyclerView.Adapter<ListaAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre, cadaCuando;
        public ViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreCardRecordatorio);
            cadaCuando = itemView.findViewById(R.id.horaCardRecordatorio);

        }
    }

    public List<ListaMedicamentos> listaMedicamentos;

    public ListaAdaptador(List<ListaMedicamentos> galeriaList){
        this.listaMedicamentos = galeriaList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recordatorio,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAdaptador.ViewHolder holder, int position) {
        holder.nombre.setText(listaMedicamentos.get(position).getNombreLista());
        holder.cadaCuando.setText(listaMedicamentos.get(position).getDescripcionLista());
        //Glide.with(context).load(galeriaList.get(position).getImagen()).into(holder.imagenMedicamentoG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return listaMedicamentos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
