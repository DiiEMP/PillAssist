package com.example.pillassist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {
    ImageButton imageButton;
    String correoUsuario = Second.correoElect;
    String id;
    RecyclerView recyclerViewLista;
    private DatabaseReference databaseReference, databaseReference2;
    List<ListaMedicamentos> lista = new ArrayList<>();
    private ListaAdaptador listaAdaptador;

    public ListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        imageButton = view.findViewById(R.id.agregarAlarma);
        recyclerViewLista = view.findViewById(R.id.recyclerLista);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuarios");
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase2.getReference("ListaMedicamentos");

        consultarId(correoUsuario);
        recyclerViewLista.setLayoutManager(new LinearLayoutManager(getActivity()));
       // mostrarListaMedicamentos();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), CrearAlarma.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });





        return view;
    }

    private void consultarId(String correoUsuario) {

        databaseReference.orderByChild("email").equalTo(correoUsuario).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        id = dataSnapshot.child("idUser").getValue(String.class);
                    }
                    mostrarListaMedicamentos();
                } else {
                    Toast.makeText(getActivity(), "Usuario No encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error al cargar", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void mostrarListaMedicamentos() {
        String idListM;
        databaseReference2.orderByChild("idUsuario").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot objSnapshot: snapshot.getChildren()){
                        ListaMedicamentos list = objSnapshot.getValue(ListaMedicamentos.class);
                        Log.d("DATOS:", list.getIdUsuario());
                        lista.add(new ListaMedicamentos(list.getIdLista(),list.getNombreLista(),list.getCadaCuandoLista(),list.getIdUsuario(),list.getDescripcionLista(),list.getDosisLista(), list.getRequestCode()));

                    }
                listaAdaptador = new ListaAdaptador(lista);
                recyclerViewLista.setAdapter(listaAdaptador);
                listaAdaptador.setOnItemClickListener(new ListaAdaptador.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(), ModificarListMedicamento.class);
                        ListaMedicamentos listaMedicamentos = lista.get(position);
                        intent.putExtra("idList",listaMedicamentos.getIdLista());
                        intent.putExtra("nombre",listaMedicamentos.getNombreLista());
                        intent.putExtra("cadaCuando",listaMedicamentos.getCadaCuandoLista());
                        intent.putExtra("idUsuario",listaMedicamentos.getIdUsuario());
                        intent.putExtra("descripcion",listaMedicamentos.getDescripcionLista());
                        intent.putExtra("dosis",listaMedicamentos.getDosisLista());
                        intent.putExtra("requestCode",listaMedicamentos.getRequestCode());
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}