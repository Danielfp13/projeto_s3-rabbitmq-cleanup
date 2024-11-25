
# Serviço de Cadastro de Usuários com AWS S3, RabbitMQ e Spring Boot

Este é um projeto que combina Spring Boot com AWS S3, RabbitMQ, PostgreSQL e outras tecnologias para criar um serviço de cadastro de usuários. Ele suporta upload de imagens, envio de emails, integração com filas RabbitMQ e agendamento de tarefas.

---

## **Tecnologias Utilizadas**

- **Spring Boot**: Framework principal para o backend.
- **AWS S3**: Para armazenamento de imagens.
- **RabbitMQ**: Para filas de mensagens e processamento assíncrono.
- **PostgreSQL**: Banco de dados relacional principal.
- **Flyway**: Para migrações de banco de dados.
- **H2**: Banco de dados em memória para testes locais.
- **Spring Mail**: Para envio de e-mails.
- **Lombok**: Para reduzir boilerplate no código.

---

## **Como Executar Localmente**

### **Pré-requisitos**

1. **Java 17+** instalado.
2. **Maven** instalado.
3. **RabbitMQ** rodando localmente ou em um servidor acessível.
4. **PostgreSQL** configurado com um banco de dados criado.
5. Variáveis de ambiente configuradas (detalhadas abaixo).
6. **AWS S3** configurado para upload de imagens.

---

### **Instalando Dependências**

Certifique-se de que todas as dependências do Maven sejam baixadas executando:

```bash
mvn clean install
```

---

### **Configuração de Variáveis de Ambiente**

Crie um arquivo `.env` ou use variáveis de ambiente para configurar as seguintes propriedades:

| Variável            | Descrição                             |
|---------------------|---------------------------------------|
| `DB_HOST`           | Endereço do banco de dados PostgreSQL |
| `DB_PORT`           | Porta do PostgreSQL (padrão: 5432)    |
| `DB_USERNAME`       | Nome de usuário do banco              |
| `DB_PASSWORD`       | Senha do banco                        |
| `AWS_ACCESS_KEY`    | Chave de acesso da AWS                |
| `AWS_SECRET_KEY`    | Chave secreta da AWS                  |
| `EMAIL_USERNAME`    | Usuário para envio de e-mails (Gmail) |
| `EMAIL_PASSWORD`    | Senha do e-mail                       |
| `APP_PROFILE`       | Perfil da aplicação                   |

---

### **Executando a Aplicação**

1. **Com Maven**:
   ```bash
   mvn spring-boot:run
   ```

2. **Com Java**:
   Gere o arquivo `.jar` com:
   ```bash
   mvn clean package
   ```
   Execute o `.jar`:
   ```bash
   java -jar target/com.daniel.s3-0.0.1-SNAPSHOT.jar
   ```

---

## **Endpoints da API**

### **Upload de Usuário**
- **URL**: `/api/users/upload`
- **Método**: `POST`
- **Descrição**: Faz upload de um usuário com uma imagem associada.

#### **Exemplo de Request**
```http
POST /api/users/upload
Content-Type: multipart/form-data

Body (form-data):
- name: user
- email: user@email.com
- password: 123456
- image: <foto-perfil.jpg>
```

---

## **Funcionalidades**

1. **Upload de Imagens**:
   - Salva as imagens no AWS S3 com uma chave única.
2. **Envio de E-mails**:
   - Envia um e-mail de boas-vindas quando um novo usuário é cadastrado.
3. **Agendamento**:
   - Remove usuários criados há mais de 24 horas automaticamente.
4. **Mensageria**:
   - Utiliza RabbitMQ para processar mensagens de boas-vindas de forma assíncrona.

---

## **Estrutura do Projeto**

```plaintext
src
├── main
│   ├── java
│   │   └── com.daniel.s3
│   │       ├── config       # Configurações da aplicação (S3, RabbitMQ, etc.)
│   │       ├── entities     # Classes de modelo (Entidades do JPA)
│   │       ├── messaging    # Consumidores de mensagens do RabbitMQ
│   │       ├── repositories # Repositórios de acesso ao banco de dados
│   │       ├── resources    # Endpoints REST
│   │       ├── scheduler    # Tarefas agendadas
│   │       └── services     # Lógica de negócios
│   └── resources
│       ├── application.yml  # Configurações da aplicação
|       ├── application-test.yml  # Configurações da aplicação de teste  
|       ├── application-prod.yml  # Configurações da aplicação em produção
│       ├── db
            └── migration
                └──V1__create_user_table.sql #DDL para o flyway

            
|        
```

---

## **Contribuindo**

1. Faça um fork do repositório.
2. Crie um branch para suas alterações:
   ```bash
   git checkout -b minha-nova-feature
   ```
3. Faça o commit de suas mudanças:
   ```bash
   git commit -m "Adiciona nova feature"
   ```
4. Faça o push do branch:
   ```bash
   git push origin minha-nova-feature
   ```
5. Abra um Pull Request.

---

## **Licença**

Este projeto é livre para uso e modificação.

---

### **Autor**

- [Daniel Fernando Pereira](https://github.com/seuusername)
