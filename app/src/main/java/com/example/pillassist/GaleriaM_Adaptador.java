package com.example.pillassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GaleriaM_Adaptador extends RecyclerView.Adapter<GaleriaM_Adaptador.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre, descripcion;
        private ImageView imagenMedicamentoG;
        public ViewHolder(View itemView){
            super(itemView);
            nombre=itemView.findViewById(R.id.nombreMedicamentoG);
            descripcion=itemView.findViewById(R.id.descripcionMedicamentoG);
            imagenMedicamentoG=itemView.findViewById(R.id.imagenMedicamentoG);
        }

    }
    public List<GaleriaMedicamentos>galeriaList;
    private Context context;

    public GaleriaM_Adaptador(Context context,List<GaleriaMedicamentos> galeriaList){
        this.galeriaList = galeriaList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmedicamentos,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GaleriaM_Adaptador.ViewHolder holder, int position) {
        holder.nombre.setText(galeriaList.get(position).getNombre());
        holder.descripcion.setText(galeriaList.get(position).getDescripcion());
        Glide.with(context).load(galeriaList.get(position).getImagen()).into(holder.imagenMedicamentoG);


    }

    @Override
    public int getItemCount() {

        return galeriaList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
