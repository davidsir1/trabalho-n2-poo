package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Cargo;
import com.davidsir1.trabalhon2.model.Usuario;
import com.davidsir1.trabalhon2.repository.UsuarioRepository;
import com.davidsir1.trabalhon2.utils.CriptografiaUtil;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class UsuarioService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$"
    );

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void cadastrar(String nome, String email, Cargo cargo, String senha) throws DadosInvalidosException {
        // Validar os campos
        validarCamposComuns(nome, email, cargo, 0);
        // validar a senha
        validarSenha(senha);

        String senhaCriptografada = CriptografiaUtil.criptografar(senha);
        Usuario novoUsuario = new Usuario(nome.trim(), email.trim().toLowerCase(Locale.ROOT), senhaCriptografada, cargo);
        usuarioRepository.salvar(novoUsuario);
    }

    public void atualizar(Usuario usuarioExistente, String nome, String email, Cargo cargo, String novaSenha) throws DadosInvalidosException {
        if (usuarioExistente == null) {
            // Retornar uma exceção
            throw new DadosInvalidosException("Usuário para edição não informado.");
        }

        // Validar os campos
        validarCamposComuns(nome, email, cargo, usuarioExistente.getId_usuario());

        String senhaFinal;
        if (novaSenha != null && !novaSenha.isBlank()) {
            validarSenha(novaSenha);
            senhaFinal = CriptografiaUtil.criptografar(novaSenha);
        } else {
            senhaFinal = usuarioExistente.getSenha();
        }

        usuarioExistente.setNome_usuario(nome.trim());
        usuarioExistente.setEmail_usuario(email.trim());
        usuarioExistente.setCargo(cargo);
        usuarioExistente.setSenha(senhaFinal);

        usuarioRepository.atualizar(usuarioExistente);
    }

    public void excluir(Usuario usuario) throws DadosInvalidosException {
        if (usuario == null) {
            throw new DadosInvalidosException("Selecione um usuário para excluir.");
        }

        usuarioRepository.excluir(usuario.getId_usuario());
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public List<Cargo> listarCargos() {
        return usuarioRepository.listarCargos();
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.buscarPorNome(nome);
    }

    private void validarCamposComuns(String nome, String email, Cargo cargo, int idUsuario) throws DadosInvalidosException{
        if (nome == null || nome.trim().length() < 3) {
            throw new DadosInvalidosException("O nome do usuário deve ter pelo menos 3 caracteres.");
        }

        if (nome.trim().length() > 100) {
            throw new DadosInvalidosException("O nome do usuário deve ter no máximo 100 caracteres.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new DadosInvalidosException("O e-mail é obrigatório.");
        }

        String emailNormalizado = email.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(emailNormalizado).matches()) {
            throw new DadosInvalidosException("Informe um endereço de e-mail válido.");
        }

        if (usuarioRepository.existeEmailEmOutroUsuario(emailNormalizado, idUsuario)) {
            throw new DadosInvalidosException("Este e-mail já está cadastrado para outro usuário.");
        }

        if (cargo == null) {
            throw new DadosInvalidosException("Selecione um cargo para o usuário.");
        }
    }

    private void validarSenha(String senha) throws DadosInvalidosException {
        if (senha == null || senha.length() < 6) {
            throw new DadosInvalidosException("A senha deve conter no mínimo 6 caracteres.");
        }
    }
}
