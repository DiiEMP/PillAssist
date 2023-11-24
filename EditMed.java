package com.example.pillassist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EditMed extends AppCompatActivity {
    private ListView medicamentosListView;
    private ArrayAdapter<Medicamento> medicamentosAdapter;
    private MedicamentosDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_med);

        medicamentosListView = findViewById(R.id.medicamentosListView);

        // Configurar el adaptador con una lista vacía inicialmente
        medicamentosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        medicamentosListView.setAdapter(medicamentosAdapter);

        // Inicializar la fuente de datos
        dataSource = new MedicamentosDataSource(this);
        dataSource.open();

        // Cargar medicamentos al iniciar la actividad
        cargarMedicamentos();

        // Configurar un oyente para la selección de elementos en la lista de medicamentos
        medicamentosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí puedes mostrar un cuadro de diálogo con opciones "Editar" y "Borrar"
                mostrarOpcionesDialog(position);
            }
        });
    }

    private void cargarMedicamentos() {
        // Obtener la lista de medicamentos desde la base de datos
        List<Medicamento> listaMedicamentos = dataSource.getAllMedicamentos();

        // Limpiar el adaptador antes de agregar nuevos datos
        medicamentosAdapter.clear();

        // Agregar todos los medicamentos al adaptador
        medicamentosAdapter.addAll(listaMedicamentos);

        // Notificar al adaptador que los datos han cambiado
        medicamentosAdapter.notifyDataSetChanged();
    }

    // Función para mostrar un cuadro de diálogo con opciones "Editar" y "Borrar"
    private void mostrarOpcionesDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones del medicamento");
        builder.setItems(new CharSequence[]{"Editar", "Borrar"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Opción "Editar" seleccionada
                    mostrarEdicionMedicamentoDialog(position);
                } else if (which == 1) {
                    // Opción "Borrar" seleccionada
                    mostrarConfirmacionBorrarDialog(position);
                }
            }
        });

        builder.show();
    }

    // Función para mostrar el cuadro de diálogo de edición del medicamento
    private void mostrarEdicionMedicamentoDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_editar_medicamento, null);
        builder.setView(dialogView);

        final EditText nombreMedicamentoEditText = dialogView.findViewById(R.id.nombreMedicamentoEditText);
        final EditText dosisDiariaEditText = dialogView.findViewById(R.id.dosisDiariaEditText);
        final EditText frecuenciaEditText = dialogView.findViewById(R.id.frecuenciaEditText);
        final EditText lapsoEditText = dialogView.findViewById(R.id.lapsoEditText);
        final EditText horaPrimeraDosisEditText = dialogView.findViewById(R.id.horaPrimeraDosisEditText);
        final EditText descripcionEditText = dialogView.findViewById(R.id.descripcionEditText);

        // Obtener el medicamento seleccionado
        final Medicamento medicamentoSeleccionado = medicamentosAdapter.getItem(position);

        // Cargar los datos del medicamento seleccionado en los EditText
        nombreMedicamentoEditText.setText(medicamentoSeleccionado.getNombre());
        dosisDiariaEditText.setText(medicamentoSeleccionado.getDosisDiaria());
        frecuenciaEditText.setText(medicamentoSeleccionado.getFrecuencia());
        lapsoEditText.setText(medicamentoSeleccionado.getLapso());
        horaPrimeraDosisEditText.setText(medicamentoSeleccionado.getHoraPrimeraDosis());
        descripcionEditText.setText(medicamentoSeleccionado.getDescripcion());

        builder.setTitle("Editar Medicamento");

        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener los valores de los EditText y guardar la información editada
                medicamentoSeleccionado.setNombre(nombreMedicamentoEditText.getText().toString());
                medicamentoSeleccionado.setDosisDiaria(dosisDiariaEditText.getText().toString());
                medicamentoSeleccionado.setFrecuencia(frecuenciaEditText.getText().toString());
                medicamentoSeleccionado.setLapso(lapsoEditText.getText().toString());
                medicamentoSeleccionado.setHoraPrimeraDosis(horaPrimeraDosisEditText.getText().toString());
                medicamentoSeleccionado.setDescripcion(descripcionEditText.getText().toString());

                // Actualizar el medicamento en la base de datos
                dataSource.updateMedicamento(medicamentoSeleccionado);

                // Mostrar un Toast indicando que el medicamento ha sido actualizado
                mostrarMensaje("Medicamento actualizado");

                // Actualizar la lista de medicamentos después de editar
                cargarMedicamentos();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    // Función para mostrar un cuadro de confirmación antes de borrar
    private void mostrarConfirmacionBorrarDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar borrado");
        builder.setMessage("¿Está seguro de que desea borrar este medicamento?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener el medicamento seleccionado
                Medicamento medicamentoSeleccionado = medicamentosAdapter.getItem(position);

                // Borrar el medicamento de la base de datos
                dataSource.deleteMedicamento(medicamentoSeleccionado.getId());

                // Mostrar un Toast indicando que el medicamento ha sido borrado
                mostrarMensaje("Medicamento borrado");

                // Actualizar la lista de medicamentos después de borrar
                cargarMedicamentos();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Mostrar un Toast indicando que el borrado ha sido cancelado
                mostrarMensaje("Borrado cancelado");

                // Cerrar el cuadro de diálogo
                dialog.dismiss();
            }
        });

        builder.show();
    }

    // Función para mostrar mensajes Toast
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
