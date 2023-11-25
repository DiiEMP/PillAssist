package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class CrearAlarma extends AppCompatActivity {
String id ;
EditText nombreMedicamento,horaRecordatorio,dosisRecordatorio,descripcionRecordatorio;
Button crear;
int hora, minutos;
String nombre, horaR,dosisR,descripcionR;
int horaReal, minutosReal;
    int numeroAleatorio;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
        //Consulta de Id
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alarma);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Toast.makeText(this, " "+id, Toast.LENGTH_SHORT).show();
        inicializarFirebase();
        Random random = new Random();

        // Generar un número aleatorio entre 0 (inclusive) y 100 (exclusive)
         numeroAleatorio = random.nextInt(1000);
        nombreMedicamento = findViewById(R.id.nombreMedicamneto);
        horaRecordatorio = findViewById(R.id.horaRecordatorio);
        dosisRecordatorio = findViewById(R.id.dosisRecordatorio);
        descripcionRecordatorio = findViewById(R.id.descripcionRecordatorio);
        crear = findViewById(R.id.crearRecordatorio);
        horaRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minutos = calendar.get(Calendar.MINUTE);
                TimePickerDialog datePickerDialog = new TimePickerDialog(CrearAlarma.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        horaRecordatorio.setText(i+":"+i1);
                        horaReal=i;
                        minutosReal = i1;
                    }
                }, hora, minutos,false);
                datePickerDialog.show();
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = nombreMedicamento.getText().toString();
                horaR = horaRecordatorio.getText().toString();
                dosisR = dosisRecordatorio.getText().toString();
                descripcionR = descripcionRecordatorio.getText().toString();
                crearReecordatorio(nombre,horaR,dosisR,descripcionR,horaReal,minutosReal);
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void crearReecordatorio(String nombre,String horaR, String dosisR, String descripcionR,int horaa, int minutos) {

        ListaMedicamentos listaMedicamentos =  new ListaMedicamentos();
        listaMedicamentos.setIdLista(UUID.randomUUID().toString());
        listaMedicamentos.setIdUsuario(id);
        listaMedicamentos.setNombreLista(nombre);
        listaMedicamentos.setCadaCuandoLista(horaR);
        listaMedicamentos.setDosisLista(dosisR);
        listaMedicamentos.setDescripcionLista(descripcionR);
        listaMedicamentos.setRequestCode(String.valueOf(numeroAleatorio));
        databaseReference.child("ListaMedicamentos").child(listaMedicamentos.getIdLista()).setValue(listaMedicamentos);
        Toast.makeText(this, "Se ha registrado con exito", Toast.LENGTH_SHORT).show();
        establecerAlarma(nombre,minutos,horaa);
        finish();



    }

    private void establecerAlarma(String mensaje, int minutos, int hora) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE,mensaje)
                .putExtra(AlarmClock.EXTRA_HOUR,hora)
                .putExtra(AlarmClock.EXTRA_MINUTES,minutos);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, numeroAleatorio, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Luego utilizas el PendingIntent al configurar la alarma
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);

        }

    }
}