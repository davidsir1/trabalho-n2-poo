package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Adocao;
import com.davidsir1.trabalhon2.model.Adotante;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.repository.AdocaoRepository;
import com.davidsir1.trabalhon2.repository.AdotanteRepository;
import com.davidsir1.trabalhon2.repository.AnimalRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AdocaoService {
    private final AnimalRepository animalRepository;
    private final AdocaoRepository adocaoRepository;
    private final AdotanteRepository adotanteRepository;

    public AdocaoService() {
        this.animalRepository = new AnimalRepository();
        this.adocaoRepository = new AdocaoRepository();
        this.adotanteRepository = new AdotanteRepository();
    }

    public List<Animal> listarAnimaisDisponiveis() throws SQLException {
        return animalRepository.buscarPorStatusAdocao("DISPONIVEL");
    }

    public List<Adotante> listarAdotantes() throws SQLException {
        return adotanteRepository.listarTodos();
    }

    public void registrarAdocao(Animal animal, Adotante adotante, LocalDate dataSelecionada) throws DadosInvalidosException, SQLException {
        if (adotante == null) {
            throw new DadosInvalidosException("Selecione um adotante para continuar.");
        }
        if (animal == null) {
            throw new DadosInvalidosException("Selecione um animal disponível para adoção.");
        }
        if (!"DISPONIVEL".equalsIgnoreCase(animal.getStatus_adocao())) {
            throw new DadosInvalidosException("O animal seleciona não está disponível.");
        }
        if (dataSelecionada == null) {
            throw new DadosInvalidosException("Informe a data da adoção.");
        }
        if (dataSelecionada.isAfter(LocalDate.now())) {
            throw new DadosInvalidosException("Não é possível adicionar adicionar datas futuras.");
        }

        LocalDateTime dataHoraAdocao = LocalDateTime.of(dataSelecionada, LocalTime.now());
        Adocao novaAdocao = new Adocao(
                adotante,
                animal,
                dataHoraAdocao);

        adocaoRepository.registrarAdocao(novaAdocao);
    }
}
