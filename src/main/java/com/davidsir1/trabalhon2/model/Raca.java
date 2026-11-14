package com.davidsir1.trabalhon2.model;

public class Raca {
    private int id_raca;
    private String raca;
    private Especie especie;

    public Raca() {
    }

    public Raca(int id_raca, String raca, Especie especie) {
        this.id_raca = id_raca;
        this.raca = raca;
        this.especie = especie;
    }

    public Raca(String raca, Especie especie) {
        this.raca = raca;
        this.especie = especie;
    }

    public int getId_raca() {
        return id_raca;
    }

    public String getRaca() {
        return raca;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    @Override
    public String toString() {
        return raca;
    }
}
