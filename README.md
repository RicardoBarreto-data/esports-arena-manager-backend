# 🏆 eSports Arena Manager

O **eSports Arena Manager** é um sistema de gestão para competições de esportes eletrônicos desenvolvido como parte do Projeto Integrador II do SENAC. A aplicação permite o cadastro de torneios, gerenciamento de equipes e jogadores, além do acompanhamento automático de rankings e resultados.

---

## 🚀 Funcionalidades

- **Gestão de Torneios:** Cadastro, edição e monitoramento de status (Aberto, Em Andamento, Finalizado).
- **Controle de Equipes:** Vinculação de times a torneios específicos.
- **Gestão de Jogadores:** Cadastro de atletas vinculados às suas respectivas equipes.
- **Ranking Automatizado:** Sistema de pontuação baseado no desempenho em partidas.
- **Interface Responsiva:** Painel administrativo com suporte a Modo Escuro (UX).
- **Segurança:** Controle de acesso para operações críticas.

---

## 🛠️ Tecnologias Utilizadas

- **Backend:** Java 17 + Spring Boot 3
- **Persistência:** Spring Data JPA + Hibernate
- **Banco de Dados:** MySQL 8.0
- **Frontend:** Thymeleaf (Templates HTML), CSS3 e JavaScript
- **Gestão de Qualidade:** Jira + Xray (Testes Unitários e Integrados)
- **Versionamento:** Git & GitHub

---

## 📦 Como rodar o projeto

### Pré-requisitos
* Java 17 ou superior
* Maven 3.6+
* MySQL 8.0

### Configuração do Banco de Dados
1. Crie um schema no MySQL chamado `esportsarena`.
2. No arquivo `src/main/resources/application.properties`, ajuste as credenciais se necessário:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/esportsarena
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
