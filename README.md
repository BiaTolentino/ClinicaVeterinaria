# 🐾 Sistema de Gestão de Clínica Veterinária

**Autores:** Ana Beatriz Santos Tolentino, 
Igor Souza Pureza, 
João Carlos Assis,
Gabriel Braulio,
Giovanna Beatriz

**Disciplina:** Desenvolvimento de Sistemas Web  
**Data:** Novembro/2025

---

## 1. Descrição do domínio modelado

O sistema modela o domínio de uma clínica veterinária. As entidades principais são:

- **Cliente:** Tutor do animal; contém informações pessoais (nome, CPF, telefone, e-mail, endereço).
- **Pet:** Animal de estimação, pertencente a um cliente. Contém espécie, raça, data de nascimento.
- **Veterinário:** Profissional que atende os pets; possui CRMV, especialidade e contato.
- **Consulta:** Atendimento que relaciona um pet e um veterinário, com data_hora, motivo e diagnóstico.
- **Exame:** Exames realizados em uma consulta (tipo, resultado, data).
- **Medicamento:** Produtos farmacológicos (nome, descrição, dosagem).
- **Tratamento:** Tabela associativa entre Consulta e Medicamento (N:N). Possui chave primária composta (id_consulta, id_medicamento) e campos adicionais (duração, observações).
- **Prontuário:** Registro clínico de um pet. Implementado como **1:1 dependente**, ou seja, **`prontuario.id_pet` é ao mesmo tempo PK e FK para `pet.id_pet`**, garantindo dependência total.

### Justificativa das entidades
- **Cliente** e **Pet** representam a relação natural tutor/pet.
- **Consulta**, **Exame**, **Medicamento** e **Tratamento** modelam o fluxo clínico (consulta gera exames e pode prescrever medicamentos).
- **Prontuário** foi modelado como entidade dependente do Pet para garantir que cada pet tenha, no máximo, um prontuário e que o prontuário não exista sem o pet — requisito da especificação.

---

## 2. Diagrama lógico das entidades e relacionamentos

Arquivo anexo: `diagrama_logico.png`

(O diagrama mostra tabelas com PKs e FKs, e as cardinalidades: Cliente (1) -> Pet (N); Pet (1) -> Prontuário (1); Consulta (N) -> Exame (1); Consulta (N) -> Tratamento (N) <- Medicamento (N); Veterinário (1) -> Consulta (N).)

---

## 3. Descrição textual das relações e operações adicionais

### Relações
- **Cliente (1) → Pet (N)**
    - `pet.id_cliente` é FK para `cliente.id_cliente`.
- **Pet (1) → Prontuário (1)**
    - `prontuario.id_pet` é PK e FK para `pet.id_pet` (dependência total).
- **Veterinário (1) → Consulta (N)**
    - `consulta.id_vet` é FK para `veterinario.id_vet`.
- **Pet (1) → Consulta (N)**
    - `consulta.id_pet` é FK para `pet.id_pet`.
- **Consulta (1) → Exame (N)**
    - `exame.id_consulta` é FK para `consulta.id_consulta`.
- **Consulta (N) ↔ Medicamento (N)** via **Tratamento**
    - `tratamento(id_consulta, id_medicamento)` com PK composta e FKs para `consulta` e `medicamento`.

### Regras de negócio e operações adicionais
- Ao excluir um **pet**, o **prontuário** associado é removido em cascata (ON DELETE CASCADE) — coerente com dependência total.
- Ao excluir um **medicamento**, os tratamentos que o referenciam também são removidos (ON DELETE CASCADE).
- Validações básicas devem existir na camada de serviço/controlador:
    - Não permitir criação de `prontuario` para um `pet` inexistente.
    - Garantir que `petId` enviado em criação/atualização do prontuário se refere ao mesmo `id` do recurso (coerência).
    - Evitar duplicidade de `prontuario` por `pet` (PK garante isso).
- Operações adicionais recomendadas:
    - Endpoint para buscar prontuário por `petId`.
    - Endpoint com filtro por tutor ou nome do pet.
    - Relatórios agregados (nº de consultas por veterinário em período).

---

## 4. Endpoints e exemplos de uso (API REST)

Base: `http://localhost:8080/api`

### Pets
- `GET /api/pets` — lista todos os pets.
- `GET /api/pets/{id}` — obtém pet por id.
- `POST /api/pets`
```
{
  "nome": "Mimi",
  "especie": "Gato",
  "raca": "SRD",
  "dataNascimento": "2022-11-02",
  "idCliente": 2
}
```
- `PUT /api/pets/{id}` — atualiza pet.
- `DELETE /api/pets/{id}` — remove pet.

### Prontuários
- `GET /api/prontuarios` — lista todos os prontuários (com dados do pet e tutor).
- `GET /api/prontuarios/{id}` — retorna prontuário (id = idPet).
- `POST /api/prontuarios`
```
{
  "petId": 2,
  "observacoesGerais": "Histórico de dermatite"
}
```
- `PUT /api/prontuarios/{id}`
```
{
  "petId": 2,
  "observacoesGerais": "Atualizado: sem sinais"
}
```
- `DELETE /api/prontuarios/{id}` — remove o prontuário (id = idPet).

### Consulta / Exame / Tratamento / Medicamento
Endpoints CRUD análogos, por exemplo:
- `POST /api/consultas`
- `GET /api/exames?consultaId=2`
- `POST /api/tratamentos` (ou criar via nested resource ao salvar uma consulta)

---

## 5. Instruções de execução

### Requisitos
- Java 17+, Maven 3.8+, MySQL/MariaDB, Node.js (opcional para frontend).

### Configuração rápida
1. Criar banco `clinica` (charset utf8mb4).
2. Ajustar `src/main/resources/application.properties` com credenciais.
3. Rodar:
```bash
mvn clean package
java -jar target/clinica-0.0.1-SNAPSHOT.jar
```
4. Frontend: abrir `index.html` ou servir via servidor estático (live-server).

---

## 6. Estrutura do repositório e entrega
Inclua na raiz do repositório:
- Código-fonte do backend (`src/`).
- Código-fonte do frontend (`src/main/resources/static` ou pasta `web`).
- `README.md` (este arquivo).
- `diagrama_logico.png` (arquivo com o diagrama).
- arquivo SQL `clinica.sql` com a definição do esquema e dados de exemplo.

---

## 7. Observações finais
O projeto atende aos critérios solicitados: modelagem completa, chaves e relações corretas, endpoints REST testáveis e documentação. Para nota máxima, recomenda-se:
- Incluir testes automatizados (unit e integration).
- Implementar camada de serviços para regras de negócio complexas.
- Adicionar validações e tratamento de exceções padronizado.

---
