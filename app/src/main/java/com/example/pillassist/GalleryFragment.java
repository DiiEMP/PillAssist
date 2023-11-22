package com.example.pillassist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView descripcion, tipo, cadac,precio;

    ImageView imagen;

    List<GaleriaMedicamentos> galeria = new ArrayList<>();
    private GaleriaM_Adaptador galeriaMAdaptador;
    public GalleryFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = view.findViewById(R.id.recyclerGalerias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        inicializarfirebase();
        listarMedicamentos(getContext());


        return view;
    }

    private void listarMedicamentos(Context context) {
        databaseReference.child("GaleriaMedicamentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    GaleriaMedicamentos g = objSnapshot.getValue(GaleriaMedicamentos.class);
                    galeria.add(new GaleriaMedicamentos(g.getId(),g.getNombre(),g.getImagen(),g.getTipoMedicamento(),g.getCadaCuanto(),g.getDescripcion(),g.getPrecio()));
                    galeriaMAdaptador = new GaleriaM_Adaptador(context,galeria);
                    recyclerView.setAdapter(galeriaMAdaptador);
                    galeriaMAdaptador.setOnItemClickListener(new GaleriaM_Adaptador.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            GaleriaMedicamentos galeriaM=galeria.get(position);
                            String idM=galeriaM.getId();
                            String nombre=galeriaM.getNombre();
                            String imagen=galeriaM.getImagen();
                            String tipoM=galeriaM.getTipoMedicamento();
                            String cadaC=galeriaM.getCadaCuanto();
                            String descripcion=galeriaM.getDescripcion();
                            String precio=galeriaM.getPrecio();

                            Intent intent=new Intent(getActivity(),VerMedicamentos.class);
                            intent.putExtra("id",idM);
                            intent.putExtra("nombre",nombre);
                            intent.putExtra("imagen",imagen);
                            intent.putExtra("tipoM",tipoM);
                            intent.putExtra("cadaC",cadaC);
                            intent.putExtra("descripcion",descripcion);
                            intent.putExtra("precio",precio);
                            startActivity(intent);


                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void inicializarfirebase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}