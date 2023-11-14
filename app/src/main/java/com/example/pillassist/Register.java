package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Register extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText nombre, email, contrasenia, fechaNacimiento;
    Button regis, iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iniciarConexion();

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.correo);
        contrasenia = findViewById(R.id.contrasena);
        fechaNacimiento = findViewById(R.id.fechaNac);

        regis = findViewById(R.id.Registrar);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUser(nombre.getText().toString(), email.getText().toString(), contrasenia.getText().toString(), fechaNacimiento.getText().toString());
                limpiarDatos();
            }
        });

        iniciarSesion = findViewById(R.id.RegresarInicioSesion);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });


    }


    private void iniciarConexion() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void registrarUser(String nombreU, String emailU, String contraseniaU, String fechaNacU) {
        RegisterUser regUsr = new RegisterUser();
        regUsr.setIdUser(UUID.randomUUID().toString());
        regUsr.setName(nombreU);
        regUsr.setEmail(emailU);
        regUsr.setContrasenia(contraseniaU);
        regUsr.setFechaNac(fechaNacU);
        databaseReference.child("Usuarios").child(regUsr.getIdUser()).setValue(regUsr);
        Toast.makeText(this, "Usuario agregado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void limpiarDatos() {
        nombre.setText("");
        email.setText("");
        contrasenia.setText("");
        fechaNacimiento.setText("");
    }



}

