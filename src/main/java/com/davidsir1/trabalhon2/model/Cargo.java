package com.davidsir1.trabalhon2.model;

public class Cargo {
    private int id_cargo;
    private String cargo;

    public Cargo(int id_cargo, String cargo) {
        this.id_cargo = id_cargo;
        this.cargo = cargo;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return cargo;
    }
}
