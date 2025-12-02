-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 02/12/2025 às 20:30
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
                                                                                         (1, 'Juraci S Tolentino', '1234567', '119993922943', 'sas@gagaga', 'Rua André Grabois'),
                                                                                         (2, 'asasas', '111', '1111', 'b@gmail.com', 'asasas');

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
                                                                                                     (2, '2025-10-28 19:33:00', 'aaa', 'qwqwqw', 4, 2),
                                                                                                     (10, '2025-11-11 19:36:00', 'wwwwwww', 'vvvvvv', 3, 1),
                                                                                                     (11, '2025-11-13 16:36:00', 'aaaa', 'sasas', 4, 2),
                                                                                                     (12, '2025-11-13 19:48:00', 'Coceira persistente', 'aaaa', 2, 2),
                                                                                                     (13, '2025-11-22 19:48:00', 'aa', 'aa', 2, 9),
                                                                                                     (14, '2025-11-06 23:56:00', 'xsx', 'za', 2, 1),
                                                                                                     (15, '2025-11-01 23:57:00', 'aa', 'aa', 5, 2);

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
                                                                                             (2, 'Mimi', 'Gato', 'SRDqq', '2022-11-02', 2),
                                                                                             (3, 'Tito', 'Cachorro', 'Poodle', '2020-03-20', 2),
                                                                                             (4, 'aaa', 'aaaa', 'Labrador', '2025-10-31', 1),
                                                                                             (5, 'qsas', 'Cachorro', 'Labrador', '2025-11-25', 1),
                                                                                             (6, 'ww', 'Cachorro', 'aaa', '2025-12-21', 2),
                                                                                             (7, 'ww', 'eee', 'Labrador', '2025-12-12', 1),
                                                                                             (8, 'bia', 'eee', 'SRD', '2025-12-28', 2),
                                                                                             (9, 'aaaaa', 'aa', 'Labrador', '2025-12-11', 1),
                                                                                             (10, 'wwq', 'eee', 'w2w2ww2', '2025-12-11', 2),
                                                                                             (11, 'dwdw', 'fefef', 'fefefe', '2025-12-19', 1),
                                                                                             (12, 'cwscsxw', 'wdwdw', 'wdwwd', '2025-12-12', 1),
                                                                                             (13, 'saaas', 'sasaas', 'asasa', '2025-12-13', 1),
                                                                                             (14, 'fdv', 'vrvr', 'vr', '2025-12-22', 1),
                                                                                             (15, 'bia', 'frf', 'SRD', '2025-12-03', 1);

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
                                                                                    (4, 'qqq', '2025-11-11 17:54:55'),
                                                                                    (5, 'aaaa', '2025-11-24 23:55:52');

-- --------------------------------------------------------

--
-- Estrutura para tabela `refresh_tokens`
--

CREATE TABLE `refresh_tokens` (
                                  `id` bigint(20) NOT NULL,
                                  `token` varchar(512) NOT NULL,
                                  `usuario_id` bigint(20) NOT NULL,
                                  `expiry_date` datetime NOT NULL,
                                  `revoked` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `refresh_tokens`
--

INSERT INTO `refresh_tokens` (`id`, `token`, `usuario_id`, `expiry_date`, `revoked`) VALUES
                                                                                         (1, 'bb0424c2-1675-4f5a-89c7-a587beb0cc52-eccf2260-7a66-4f01-b051-d6b0d439a748', 1, '2025-12-02 03:48:21', 0),
                                                                                         (2, '3986ae3b-7b6f-4b11-8da0-0c8440f847ea-b5dd9c84-5076-4eba-9f53-1b0c00071fd7', 1, '2025-12-02 03:56:26', 0),
                                                                                         (3, '44bbd1c4-9f2a-45e8-8dab-b0bf3eb3ffca-b832877a-de7a-48d3-b4c5-4c3484a3412b', 1, '2025-12-02 03:59:14', 0),
                                                                                         (4, '9fb3eda1-d4f0-4501-98cd-3cac914b8cdd-0e0c63f3-5883-4c97-b783-fc7462d7b2f4', 1, '2025-12-02 04:01:31', 0),
                                                                                         (5, '37f66e7b-afa1-4375-9e45-f2da2cbab580-c22778cc-59a1-4cf8-9af9-9319ddbd92a1', 1, '2025-12-02 04:01:58', 0),
                                                                                         (6, '2b7ce640-9a5b-4816-9155-6ec9449ba866-aa13db71-4378-4e3f-9931-d5588e0f03b7', 1, '2025-12-02 04:04:04', 0),
                                                                                         (7, 'fcb33e42-4bc9-4ecb-a059-f6fbd8146598-909dd3b9-c702-4910-949d-97049d5e66a7', 1, '2025-12-02 04:06:41', 0),
                                                                                         (8, '4c2049c9-456b-4ea4-9d3c-efa314e4fe0b-5d826c16-b81a-4ecb-a70d-4ad6af66896d', 1, '2025-12-02 04:09:15', 0),
                                                                                         (9, '4ebd9493-1937-481d-a5c8-87842e610d9c-4e577e4a-741c-4dbc-b121-56378e5a52e4', 1, '2025-12-02 04:10:42', 1),
                                                                                         (10, '4da435da-8e23-48ed-9cc1-da112201e309-22e4c5ae-837f-4a49-bf1b-b6f6499777bd', 1, '2025-12-02 04:10:59', 0),
                                                                                         (11, '56cada75-610b-4fab-8ebc-3885e60d1296-b73e5d40-e6b7-46a7-8d45-f10dd0f3fa06', 1, '2025-12-02 04:14:33', 1),
                                                                                         (12, 'ef5eb659-8ef2-47d1-a4c3-4312290e1011-b529bece-ec33-44ef-8826-cb31a30ed359', 1, '2025-12-02 04:14:50', 0),
                                                                                         (13, '12b279f6-4116-4d23-ae59-f4b773db233d-b4b42b16-697e-4812-a039-37860eea3c36', 1, '2025-12-02 04:16:16', 1),
                                                                                         (14, '9dbc0c01-792c-4614-b22e-fffc4d1046fe-7f377df4-0332-40cf-98ab-be060899d68b', 1, '2025-12-02 04:16:24', 0),
                                                                                         (15, 'd55a82f7-b647-4651-881a-1f14e155b2c6-c4c8d501-957f-4e61-bcdd-26bdeff5412b', 1, '2025-12-02 04:16:53', 1),
                                                                                         (16, '81d22940-dd28-4213-a6ab-8590e9e00d73-5dbd5932-706c-4218-bd94-c2b230a30d81', 1, '2025-12-02 04:17:09', 0),
                                                                                         (17, '73763c89-0e0c-4a61-aaf3-a3712beb4821-37a81191-3f8d-4c5a-a66a-09f311b7c462', 1, '2025-12-02 04:18:46', 1),
                                                                                         (18, 'e98e238f-6108-4cfc-8b90-86d8415f5bee-19af912d-bbdb-480a-bdb2-1e6dcb586fa4', 1, '2025-12-02 04:18:53', 0),
                                                                                         (19, 'a3181692-4d35-49d3-b723-053ca22b4c1d-451abc78-c5a1-4420-bda2-afc5ff9c81a6', 1, '2025-12-02 04:22:30', 1),
                                                                                         (20, '01e9bb73-5909-4ae3-bd5c-49a2d28c1156-3c791b59-dfca-4036-9471-7ac67df4b4d9', 1, '2025-12-02 04:22:42', 0),
                                                                                         (21, 'e1ecf036-d3f0-4d46-bcbe-a753001a1fef-c2daddee-ba48-4779-b695-f6e7f69475a3', 1, '2025-12-02 04:23:05', 1),
                                                                                         (22, '06211010-e715-49cc-81f8-bf9a6d2ae143-41b21f13-578d-44f2-a432-7c726d063745', 1, '2025-12-02 04:23:11', 0),
                                                                                         (23, '31496602-fe63-4052-9e7c-4981e2ef838b-5e39023b-e8ce-46ed-b793-63f66ffd640c', 1, '2025-12-02 04:27:32', 1),
                                                                                         (24, '7a8151a2-7f96-437c-abbf-0d2bed1ee2ab-7632cb53-e7f2-4c36-a56f-f3ec4ec5471e', 1, '2025-12-02 04:27:40', 0),
                                                                                         (25, 'e73e49f6-861e-4cca-b8db-d147abcfa2b1-7821d261-0803-4217-8ca2-86b567c7d266', 1, '2025-12-02 04:28:01', 1),
                                                                                         (26, '4c60d6fc-2410-4c6b-aa0d-d9bcf8c08402-eecdb742-46df-4d1a-8af4-2e6b7c7d6c26', 1, '2025-12-02 04:28:22', 0),
                                                                                         (27, 'e0e0a30f-709e-4d6d-b1d8-db95287c26d8-880fd737-e8e1-4b9c-a94e-27b676caac38', 1, '2025-12-02 04:28:45', 1),
                                                                                         (28, 'a4db66c8-6f22-44b7-86c3-a433d44fd8bd-fc416469-b4e0-4f68-ac2b-daac59662d88', 1, '2025-12-02 04:29:01', 0),
                                                                                         (29, 'cf974a59-95cf-4d01-9201-dac6d7d5dbf2-7ea87c36-56ab-4aa0-a0e0-0280e761fa4a', 1, '2025-12-02 04:32:55', 1),
                                                                                         (30, '5e878d3a-58c7-4f55-92ad-01c26caaf471-2bc7087b-438c-49cf-b3d3-1aae573d7358', 1, '2025-12-02 04:33:10', 0),
                                                                                         (31, 'f20b39e4-5ab9-4e78-98e7-2463d9f5d387-d3fe0ef4-b319-4bb4-a116-56260abe3bc1', 1, '2025-12-02 04:35:45', 1),
                                                                                         (32, 'a4c55993-7a7f-4061-966d-a7d3190d0316-637a2f4b-677b-4ce3-98ee-956630001949', 1, '2025-12-02 04:36:04', 0),
                                                                                         (33, '37ad4347-d38b-4782-a7ae-ee6303566a6f-8b264684-f9ad-441c-9474-78d53f8b44f4', 1, '2025-12-02 04:36:41', 1),
                                                                                         (34, '880c3da1-3d90-4d2c-8873-abc65515b4c5-7be2f88c-4870-4f93-bace-94bdab28ce7a', 1, '2025-12-02 04:36:52', 0),
                                                                                         (35, '2651a9b0-e477-46e6-ba69-7445288817e8-5749cf51-44c1-4c5d-b73d-fc1947a975b2', 1, '2025-12-02 04:38:48', 1),
                                                                                         (36, '314fabff-ac80-4d88-a820-90ade1b78a9a-5ad18c45-5606-4b3d-a9f9-3ebf20e1df64', 1, '2025-12-02 04:39:07', 0),
                                                                                         (37, '1c5cedbd-3cfd-4592-9330-f9c751ff294c-90a27d6f-428d-4c99-9a01-ad993cc2e446', 1, '2025-12-02 15:27:28', 1),
                                                                                         (38, '5cf0571e-8033-41df-9b55-d266ff20c4f2-95d6f44d-c19e-498a-91b2-6ca13999b9aa', 1, '2025-12-02 15:29:49', 0),
                                                                                         (39, '9dca7205-f78f-4016-bf12-017b9e707fdb-1966ec1a-5957-453a-9bdc-156fff179d51', 1, '2025-12-02 15:29:57', 1),
                                                                                         (40, '592e1df0-4527-410a-a82e-95c7b02899fb-30574186-0bea-4179-b32c-9eb6a9ddf5a2', 1, '2025-12-02 15:30:07', 1),
                                                                                         (41, '91a8ef85-42e8-4cc1-936d-2c84d973ee4d-52d45861-b4af-44cf-af84-089cccef96a1', 1, '2025-12-02 15:31:29', 0),
                                                                                         (42, '4dfb57df-8711-4f67-9dc3-83eb64a56b74-434c3587-eaf9-47de-9c51-99113cdc47bb', 1, '2025-12-02 15:36:57', 0),
                                                                                         (43, 'a850b1a7-c572-4a5f-b515-2ed9e185bef5-a1c4815d-7c3e-4769-b7a0-bc54c583e239', 1, '2025-12-02 15:36:57', 1),
                                                                                         (44, '034c6f17-be5c-4219-99a5-f2130662fa5e-f219bb68-d74d-4493-9251-532fb19dddd1', 1, '2025-12-02 15:36:57', 0),
                                                                                         (45, 'dd8e31bd-0aac-4819-8efb-44ccc3df204a-19c1f65f-6c3f-467c-83ec-95934a0af685', 1, '2025-12-02 15:37:31', 0),
                                                                                         (46, '14d0bec6-867e-4fbb-aeb6-07b62c554907-804ca4ea-3765-4c34-bbf3-fae8bf044ca3', 1, '2025-12-02 15:40:51', 1),
                                                                                         (47, '5e906f1a-d7a5-40cd-bfbc-7c066072ac50-f72bcf2b-08d4-4d08-af96-f32537d7c9d8', 1, '2025-12-02 15:41:03', 1),
                                                                                         (48, 'd3546561-6784-4c9e-aabc-16557b8a2e68-dd6c716a-a2af-4ccb-b508-41e7e1fea491', 1, '2025-12-02 15:43:47', 0),
                                                                                         (49, '69fb873e-d159-4f6c-ae98-8c1f67ef8968-9c59ec2e-98f3-4a52-b0b6-113ce67d4b40', 1, '2025-12-02 15:45:29', 1),
                                                                                         (50, 'a9cd8f44-004e-4a32-9449-31939a31655f-bb181173-be0a-4a26-ab3e-723f1dc2c35c', 1, '2025-12-02 15:45:40', 1),
                                                                                         (51, '0a5d703b-b052-4ad2-950d-a21e9312940d-8afe9916-896a-4c51-aadb-4043fbc5a83d', 1, '2025-12-02 15:47:26', 1),
                                                                                         (52, 'c2c9d425-1d7d-440c-a2b1-0ece50051f04-4f820fd6-a9e7-4fc7-9ef1-cf687bd32908', 1, '2025-12-02 15:54:51', 0),
                                                                                         (53, '5ba50afe-080d-4bfa-a82c-dc6a464eaca6-4031ff5a-5452-483b-98d2-9fc0a0464580', 1, '2025-12-02 15:57:00', 1),
                                                                                         (54, '0ffbb368-a74a-498a-baca-a875426a8cc8-d3ff1cec-004c-4873-bf6d-b23bd14d4524', 1, '2025-12-02 15:57:43', 0),
                                                                                         (55, '9acc814e-72ca-4b7c-850c-348cb1ba81e2-0bdecc29-6ce7-4fc7-98d1-fc67d6fa4aa4', 1, '2025-12-08 02:51:35', 1),
                                                                                         (56, '2b6409af-22fd-4a4d-a041-d29ba249526b-9e1e0d1f-ffaa-4b07-b87f-c6919dead983', 1, '2025-12-08 03:02:09', 1),
                                                                                         (57, '87c06a06-153e-401e-836d-877dbc1555ca-17993125-a35d-4e36-b89c-199e29873a64', 1, '2025-12-08 03:06:10', 0),
                                                                                         (58, '210eedaf-e2d3-4bde-9213-9cb30ce63e69-4d02abbb-28d3-44e2-8b45-1bf5b1d4810e', 1, '2025-12-08 03:06:20', 0),
                                                                                         (59, '827551c6-5e21-47aa-b51c-6976c8e0b24c-7bf6ecc7-346d-41a0-b4c8-c1b97730bc6e', 1, '2025-12-08 03:08:54', 0),
                                                                                         (60, 'cf13cb81-44fb-4b22-9304-35b57cc0121b-e72aaccd-b686-4cdb-a5e1-c6d1bb4e563f', 1, '2025-12-08 03:20:24', 1),
                                                                                         (61, '3e8d45d4-f2dd-4c21-81da-bfd57d4398ba-363769ae-2b84-49a8-b930-2c839a857af6', 1, '2025-12-08 03:20:40', 1),
                                                                                         (62, '17ce8502-0f95-453b-b2d6-34ba1de34495-5ff622eb-96d9-4601-b367-b4ad39f07967', 1, '2025-12-08 03:20:53', 0),
                                                                                         (63, '2b399c78-b0fb-441c-bcaf-6c0edb2a80c4-54367276-5251-40af-bdab-1ccb98a1bc05', 1, '2025-12-08 03:36:08', 1),
                                                                                         (64, 'fd370584-e6c7-4ca2-8e25-e8094f831c36-0086e0d8-9c17-48f9-b447-2f505dbbb1a2', 1, '2025-12-08 03:36:19', 0),
                                                                                         (65, 'bc98d3a9-1405-4bc4-ae7f-670c8b44ea93-c1325c30-a7e6-4a00-8c53-0e22296ebd59', 1, '2025-12-08 03:41:04', 1),
                                                                                         (66, '2732a620-4aa5-43fb-acda-58ba038a6a46-05f0de1c-9ebf-4cb0-8f68-01a1c2142a1a', 1, '2025-12-08 03:41:16', 0),
                                                                                         (67, '093d8770-26c7-4e05-aa7e-40afa0747129-cbdd6ebf-942b-4f5a-9b5d-06f53387733d', 1, '2025-12-08 03:44:56', 0),
                                                                                         (68, '993232b9-d695-476a-b3de-c96cb2aa3600-7b6024ac-da0f-49a5-a114-2ecd52abc6b4', 1, '2025-12-08 03:45:37', 0),
                                                                                         (69, 'e0093a51-d9c5-4a27-99e4-170c9d968bec-8213b63f-b4c6-4ea7-a221-a0c98148d67a', 1, '2025-12-08 03:46:48', 1),
                                                                                         (70, 'b201d39b-4855-4e2a-9ec2-c3e7a2ff1c5d-b3e21fa2-1d89-43bb-b812-1a33df7e41e4', 1, '2025-12-08 04:07:08', 0),
                                                                                         (71, '62bd796f-c04d-4450-8991-1af584a2273b-11b57795-5353-4dcd-95f1-776561bee7d1', 1, '2025-12-08 04:07:13', 1),
                                                                                         (72, '72391a9a-bd98-456a-81c7-1f8904989c6e-ffde46fb-aef6-418b-9b90-bd79f89b0909', 1, '2025-12-08 04:07:21', 0),
                                                                                         (73, '27b667e9-338e-44a1-ab3b-9bf9a67158ab-2517465d-5a6f-4078-ba54-a692718c1338', 1, '2025-12-08 23:15:10', 0),
                                                                                         (74, 'cf572fa3-2629-4b2e-8ef0-db6eb0e9b419-4710efdd-0e1f-4978-82e1-db1181db0a5e', 1, '2025-12-08 23:31:09', 1),
                                                                                         (75, 'def0f862-1467-4627-b4e2-4d377ab48be0-5acada9e-1551-4468-941d-c96eccdbf8ab', 1, '2025-12-09 00:44:17', 0),
                                                                                         (76, 'f739685f-f748-4f20-b16d-acee3393495b-6d9bc3a1-8063-497e-81c4-ed9f41f954d5', 1, '2025-12-09 16:49:54', 0),
                                                                                         (77, '27c3282b-e01f-4c39-ae9b-ea19fa17868a-fb541baa-01ee-4ea3-a2de-4b572efcb785', 1, '2025-12-09 17:03:13', 0);

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
    (2, 2, 22222, 'ewewsew');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuarios`
--

CREATE TABLE `usuarios` (
                            `id` bigint(20) NOT NULL,
                            `nome` varchar(100) NOT NULL,
                            `username` varchar(50) NOT NULL,
                            `senha` varchar(255) NOT NULL,
                            `papel` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuarios`
--

INSERT INTO `usuarios` (`id`, `nome`, `username`, `senha`, `papel`) VALUES
                                                                        (1, 'Ana Beatriz', 'anabeatriz', '$2a$10$QTvz3wUi6tm/NGrklnRKcuiYhIuG1LnguFk8J3s5ASVqXLqnnRmAK', 'ADMIN'),
                                                                        (2, 'Gabriel Nice', 'gabriel', '$2a$10$YQ7uNn0UJ0qkVh6B5Y8dZOHp7j2aOQylhD3pmz9Fz1Vr0v3yjUNPa', 'VET'),
                                                                        (3, 'João Assis', 'joao', '$2a$10$YQ7uNn0UJ0qkVh6B5Y8dZOHp7j2aOQylhD3pmz9Fz1Vr0v3yjUNPa', 'FUNCIONARIO'),
                                                                        (4, 'Geovana Barbosa', 'geovana', '$2a$10$YQ7uNn0UJ0qkVh6B5Y8dZOHp7j2aOQylhD3pmz9Fz1Vr0v3yjUNPa', 'FUNCIONARIO');

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
-- Índices de tabela `refresh_tokens`
--
ALTER TABLE `refresh_tokens`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `token` (`token`),
  ADD KEY `fk_refresh_usuario` (`usuario_id`);

--
-- Índices de tabela `tratamento`
--
ALTER TABLE `tratamento`
    ADD PRIMARY KEY (`id_consulta`,`id_medicamento`),
  ADD KEY `fk_tratamento_medicamento` (`id_medicamento`);

--
-- Índices de tabela `usuarios`
--
ALTER TABLE `usuarios`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

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
    MODIFY `id_consulta` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `exame`
--
ALTER TABLE `exame`
    MODIFY `id_exame` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `medicamento`
--
ALTER TABLE `medicamento`
    MODIFY `id_medicamento` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `pet`
--
ALTER TABLE `pet`
    MODIFY `id_pet` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `refresh_tokens`
--
ALTER TABLE `refresh_tokens`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- AUTO_INCREMENT de tabela `usuarios`
--
ALTER TABLE `usuarios`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `veterinario`
--
ALTER TABLE `veterinario`
    MODIFY `id_vet` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

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
-- Restrições para tabelas `refresh_tokens`
--
ALTER TABLE `refresh_tokens`
    ADD CONSTRAINT `fk_refresh_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);

--
-- Restrições para tabelas `tratamento`
--
ALTER TABLE `tratamento`
    ADD CONSTRAINT `fk_tratamento_medicamento` FOREIGN KEY (`id_medicamento`) REFERENCES `medicamento` (`id_medicamento`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
