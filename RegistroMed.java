package com.example.pillassist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class RegistroMed extends AppCompatActivity {
    private EditText idMedicamentoEditText;
    private EditText nombreMedicamentoEditText;
    private EditText dosisDiariaEditText;
    private EditText frecuenciaEditText;
    private EditText lapsoEditText;
    private EditText horaPrimeraDosisEditText;
    private EditText descripcionEditText;
    private Button registrarButton;
    private Button registrosButton;
    private Button porFotoButton;
    private MedicamentosDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registromed);

        idMedicamentoEditText = findViewById(R.id.idMedicamento);
        nombreMedicamentoEditText = findViewById(R.id.nombreMedicamento);
        dosisDiariaEditText = findViewById(R.id.dosisDiaria);
        frecuenciaEditText = findViewById(R.id.frecuencia);
        lapsoEditText = findViewById(R.id.lapso);
        horaPrimeraDosisEditText = findViewById(R.id.horaPrimeraDosis);
        descripcionEditText = findViewById(R.id.descripcion);
        registrarButton = findViewById(R.id.registrarButton);
        registrosButton = findViewById(R.id.registrosButton);
        porFotoButton = findViewById(R.id.porFotoButton);

        dataSource = new MedicamentosDataSource(this);
        dataSource.open();

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long resultado = dataSource.insertMedicamento(
                        nombreMedicamentoEditText.getText().toString(),
                        dosisDiariaEditText.getText().toString(),
                        frecuenciaEditText.getText().toString(),
                        lapsoEditText.getText().toString(),
                        horaPrimeraDosisEditText.getText().toString(),
                        descripcionEditText.getText().toString()
                );

                if (resultado != -1) {
                    mostrarToast("Medicamento registrado");
                } else {
                    mostrarToast("Error al registrar el medicamento");
                }
            }
        });

        porFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarToast("Acción relacionada con la foto realizada");
            }
        });

        registrosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroMed.this, EditMed.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(RegistroMed.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
