🐾 Sistema de Gestão de Clínica Veterinária

Autores: Ana Beatriz Tolentino, Igor Souza Pureza, João Carlos Rodrigues de Assis, Gabriel Braulio, Giovanna Beatriz
Disciplina: Desenvolvimento de Sistemas Web
Data: Novembro/2025

1. Descrição do Domínio Modelado

O sistema representa o domínio de uma clínica veterinária, contendo as seguintes entidades principais:

Cliente: Tutor do animal, com dados pessoais (nome, CPF, telefone, e-mail e endereço).

Pet: Animal pertencente a um cliente, contendo espécie, raça e data de nascimento.

Veterinário: Profissional responsável pelos atendimentos, com CRMV, especialidade e informações de contato.

Consulta: Atendimento que relaciona um pet a um veterinário, contendo data/hora, motivo e diagnóstico.

Exame: Exames associados a uma consulta (tipo, resultado, data).

Medicamento: Produtos farmacológicos cadastrados (nome, descrição e dosagem).

Tratamento: Relação N:N entre Consulta e Medicamento. Possui chave primária composta (id_consulta, id_medicamento) e campos adicionais como duração e observações.

Prontuário: Registro clínico de um pet, modelado como relação 1:1 dependente. O campo prontuario.id_pet é simultaneamente PK e FK para pet.id_pet, garantindo que o prontuário exista apenas quando houver um pet correspondente.

Justificativa da Modelagem

Cliente e Pet representam a relação natural tutor/animal.

Consulta, Exame, Medicamento e Tratamento descrevem o fluxo clínico (consultas podem gerar exames e prescrever medicamentos).

O Prontuário foi modelado como entidade dependente para assegurar que cada pet possua, no máximo, um prontuário, e que o registro não exista sem o pet — conforme exigido na especificação.

2. Diagrama Lógico de Entidades e Relacionamentos

Arquivo anexo: diagrama_logico.png

(O diagrama apresenta tabelas, chaves primárias/estrangeiras e cardinalidades: Cliente (1) → Pet (N); Pet (1) → Prontuário (1); Consulta (N) → Exame (1); Consulta (N) → Tratamento (N) ← Medicamento (N); Veterinário (1) → Consulta (N).)

3. Descrição Textual das Relações e Operações Adicionais
   Relações

Cliente (1) → Pet (N)

pet.id_cliente é FK para cliente.id_cliente.

Pet (1) → Prontuário (1)

prontuario.id_pet é PK e FK para pet.id_pet.

Veterinário (1) → Consulta (N)

consulta.id_vet é FK para veterinario.id_vet.

Pet (1) → Consulta (N)

consulta.id_pet é FK para pet.id_pet.

Consulta (1) → Exame (N)

exame.id_consulta é FK para consulta.id_consulta.

Consulta (N) ↔ Medicamento (N) por meio de Tratamento

tratamento(id_consulta, id_medicamento) com chave primária composta e FKs para consulta e medicamento.

Regras de Negócio e Operações Complementares

Ao excluir um pet, o prontuário associado deve ser removido automaticamente (ON DELETE CASCADE), devido à dependência total.

Ao excluir um medicamento, os tratamentos vinculados também devem ser removidos (ON DELETE CASCADE).

Validações recomendadas na camada de serviço/controlador:

Impedir criação de prontuário para pets inexistentes.

Garantir coerência entre petId do payload e o recurso manipulado.

Evitar duplicidade de prontuários (a PK já garante essa restrição).

Operações adicionais sugeridas:

Endpoint para consulta de prontuário por petId.

Filtros por nome do pet ou por tutor.

Relatórios, como número de consultas por veterinário em determinado período.

4. Endpoints e Exemplos de Uso (API REST)

Base da API: http://localhost:8080/api

Pets

GET /api/pets — retorna todos os pets.

GET /api/pets/{id} — retorna um pet específico.

POST /api/pets

{
"nome": "Mimi",
"especie": "Gato",
"raca": "SRD",
"dataNascimento": "2022-11-02",
"idCliente": 2
}


PUT /api/pets/{id} — atualiza dados do pet.

DELETE /api/pets/{id} — exclui o pet.

Prontuários

GET /api/prontuarios — lista todos os prontuários (incluindo informações do pet e tutor).

GET /api/prontuarios/{id} — retorna o prontuário correspondente ao pet (id = idPet).

POST /api/prontuarios

{
"petId": 2,
"observacoesGerais": "Histórico de dermatite"
}


PUT /api/prontuarios/{id}

{
"petId": 2,
"observacoesGerais": "Atualizado: sem sinais"
}


DELETE /api/prontuarios/{id} — exclui o prontuário do pet.

Consultas, Exames, Tratamentos e Medicamentos

Endpoints CRUD seguem a mesma estrutura, por exemplo:

POST /api/consultas

GET /api/exames?consultaId=2

POST /api/tratamentos (ou inclusão junto ao cadastro da consulta)

5. Instruções de Execução
   Requisitos

Java 17+, Maven 3.8+, MySQL/MariaDB, Node.js (opcional para o frontend).

Passo a passo

Criar o banco de dados clinica com charset utf8mb4.

Configurar credenciais no arquivo src/main/resources/application.properties.

Executar:

mvn clean package
java -jar target/clinica-0.0.1-SNAPSHOT.jar


Frontend: abrir index.html ou servir via servidor estático (ex.: live-server).

6. Estrutura do Repositório

A raiz do repositório deve conter:

Código-fonte do backend (src/).

Código-fonte do frontend (src/main/resources/static ou diretório web).

Arquivo README.md.

diagrama_logico.png (diagrama do banco).

Arquivo SQL clinica.sql com definição do esquema e dados de exemplo.