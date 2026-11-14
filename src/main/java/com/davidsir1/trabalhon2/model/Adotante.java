package com.davidsir1.trabalhon2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Adotante {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int id_adotante;
    private String nome_adotante;
    private String telefone;
    private String email_adotante;
    private String endereco;
    private LocalDate data_cadastro;

    public Adotante() {
        this.data_cadastro = LocalDate.now();
    }

    public Adotante(int id_adotante, String nome_adotante, String telefone, String email_adotante,
                    String endereco, LocalDate data_cadastro) {
        this.id_adotante = id_adotante;
        this.nome_adotante = nome_adotante;
        this.telefone = telefone;
        this.email_adotante = email_adotante;
        this.endereco = endereco;
        this.data_cadastro = data_cadastro;
    }

    public Adotante(String nome_adotante, String telefone, String email_adotante, String endereco) {
        this.nome_adotante = nome_adotante;
        this.telefone = telefone;
        this.email_adotante = email_adotante;
        this.endereco = endereco;
        this.data_cadastro = LocalDate.now();
    }

    public int getId_adotante() {
        return id_adotante;
    }

    public void setId_adotante(int id_adotante) {
        this.id_adotante = id_adotante;
    }

    public String getNome_adotante() {
        return nome_adotante;
    }

    public void setNome_adotante(String nome_adotante) {
        this.nome_adotante = nome_adotante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail_adotante() {
        return email_adotante;
    }

    public void setEmail_adotante(String email_adotante) {
        this.email_adotante = email_adotante;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(LocalDate data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public String getDataCadastroFormatada() {
        return data_cadastro.format(FORMATO);
    }

    @Override
    public String toString() {
        return nome_adotante;
    }
}
