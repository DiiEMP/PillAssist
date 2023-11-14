package com.example.pillassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class RegisterMed extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView sinImagen;
    Button registrar, agregarImagen, registros;
    EditText nombreMed, dosisMed, tipoMed, cadacunto, descripcion, precio;
    private static final int PICK_IMAGE = 100;
    String x;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_med);
        inicializarConexion();
        nombreMed = findViewById(R.id.nombreMedicamento);
        dosisMed = findViewById(R.id.dosisDiaria);
        tipoMed = findViewById(R.id.tipoMed);
        cadacunto = findViewById(R.id.cadaCuanto);
        descripcion = findViewById(R.id.descripcion);
        precio = findViewById(R.id.precioMA);
        sinImagen = findViewById(R.id.sinImagen);
        agregarImagen = findViewById(R.id.agregarImagenA);
        registros = findViewById(R.id.registrosButton);
        registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterMed.this, EditarMed.class);
                startActivity(intent);
            }
        });
        registrar = findViewById(R.id.registrarButton);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreM, dosisM,tipoM,cadaC, descripcionM, precioM;
                nombreM= nombreMed.getText().toString();
                dosisM= dosisMed.getText().toString();
                tipoM= tipoMed.getText().toString();
                cadaC= cadacunto.getText().toString();
                descripcionM= descripcion.getText().toString();
                precioM= precio.getText().toString();
                registrarDatos(nombreM, dosisM,tipoM,cadaC,descripcionM,precioM);
            }
        });

        agregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(intent,PICK_IMAGE);
            }
        });
    }

    private void registrarDatos(String nombreM, String dosisM, String tipoM, String cadaC, String descripcionM, String precioM) {
        GaleriaMedicamentos galeria = new GaleriaMedicamentos();
        galeria.setId(UUID.randomUUID().toString());
        galeria.setNombre(nombreM);
        galeria.setTipoMedicamento(tipoM);
        galeria.setCadaCuanto(cadaC);
        galeria.setDescripcion(descripcionM);
        galeria.setPrecio(precioM);
        Bitmap imageBitmap = ((BitmapDrawable) sinImagen.getDrawable()).getBitmap();

        // Convierte el Bitmap a un arreglo de bytes (byte[])
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String fileName = "images/" + UUID.randomUUID().toString() + ".jpg"; // Nombre de archivo único
        StorageReference imageRef = storageRef.child(fileName);
        imageRef.putBytes(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUri = uri.toString();
                        galeria.setImagen(downloadUri);
                        databaseReference.child("GaleriaMedicamentos").child(galeria.getId()).setValue(galeria);
                        Toast.makeText(RegisterMed.this, "Se ha registrado con exito", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterMed.this, "Ah ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void inicializarConexion() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode== RESULT_OK && requestCode==PICK_IMAGE)
        {
            Uri uri = data.getData();
            sinImagen.setImageURI(uri);
            x = getPath(uri);
            //Toast.makeText(this, "Se ha agregado con Exito", Toast.LENGTH_SHORT).show();
        }

    }

    private String getPath(Uri uri) {
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.managedQuery(uri, projection,null,null,null);
        if(cursor!=null)
        {
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    public void limpiarDatos(){

        nombreMed.setText("");
        dosisMed.setText("");
        tipoMed.setText("");
        cadacunto.setText("");
        descripcion.setText("");
        precio.setText("");
        sinImagen.setImageResource(R.drawable.sinimagenn);
    }
}