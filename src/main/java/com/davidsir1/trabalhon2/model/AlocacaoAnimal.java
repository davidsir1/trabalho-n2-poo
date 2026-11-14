package com.davidsir1.trabalhon2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlocacaoAnimal {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int id_alocacao_animal;
    private LocalDateTime data_transferencia;
    private Animal animal;
    private Baia baia;

    public AlocacaoAnimal() {
    }

    public AlocacaoAnimal(int id_alocacao_animal, LocalDateTime data_transferencia, Animal animal, Baia baia) {
        this.id_alocacao_animal = id_alocacao_animal;
        this.data_transferencia = data_transferencia;
        this.animal = animal;
        this.baia = baia;
    }

    public AlocacaoAnimal(Animal animal, Baia baia) {
        this.animal = animal;
        this.baia = baia;
        this.data_transferencia = LocalDateTime.now();
    }

    public int getId_alocacao_animal() {
        return id_alocacao_animal;
    }

    public void setId_alocacao_animal(int id_alocacao_animal) {
        this.id_alocacao_animal = id_alocacao_animal;
    }

    public LocalDateTime getData_transferencia() {
        return data_transferencia;
    }

    public void setData_transferencia(LocalDateTime data_transferencia) {
        this.data_transferencia = data_transferencia;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Baia getBaia() {
        return baia;
    }

    public void setBaia(Baia baia) {
        this.baia = baia;
    }

    // Auxiliares para PropertyValueFactory na TableView
    public String getNomeAnimal() {
        return animal != null ? animal.getNome_animal() : "";
    }

    public String getDescricaoBaia() {
        return baia != null ? "Baia #" + baia.getId_baia() : "";
    }

    public String getDataTransferenciaFormatada() {
        return data_transferencia != null ? data_transferencia.format(FORMATTER) : "";
    }

    @Override
    public String toString() {
        return getNomeAnimal() + " -> " + getDescricaoBaia();
    }
}
