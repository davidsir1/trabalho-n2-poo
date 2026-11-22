package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.repository.AdotanteRepository;
import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Adotante;
import java.util.regex.Pattern;
import java.util.List;

public class AdotanteService {

    private final AdotanteRepository adotanteRepository;

    public AdotanteService() {
            this.adotanteRepository = new AdotanteRepository();
    }

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");

    private void validarDados(Adotante adotante)
        throws DadosInvalidosException {

        if (adotante == null) {
            throw new DadosInvalidosException("Adotante nao informado");
        }

        if (adotante.getNome_adotante() == null ||
                adotante.getNome_adotante().trim().length() < 3 ||
                adotante.getNome_adotante().trim().length() > 100) {

            throw new DadosInvalidosException(
                    "o nome deve possuir entre 3 e 100 caracteres.");

        }

        if (adotante.getTelefone() == null||
                adotante.getTelefone().trim().isEmpty()){

            throw new DadosInvalidosException(" o telefone é obrigatorio.");
        }

        if (!adotante.getTelefone().trim().matches("^\\d{10,11}$")) {
            throw new DadosInvalidosException("O telefone deve conter apenas números (10 ou 11 dígitos com DDD)");
        }

        if (adotante.getEmail_adotante() == null ||
                adotante.getEmail_adotante().trim().isEmpty()) {

            throw new DadosInvalidosException("O e-mail é obrigatório.");
        }

        if (!EMAIL_PATTERN.matcher(
                adotante.getEmail_adotante().trim()).matches()) {

            throw new DadosInvalidosException("E-mail inválido.");
        }
    }

    public void cadastrar(Adotante adotante)
        throws DadosInvalidosException {

        try {
            validarDados(adotante);
        } catch (DadosInvalidosException e) {
            throw e;
        }

        adotanteRepository.salvar(adotante);
    }

    public void atualizar(Adotante adotante)
            throws DadosInvalidosException {

        validarDados(adotante);

        if (adotante.getId_adotante() <= 0) {
            throw new DadosInvalidosException(
                    "ID do adotante inválido."
            );

        }

        adotanteRepository.atualizar(adotante);
    }

    public void excluir(int idAdotante)
            throws DadosInvalidosException {

        if (idAdotante <= 0) {
            throw new DadosInvalidosException("ID do adotante inválido.");
        }

        if (adotanteRepository.possuiAdocao(idAdotante)) {
            throw new DadosInvalidosException(
                    "Não é possível excluir um adotante que possui adoção registrada."
            );
        }

        adotanteRepository.excluir(idAdotante);
    }

    public List<Adotante> listarTodos() {
            return adotanteRepository.listarTodos();
    }

    public List<Adotante> buscarPorNome(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            return listarTodos();
        }

        return adotanteRepository.buscarPorNome(nome.trim());
    }

}