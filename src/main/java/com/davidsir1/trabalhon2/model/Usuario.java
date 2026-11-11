package com.davidsir1.trabalhon2.model;

public class Usuario {
    private int id_usuario;
    private String nome_usuario;
    private String email_usuario;
    private String senha;
    private String cargo;

    public Usuario(int id_usuario, String nome_usuario, String email_usuario, String senha, String cargo) {
        this.id_usuario = id_usuario;
        this.nome_usuario = nome_usuario;
        this.email_usuario = email_usuario;
        this.senha = senha;
        this.cargo = cargo;
    }

    public Usuario(String nome_usuario, String email_usuario, String senha, String cargo) {
        this.nome_usuario = nome_usuario;
        this.email_usuario = email_usuario;
        this.senha = senha;
        this.cargo = cargo;
    }

    public Usuario(String nome_usuario, String email_usuario, String cargo) {
        this.nome_usuario = nome_usuario;
        this.email_usuario = email_usuario;
        this.cargo = cargo;
    }

    public Usuario(String nome_usuario, String email_usuario) {
        this.nome_usuario = nome_usuario;
        this.email_usuario = email_usuario;
    }

    // Getters e Setters
    public int getId_usuario() {
        return id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
