package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.repository.AnimalRepository;

import java.util.List;
import java.util.Locale;

public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService() {
        this.animalRepository = new AnimalRepository();
    }

    public void cadastrar(Animal animal) throws DadosInvalidosException {
        validarAnimal(animal);

        animal.setNome_animal(animal.getNome_animal().trim());
        animal.setPorte(animal.getPorte().trim().toUpperCase(Locale.ROOT));
        animal.setSexo(animal.getSexo().trim().toUpperCase(Locale.ROOT));
        animal.setStatus_adocao(animal.getStatus_adocao().trim().toUpperCase(Locale.ROOT));

        animalRepository.salvar(animal);
    }

    public void atualizar(Animal animal) throws DadosInvalidosException {
        validarAnimal(animal);

        if (animal.getId_animal() <= 0) {
            throw new DadosInvalidosException("Selecione um animal válido para editar.");
        }

        animal.setNome_animal(animal.getNome_animal().trim());
        animal.setPorte(animal.getPorte().trim().toUpperCase(Locale.ROOT));
        animal.setSexo(animal.getSexo().trim().toUpperCase(Locale.ROOT));

        animalRepository.atualizar(animal);
    }

    public void excluir(Animal animal) throws DadosInvalidosException {
        if (animal == null) {
            throw new DadosInvalidosException("Selecione um animal para excluir.");
        }

        if (animal.getStatus_adocao().equals("ADOTADO")) {
            throw new DadosInvalidosException("Não é possível deletar um animal já adotado.");
        }

        animalRepository.excluir(animal.getId_animal());
    }

    public List<Animal> listarTodos() {
        return animalRepository.listarTodos();
    }

    public List<Animal> buscarPorNomeEStatus(String nome, String status) {
        return animalRepository.buscarPorNomeEStatus(nome, status);
    }

    private void validarAnimal(Animal animal) throws DadosInvalidosException {
        if (animal == null) {
            throw new DadosInvalidosException("Informe os dados do animal.");
        }

        if (animal.getNome_animal() == null || animal.getNome_animal().trim().isEmpty()) {
            throw new DadosInvalidosException("O nome do animal é obrigatório.");
        }

        if (animal.getNome_animal().trim().length() > 100) {
            throw new DadosInvalidosException("O nome do animal deve ter no máximo 100 caracteres.");
        }

        if (animal.getPorte() == null || animal.getPorte().trim().isEmpty()) {
            throw new DadosInvalidosException("Selecione o porte do animal.");
        }

        String porte = animal.getPorte().trim().toUpperCase(Locale.ROOT);
        if (!porte.equals("GRANDE") && !porte.equals("MEDIO") && !porte.equals("PEQUENO")) {
            throw new DadosInvalidosException("O porte deve ser GRANDE, MEDIO ou PEQUENO.");
        }

        if (animal.getSexo() == null || animal.getSexo().trim().isEmpty()) {
            throw new DadosInvalidosException("Selecione o sexo do animal.");
        }

        if (animal.getRaca() == null) {
            throw new DadosInvalidosException("Selecione a raça do animal.");
        }

        if (animal.getStatus_adocao() == null || animal.getStatus_adocao().trim().isEmpty()) {
            throw new DadosInvalidosException("Seleciona um status do animal.");
        }
    }
}
