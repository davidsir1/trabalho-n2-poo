-- Comando de criação de Banco de Dados
DROP DATABASE IF EXISTS adocao_bd;
CREATE DATABASE adocao_bd;

USE adocao_bd;

-- Comando de criação do usuario
CREATE USER 'laika'@'localhost' IDENTIFIED BY '1957';

GRANT SELECT, INSERT, UPDATE, DELETE ON adocao_bd.* TO 'laika'@'localhost';

-- Modelo Físico do Pet

DROP TABLE IF EXISTS cargo;

CREATE TABLE cargo (
	id_cargo INT AUTO_INCREMENT PRIMARY KEY,
   cargo VARCHAR(60)
);

DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
       id_usuario INT AUTO_INCREMENT PRIMARY KEY,
       nome_usuario VARCHAR(100) NOT NULL,
       email_usuario VARCHAR(100) NOT NULL UNIQUE,
       senha VARCHAR(255) NOT NULL,
       id_cargo INT NOT NULL,
       FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo)
);

DROP TABLE IF EXISTS especie;

CREATE TABLE especie (
       id_especie INT AUTO_INCREMENT PRIMARY KEY,
       especie VARCHAR(60) NOT NULL
);

DROP TABLE IF EXISTS raca;

CREATE TABLE raca (
       id_raca INT AUTO_INCREMENT PRIMARY KEY,
       raca VARCHAR(50) NOT NULL,
       id_especie INT NOT NULL,
       FOREIGN KEY (id_especie) REFERENCES especie(id_especie)
);

DROP TABLE IF EXISTS animal;

CREATE TABLE animal (
       id_animal INT AUTO_INCREMENT PRIMARY KEY,
       nome_animal VARCHAR(100) NOT NULL,
       porte VARCHAR(20) NOT NULL,
       sexo VARCHAR(10) NOT NULL,
       status_adocao VARCHAR(30) NOT NULL DEFAULT 'DISPONIVEL',
       id_raca INT NOT NULL,
       FOREIGN KEY (id_raca) REFERENCES raca(id_raca)
);

DROP TABLE IF EXISTS baia;

CREATE TABLE baia (
       id_baia INT AUTO_INCREMENT PRIMARY KEY,
       cod_baia VARCHAR(30) NOT NULL,
       capacidade INT NOT NULL
);

DROP TABLE IF EXISTS alocacao_animal;

CREATE TABLE alocacao_animal (
      id_alocacao_animal INT AUTO_INCREMENT PRIMARY KEY,
      data_transferencia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      id_animal INT NOT NULL,
      id_baia INT NOT NULL,
      FOREIGN KEY (id_animal) REFERENCES animal(id_animal),
      FOREIGN KEY (id_baia) REFERENCES baia(id_baia)
);

DROP TABLE IF EXISTS adotante;

CREATE TABLE adotante (
      id_adotante INT AUTO_INCREMENT PRIMARY KEY,
      nome_adotante VARCHAR(100) NOT NULL,
      telefone VARCHAR(20) NOT NULL,
      email_adotante VARCHAR(100) NOT NULL,
      endereco VARCHAR(255),
      data_cadastro DATE NOT NULL DEFAULT (CURRENT_DATE)
);

DROP TABLE IF EXISTS adocao;

CREATE TABLE adocao (
      id_adocao INT AUTO_INCREMENT PRIMARY KEY,
      id_usuario INT NOT NULL,
      id_adotante INT NOT NULL,
      id_animal INT NOT NULL,
      data_adocao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
      FOREIGN KEY (id_adotante) REFERENCES adotante(id_adotante),
      FOREIGN KEY (id_animal) REFERENCES animal(id_animal)
);

-- Comando de inserção de dados nas tabelas

INSERT INTO cargo(id_cargo, cargo)
VALUES (1, "Admin"), (2, "Funcionario");
