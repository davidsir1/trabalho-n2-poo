package com.davidsir1.trabalhon2.model;

public class Baia {
    private int id_baia;
    private String cod_baia;
    private int capacidade;

    public Baia() {
    }

    public Baia(int id_baia, String cod_baia, int capacidade) {
        this.id_baia = id_baia;
        this.cod_baia = cod_baia;
        this.capacidade = capacidade;
    }

    public Baia(String cod_baia, int capacidade) {
        this.cod_baia = cod_baia;
        this.capacidade = capacidade;
    }

    public int getId_baia() {
        return id_baia;
    }

    public String getCod_baia() {
        return cod_baia;
    }

    public void setCod_baia(String cod_baia) {
        this.cod_baia = cod_baia;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return "Baia " + cod_baia + " (Cap: " + capacidade + ")";
    }
}
