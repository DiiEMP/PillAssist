package com.example.pillassist;

// MedicamentosDBHelper.java
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicamentosDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "medicamentos.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_MEDICAMENTOS = "medicamentos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DOSIS_DIARIA = "dosis_diaria";
    public static final String COLUMN_FRECUENCIA = "frecuencia";
    public static final String COLUMN_LAPSO = "lapso";
    public static final String COLUMN_HORA_PRIMERA_DOSIS = "hora_primera_dosis";
    public static final String COLUMN_DESCRIPCION = "descripcion";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_MEDICAMENTOS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NOMBRE
            + " text not null, " + COLUMN_DOSIS_DIARIA
            + " text not null, " + COLUMN_FRECUENCIA
            + " text not null, " + COLUMN_LAPSO
            + " text not null, " + COLUMN_HORA_PRIMERA_DOSIS
            + " text not null, " + COLUMN_DESCRIPCION
            + " text not null);";

    public MedicamentosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar actualizaciones de la base de datos si es necesario
    }
}
