package com.davidsir1.trabalhon2.model;

public class Animal {
    private int id_animal;
    private String nome_animal;
    private String porte;
    private String sexo;
    private String status_adocao;
    private Raca raca;
    private Especie especie;

    public Animal() {
        this.status_adocao = "DISPONIVEL";
    }

    public Animal(int id_animal, String nome_animal, String porte, String sexo, String status_adocao, Raca raca) {
        this.id_animal = id_animal;
        this.nome_animal = nome_animal;
        this.porte = porte;
        this.sexo = sexo;
        this.status_adocao = status_adocao;
        this.raca = raca;
    }

    public Animal(String nome_animal, String porte, String sexo, String status_adocao, Raca raca) {
        this.nome_animal = nome_animal;
        this.porte = porte;
        this.sexo = sexo;
        this.status_adocao = status_adocao;
        this.raca = raca;
    }

    public int getId_animal() {
        return id_animal;
    }

    public String getNome_animal() {
        return nome_animal;
    }

    public String getPorte() {
        return porte;
    }

    public String getSexo() {
        return sexo;
    }

    public String getStatus_adocao() {
        return status_adocao;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setNome_animal(String nome_animal) {
        this.nome_animal = nome_animal;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setStatus_adocao(String status_adocao) {
        this.status_adocao = status_adocao;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public Especie getEspecie() {
        return this.raca.getEspecie();
    }

    @Override
    public String toString() {
        return nome_animal + "(" + raca + " - " + raca.getNomeEspecie() + ")";
    }
}
