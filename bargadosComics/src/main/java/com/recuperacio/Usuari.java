package com.recuperacio;

public class Usuari extends Persona{
    private String password;
    private String rol;
    private String email;
    private String dni;

    public Usuari(String nom, String cognom, String password, String rol, String email, String dni) {
        super(nom, cognom);
        this.password = password;
        this.rol = rol;
        this.email = email;
        this.dni = dni;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }



}

