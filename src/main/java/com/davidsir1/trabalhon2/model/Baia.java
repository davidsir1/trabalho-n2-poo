package com.davidsir1.trabalhon2.model;

public class Baia {
    private int id_baia;
    private int capacidade;

    public Baia() {
    }

    public Baia(int id_baia, int capacidade) {
        this.id_baia = id_baia;
        this.capacidade = capacidade;
    }

    public Baia(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getId_baia() {
        return id_baia;
    }

    public void setId_baia(int id_baia) {
        this.id_baia = id_baia;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return "Baia #" + id_baia + " (Cap: " + capacidade + ")";
    }
}
