package com.example.pillassist;

public class Medicamento {
    private long id;
    private String nombre;
    private String dosisDiaria;
    private String frecuencia;
    private String lapso;
    private String horaPrimeraDosis;
    private String descripcion;

    // Constructor con argumentos
    public Medicamento(String nombre, String dosisDiaria, String frecuencia, String lapso,
                       String horaPrimeraDosis, String descripcion) {
        this.nombre = nombre;
        this.dosisDiaria = dosisDiaria;
        this.frecuencia = frecuencia;
        this.lapso = lapso;
        this.horaPrimeraDosis = horaPrimeraDosis;
        this.descripcion = descripcion;
    }

    // Constructor sin argumentos
    public Medicamento() {
        // Puedes inicializar con valores predeterminados si es necesario
        this.nombre = "Nombre del Medicamento";
        this.dosisDiaria = "Dosis Diaria";
        this.frecuencia = "Frecuencia";
        this.lapso = "Lapso";
        this.horaPrimeraDosis = "Hora de la Primera Dosis";
        this.descripcion = "Descripción";
    }

    // Getters y Setters

    @Override
    public String toString() {
        return nombre + "\n" +
                "Cada: " + dosisDiaria + "\n" +
                "Lapso: " + lapso;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosisDiaria() {
        return dosisDiaria;
    }

    public void setDosisDiaria(String dosisDiaria) {
        this.dosisDiaria = dosisDiaria;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getLapso() {
        return lapso;
    }

    public void setLapso(String lapso) {
        this.lapso = lapso;
    }

    public String getHoraPrimeraDosis() {
        return horaPrimeraDosis;
    }

    public void setHoraPrimeraDosis(String horaPrimeraDosis) {
        this.horaPrimeraDosis = horaPrimeraDosis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
