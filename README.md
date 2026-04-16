# Usuario Management System

Este é um sistema de gerenciamento de usuários desenvolvido em Spring Boot, que permite o cadastro, autenticação e gerenciamento de usuários, endereços e telefones. O sistema integra com a API ViaCep para obter dados de endereços brasileiros.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Data JPA** - Para persistência de dados
- **Spring Security** - Para autenticação e autorização
- **Spring Cloud OpenFeign** - Para integração com APIs externas
- **JWT (JSON Web Tokens)** - Para autenticação stateless
- **PostgreSQL** - Banco de dados relacional
- **Lombok** - Para reduzir código boilerplate
- **Gradle** - Gerenciamento de dependências e build
- **Docker** - Containerização da aplicação

## Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controller**: Camada de apresentação, expõe os endpoints REST
- **Business/Service**: Camada de negócio, contém a lógica de aplicação
- **Infrastructure**: Camada de infraestrutura
  - **Entity**: Entidades JPA
  - **Repository**: Interfaces de acesso a dados
  - **Client**: Clientes para APIs externas
  - **Security**: Configurações de segurança
  - **Exceptions**: Exceções customizadas

## Funcionalidades

### Gerenciamento de Usuários
- Cadastro de novos usuários
- Autenticação com JWT
- Busca de usuários por email ou nome
- Atualização de dados do usuário
- Exclusão de usuários

### Gerenciamento de Endereços
- Cadastro de endereços para usuários
- Atualização de endereços existentes
- Integração com ViaCep para preenchimento automático de dados de endereço

### Gerenciamento de Telefones
- Cadastro de telefones para usuários
- Atualização de telefones existentes

## Endpoints da API

### Usuários
- `POST /usuario` - Cadastrar novo usuário
- `POST /usuario/login` - Autenticação (retorna token JWT)
- `GET /usuario?email={email}` - Buscar usuário por email
- `GET /usuario/nome?nome={nome}` - Buscar usuário por nome
- `PUT /usuario` - Atualizar dados do usuário (requer token)
- `DELETE /usuario/{email}` - Excluir usuário

### Endereços
- `POST /usuario/endereco` - Cadastrar endereço (requer token)
- `PUT /usuario/endereco?id={id}` - Atualizar endereço
- `GET /usuario/endereco/{cep}` - Buscar dados de endereço via ViaCep

### Telefones
- `POST /usuario/telefone` - Cadastrar telefone (requer token)
- `PUT /usuario/telefone?id={id}` - Atualizar telefone

## Configuração e Execução

### Pré-requisitos
- Java 17
- Gradle
- PostgreSQL (ou Docker para execução com containers)

### Configuração do Banco de Dados
O sistema utiliza PostgreSQL. As configurações estão no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_usuario
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
```

### Execução Local
1. Clone o repositório
2. Configure o banco de dados PostgreSQL
3. Execute o comando:
   ```bash
   ./gradlew bootRun
   ```

### Execução com Docker
O projeto inclui Docker e Docker Compose para facilitar a execução:

1. Construa e execute os containers:
   ```bash
   docker-compose up --build
   ```

Isso irá:
- Construir a imagem da aplicação
- Iniciar o PostgreSQL
- Executar a aplicação na porta 8080

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/benevenuto/usuario/
│   │   ├── UsuarioApplication.java
│   │   ├── business/
│   │   │   ├── UsuarioService.java
│   │   │   ├── ViaCepService.java
│   │   │   ├── converter/
│   │   │   ├── dto/
│   │   │   │   ├── EnderecoDTO.java
│   │   │   │   ├── TelefoneDTO.java
│   │   │   │   └── UsuarioDTO.java
│   │   ├── controller/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── UsuarioController.java
│   │   └── infrastructure/
│   │       ├── client/
│   │       │   ├── ViaCepClient.java
│   │       │   └── ViaCepDTO.java
│   │       ├── entity/
│   │       │   ├── Endereco.java
│   │       │   ├── Telefone.java
│   │       │   └── Usuario.java
│   │       ├── exceptions/
│   │       │   ├── ConflictException.java
│   │       │   ├── ResourceNotFoundException.java
│   │       │   └── UnauthorizedException.java
│   │       ├── repository/
│   │       │   ├── EnderecoRepository.java
│   │       │   ├── TelefoneRepository.java
│   │       │   └── UsuarioRepository.java
│   │       └── security/
│   │           ├── JwtRequestFilter.java
│   │           ├── JwtUtil.java
│   │           └── SecurityConfig.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/benevenuto/usuario/
        └── utils/
```

## Modelo de Dados

### Usuario
- `id`: Long (chave primária)
- `nome`: String
- `email`: String (único)
- `senha`: String (criptografada)
- `enderecos`: List<Endereco>
- `telefones`: List<Telefone>

### Endereco
- `id`: Long (chave primária)
- `rua`: String
- `numero`: Long
- `complemento`: String
- `cidade`: String
- `estado`: String
- `cep`: String
- `usuario_id`: Long (chave estrangeira)

### Telefone
- `id`: Long (chave primária)
- `numero`: String
- `ddd`: String
- `usuario_id`: Long (chave estrangeira)

## Segurança

- Autenticação baseada em JWT
- Senhas criptografadas com BCrypt
- Filtros de segurança para endpoints protegidos
- Tratamento de exceções de segurança

## Tratamento de Erros

O sistema inclui um `GlobalExceptionHandler` que trata:
- `ResourceNotFoundException` - Recursos não encontrados
- `ConflictException` - Conflitos de dados
- `UnauthorizedException` - Acesso não autorizado

## Integração com ViaCep

O sistema integra com a API pública ViaCep (https://viacep.com.br/) para obter dados de endereços brasileiros através do CEP. Esta integração permite o preenchimento automático de campos de endereço.

## Desenvolvimento

### Build
```bash
./gradlew build
```

### Testes
```bash
./gradlew test
```

### Limpeza
```bash
./gradlew clean
```