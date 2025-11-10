-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 11/11/2025 às 18:55
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `clinica`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` bigint(20) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `endereco` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nome`, `cpf`, `telefone`, `email`, `endereco`) VALUES
(1, 'Juraci S Tolentino', '123456', '11989392294', 'sas@gagaga', 'Rua André Grabois'),
(2, 'Bruno Costa', '123', '+55-11-98888-0002', 'bruno@mail.com', 'Av. B, 456');

-- --------------------------------------------------------

--
-- Estrutura para tabela `consulta`
--

CREATE TABLE `consulta` (
  `id_consulta` bigint(20) NOT NULL,
  `data_hora` datetime NOT NULL,
  `motivo` text DEFAULT NULL,
  `diagnostico` text DEFAULT NULL,
  `id_pet` bigint(20) DEFAULT NULL,
  `id_vet` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `consulta`
--

INSERT INTO `consulta` (`id_consulta`, `data_hora`, `motivo`, `diagnostico`, `id_pet`, `id_vet`) VALUES
(1, '2025-01-10 09:30:00', 'Vacinação e checkup', 'Saudável', 1, 1),
(2, '2025-02-12 14:00:00', 'Coceira persistente', 'aa', 2, 2),
(10, '2025-10-27 02:11:00', 'qqq', 'aaa', 4, 10),
(11, '2025-11-13 16:36:00', 'aaaa', 'sasas', 4, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `exame`
--

CREATE TABLE `exame` (
  `id_exame` bigint(20) NOT NULL,
  `tipo` varchar(150) DEFAULT NULL,
  `resultado` text DEFAULT NULL,
  `data_exame` date DEFAULT NULL,
  `id_consulta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `exame`
--

INSERT INTO `exame` (`id_exame`, `tipo`, `resultado`, `data_exame`, `id_consulta`) VALUES
(1, 'Exame', 'aa', '2025-11-04', 2),
(2, 'Exame de pele', 'Presença de ácaros', '2025-02-12', 2),
(3, 'aaa', 'se', '2025-11-15', 2),
(7, 'aaaaaaw', 'Normal', '2025-11-07', 10);

-- --------------------------------------------------------

--
-- Estrutura para tabela `medicamento`
--

CREATE TABLE `medicamento` (
  `id_medicamento` bigint(20) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `descricao` text DEFAULT NULL,
  `dosagem` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `medicamento`
--

INSERT INTO `medicamento` (`id_medicamento`, `nome`, `descricao`, `dosagem`) VALUES
(1, 'Amoxilina', NULL, '1111'),
(2, 'Antibiótico Y', 'Curso por 7 dias', 'Suspensão oral 10ml 12/12h'),
(3, 'Antipulgas X	', NULL, '1111'),
(5, 'XArope', NULL, '400');

-- --------------------------------------------------------

--
-- Estrutura para tabela `pet`
--

CREATE TABLE `pet` (
  `id_pet` bigint(20) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `especie` varchar(50) DEFAULT NULL,
  `raca` varchar(100) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pet`
--

INSERT INTO `pet` (`id_pet`, `nome`, `especie`, `raca`, `data_nascimento`, `id_cliente`) VALUES
(2, 'Mimi', 'Gato', 'SRD', '2022-11-02', 2),
(3, 'Tito', 'Cachorro', 'Poodle', '2020-03-20', 2),
(4, 'aaa', 'aaaa', 'Labrador', '2025-10-31', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `prontuario`
--

CREATE TABLE `prontuario` (
  `id_pet` bigint(20) NOT NULL,
  `observacoes_gerais` text DEFAULT NULL,
  `ultima_atualizacao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `prontuario`
--

INSERT INTO `prontuario` (`id_pet`, `observacoes_gerais`, `ultima_atualizacao`) VALUES
(2, 'a', '2025-11-11 17:55:37'),
(3, 'qqwqwqwqwqw', '2025-11-11 17:55:06'),
(4, 'qqq', '2025-11-11 17:54:55');

-- --------------------------------------------------------

--
-- Estrutura para tabela `tratamento`
--

CREATE TABLE `tratamento` (
  `id_consulta` bigint(20) UNSIGNED NOT NULL,
  `id_medicamento` bigint(20) NOT NULL,
  `duracao_dias` int(11) DEFAULT NULL,
  `observacoes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tratamento`
--

INSERT INTO `tratamento` (`id_consulta`, `id_medicamento`, `duracao_dias`, `observacoes`) VALUES
(2, 1, 111, 'aaa'),
(2, 2, 22222, 'ewewsew');

-- --------------------------------------------------------

--
-- Estrutura para tabela `veterinario`
--

CREATE TABLE `veterinario` (
  `id_vet` bigint(20) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `crmv` varchar(50) NOT NULL,
  `especialidade` varchar(100) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `veterinario`
--

INSERT INTO `veterinario` (`id_vet`, `nome`, `crmv`, `especialidade`, `telefone`, `email`) VALUES
(1, 'aaaa', 'aaa', 'aaa', '+5511989392294', 'sas@gagaga'),
(2, 'Dra. Marina Lopes', 'CRMV-SP 67890', 'Dermatologia', '+55-11-96666-2222', 'marina@vet.com'),
(6, 'sasasasa', 'sasasas', 'asasa', '+5511989392294', 'sas@gagaga'),
(7, 'aaAaa', 'qqqqq', 'aqaq', '+5511989392294', 'teste@email.com'),
(9, 'bia', 'CRMV-SP 123459999', 'Pediatria', '+5511989392294', 'b@gmail.com'),
(10, 'Joao', '1234', 'Pediatria', '+5511989392294', 'b@gmail.com');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `cpf` (`cpf`);

--
-- Índices de tabela `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`id_consulta`),
  ADD KEY `fk_consulta_pet` (`id_pet`),
  ADD KEY `fk_consulta_vet` (`id_vet`);

--
-- Índices de tabela `exame`
--
ALTER TABLE `exame`
  ADD PRIMARY KEY (`id_exame`),
  ADD KEY `fk_exame_consulta` (`id_consulta`);

--
-- Índices de tabela `medicamento`
--
ALTER TABLE `medicamento`
  ADD PRIMARY KEY (`id_medicamento`);

--
-- Índices de tabela `pet`
--
ALTER TABLE `pet`
  ADD PRIMARY KEY (`id_pet`),
  ADD KEY `fk_pet_cliente` (`id_cliente`);

--
-- Índices de tabela `prontuario`
--
ALTER TABLE `prontuario`
  ADD PRIMARY KEY (`id_pet`);

--
-- Índices de tabela `tratamento`
--
ALTER TABLE `tratamento`
  ADD PRIMARY KEY (`id_consulta`,`id_medicamento`),
  ADD KEY `fk_tratamento_medicamento` (`id_medicamento`);

--
-- Índices de tabela `veterinario`
--
ALTER TABLE `veterinario`
  ADD PRIMARY KEY (`id_vet`),
  ADD UNIQUE KEY `crmv` (`crmv`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `consulta`
--
ALTER TABLE `consulta`
  MODIFY `id_consulta` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de tabela `exame`
--
ALTER TABLE `exame`
  MODIFY `id_exame` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `medicamento`
--
ALTER TABLE `medicamento`
  MODIFY `id_medicamento` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `pet`
--
ALTER TABLE `pet`
  MODIFY `id_pet` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `veterinario`
--
ALTER TABLE `veterinario`
  MODIFY `id_vet` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `fk_consulta_vet` FOREIGN KEY (`id_vet`) REFERENCES `veterinario` (`id_vet`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `exame`
--
ALTER TABLE `exame`
  ADD CONSTRAINT `fk_exame_consulta` FOREIGN KEY (`id_consulta`) REFERENCES `consulta` (`id_consulta`);

--
-- Restrições para tabelas `pet`
--
ALTER TABLE `pet`
  ADD CONSTRAINT `fk_pet_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`);

--
-- Restrições para tabelas `prontuario`
--
ALTER TABLE `prontuario`
  ADD CONSTRAINT `fk_prontuario_pet` FOREIGN KEY (`id_pet`) REFERENCES `pet` (`id_pet`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `tratamento`
--
ALTER TABLE `tratamento`
  ADD CONSTRAINT `fk_tratamento_medicamento` FOREIGN KEY (`id_medicamento`) REFERENCES `medicamento` (`id_medicamento`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
