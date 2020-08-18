# Registro de Pessoas

## Resumo

Neste repositório contém a aplicação para registro de pessoas, com as seguintes informações para cadastro:

- **Nome** - obrigatório
- **Sexo** - não obrigatório, deve ser validado caso preenchido
- **E-mail**
- **Data de Nascimento** - obrigatório, deve ser validada
- **Naturalidade**
- **Nacionalidade**
- **CPF**  - obrigatório, deve ter formato validado e não pode haver dois cadastros com mesmo CPF

Para acessar a aplicação em ambiente cloud é só clicar aqui, [Registro de Pessoas](https://personalregister-front.herokuapp.com)

O usuário de acesso é ***guest*** e senha ***guest***

## Tecnologias utilizadas

- **Angular** - versão 8.2.5
- **Spring Boot** -  versão 2.3.2


## Informações do Back-End

Aplicação Back-end implementada com Java 11 e Spring Boot 2.3.2 e bando de dados MongoDB. Compostas para gerenciar dois domínios:

- **Pessoa**: atendendo o requisito do cadastro de pessoas.
- **Usuário**: atendendo aos requisitos de segurança. 

Para atender as necessidade do sistema foram implementadas as seguintes endpoints para consumo:

- Buscar por todas as pessoas com pagináção:

```
GET /person
```

- Buscar de pessoa por CPF:

```bach
GET /person/48294973009
```

- Criação de pessoa:

```bach
POST /person
```

- Atualização de pessoa por CPF:

```bach
PUT /person/48294973009
```

- Exclusão de pessoa por CPF:

```bach
DELETE /person/48294973009
```

- Criação de usuário para autenticação:

```bach
POST /user
```

- Geração de token JWT de usuário:

```bach
POST /user/auth
```

Para requisitar aos endpoints foi disponibilizado uma collection para importação no Postman. O arquivo pode ser encontrado na raiz do repositório, ***person-register.postman_collection.json***.

## Informações do Front-End

Aplicação Front-end implementada com TypeScript e Angular 8. Composta pelos seguintes componentes principais:

- **login** - para dar acesso ao usuário.
- **add-person** - para cadastrar pessoa.
- **edit-person** - para editar informações de pessoa.
- **list-person** - para listar pessoas cadastradas.



## Execução das aplicações localmente

- Back-End: contendo o Maven instalado você pode utilizar a IDE que desejar e dar o start na aplicação, ou simplesmente executar o comando abaixo na raiz do projeto do back-end (personalregister/):

```bash
mvn spring-boot:run
```

- Front-End: utilizando o Angular CLI em sua máquina, execute o comando abaixo na raiz do projeto forn-end (personal-register-front/):

```bash
ng serve
```

Caso não tenha o Angular CLI instalado, rodar o comando abaixo:

```bash
npm install -g @angular/cli