package com.davidsir1.trabalhon2.service;

import com.davidsir1.trabalhon2.model.Cargo;
import com.davidsir1.trabalhon2.model.Usuario;
import com.davidsir1.trabalhon2.repository.UsuarioRepository;
import com.davidsir1.trabalhon2.utils.CriptografiaUtil;

import java.util.List;
import java.util.Locale;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void cadastrar(String nome, String email, Cargo cargo, String senha) {
        // Validar os campos
        // validar a senha

        String senhaCriptografada = CriptografiaUtil.criptografar(senha);
        Usuario novoUsuario = new Usuario(nome.trim(), email.trim().toLowerCase(Locale.ROOT), senhaCriptografada, cargo);
        usuarioRepository.salvar(novoUsuario);
    }

    public void atualizar(Usuario usuarioExistente, String nome, String email, Cargo cargo, String novaSenha) {
        if (usuarioExistente == null) {
            // Retornar uma exceção
            return;
        }

        // Validar os campos

        String senhaFinal;
        if (novaSenha != null && !novaSenha.isBlank()) {
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

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public List<Cargo> listarCargos() {
        return usuarioRepository.listarCargos();
    }
}
