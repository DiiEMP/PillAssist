package com.example.pillassist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ProfileFragment extends Fragment {

    FirebaseDatabase firebaseDatabase, firebaseDatabase2;
    DatabaseReference databaseReference, databaseReference2;
    ImageButton abrirG;
    TextView nomApe, correoE, nacimiento, edad;
    ImageButton sinImagen;
    String changepswd;
    String datosUser = Second.correoElect;
    String id;
    String x;
    Button takePhoto;
    Button cambiarContra, cerrarSesion;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        abrirG = view.findViewById(R.id.abrirGaleria);
        //imagenP =view.findViewById(R.id.abrirGaleria);
        nomApe = view.findViewById(R.id.textNombreApellido);
        correoE = view.findViewById(R.id.txtEmail);
        nacimiento = view.findViewById(R.id.txtNacimiento);
        edad = view.findViewById(R.id.txtEdad);
        sinImagen = view.findViewById(R.id.abrirGaleria);

        takePhoto = view.findViewById(R.id.tomarFoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        inicializarFB();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuarios");
        abrirG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(intent,PICK_IMAGE);
            }
        });
        idusuario();
        mostrarDatos(datosUser);

        cambiarContra = view.findViewById(R.id.changePswd);
        cambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CambiarContrasenaProfile.class);
                startActivity(intent);
            }
        });

        cerrarSesion = view.findViewById(R.id.cerrarSesionP);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });





        return view;
    }


    private void inicializarFB() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase2.getReference();
    }


    private void mostrarDatos(String datosUser) {
        databaseReference.orderByChild("email").equalTo(datosUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                        nomApe.setText(dataSnapshot.child("name").getValue(String.class));
                        correoE.setText(dataSnapshot.child("email").getValue(String.class));;
                        nacimiento.setText(dataSnapshot.child("fechaNac").getValue(String.class));;
                        edad.setText(dataSnapshot.child("edad").getValue(String.class));;

                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Error al consultar el usuario", error.toException());

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode== Activity.RESULT_OK && requestCode==PICK_IMAGE)
        {
            Uri uri = data.getData();
            abrirG.setImageURI(uri);
            x = getPath(uri);
            RegisterUser ruser = new RegisterUser();
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
                            ruser.setPhoto(downloadUri);
                            Log.d("IDUSUARIO", id);
                            databaseReference2.child("Usuarios").child(id).child("Photo").setValue(downloadUri);
                            Toast.makeText(getActivity(), "Imagen guardada", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error al cargar la foto", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap1 = (Bitmap) extras.get("data");
                abrirG.setImageBitmap(imageBitmap1);

                RegisterUser ruser = new RegisterUser();
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
                                ruser.setPhoto(downloadUri);
                                Log.d("IDUSUARIO", id);
                                databaseReference2.child("Usuarios").child(id).child("Photo").setValue(downloadUri);
                                Toast.makeText(getActivity(), "Imagen guardada", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error al cargar la foto", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void idusuario() {
        databaseReference.orderByChild("email").equalTo(datosUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        id = dataSnapshot.child("idUser").getValue(String.class);
                        //Toast.makeText(getActivity(), "ID USUARIO"+id, Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String getPath(Uri uri) {
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection,null,null,null);
        if(cursor!=null)
        {
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }


}
