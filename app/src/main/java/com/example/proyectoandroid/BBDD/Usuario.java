package com.example.proyectoandroid.BBDD;

public class Usuario {
    String email,password;

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
