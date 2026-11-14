package com.davidsir1.trabalhon2.utils;

import org.mindrot.jbcrypt.BCrypt;

public final class CriptografiaUtil {
    private static final int FATOR = 12;

    private CriptografiaUtil(){}

    public static String criptografar(String senha) {
        if (senha == null || senha.isBlank()) {
            // Retornar uma exceção dizendo que a senha não pode ser vazia
            System.err.println("A senha não pode ser nula ou vazia.");
            return null;
        }

        try {
            return BCrypt.hashpw(senha, BCrypt.gensalt(FATOR));
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao gerar o hash da senha: " + e.getMessage());
            return null;
        }
    }

    public static boolean verificarSenha(String senha, String hash) {
        if (senha == null || hash == null || senha.isBlank() || hash.isBlank()) {
            return false;
        }

        try {
            return BCrypt.checkpw(senha, hash);
        } catch (IllegalArgumentException e) {
            System.err.println("Hash de senha armazenado em formato inválido: " + e.getMessage());
            return false;
        }
    }
}
