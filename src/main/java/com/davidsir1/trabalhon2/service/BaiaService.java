package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.AlocacaoAnimal;
import com.davidsir1.trabalhon2.model.Baia;
import com.davidsir1.trabalhon2.repository.AlocacaoAnimalRepository;
import com.davidsir1.trabalhon2.repository.AnimalRepository;
import com.davidsir1.trabalhon2.repository.BaiaRepository;

import java.sql.SQLException;
import java.util.List;

public class BaiaService {
    private final AnimalRepository animalRepository = new AnimalRepository();
    private final BaiaRepository baiaRepository = new BaiaRepository();
    private final AlocacaoAnimalRepository alocacaoAnimalRepository = new AlocacaoAnimalRepository();

    public void cadastrarBaia(String codigo, int capacidade) throws DadosInvalidosException, SQLException {
        validarBaia(codigo, capacidade);
        baiaRepository.salvar(new Baia(codigo.trim(), capacidade));
    }

    public void atualizarBaia(Baia baia, String codigo, int capacidade)
            throws DadosInvalidosException, SQLException {
        if (baia == null) {
            throw new DadosInvalidosException("Selecione uma baia para editar.");
        }
        validarBaia(codigo, capacidade);
        if (capacidade < baiaRepository.contarAnimaisAlocados(baia.getId_baia())) {
            throw new DadosInvalidosException("A capacidade não pode ser menor que a quantidade de animais alocados.");
        }
        baiaRepository.atualizar(new Baia(baia.getId_baia(), codigo.trim(), capacidade));
    }

    public void excluirBaia(Baia baia) throws DadosInvalidosException, SQLException {
        if (baia == null) {
            throw new DadosInvalidosException("Selecione uma baia para excluir.");
        }
        baiaRepository.excluir(baia.getId_baia());
    }

    public void alocarAnimal(Animal animal, Baia baia) throws DadosInvalidosException, SQLException {
        if (animal == null) {
            throw new DadosInvalidosException("Selecione um animal para alocar.");
        }
        if (baia == null) {
            throw new DadosInvalidosException("Selecione uma baia para alocar o animal.");
        }
        if (baia.getCapacidade() <= 0) {
            throw new DadosInvalidosException("A capacidade da baia deve ser maior que zero.");
        }
        if (baiaRepository.contarAnimaisAlocados(baia.getId_baia()) >= baia.getCapacidade()) {
            throw new DadosInvalidosException("A baia selecionada está lotada.");
        }
        if (animal.getStatus_adocao().equals("ADOTADO")) {
            throw new DadosInvalidosException("Não é possível alocar um animal já adotado.");
        }

        alocacaoAnimalRepository.salvar(new AlocacaoAnimal(animal, baia));
    }

    public List<Baia> listarBaias() throws SQLException {
        return baiaRepository.listarTodos();
    }

    public List<Animal> listarAnimais() throws SQLException {
        return animalRepository.listarTodos();
    }

    public List<AlocacaoAnimal> listarHistoricoAlocacoes() throws SQLException {
        return alocacaoAnimalRepository.listarHistorico();
    }

    private void validarBaia(String codigo, int capacidade) throws DadosInvalidosException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new DadosInvalidosException("O código da baia é obrigatório.");
        }
        if (capacidade <= 0) {
            throw new DadosInvalidosException("A capacidade da baia deve ser maior que zero.");
        }
    }
}
