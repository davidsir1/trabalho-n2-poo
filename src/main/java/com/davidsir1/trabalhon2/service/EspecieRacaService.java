package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import com.davidsir1.trabalhon2.repository.EspecieRepository;
import com.davidsir1.trabalhon2.repository.RacaRepository;

import java.sql.SQLException;
import java.util.List;

public class EspecieRacaService {
    private final EspecieRepository especieRepository = new EspecieRepository();
    private final RacaRepository racaRepository = new RacaRepository();

    public void cadastrarEspecie(String nome) throws DadosInvalidosException, SQLException {
        validarNome(nome, "espécie");
        especieRepository.salvar(new Especie(nome.trim()));
    }

    public void cadastrarRaca(String nome, Especie especie) throws DadosInvalidosException, SQLException {
        validarNome(nome, "raça");
        if (especie == null) {
            throw new DadosInvalidosException("Selecione uma espécie para a raça.");
        }
        racaRepository.salvar(new Raca(nome.trim(), especie));
    }

    public void atualizarEspecie(Especie especie, String nome) throws DadosInvalidosException, SQLException {
        if (especie == null) {
            throw new DadosInvalidosException("Selecione uma espécie para editar.");
        }
        validarNome(nome, "espécie");
        especieRepository.atualizar(new Especie(especie.getId_especie(), nome.trim()));
    }

    public void excluirEspecie(Especie especie) throws DadosInvalidosException, SQLException {
        if (especie == null) {
            throw new DadosInvalidosException("Selecione uma espécie para excluir.");
        }
        especieRepository.excluir(especie.getId_especie());
    }

    public void atualizarRaca(Raca raca, String nome, Especie especie)
            throws DadosInvalidosException, SQLException {
        if (raca == null) {
            throw new DadosInvalidosException("Selecione uma raça para editar.");
        }
        validarNome(nome, "raça");
        if (especie == null) {
            throw new DadosInvalidosException("Selecione uma espécie para a raça.");
        }
        racaRepository.atualizar(new Raca(raca.getId_raca(), nome.trim(), especie));
    }

    public void excluirRaca(Raca raca) throws DadosInvalidosException, SQLException {
        if (raca == null) {
            throw new DadosInvalidosException("Selecione uma raça para excluir.");
        }
        racaRepository.excluir(raca.getId_raca());
    }

    public List<Especie> listarEspecies() throws SQLException {
        return especieRepository.listarTodos();
    }

    public List<Raca> listarRacas() throws SQLException {
        return racaRepository.listarTodos();
    }

    public List<Raca> listarRacaPorEspecie(String especie) throws SQLException {
        return racaRepository.listarRacaPorEspecie(especie);
    }

    private void validarNome(String nome, String tipo) throws DadosInvalidosException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("O nome da " + tipo + " é obrigatório.");
        }
    }
}
