<h1 align="center">sboot-nosql-geolocation</h1>

<p align="center" style="margin-bottom: 20;">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/apache%20maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/MongoDB-47A248?logo=mongodb&logoColor=fff&style=for-the-badge" alt="MongoDB" />
</p>

<p align="center">A <b>sboot-nosql-geolocation</b> é uma API REST desenvolvida em Java 21 com Spring Boot 3.5.5 para gerenciar informações de geolocalização. Ela permite o cadastro, atualização e consulta de locais, utilizando um CEP para obter dados de endereço e oferecendo buscas avançadas, incluindo consultas geoespaciais.</p>

<h2>📌 Visão Geral</h2>
<p align="justify">
Este projeto consiste em uma API de geolocalização que oferece funcionalidades para registrar locais de interesse. Ao cadastrar um novo local, a API consome um serviço externo (BrasilAPI) para obter dados de endereço e coordenadas geográficas a partir do CEP informado. Os dados são persistidos em um banco de dados não relacional <b>MongoDB</b>.
</p>
<p align="justify">
A API também disponibiliza endpoints de busca flexíveis, permitindo consultas por múltiplos atributos textuais (nome, rua, cidade, etc.) e também buscas geoespaciais, encontrando locais dentro de um raio a partir de uma coordenada de latitude e longitude. A abordagem utilizada é <b>Contract First</b>, onde a especificação OpenAPI serve como fonte da verdade para a estrutura da API. Para garantir a conversão de dados de forma eficiente entre as camadas, o projeto utiliza <b>MapStruct</b> para o mapeamento entre DTOs e as entidades de domínio.</p>

<h2>🚀 Tecnologias Utilizadas</h2>

* **Java 21 + Spring Boot 3.5.5**
* **Spring Web:** Para a construção dos endpoints RESTful.
* **Spring Data MongoDB:** Para integração e abstração do acesso ao banco de dados MongoDB.
* **MongoDB:** Banco de dados NoSQL para persistência dos dados.
* **MapStruct:** Para o mapeamento de DTOs para entidades de forma automatizada e performática.
* **OpenAPI 3:** Para documentação e especificação do contrato da API.

<h2>Endpoints da API</h2>

A API expõe os seguintes endpoints para manipulação e consulta de locais:

| Método | Endpoint                    | Descrição                                                                         |
| :----- | :-------------------------- | :-----------------------------------------------------------------------------    |
| `GET`  | `/v1/findLocation`          | Consulta um endereço e geolocalização a partir de um CEP em um serviço externo.   |
| `POST` | `/v1/location`              | Cadastra um novo local no banco de dados.                                         |
| `GET`  | `/v1/location`              | Busca locais cadastrados por nome, endereço, avaliação ou proximidade geográfica. |
| `PATCH`| `/v1/location/{idPlace}`    | Atualiza a avaliação ou o status (ativo/inativo) de um local existente.           |

<h2>🏗️ Estrutura do Projeto</h2>

```bash
sboot-nosql-geolocation
│-- src/main/java/com/portfolio/luisfmdc/sboot_nosql_geolocation
│   ├── config/               # Configurações do Spring (ex: Beans, Exception Handler)
│   ├── controller/           # Camada de Endpoints REST (API)
│   ├── service/              # Camada de regras de negócio
│   ├── repository/           # Interfaces do Spring Data para acesso ao MongoDB
│   ├── domain/               # Entidades do banco de dados (documentos)
│   ├── dto/                  # Data Transfer Objects (Requests e Responses)
│   ├── mapper/               # Interfaces MapStruct para conversão de objetos
│   └── Application.java      # Classe principal da aplicação
│-- src/main/resources
│   ├── application.yml       # Configurações da aplicação
|   ├── resources
|     ├── openapi.yml         # Arquivo de contrato OpenAPI
│-- pom.xml                   # Dependências e plugins do Maven
```

<h2>🛠️ Configuração e Execução</h2>

<h3>📌 Pré-requisitos</h3>

- Java 21 (ou superior) instalado.
- Apache Maven instalado.
- MongoDB instalado e em execução na porta padrão (27017).

<h3>📜 Configuração da Aplicação (<code>application.yml</code>)</h3>

O arquivo src/main/resources/application.yml deve conter as configurações para a conexão com o MongoDB e a URL do serviço externo de CEP.

```yaml
spring:
  application:
    name: sboot-nosql-geolocation
  data:
    mongodb:
      uri: mongodb://localhost:27017/DBGeolocation

services:
  brasilapi:
    url: [https://brasilapi.com.br/api/cep/v2](https://brasilapi.com.br/api/cep/v2)
```
<h3>🚀 Executando a API</h3>

1. Clone o repositório:

```bash
git clone [https://github.com/SEU_USUARIO/sboot-nosql-geolocation.git](https://github.com/SEU_USUARIO/sboot-nosql-geolocation.git)
cd sboot-nosql-geolocation
```

2. Compile e empacote o projeto com o Maven:

```bash
mvn clean install
```

3. Execute a aplicação:

```bash
mvn spring-boot:run
```

<h2>🧩 OpenAPI / Contract First</h2>

<p>O contrato da API está definido no arquivo <code>src/main/resources/swagger/openapi.yml</code>. Esta especificação é a fonte primária para entender todos os endpoints, parâmetros, schemas de request/response e códigos de erro possíveis.</p>

<h2>📚 Mais Informações</h2>

<p>Este projeto foi desenvolvido como um portfólio para demonstrar a implementação de uma API REST moderna utilizando Java e Spring Boot, com foco em:
<ul>
<li>Integração com banco de dados NoSQL (MongoDB).</li>
<li>Consumo de APIs externas.</li>
<li>Implementação de buscas complexas, incluindo consultas geoespaciais.</li>
<li>Utilização do MapStruct para mapeamento de objetos (DTOs/Entidades).</li>
<li>Boas práticas de desenvolvimento, como a abordagem <i>Contract First</i>.</li>
</ul>