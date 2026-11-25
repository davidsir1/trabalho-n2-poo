-- Comando de criação de Banco de Dados
DROP DATABASE IF EXISTS adocao_bd;
CREATE DATABASE adocao_bd;

USE adocao_bd;

-- Comando de criação do usuario
CREATE USER 'laika'@'%' IDENTIFIED BY '1957';

GRANT SELECT, INSERT, UPDATE, DELETE ON adocao_bd.* TO 'laika'@'localhost';

-- Modelo Físico do Pet

DROP TABLE IF EXISTS especie;

CREATE TABLE especie (
       id_especie INT AUTO_INCREMENT PRIMARY KEY,
       especie VARCHAR(60) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS raca;

CREATE TABLE raca (
       id_raca INT AUTO_INCREMENT PRIMARY KEY,
       raca VARCHAR(50) NOT NULL UNIQUE,
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
       cod_baia VARCHAR(30) NOT NULL UNIQUE,
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
      email_adotante VARCHAR(100) NOT NULL UNIQUE,
      endereco VARCHAR(255),
      data_cadastro DATE NOT NULL DEFAULT (CURRENT_DATE)
);

DROP TABLE IF EXISTS adocao;

CREATE TABLE adocao (
      id_adocao INT AUTO_INCREMENT PRIMARY KEY,
      id_adotante INT NOT NULL,
      id_animal INT NOT NULL,
      data_adocao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (id_adotante) REFERENCES adotante(id_adotante),
      FOREIGN KEY (id_animal) REFERENCES animal(id_animal)
);

-- Comando de inserção de dados nas tabelas

-- Inserção de animais, espécies e raças na tabela
INSERT INTO especie(id_especie, especie) VALUES
(1,'Cachorro'),
(2,'Gato'),
(3,'Ave'),
(4,'Roedor');

INSERT INTO raca(raca, id_especie)
VALUES
('Labrador', 1),
('Golden Retriever', 1),
('Pinscher', 1),
('Bulldog Françês', 1),
('Siamês', 2),
('Persa', 2),
('Angorá', 2),
('Bengal', 2),
('Calopsita', 3),
('Agapornis', 3),
('Cacatua', 3),
('Hamster Sírio', 4),
('Porquinho-da-Índia', 4);

-- Inserção de Adotantes na tabela
INSERT INTO adotante(nome_adotante, telefone, email_adotante, endereco)
VALUES
('Mariana Oliveira Santos', '11991230001', 'mariana.santos@exemplo.com.br', 'Rua das Acácias, 142 - Vila Mariana'),
('Lucas Henrique Ferreira', '21992340002', 'lucas.ferreira@exemplo.com.br', 'Av. Atlântica, 890, Apto 302 - Copacabana'),
('Camila Costa Almeida', '31993450003', 'camila.almeida@exemplo.com.br', 'Rua Pernambuco, 415 - Savassi'),
('Rafael Souza Lima', '41994560004', 'rafael.lima@exemplo.com.br', 'Alameda Dr. Carlos de Carvalho, 1020 - Batel'),
('Beatriz Rocha Mendes', '51995670005', 'beatriz.mendes@exemplo.com.br', 'Rua Padre Chagas, 275, Sala 4 - Moinhos de Vento');

-- Inserção de Baias na tabela
INSERT INTO baia(cod_baia, capacidade) VALUES
('B101', 4),
('B102', 2),
('B103', 3),
('B104', 1),
('B105', 1),
('B106', 2),
('B107', 2),
('B108', 2),
('B109', 1);

-- Inserção de Animais na tabela
INSERT INTO animal(nome_animal, porte, sexo, status_adocao, id_raca) VALUES
('Thor', 'GRANDE', 'MACHO', 'DISPONIVEL', 1),
('Mel', 'GRANDE', 'FÊMEA', 'DISPONIVEL', 2),
('Pipoca', 'PEQUENO', 'FÊMEA', 'ADOTADO', 3),
('Bento', 'MÉDIO', 'MACHO', 'DISPONIVEL', 4),
('Luna', 'PEQUENO', 'FÊMEA', 'DISPONIVEL', 5),
('Simba', 'MÉDIO', 'MACHO', 'EM TRATAMENTO', 6),
('Nina', 'PEQUENO', 'FÊMEA', 'DISPONIVEL', 7),
('Oliver', 'MÉDIO', 'MACHO', 'ADOTADO', 8),
('Piu', 'PEQUENO', 'MACHO', 'DISPONIVEL', 9),
('Kiwi', 'PEQUENO', 'FÊMEA', 'DISPONIVEL', 10),
('Fred', 'MÉDIO', 'MACHO', 'INDISPONIVEL', 11),
('Bolinha', 'PEQUENO', 'MACHO', 'DISPONIVEL', 12),
('Amora', 'PEQUENO', 'FÊMEA', 'DISPONIVEL', 13);
