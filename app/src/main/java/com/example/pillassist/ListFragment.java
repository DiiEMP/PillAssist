package com.example.pillassist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


public class ListFragment extends Fragment {
    ImageButton imageButton;
    String correoUsuario = Second.correoElect;
    String id;
    private DatabaseReference databaseReference;

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
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuarios");
        mostrarListaMedicamentos(correoUsuario);
        consultarId(correoUsuario);

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    private void mostrarListaMedicamentos(String correoUsuario) {
        databaseReference.orderByChild("email").equalTo(correoUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(getActivity(), "Correo Encontrado", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Erro Correo No encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}