package com.example.pillassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class MedicamentosDataSource {

    private SQLiteDatabase database;
    private MedicamentosDBHelper dbHelper;

    public MedicamentosDataSource(Context context) {
        dbHelper = new MedicamentosDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertMedicamento(String nombre, String dosisDiaria, String frecuencia,
                                  String lapso, String horaPrimeraDosis, String descripcion) {
        ContentValues values = new ContentValues();
        values.put(MedicamentosDBHelper.COLUMN_NOMBRE, nombre);
        values.put(MedicamentosDBHelper.COLUMN_DOSIS_DIARIA, dosisDiaria);
        values.put(MedicamentosDBHelper.COLUMN_FRECUENCIA, frecuencia);
        values.put(MedicamentosDBHelper.COLUMN_LAPSO, lapso);
        values.put(MedicamentosDBHelper.COLUMN_HORA_PRIMERA_DOSIS, horaPrimeraDosis);
        values.put(MedicamentosDBHelper.COLUMN_DESCRIPCION, descripcion);

        // Insertar el medicamento y obtener su ID
        long id = database.insert(MedicamentosDBHelper.TABLE_MEDICAMENTOS, null, values);



        return id;
    }


    public List<Medicamento> getAllMedicamentos() {
        List<Medicamento> medicamentos = new ArrayList<>();

        Cursor cursor = database.query(
                MedicamentosDBHelper.TABLE_MEDICAMENTOS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Medicamento medicamento = cursorToMedicamento(cursor);
            medicamentos.add(medicamento);
            cursor.moveToNext();
        }

        cursor.close();

        return medicamentos;
    }

    public int updateMedicamento(Medicamento medicamento) {
        ContentValues values = new ContentValues();
        values.put(MedicamentosDBHelper.COLUMN_NOMBRE, medicamento.getNombre());
        values.put(MedicamentosDBHelper.COLUMN_DOSIS_DIARIA, medicamento.getDosisDiaria());
        values.put(MedicamentosDBHelper.COLUMN_FRECUENCIA, medicamento.getFrecuencia());
        values.put(MedicamentosDBHelper.COLUMN_LAPSO, medicamento.getLapso());
        values.put(MedicamentosDBHelper.COLUMN_HORA_PRIMERA_DOSIS, medicamento.getHoraPrimeraDosis());
        values.put(MedicamentosDBHelper.COLUMN_DESCRIPCION, medicamento.getDescripcion());

        // Identificador del medicamento a actualizar
        long id = medicamento.getId();

        // Actualizar el medicamento en la base de datos
        return database.update(
                MedicamentosDBHelper.TABLE_MEDICAMENTOS,
                values,
                MedicamentosDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public int deleteMedicamento(long medicamentoId) {
        return database.delete(
                MedicamentosDBHelper.TABLE_MEDICAMENTOS,
                MedicamentosDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(medicamentoId)}
        );
    }

    private Medicamento cursorToMedicamento(Cursor cursor) {
        Medicamento medicamento = new Medicamento();
        medicamento.setId(cursor.getLong(cursor.getColumnIndex(MedicamentosDBHelper.COLUMN_ID)));
        medicamento.setNombre(cursor.getString(cursor.getColumnIndex(MedicamentosDBHelper.COLUMN_NOMBRE)));
        medicamento.setDosisDiaria(cursor.getString(cursor.getColumnIndex(MedicamentosDBHelper.COLUMN_DOSIS_DIARIA)));
        medicamento.setFrecuencia(cursor.getString(cursor.getColumnIndex(MedicamentosDBHelper.COLUMN_FRECUENCIA)));
        medicamento.setLapso(cursor.getString(cursor.getColumnIndex(MedicamentosDBHelper.COLUMN_LAPSO)));
        medicamento.setHoraPrimeraDosis(cursor.getString(cursor.getColumnIndex(MedicamentosDBHelper.COLUMN_HORA_PRIMERA_DOSIS)));
        medicamento.setDescripcion(cursor.getString(cursor.getColumnIndex(MedicamentosDBHelper.COLUMN_DESCRIPCION)));

        return medicamento;
    }
}
