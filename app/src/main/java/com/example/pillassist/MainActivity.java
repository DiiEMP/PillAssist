package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button crear, fpswd, ingresar;
    String valor = "";
    @SuppressLint("MissingInflatedId")
    EditText correo, contrasena;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crear = findViewById(R.id.crearCuenta);
        ingresar = findViewById(R.id.ingresar);
        correo = findViewById(R.id.user);
        contrasena = findViewById(R.id.contrasena);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(correo.getText().toString().equalsIgnoreCase("admin@gmail.com") && contrasena.getText().toString().equalsIgnoreCase("12345")){
                    Intent ingresar = new Intent(MainActivity.this, RegisterMed.class);
                    startActivity(ingresar);
                }
                else{
                    Toast.makeText(MainActivity.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(MainActivity.this, Register.class);
                startActivity(c);
            }
        });
        fpswd = findViewById(R.id.forgotPswd);
        fpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f = new Intent(MainActivity.this, ForgotPassword.class);
                valor = "one";
                f.putExtra("Valor_Frame",valor);
                startActivity(f);
            }
        });

    }
}