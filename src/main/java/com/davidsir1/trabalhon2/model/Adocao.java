package com.davidsir1.trabalhon2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Adocao {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int id_adocao;
    private Usuario usuario;
    private Adotante adotante;
    private Animal animal;
    private LocalDateTime data_adocao;

    public Adocao() {
    }

    public Adocao(int id_adocao, Usuario usuario, Adotante adotante, Animal animal, LocalDateTime data_adocao) {
        this.id_adocao = id_adocao;
        this.usuario = usuario;
        this.adotante = adotante;
        this.animal = animal;
        this.data_adocao = data_adocao;
    }

    public Adocao(Usuario usuario, Adotante adotante, Animal animal) {
        this.usuario = usuario;
        this.adotante = adotante;
        this.animal = animal;
        this.data_adocao = LocalDateTime.now();
    }

    public int getId_adocao() {
        return id_adocao;
    }

    public void setId_adocao(int id_adocao) {
        this.id_adocao = id_adocao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Adotante getAdotante() {
        return adotante;
    }

    public void setAdotante(Adotante adotante) {
        this.adotante = adotante;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDateTime getData_adocao() {
        return data_adocao;
    }

    public void setData_adocao(LocalDateTime data_adocao) {
        this.data_adocao = data_adocao;
    }

    public String getNomeUsuario() {
        return usuario.getNome_usuario();
    }

    public String getNomeAdotante() {
        return adotante.getNome_adotante();
    }

    public String getNomeAnimal() {
        return animal.getNome_animal();
    }

    public String getDataAdocaoFormatada() {
        return data_adocao.format(FORMATO);
    }

    @Override
    public String toString() {
        return "Adoção #" + id_adocao + " - " + getNomeAnimal() + " -> " + getNomeAdotante();
    }
}
