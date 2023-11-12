package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button crear, fpswd, ingresar;
    String valor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crear = findViewById(R.id.crearCuenta);
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

        ingresar = findViewById(R.id.iniciarSesion);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent is = new Intent(MainActivity.this, Second.class);
                startActivity(is);
            }
        });




    }
}