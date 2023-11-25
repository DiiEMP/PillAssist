package com.example.pillassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CambiarContrasenaProfile extends AppCompatActivity {

    Button guardar;
    EditText oldpswd, newpswd, confirmnewpswd;
    String correo = Second.correoElect;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    FirebaseDatabase firebaseDatabase2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena_profile);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuarios");

        oldpswd = findViewById(R.id.contraactual);
        newpswd = findViewById(R.id.newPswd);
        confirmnewpswd = findViewById(R.id.confirmNewPswd);

        guardar = findViewById(R.id.btnGuardarCambios);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (newpswd.getText().toString().equals(confirmnewpswd.getText().toString())) {
                    //Toast.makeText(CambiarContrasenaProfile.this, "Contraseñas iguales", Toast.LENGTH_SHORT).show();
                   verificar();

                } else {
                    Toast.makeText(CambiarContrasenaProfile.this, "Las contraseñas son diferentes", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void verificar() {
        databaseReference.orderByChild("email").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        if (oldpswd.getText().toString().equals(dataSnapshot.child("contrasenia").getValue(String.class))) {
                            Toast.makeText(CambiarContrasenaProfile.this, "Las contraseñas coinciden", Toast.LENGTH_SHORT).show();
                            actualizarContra(confirmnewpswd.getText().toString());
                        } else {
                            Toast.makeText(CambiarContrasenaProfile.this, "La contraseñas no coincide", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void actualizarContra(String confirmnewpswd) {
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase2.getReference("Usuarios");
        //databaseReference2.child("contrasenia").setValue(confirmnewpswd);
        databaseReference2.orderByChild("email").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        String id = dataSnapshot.child("idUser").getValue(String.class);
                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(id);
                        userReference.child("contrasenia").setValue(confirmnewpswd).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // El dato 'contrasenia' fue actualizado exitosamente en la base de datos
                                    Log.d("ACTUALIZACION", "Contraseña actualizada exitosamente en la base de datos");
                                } else {
                                    // Ocurrió un error al actualizar el dato en la base de datos
                                    Log.e("ACTUALIZACION", "Error al actualizar la contraseña en la base de datos", task.getException());
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
    }

}