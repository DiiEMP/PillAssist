package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrearAlarma extends AppCompatActivity {
String id ;
EditText nombreMedicamento, horaRecordatorio,dosisRecordatorio,descripcionRecordatorio;
Button crear;
String nombre, horaR,dosisR,descripcionR;
        //Consulta de Id
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alarma);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Toast.makeText(this, " "+id, Toast.LENGTH_SHORT).show();

        nombreMedicamento = findViewById(R.id.nombreMedicamento);
        horaRecordatorio = findViewById(R.id.horaRecordatorio);
        dosisRecordatorio = findViewById(R.id.dosisRecordatorio);
        descripcionRecordatorio = findViewById(R.id.descripcionRecordatorio);
        crear = findViewById(R.id.crearRecordatorio);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = nombreMedicamento.getText().toString();
                horaR = horaRecordatorio.getText().toString();
                dosisR = dosisRecordatorio.getText().toString();
                descripcionR = descripcionRecordatorio.getText().toString();

                crearReecordatorio(nombre, horaR,dosisR,descripcionR);
            }
        });
    }

    private void crearReecordatorio(String nombre, String horaR, String dosisR, String descripcionR) {




    }
}