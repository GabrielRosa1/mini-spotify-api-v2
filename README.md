<div align="center">

<img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/>
<img src="https://img.shields.io/badge/REST_API-005571?style=for-the-badge&logo=fastapi&logoColor=white"/>

# 🎧 Mini Spotify API

> **API REST** desenvolvida com **Spring Boot** que simula as funcionalidades essenciais de uma plataforma de streaming de música, inspirada no Spotify.

[![Status](https://img.shields.io/badge/Status-Ativo-1DB954?style=flat-square)]()
[![Licença](https://img.shields.io/badge/Licença-MIT-blue?style=flat-square)]()
[![Armazenamento](https://img.shields.io/badge/Storage-In--Memory-orange?style=flat-square)]()

</div>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias-utilizadas)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Entidades](#-entidades-do-projeto)
- [Endpoints](#-endpoints-crud)
- [Regras de Negócio](#-regras-de-negócio-implementadas)
- [Exclusão Lógica](#-exclusão-lógica)
- [Validações](#-validações-implementadas)
- [Como Executar](#-como-executar-o-projeto)
- [Exemplos de Requisições](#-exemplos-de-requisições)
- [Coleção Postman](#-coleção-postman)
- [Autor](#-autor)

---

## 🎯 Sobre o Projeto

A aplicação permite o gerenciamento completo de:

| Módulo | Descrição |
|--------|-----------|
| 👤 **Usuários** | Cadastro e controle de acesso à plataforma |
| 🎤 **Artistas** | Registro de artistas e bandas |
| 💿 **Álbuns** | Gerenciamento de álbuns musicais |
| 🎵 **Músicas** | Controle de faixas e reproduções |
| 📂 **Playlists** | Criação e curadoria de playlists |
| 📊 **Estatísticas** | Histórico e preferências musicais dos usuários |

Além do CRUD tradicional, a aplicação implementa **regras de negócio específicas** como reprodução de músicas, adição de músicas em playlists e relatório das músicas mais reproduzidas.

### 🎓 Objetivo Acadêmico

> Desenvolvida para demonstrar na prática os seguintes conceitos:

- ✅ Modelagem de entidades
- ✅ Separação em camadas (Controller / Service)
- ✅ Regras de negócio
- ✅ Boas práticas REST
- ✅ Validações de entrada
- ✅ Exclusão lógica

---

## 🛠 Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white) | 17+ | Linguagem principal |
| ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white) | 3.x | Framework da aplicação |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apache-maven&logoColor=white) | — | Gerenciador de dependências |
| `UUID` | — | Identificação das entidades |
| `HashMap` | — | Armazenamento em memória |

---

## 🧱 Estrutura do Projeto

```
src/main/java/com/insper/mini_spotify_api/
│
├── 👤 Usuario.java
├──    UsuarioController.java
├──    UsuarioService.java
│
├── 🎤 Artista.java
├──    ArtistaController.java
├──    ArtistaService.java
│
├── 💿 Album.java
├──    AlbumController.java
├──    AlbumService.java
│
├── 🎵 Musica.java
├──    MusicaController.java
├──    MusicaService.java
│
├── 📂 Playlist.java
├──    PlaylistController.java
├──    PlaylistService.java
│
├── 📊 Estatisticas.java
├──    EstatisticasController.java
├──    EstatisticasService.java
│
└── 🚀 MiniSpotifyApiApplication.java
```

---

## 🗂 Entidades do Projeto

<details>
<summary><strong>👤 Usuário</strong> — Representa um usuário da plataforma</summary>

| Atributo | Tipo |
|----------|------|
| `id` | `UUID` |
| `nome` | `String` |
| `email` | `String` |
| `tipoPlano` | `ENUM: FREE, PREMIUM` |
| `ativo` | `boolean` |
| `dataCriacao` | `LocalDateTime` |

</details>

<details>
<summary><strong>🎤 Artista</strong> — Representa um artista ou banda</summary>

| Atributo | Tipo |
|----------|------|
| `id` | `UUID` |
| `nome` | `String` |
| `generoMusical` | `String` |
| `paisOrigem` | `String` |
| `albuns` | `List<Album>` |
| `ativo` | `boolean` |

</details>

<details>
<summary><strong>💿 Álbum</strong> — Representa um álbum musical</summary>

| Atributo | Tipo |
|----------|------|
| `id` | `UUID` |
| `titulo` | `String` |
| `dataLancamento` | `LocalDate` |
| `artista` | `Artista` |
| `musicas` | `List<Musica>` |
| `ativo` | `boolean` |

</details>

<details>
<summary><strong>🎵 Música</strong> — Representa uma faixa disponível na plataforma</summary>

| Atributo | Tipo |
|----------|------|
| `id` | `UUID` |
| `titulo` | `String` |
| `duracaoSegundos` | `int` |
| `numeroFaixa` | `int` |
| `album` | `Album` |
| `artista` | `Artista` |
| `totalReproducoes` | `long` |
| `ativo` | `boolean` |

</details>

<details>
<summary><strong>📂 Playlist</strong> — Representa uma playlist criada por um usuário</summary>

| Atributo | Tipo |
|----------|------|
| `id` | `UUID` |
| `nome` | `String` |
| `publica` | `Boolean` |
| `dataCriacao` | `LocalDateTime` |
| `usuario` | `Usuario` |
| `musicas` | `List<Musica>` |
| `ativo` | `boolean` |

</details>

<details>
<summary><strong>📊 Estatísticas</strong> — Histórico e preferências de consumo musical do usuário</summary>

| Atributo | Tipo |
|----------|------|
| `usuario` | `Usuario` |
| `musicasReproduzidas` | `Integer` |
| `artistaFavorito` | `Artista` |
| `albumFavorito` | `Album` |
| `musicaFavorita` | `Musica` |
| `tempoReproducao` | `Integer` |

</details>

### 🔗 Relacionamentos

```
Artista  ──(1:N)──▶  Álbum
Álbum    ──(1:N)──▶  Música
Usuário  ──(1:N)──▶  Playlist
Playlist ──(N:N)──▶  Música
Usuário  ──(1:1)──▶  Estatísticas
```

> ⚠️ Como o projeto utiliza armazenamento em memória, os relacionamentos são representados por **referências entre objetos**.

---

## 🌐 Endpoints CRUD

### 👤 Usuários — `/usuarios`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/usuarios` | Criar usuário |
| `GET` | `/usuarios` | Listar usuários |
| `GET` | `/usuarios/{id}` | Buscar usuário por ID |
| `PUT` | `/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/usuarios/{id}` | Excluir usuário (lógico) |
| `PUT` | `/usuarios/reativar/{id}` | Reativar usuário |

### 🎤 Artistas — `/artistas`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/artistas` | Criar artista |
| `GET` | `/artistas` | Listar artistas |
| `GET` | `/artistas/{id}` | Buscar artista por ID |
| `PUT` | `/artistas/{id}` | Atualizar artista |
| `DELETE` | `/artistas/{id}` | Excluir artista (lógico) |
| `PUT` | `/artistas/reativar/{id}` | Reativar artista |

### 💿 Álbuns — `/albuns`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/albuns` | Criar álbum |
| `GET` | `/albuns` | Listar álbuns |
| `GET` | `/albuns/{id}` | Buscar álbum por ID |
| `PUT` | `/albuns/{id}` | Atualizar álbum |
| `DELETE` | `/albuns/{id}` | Excluir álbum (lógico) |
| `PUT` | `/albuns/reativar/{id}` | Reativar álbum |

### 🎵 Músicas — `/musicas`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/musicas` | Criar música |
| `GET` | `/musicas` | Listar músicas |
| `GET` | `/musicas/{id}` | Buscar música por ID |
| `PUT` | `/musicas/{id}` | Atualizar música |
| `DELETE` | `/musicas/{id}` | Excluir música (lógico) |
| `PUT` | `/musicas/reativar/{id}` | Reativar música |

### 📂 Playlists — `/playlists`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/playlists` | Criar playlist |
| `GET` | `/playlists` | Listar playlists |
| `GET` | `/playlists/{id}` | Buscar playlist por ID |
| `PUT` | `/playlists/{id}` | Atualizar playlist |
| `DELETE` | `/playlists/{id}` | Excluir playlist (lógico) |
| `PUT` | `/playlists/reativar/{id}` | Reativar playlist |

### 📊 Estatísticas — `/usuarios/{usuarioId}/estatisticas`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/usuarios/{usuarioId}/estatisticas` | Criar estatísticas |
| `GET` | `/usuarios/{usuarioId}/estatisticas` | Buscar estatísticas |
| `PUT` | `/usuarios/{usuarioId}/estatisticas` | Atualizar estatísticas |
| `DELETE` | `/usuarios/{usuarioId}/estatisticas` | Excluir estatísticas |

---

## 🚀 Regras de Negócio Implementadas

### 1️⃣ Reproduzir Música

```http
POST /musicas/{id}/reproduzir
X-USER-ID: {idUsuario}
```

| Regra | Descrição |
|-------|-----------|
| ▶️ Reprodução | Incrementa `totalReproducoes` da música |
| 🔑 Autenticação | Exige o usuário via header `X-USER-ID` |
| 🚫 Bloqueio | Impede reprodução se o usuário estiver inativo |
| 📊 Estatísticas | Atualiza automaticamente as estatísticas do usuário |

---

### 2️⃣ Adicionar Música à Playlist

```http
POST /playlists/{playlistId}/musicas/{musicaId}
X-USER-ID: {idUsuario}
```

| Regra | Descrição |
|-------|-----------|
| 🔒 Propriedade | Apenas o dono da playlist pode adicionar músicas |
| 🔁 Duplicatas | Não permite adicionar a mesma música duas vezes |
| ✅ Playlist ativa | A playlist precisa existir e estar ativa |
| ✅ Música ativa | A música precisa existir e estar ativa |

---

### 3️⃣ Top 10 Músicas Mais Reproduzidas

```http
GET /relatorios/top-musicas
```

> Retorna as **10 músicas** com maior número de reproduções em **ordem decrescente**, contendo:

- 🎵 Título da música
- 🎤 Nome do artista
- 🔢 Total de reproduções

---

## ❌ Exclusão Lógica

Todas as entidades utilizam **exclusão lógica**. O campo `ativo` é alterado para `false` em vez de remover o registro.

```
DELETE /entidade/{id}  →  ativo: true  ──▶  ativo: false
PUT /entidade/reativar/{id}  →  ativo: false  ──▶  ativo: true
```

| Comportamento | Descrição |
|---------------|-----------|
| 👁️ Listagens | Registros excluídos **não aparecem** |
| 🚫 Operações | Registros inativos **não podem** ser usados |
| ♻️ Reativação | Possível via `/reativar/{id}` |

---

## ✅ Validações Implementadas

- 🔴 Campos obrigatórios não podem ser nulos ou vazios
- 📧 Não é permitido cadastrar usuários com **e-mail duplicado**
- 🎵 Não é permitido cadastrar músicas ou playlists com dados inválidos
- 🚫 **Usuários inativos** não podem reproduzir músicas
- 🚫 **Usuários inativos** não podem criar playlists
- 🔗 Artistas e álbuns precisam existir para serem associados a músicas
- 🔁 Não é permitido **adicionar música repetida** na mesma playlist

---

## ▶️ Como Executar o Projeto

### Pré-requisitos

- ☕ Java 17+
- 📦 Maven
- 💻 IDE Java (ex.: IntelliJ IDEA)

### Passos

**1. Clone o repositório:**

```bash
git clone https://github.com/GabrielRosa1/mini-spotify-api.git
```

**2. Entre na pasta do projeto:**

```bash
cd mini-spotify-api
```

**3. Execute a aplicação:**

```bash
mvn spring-boot:run
```

> Ou rode a classe principal `MiniSpotifyApiApplication.java` diretamente pela IDE.

### 🌍 Base URL

```
http://localhost:8080
```

---

## 🧪 Exemplos de Requisições

<details>
<summary><strong>👤 Criar Usuário</strong></summary>

```http
POST /usuarios
Content-Type: application/json
```

```json
{
  "nome": "Gabriel",
  "email": "gabriel@email.com",
  "tipoPlano": "PREMIUM"
}
```

</details>

<details>
<summary><strong>🎤 Criar Artista</strong></summary>

```http
POST /artistas
Content-Type: application/json
```

```json
{
  "nome": "The Weeknd",
  "generoMusical": "Pop",
  "paisOrigem": "Canadá"
}
```

</details>

<details>
<summary><strong>💿 Criar Álbum</strong></summary>

```http
POST /albuns
Content-Type: application/json
```

```json
{
  "titulo": "After Hours",
  "artista": {
    "id": "UUID_DO_ARTISTA"
  }
}
```

</details>

<details>
<summary><strong>🎵 Criar Música</strong></summary>

```http
POST /musicas
Content-Type: application/json
```

```json
{
  "titulo": "Blinding Lights",
  "duracaoSegundos": 200,
  "numeroFaixa": 1,
  "album": {
    "id": "UUID_DO_ALBUM"
  },
  "artista": {
    "id": "UUID_DO_ARTISTA"
  }
}
```

</details>

<details>
<summary><strong>📂 Criar Playlist</strong></summary>

```http
POST /playlists
Content-Type: application/json
```

```json
{
  "nome": "Favoritas",
  "publica": true,
  "usuario": {
    "id": "UUID_DO_USUARIO"
  },
  "musicas": []
}
```

</details>

<details>
<summary><strong>▶️ Reproduzir Música</strong></summary>

```http
POST /musicas/{id}/reproduzir
X-USER-ID: UUID_DO_USUARIO
```

</details>

<details>
<summary><strong>➕ Adicionar Música à Playlist</strong></summary>

```http
POST /playlists/{playlistId}/musicas/{musicaId}
X-USER-ID: UUID_DO_USUARIO
```

</details>

---

## 📮 Coleção Postman

O projeto acompanha uma **coleção Postman** para facilitar os testes dos endpoints.

📁 `postman/MiniSpotify.postman_collection.json`

### 🗺️ Ordem sugerida para testes

```
1. 👤 Criar usuário
2. 🎤 Criar artista
3. 💿 Criar álbum
4. 🎵 Criar música
5. 📂 Criar playlist
6. ▶️ Reproduzir música
7. ➕ Adicionar música à playlist
8. 📊 Consultar estatísticas do usuário
9. 🏆 Consultar top músicas
```

---

## 📝 Observações Técnicas

> ⚠️ **Armazenamento em memória:** o projeto utiliza `HashMap`, sem banco de dados. Os dados são perdidos ao reiniciar a aplicação.

- 🔑 IDs gerados automaticamente com `UUID`
- 🛡️ Respostas de erro tratadas com `try/catch` nos controllers
- 🏗️ Foco em **modelagem**, **regras de negócio** e **organização em camadas**

---

## 📊 Critérios Atendidos

- [x] Modelagem das entidades
- [x] CRUD completo das entidades principais
- [x] Regras de negócio solicitadas
- [x] Estrutura em camadas (Controller e Service)
- [x] Exclusão lógica com reativação
- [x] Organização e clareza do código
- [x] Validações de entrada

---

## 👨‍💻 Autor

<div align="center">

**Gabriel Rosa**

Projeto desenvolvido para a disciplina de **Arquitetura de Objetos e Times Ágeis**

[![GitHub](https://img.shields.io/badge/GitHub-GabrielRosa1-181717?style=for-the-badge&logo=github)](https://github.com/GabrielRosa1/mini-spotify-api)

</div>