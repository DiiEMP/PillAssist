package com.example.pillassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditarMed extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<GaleriaMedicamentos> galeria = new ArrayList<>();
    private GaleriaM_Adaptador galeriaMAdaptador;
    //editarMed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_med);
        recyclerView = findViewById(R.id.galeriaMR);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inicializarFirebase();
        listarDatos(this);

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void listarDatos(Context context){
        databaseReference.child("GaleriaMedicamentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    GaleriaMedicamentos galeriaMedicamentos = objSnapshot.getValue(GaleriaMedicamentos.class);
                    galeria.add(new GaleriaMedicamentos(galeriaMedicamentos.getId(),galeriaMedicamentos.getNombre(),galeriaMedicamentos.getImagen(),galeriaMedicamentos.getTipoMedicamento(),galeriaMedicamentos.getCadaCuanto(),galeriaMedicamentos.getDescripcion(),galeriaMedicamentos.getPrecio()));
                    galeriaMAdaptador = new GaleriaM_Adaptador(context,galeria);
                    recyclerView.setAdapter(galeriaMAdaptador);

                    galeriaMAdaptador.setOnItemClickListener(new GaleriaM_Adaptador.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            GaleriaMedicamentos galeriaM = galeria.get(position);
                            String idM = galeriaM.getId();
                            String nombre = galeriaM.getNombre();
                            String imagen = galeriaM.getImagen();
                            String tipoM = galeriaM.getTipoMedicamento();
                            String cadaC = galeriaM.getCadaCuanto();
                            String descripcion = galeriaM.getDescripcion();
                            String precio = galeriaM.getPrecio();

                            Intent intent = new Intent(EditarMed.this, Vermedicamento.class);
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

            }
        });
    }
}