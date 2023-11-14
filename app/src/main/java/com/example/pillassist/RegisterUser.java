package com.example.pillassist;

public class RegisterUser {
    private String idUser;
    private String idMedicamentos;
    private String photo;
    private String name;
    private String email;
    private String contrasenia;
    private String fechaNac;
    private String edad;

    public RegisterUser() {}

    public RegisterUser(String idUser, String idMedicamentos, String photo, String name, String email, String contrasenia, String fechaNac, String edad) {
        this.idUser = idUser;
        this.idMedicamentos = idMedicamentos;
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.contrasenia = contrasenia;
        this.fechaNac = fechaNac;
        this.edad = edad;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdMedicamentos() {
        return idMedicamentos;
    }

    public void setIdMedicamentos(String idMedicamentos) {
        this.idMedicamentos = idMedicamentos;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }



}
