package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class VerMedicamentos extends AppCompatActivity {
    TextView descripcion, tipo, cadac,precio;
    String idm,nombreM,imagenM,tipoM,cadaC,descripcionM,precioM;
    Toolbar toolbar;
    ImageView imagen;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_medicamentos);
        descripcion = findViewById(R.id.descripcionG);
        tipo = findViewById(R.id.tipoMed);
        cadac = findViewById(R.id.cadaCuantoM);
        precio = findViewById(R.id.precioM);
        imagen = findViewById(R.id.imagenM);
        toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        idm = intent.getStringExtra("id");
        nombreM = intent.getStringExtra("nombre");
        imagenM = intent.getStringExtra("imagen");
        tipoM = intent.getStringExtra("tipoM");
        cadaC = intent.getStringExtra("cadaC");
        descripcionM = intent.getStringExtra("descripcion");
        precioM = intent.getStringExtra("precio");
        descripcion.setText(descripcionM);
        tipo.setText(tipoM);
        cadac.setText(cadaC+" h");
        precio.setText(precioM);
        Glide.with(VerMedicamentos.this).load(imagenM).into(imagen);
        toolbar.setTitle(nombreM);


    }



}