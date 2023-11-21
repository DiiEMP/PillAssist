package com.example.pillassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.UUID;

public class Register extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText nombre, email, contrasenia, fechaNacimiento;
    Button regis, iniciarSesion, dateButton;
    DatePickerDialog datePickerDialog;
    int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iniciarConexion();

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.correo);
        contrasenia = findViewById(R.id.contrasena);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);

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

        initDatePicker();
        dateButton = findViewById(R.id.btnfechaNacimiento);
        dateButton.setText(getTodayDate());

    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                fechaNacimiento.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private String getMonthFormart(int month) {
        if (month == 1) {
            return "Enero";
        }
        if (month == 2) {
            return "Febrero";
        }
        if (month == 3) {
            return "Marzo";
        }
        if (month == 4) {
            return "Abril";
        }
        if (month == 5) {
            return "Mayo";
        }
        if (month == 6) {
            return "Junio";
        }
        if (month == 7) {
            return "Julio";
        }
        if (month == 8) {
            return "Agosto";
        }
        if (month == 9) {
            return "Septiembre";
        }
        if (month == 10) {
            return "Octubre";
        }
        if (month == 11) {
            return "Noviembre";
        }
        if (month == 12) {
            return "Diciembre";
        }
        //default valor
        return "Enero";
    }

    public void openDatePicker(View view) {
        datePickerDialog. show();
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

        // Calcular la edad
        int edad = calcularEdad(fechaNacU);
        regUsr.setEdad(String.valueOf(edad));

        // Guardar en la base de datos
        databaseReference.child("Usuarios").child(regUsr.getIdUser()).setValue(regUsr);
        Toast.makeText(this, "Usuario agregado exitosamente", Toast.LENGTH_SHORT).show();


    }

    private void limpiarDatos() {
        nombre.setText("");
        email.setText("");
        contrasenia.setText("");
        fechaNacimiento.setText("");
    }

    private int calcularEdad(String fechaNacimiento) {
        try {
            String[] parts = fechaNacimiento.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();

            dob.set(year, month - 1, day);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Manejar errores o retornar un valor predeterminado en caso de error
        }
    }



}

