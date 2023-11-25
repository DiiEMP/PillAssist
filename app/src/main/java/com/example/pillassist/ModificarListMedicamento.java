package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class ModificarListMedicamento extends AppCompatActivity {
    String idList, nombre, cadaCuando, idUsuario, descripcion,dosis, requestCode;
    EditText nombreMed, dosisMed, descripcionMed, horaMed;
    int hora, minutos, horaReal, minutosReal;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button modificar, eliminar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_list_medicamento);
        inicializarFirebase();
        Intent intent = getIntent();
        idList = intent.getStringExtra("idList");
        nombre = intent.getStringExtra("nombre");
        cadaCuando = intent.getStringExtra("cadaCuando");
        idUsuario = intent.getStringExtra("idUsuario");
        descripcion = intent.getStringExtra("descripcion");
        dosis = intent.getStringExtra("dosis");
        requestCode = intent.getStringExtra("requestCode");
        modificar = findViewById(R.id.modificarRecordatorio);
        nombreMed = findViewById(R.id.nombreMedicamnetoM);
        dosisMed = findViewById(R.id.dosisRecordatorioM);
        descripcionMed = findViewById(R.id.descripcionRecordatorioM);
        eliminar = findViewById(R.id.EliminarAlarma);
        horaMed = findViewById(R.id.horaRecordatorioM);
        nombreMed.setText(nombre);
        dosisMed.setText(dosis);
        descripcionMed.setText(descripcion);
        horaMed.setText(cadaCuando);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarMedicamento(idList);
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearReecordatorio(nombreMed.getText().toString(), horaMed.getText().toString(),dosisMed.getText().toString(), descripcionMed.getText().toString(), horaReal, minutosReal);
            }
        });
        horaMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minutos = calendar.get(Calendar.MINUTE);
                TimePickerDialog datePickerDialog = new TimePickerDialog(ModificarListMedicamento.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        horaMed.setText(i+":"+i1);
                        horaReal=i;
                        minutosReal = i1;
                    }
                }, hora, minutos,false);
                datePickerDialog.show();
            }
        });




    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void eliminarMedicamento(String id) {
        ListaMedicamentos p = new ListaMedicamentos();
        p.setIdLista(id);
        databaseReference.child("ListaMedicamentos").child(p.getIdLista()).removeValue();
        Toast.makeText(ModificarListMedicamento.this, "ELIMINADO", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void crearReecordatorio(String nombre,String horaR, String dosisR, String descripcionR,int horaa, int minutos) {

        ListaMedicamentos listaMedicamentos =  new ListaMedicamentos();
        listaMedicamentos.setIdLista(idList);
        listaMedicamentos.setIdUsuario(idUsuario);
        listaMedicamentos.setNombreLista(nombre);
        listaMedicamentos.setCadaCuandoLista(horaR);
        listaMedicamentos.setDosisLista(dosisR);
        listaMedicamentos.setDescripcionLista(descripcionR);
        listaMedicamentos.setRequestCode(requestCode);
        databaseReference.child("ListaMedicamentos").child(listaMedicamentos.getIdLista()).setValue(listaMedicamentos);
        Toast.makeText(this, "Se ha modificado con exito", Toast.LENGTH_SHORT).show();
        establecerAlarma(nombre,minutos,horaa);
        finish();



    }

    private void establecerAlarma(String nombre, int minutos, int horaa) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE,nombre)
                .putExtra(AlarmClock.EXTRA_HOUR,hora)
                .putExtra(AlarmClock.EXTRA_MINUTES,minutos);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.parseInt(requestCode), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Luego utilizas el PendingIntent al configurar la alarma
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);

        }

    }


}