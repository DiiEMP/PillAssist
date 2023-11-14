package com.example.pillassist;

public class GaleriaMedicamentos {
    private String id;
    private String nombre;
    private String imagen;
    private String tipoMedicamento;
    private String cadaCuanto;
    private String descripcion;
    private String precio;
    public GaleriaMedicamentos(){}

    public GaleriaMedicamentos(String id, String nombre, String imagen, String tipoMedicamento, String cadaCuanto, String descripcion, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.tipoMedicamento = tipoMedicamento;
        this.cadaCuanto = cadaCuanto;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public String getCadaCuanto() {
        return cadaCuanto;
    }

    public void setCadaCuanto(String cadaCuanto) {
        this.cadaCuanto = cadaCuanto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}