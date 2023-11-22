package com.example.pillassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class Vermedicamento extends AppCompatActivity {
    String idm,nombre,imagen,tipoM,cadaC,descripcion,precio;
    EditText id, nombreM,tipo,cadaCM,descripcionM,precioM;
    ImageView image;
    String x;
    Button abrirGaleria, eliminar,editar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Uri uri;
    private static final int PICK_IMAGE = 100;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermedicamento);
        Intent intent = getIntent();
        inicializarDatabase();
        abrirGaleria = findViewById(R.id.editarImagenA);
        abrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

        editar = findViewById(R.id.guardar);


        eliminar = findViewById(R.id.eliminar);


        idm = intent.getStringExtra("id");
        nombre = intent.getStringExtra("nombre");
        imagen = intent.getStringExtra("imagen");
        tipoM = intent.getStringExtra("tipoM");
        cadaC = intent.getStringExtra("cadaC");
        descripcion = intent.getStringExtra("descripcion");
        precio = intent.getStringExtra("precio");
        nombreM = findViewById(R.id.nombreMedicamentoEditText);
        tipo = findViewById(R.id.TipoEditText);
        cadaCM = findViewById(R.id.lapsoEditText);
        descripcionM = findViewById(R.id.descripcionEditText);
        precioM = findViewById(R.id.precioMA);
        image = findViewById(R.id.imagenEdit);
        nombreM.setText(nombre);
        tipo.setText(tipoM);
        cadaCM.setText(cadaC);
        descripcionM.setText(descripcion);
        precioM.setText(precio);
        Glide.with(Vermedicamento.this).load(imagen).into(image);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eliminarMedicamento(idm);
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = nombreM.getText().toString();
                tipoM = tipo.getText().toString();
                cadaC = cadaCM.getText().toString();
                descripcion = descripcionM.getText().toString();
                precio = precioM.getText().toString();
                modificarMedicamento(idm,nombre,tipoM,cadaC,descripcion,precio);

            }
        });
    }


    private void eliminarMedicamento(String id) {
        GaleriaMedicamentos p = new GaleriaMedicamentos();
        p.setId(id);
        databaseReference.child("GaleriaMedicamentos").child(p.getId()).removeValue();
        Toast.makeText(Vermedicamento.this, "ELIMINADO", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(Vermedicamento.this, EditarMed.class);
        startActivity(intent1);
        finish();
    }

    private void modificarMedicamento(String idm, String nombreMed, String tipoM, String cadaCuan, String descripcion, String precio) {
        GaleriaMedicamentos galeriaMedicamentos = new GaleriaMedicamentos();
        galeriaMedicamentos.setId(idm);
        galeriaMedicamentos.setNombre(nombreMed);
        galeriaMedicamentos.setTipoMedicamento(tipoM);
        galeriaMedicamentos.setCadaCuanto(cadaCuan);
        galeriaMedicamentos.setDescripcion(descripcion);
        galeriaMedicamentos.setPrecio(precio);

        Bitmap imageBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        // Convierte el Bitmap a un arreglo de bytes (byte[])
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        // Crea una referencia en Firebase Storage y carga la imagen
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String fileName = "images/" + UUID.randomUUID().toString() + ".jpg"; // Nombre de archivo único
        StorageReference imageRef = storageRef.child(fileName);

        imageRef.putBytes(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // La imagen se cargó con éxito
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();
                                galeriaMedicamentos.setImagen(downloadUrl); // Guarda la URL de descarga en el objeto Persona
                                // Ahora puedes guardar el objeto Persona en la base de datos
                                databaseReference.child("GaleriaMedicamentos").child(galeriaMedicamentos.getId()).setValue(galeriaMedicamentos);
                                Toast.makeText(Vermedicamento.this, "Se ha registrado con exito", Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(Vermedicamento.this, EditarMed.class);
                                startActivity(intent1);
                                finish();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Vermedicamento.this, "Error al cargar la foto", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseStorage", "Error al cargar la imagen: " + e.getMessage());
                    }
                });
    }

    private void inicializarDatabase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode== Activity.RESULT_OK && requestCode==PICK_IMAGE)
        {
            uri = data.getData();
            image.setImageURI(uri);
            x = getPath(uri);
            Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
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


}