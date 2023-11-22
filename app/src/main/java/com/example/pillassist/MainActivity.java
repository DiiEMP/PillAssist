package com.example.pillassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button crear, fpswd, ingresar;
    String valor = "";
    @SuppressLint("MissingInflatedId")
    EditText correo, contrasena;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuarios");

        //mAuth = FirebaseAuth.getInstance();

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
                    String email = correo.getText().toString().trim();
                    String password = contrasena.getText().toString().trim();
                    // Verificar que los campos de correo y contraseña no estén vacíos
                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                        Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    }else {
                        verificarRegistro(correo.getText().toString(), contrasena.getText().toString());
                    }

                    /*
                    Intent is = new Intent(MainActivity.this, Second.class);
                    startActivity(is); */
                    //Toast.makeText(MainActivity.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
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

    private void verificarRegistro(String correo, String contrasenaa) {
        databaseReference.orderByChild("email").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /** esto va en Register.java
                if (snapshot.exists()) {
                    Toast.makeText(MainActivity.this, "El correo existe", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "El correo no existe", Toast.LENGTH_SHORT).show();
                } +*/
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        String contrasenaAlmacenada = dataSnapshot.child("contrasenia").getValue(String.class);
                        if (contrasenaa.equals(contrasenaAlmacenada)) {
                            Intent intent = new Intent(MainActivity.this, Second.class);
                            intent.putExtra("correo", correo);
                            intent.putExtra("contrasenia", contrasenaa);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Error al consultar el usuario", error.toException());
            }
        });


    }
}