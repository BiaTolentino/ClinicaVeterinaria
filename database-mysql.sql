CREATE DATABASE IF NOT EXISTS clinica CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE clinica;
DROP TABLE IF EXISTS tratamento;
DROP TABLE IF EXISTS exame;
DROP TABLE IF EXISTS consulta;
DROP TABLE IF EXISTS prontuario;
DROP TABLE IF EXISTS medicamento;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS veterinario;
DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(200),
    endereco TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE veterinario (
    id_vet INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    crmv VARCHAR(50) UNIQUE NOT NULL,
    especialidade VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE pet (
    id_pet INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    especie VARCHAR(50),
    raca VARCHAR(100),
    data_nascimento DATE,
    id_cliente INT NOT NULL,
    CONSTRAINT fk_pet_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE prontuario (
    id_prontuario INT PRIMARY KEY,
    observacoes_gerais TEXT,
    ultima_atualizacao DATETIME,
    CONSTRAINT fk_prontuario_pet FOREIGN KEY (id_prontuario) REFERENCES pet(id_pet) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE consulta (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    data_hora DATETIME NOT NULL,
    motivo TEXT,
    diagnostico TEXT,
    id_pet INT NOT NULL,
    id_vet INT NOT NULL,
    CONSTRAINT fk_consulta_pet FOREIGN KEY (id_pet) REFERENCES pet(id_pet) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_consulta_vet FOREIGN KEY (id_vet) REFERENCES veterinario(id_vet) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE exame (
    id_exame INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(150),
    resultado TEXT,
    data_exame DATE,
    id_consulta INT NOT NULL,
    CONSTRAINT fk_exame_consulta FOREIGN KEY (id_consulta) REFERENCES consulta(id_consulta) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE medicamento (
    id_medicamento INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descricao TEXT,
    dosagem VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tratamento (
    id_consulta INT NOT NULL,
    id_medicamento INT NOT NULL,
    duracao_dias INT,
    observacoes TEXT,
    PRIMARY KEY (id_consulta, id_medicamento),
    CONSTRAINT fk_tratamento_consulta FOREIGN KEY (id_consulta) REFERENCES consulta(id_consulta) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_tratamento_medicamento FOREIGN KEY (id_medicamento) REFERENCES medicamento(id_medicamento) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
