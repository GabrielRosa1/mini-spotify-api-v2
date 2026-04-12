<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=1DB954&height=200&section=header&text=Mini%20Spotify%20API&fontSize=52&fontColor=ffffff&fontAlignY=38&desc=REST%20API%20com%20Spring%20Boot%20inspirada%20no%20Spotify&descAlignY=58&descSize=18" width="100%"/>

<br/>

<img src="https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-16+-316192?style=for-the-badge&logo=postgresql&logoColor=white"/>
<img src="https://img.shields.io/badge/REST_API-005571?style=for-the-badge"/>

<br/><br/>

[![Status](https://img.shields.io/badge/Status-Ativo-1DB954?style=flat-square&logo=spotify&logoColor=white)]()
[![Licença](https://img.shields.io/badge/Licença-MIT-blue?style=flat-square)]()
[![Storage](https://img.shields.io/badge/Storage-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)]()

<br/>

> 🎧 **API REST** desenvolvida com **Spring Boot** que simula funcionalidades essenciais de uma plataforma de streaming de música, inspirada no Spotify.

</div>

---

## 📋 Índice

- [🎯 Sobre o Projeto](#-sobre-o-projeto)
- [🛠 Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [🧱 Estrutura do Projeto](#-estrutura-do-projeto)
- [🗂 Entidades do Projeto](#-entidades-do-projeto)
- [🔗 Relacionamentos](#-relacionamentos)
- [🌐 Endpoints](#-endpoints)
- [🚀 Regras de Negócio](#-regras-de-negócio-implementadas)
- [❌ Exclusão Lógica](#-exclusão-lógica)
- [✅ Validações](#-validações-implementadas)
- [▶️ Como Executar](#-como-executar-o-projeto)
- [🧪 Exemplos de Requisições](#-exemplos-de-requisições)
- [📮 Coleção Postman](#-coleção-postman)
- [📝 Observações Técnicas](#-observações-técnicas)

---

## 🎯 Sobre o Projeto

A aplicação permite o gerenciamento completo de uma plataforma de música:

<div align="center">

| Módulo | Descrição |
|:------:|-----------|
| 👤 **Usuários** | Cadastro e gerenciamento de usuários da plataforma |
| 🎤 **Artistas** | Registro de artistas e bandas |
| 💿 **Álbuns** | Gerenciamento de álbuns musicais |
| 🎵 **Músicas** | Cadastro de músicas, reprodução e ranking |
| 📂 **Playlists** | Criação, edição e curadoria de playlists |
| 📊 **Estatísticas** | Histórico de reprodução e preferências do usuário |

</div>

Além do CRUD tradicional, a aplicação implementa regras de negócio como:

- 🔁 Reprodução de músicas com atualização automática de estatísticas
- 🔒 Adição de músicas à playlist com validação de dono
- 🏆 Ranking das músicas mais reproduzidas

### 🎓 Objetivo Acadêmico

Projeto desenvolvido para demonstrar na prática os seguintes conceitos:

| | Conceito |
|:-:|---------|
| ✅ | Modelagem de entidades |
| ✅ | Relacionamentos com JPA |
| ✅ | Persistência com PostgreSQL |
| ✅ | Separação em camadas (`Controller`, `Service`, `Repository`) |
| ✅ | Regras de negócio |
| ✅ | Boas práticas REST |
| ✅ | Validações de entrada |
| ✅ | Exclusão lógica |

---

## 🛠 Tecnologias Utilizadas

<div align="center">

| Tecnologia | Versão | Descrição |
|:----------:|:------:|-----------|
| ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white) | 25 | Linguagem principal |
| ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white) | 4.0.3 | Framework da aplicação |
| ![JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&logo=spring&logoColor=white) | 4.x | Persistência e repositórios |
| ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white) | 7.x | ORM e mapeamento de entidades |
| ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white) | 16+ | Banco de dados relacional |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apache-maven&logoColor=white) | — | Gerenciador de dependências |
| ![Jackson](https://img.shields.io/badge/Jackson-000000?style=flat-square) | — | Serialização JSON |

</div>

---

## 🧱 Estrutura do Projeto

```text
src/main/java/com/insper/mini_spotify_api/
│
├── 👤 Usuario.java
├──    UsuarioController.java
├──    UsuarioService.java
├──    UsuarioRepository.java
│
├── 🎤 Artista.java
├──    ArtistaController.java
├──    ArtistaService.java
├──    ArtistaRepository.java
│
├── 💿 Album.java
├──    AlbumController.java
├──    AlbumService.java
├──    AlbumRepository.java
│
├── 🎵 Musica.java
├──    MusicaController.java
├──    MusicaService.java
├──    MusicaRepository.java
│
├── 📂 Playlist.java
├──    PlaylistController.java
├──    PlaylistService.java
├──    PlaylistRepository.java
│
├── 📊 Estatisticas.java
├──    EstatisticasController.java
├──    EstatisticasService.java
├──    EstatisticasRepository.java
│
└── 🚀 MiniSpotifyApiApplication.java
```

---

## 🗂 Entidades do Projeto

<details>
<summary><strong>👤 Usuário</strong></summary>
<br/>

| Atributo | Tipo |
|----------|------|
| `id` | UUID |
| `nome` | String |
| `email` | String |
| `tipoPlano` | `ENUM: FREE, PREMIUM` |
| `ativo` | boolean |
| `dataCriacao` | LocalDateTime |
| `estatisticas` | Estatisticas |
| `playlists` | List\<Playlist\> |

</details>

<details>
<summary><strong>🎤 Artista</strong></summary>
<br/>

| Atributo | Tipo |
|----------|------|
| `id` | UUID |
| `nome` | String |
| `generoMusical` | String |
| `paisOrigem` | String |
| `albuns` | List\<Album\> |
| `ativo` | boolean |

</details>

<details>
<summary><strong>💿 Álbum</strong></summary>
<br/>

| Atributo | Tipo |
|----------|------|
| `id` | UUID |
| `titulo` | String |
| `dataLancamento` | LocalDate |
| `artista` | Artista |
| `musicas` | List\<Musica\> |
| `ativo` | boolean |

</details>

<details>
<summary><strong>🎵 Música</strong></summary>
<br/>

| Atributo | Tipo |
|----------|------|
| `id` | UUID |
| `titulo` | String |
| `duracaoSegundos` | int |
| `numeroFaixa` | int |
| `album` | Album |
| `artista` | Artista |
| `totalReproducoes` | long |
| `ativo` | boolean |

</details>

<details>
<summary><strong>📂 Playlist</strong></summary>
<br/>

| Atributo | Tipo |
|----------|------|
| `id` | UUID |
| `nome` | String |
| `publica` | Boolean |
| `dataCriacao` | LocalDateTime |
| `usuario` | Usuario |
| `musicas` | List\<Musica\> |
| `ativo` | boolean |

</details>

<details>
<summary><strong>📊 Estatísticas</strong></summary>
<br/>

| Atributo | Tipo |
|----------|------|
| `id` | UUID |
| `usuario` | Usuario |
| `musicasReproduzidas` | Integer |
| `artistaFavorito` | Artista |
| `albumFavorito` | Album |
| `musicaFavorita` | Musica |
| `tempoReproducao` | Integer |

</details>

---

## 🔗 Relacionamentos

```
👤 Usuário  ──(1:N)──▶  📂 Playlist
👤 Usuário  ──(1:1)──▶  📊 Estatísticas
🎤 Artista  ──(1:N)──▶  💿 Álbum
💿 Álbum    ──(1:N)──▶  🎵 Música
📂 Playlist ──(N:N)──▶  🎵 Música
```

> Todos os relacionamentos são persistidos com **JPA/Hibernate** no **PostgreSQL**.

---

## 🌐 Endpoints

### 👤 Usuários — `/usuarios`

| Método | Endpoint | Descrição |
|:------:|----------|-----------|
| `POST` | `/usuarios` | Criar usuário |
| `GET` | `/usuarios` | Listar usuários |
| `GET` | `/usuarios/{id}` | Buscar usuário por ID |
| `PUT` | `/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/usuarios/{id}` | Excluir logicamente |
| `PUT` | `/usuarios/reativar/{id}` | Reativar usuário |

### 🎤 Artistas — `/artistas`

| Método | Endpoint | Descrição |
|:------:|----------|-----------|
| `POST` | `/artistas` | Criar artista |
| `GET` | `/artistas` | Listar artistas |
| `GET` | `/artistas/{id}` | Buscar artista por ID |
| `PUT` | `/artistas/{id}` | Atualizar artista |
| `DELETE` | `/artistas/{id}` | Excluir logicamente |
| `PUT` | `/artistas/reativar/{id}` | Reativar artista |

### 💿 Álbuns — `/albuns`

| Método | Endpoint | Descrição |
|:------:|----------|-----------|
| `POST` | `/albuns` | Criar álbum |
| `GET` | `/albuns` | Listar álbuns |
| `GET` | `/albuns/{id}` | Buscar álbum por ID |
| `PUT` | `/albuns/{id}` | Atualizar álbum |
| `DELETE` | `/albuns/{id}` | Excluir logicamente |
| `PUT` | `/albuns/reativar/{id}` | Reativar álbum |

### 🎵 Músicas — `/musicas`

| Método | Endpoint | Descrição |
|:------:|----------|-----------|
| `POST` | `/musicas` | Criar música |
| `GET` | `/musicas` | Listar músicas |
| `GET` | `/musicas/{id}` | Buscar música por ID |
| `PUT` | `/musicas/{id}` | Atualizar música |
| `DELETE` | `/musicas/{id}` | Excluir logicamente |
| `PUT` | `/musicas/reativar/{id}` | Reativar música |
| `POST` | `/musicas/{id}/reproduzir` | ▶️ Reproduzir música |
| `GET` | `/musicas/top-musicas` | 🏆 Top 10 mais reproduzidas |

### 📂 Playlists — `/playlists`

| Método | Endpoint | Descrição |
|:------:|----------|-----------|
| `POST` | `/playlists` | Criar playlist |
| `GET` | `/playlists` | Listar playlists |
| `GET` | `/playlists/{id}` | Buscar playlist por ID |
| `PUT` | `/playlists/{id}` | Atualizar playlist |
| `DELETE` | `/playlists/{id}` | Excluir logicamente |
| `PUT` | `/playlists/reativar/{id}` | Reativar playlist |
| `POST` | `/playlists/{playlistId}/musicas/{musicaId}` | ➕ Adicionar música |

### 📊 Estatísticas — `/usuarios/{usuarioId}/estatisticas`

| Método | Endpoint | Descrição |
|:------:|----------|-----------|
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
|:-----:|-----------|
| ▶️ **Reprodução** | Incrementa `totalReproducoes` da música |
| 🔑 **Header obrigatório** | Exige o usuário via `X-USER-ID` |
| 🚫 **Bloqueio** | Usuário inativo não pode reproduzir |
| 📊 **Estatísticas** | Cria/atualiza automaticamente as estatísticas do usuário |

### 2️⃣ Adicionar Música à Playlist

```http
POST /playlists/{playlistId}/musicas/{musicaId}
X-USER-ID: {idUsuario}
```

| Regra | Descrição |
|:-----:|-----------|
| 🔒 **Propriedade** | Apenas o dono da playlist pode adicionar músicas |
| 🔁 **Duplicatas** | Não permite adicionar a mesma música duas vezes |
| ✅ **Playlist ativa** | A playlist precisa existir e estar ativa |
| ✅ **Música ativa** | A música precisa existir e estar ativa |

### 3️⃣ Ranking de Músicas

```http
GET /musicas/top-musicas
```

> Retorna até **10 músicas ativas** ordenadas por `totalReproducoes` em ordem decrescente.

---

## ❌ Exclusão Lógica

As entidades utilizam exclusão lógica através do campo `ativo`:

`Usuario` · `Artista` · `Album` · `Musica` · `Playlist`

```
DELETE /entidade/{id}        →  ativo: true  ──▶  ativo: false
PUT    /entidade/reativar/{id}  →  ativo: false ──▶  ativo: true
```

| Comportamento | Descrição |
|:-------------:|-----------|
| 👁️ **Listagens** | Registros inativos não aparecem |
| 🚫 **Operações** | Registros inativos não podem ser usados nas regras de negócio |
| ♻️ **Reativação** | Possível via endpoint `/reativar/{id}` |

> ⚠️ **Exceção:** A entidade `Estatísticas` **não** utiliza exclusão lógica — o `DELETE` remove o registro fisicamente do banco.

---

## ✅ Validações Implementadas

- 🔴 Campos obrigatórios não podem ser nulos ou vazios
- 📧 Não é permitido cadastrar usuários com **e-mail duplicado**
- 🎤 Não é permitido cadastrar artistas com **nome duplicado**
- 💿 Não é permitido cadastrar álbuns com **título duplicado**
- 🎵 Não é permitido cadastrar músicas com **título duplicado**
- 📂 Não é permitido cadastrar playlists com **nome duplicado**
- 🔢 `numeroFaixa` e `duracaoSegundos` devem ser **maiores ou iguais a zero**
- ✅ Artistas e álbuns devem **existir e estar ativos** para associação
- 🚫 Usuários **inativos não podem reproduzir** músicas
- 🔒 Apenas o **dono da playlist** pode adicionar músicas
- 🔁 Não é permitido adicionar **música repetida** na mesma playlist

---

## ▶️ Como Executar o Projeto

### Pré-requisitos

- ☕ Java 25
- 📦 Maven
- 🐘 PostgreSQL acessível
- 🖥️ IDE Java (opcional)

### Variáveis de Ambiente

A aplicação utiliza variáveis de ambiente para conectar ao banco:

| Variável | Descrição |
|----------|-----------|
| `DB_URL` | URL de conexão JDBC |
| `DB_USERNAME` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |

### `application.properties`

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Passo a Passo

**1. Clone o repositório**
```bash
git clone https://github.com/GabrielRosa1/mini-spotify-api-v2.git
```

**2. Entre na pasta do projeto**
```bash
cd mini-spotify-api-v2
```

**3. Configure as variáveis de ambiente**

<details>
<summary>🪟 PowerShell</summary>

```powershell
$env:DB_URL="jdbc:postgresql://HOST:PORT/BANCO?sslmode=require"
$env:DB_USERNAME="SEU_USUARIO"
$env:DB_PASSWORD="SUA_SENHA"
```
</details>

<details>
<summary>🐧 Linux / macOS</summary>

```bash
export DB_URL="jdbc:postgresql://HOST:PORT/BANCO?sslmode=require"
export DB_USERNAME="SEU_USUARIO"
export DB_PASSWORD="SUA_SENHA"
```
</details>

**4. Execute a aplicação**
```bash
mvn spring-boot:run
```

> Ou rode a classe `MiniSpotifyApiApplication.java` diretamente pela IDE.

**🌍 Base URL:** `http://localhost:8080`

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
  "album": { "id": "UUID_DO_ALBUM" },
  "artista": { "id": "UUID_DO_ARTISTA" }
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
  "usuario": { "id": "UUID_DO_USUARIO" },
  "musicas": []
}
```
</details>

<details>
<summary><strong>➕ Adicionar Música à Playlist</strong></summary>

```http
POST /playlists/{playlistId}/musicas/{musicaId}
X-USER-ID: UUID_DO_USUARIO
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
<summary><strong>🏆 Top Músicas</strong></summary>

```http
GET /musicas/top-musicas
```
</details>

---

## 📮 Coleção Postman

O projeto acompanha uma coleção Postman para facilitar os testes:

📁 `MiniSpotifyAPI_Corrigida.postman_collection.json`

### Ordem sugerida para testes

```
1. 👤 Criar usuário
2. 🎤 Criar artista
3. 💿 Criar álbum
4. 🎵 Criar música
5. 📂 Criar playlist
6. ➕ Adicionar música à playlist
7. ▶️ Reproduzir música
8. 📊 Buscar estatísticas
9. 🏆 Buscar top músicas
```

> A coleção foi organizada com um fluxo principal, rotas de CRUD extra e requests de estatísticas separadas como testes manuais/opcionais.

---

## 📝 Observações Técnicas

- 🐘 Persistência com **PostgreSQL** via Spring Data JPA
- 🔑 IDs gerados automaticamente com **UUID**
- 🛡️ Respostas de erro tratadas com `try/catch` nos controllers
- 🔗 Uso de **relacionamentos JPA** entre as entidades
- 🔄 Serialização JSON controlada com anotações **Jackson** para evitar recursão infinita
- 📅 `dataCriacao` e `dataLancamento` definidos automaticamente em alguns fluxos de criação
- 📊 Estatísticas criadas automaticamente ao reproduzir música pela primeira vez

---

## 📊 Critérios Atendidos

<div align="center">

| | Critério |
|:-:|---------|
| ✅ | Modelagem das entidades |
| ✅ | CRUD completo das entidades principais |
| ✅ | Relacionamentos persistidos com JPA |
| ✅ | Regras de negócio solicitadas |
| ✅ | Estrutura em camadas (Controller, Service, Repository) |
| ✅ | Exclusão lógica com reativação |
| ✅ | Persistência em banco relacional |
| ✅ | Organização e clareza do código |
| ✅ | Validações de entrada |

</div>

---

## 👨‍💻 Autor

<div align="center">

<img src="https://github.com/GabrielRosa1.png" width="100px" style="border-radius: 50%; border: 3px solid #1DB954;"/>

**Gabriel Rosa**

[![GitHub](https://img.shields.io/badge/GitHub-GabrielRosa1-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/GabrielRosa1)

<br/>

*Projeto desenvolvido para a disciplina de **Arquitetura de Objetos e Times Ágeis***

<img src="https://capsule-render.vercel.app/api?type=waving&color=1DB954&height=100&section=footer" width="100%"/>

</div>