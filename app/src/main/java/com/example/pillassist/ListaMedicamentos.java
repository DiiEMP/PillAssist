package com.example.pillassist;

public class ListaMedicamentos {
    private  String idLista;
    private  String nombreLista;
    private  String cadaCuandoLista;
    private String requestCode;
    private  String idUsuario;
    private  String descripcionLista;
    private  String dosisLista;
    public ListaMedicamentos(){}


    public ListaMedicamentos(String idLista, String nombreLista, String cadaCuandoLista, String idUsuario, String descripcionLista, String dosisLista, String requestCode) {
        this.idLista = idLista;
        this.nombreLista = nombreLista;
        this.cadaCuandoLista = cadaCuandoLista;
        this.idUsuario = idUsuario;
        this.descripcionLista = descripcionLista;
        this.dosisLista = dosisLista;
        this.requestCode = requestCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getIdLista() {
        return idLista;
    }

    public void setIdLista(String idLista) {
        this.idLista = idLista;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getCadaCuandoLista() {
        return cadaCuandoLista;
    }

    public void setCadaCuandoLista(String cadaCuandoLista) {
        this.cadaCuandoLista = cadaCuandoLista;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescripcionLista() {
        return descripcionLista;
    }

    public void setDescripcionLista(String descripcionLista) {
        this.descripcionLista = descripcionLista;
    }

    public String getDosisLista() {
        return dosisLista;
    }

    public void setDosisLista(String dosisLista) {
        this.dosisLista = dosisLista;
    }
}
