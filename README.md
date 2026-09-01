# 🏦 MM Bank - Sistema Bancário em Java

Um sistema bancário interativo desenvolvido em **Java** com integração a banco de dados **PostgreSQL**. O sistema oferece funcionalidades completas de gerenciamento de contas, depósitos, saques e transferências bancárias com segurança e validações.

---

## ✨ Funcionalidades

- ✅ **Autenticação de Usuários** - Acesso seguro com email e senha
- ✅ **Criar Conta** - Cadastro de novas contas com geração automática de número
- ✅ **Consultar Saldo** - Visualização do saldo disponível em tempo real
- ✅ **Depositar** - Adicionar fundos à conta com validação
- ✅ **Sacar** - Retirar fundos com validação de saldo
- ✅ **Transferências** - Transferir valores entre contas com segurança
- ✅ **Validações Robustas** - Verificações de saldo, dados e segurança
- ✅ **Transações Seguras** - Uso de prepared statements e transações com rollback

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|-----------|--------|----------|
| **Java** | 8+ | Linguagem principal |
| **PostgreSQL** | 12+ | Banco de dados relacional |
| **JDBC** | - | Conectividade com banco |
| **Git** | - | Controle de versão |

---

## 📁 Estrutura do Projeto

```
Prototipo-Sistema-Bancario-V2/
├── Java code 10 - Sys.bank_update/
│   ├── src/
│   │   ├── Main.java              # Classe principal com menu interativo
│   │   ├── Conta.java             # Modelo de dados da conta
│   │   ├── ConexaoDB.java         # Gerenciador de conexão com BD
│   │   ├── ContaDAO.java          # Data Access Object para contas
│   │   └── TransacoesDAO.java     # Data Access Object para transações
│   ├── lib/                        # Bibliotecas externas (JDBC driver)
│   └── Java code 10 - Sys.bank_update.iml
├── .env                            # Configurações de conexão (não committado)
└── README.md                        # Este arquivo
```

### 📝 Descrição das Classes

#### `Main.java`
Gerencia a interface do usuário e o fluxo do programa:
- Menu de autenticação/cadastro com validações
- Menu de operações bancárias
- Loop principal de controle
- Gerenciamento de entrada do usuário

#### `Conta.java`
Modelo que representa uma conta bancária:
- **Atributos:** nome, número, senha, email, saldo
- **Métodos:** 
  - `verificarSenha()` - Valida senha da conta
  - `mostrarSaldo()` - Exibe saldo formatado
  - `saque()` - Processa saque interno

#### `ConexaoDB.java`
Gerencia conexão com o banco de dados:
- Lê configurações do arquivo `.env`
- Usa Connection pooling simples
- Retorna conexão ativa ou null em caso de erro
- Captura exceções de SQLException e IOException

#### `ContaDAO.java`
Data Access Object para persistência de contas:
- `criarConta(nome, email, senha)` - Insere nova conta e retorna objeto Conta
- `buscarConta(numero, email)` - Busca por número e email
- `buscarContaPorNumero(numero)` - Busca apenas pelo número (usada em transferências)

#### `TransacoesDAO.java`
Data Access Object para operações de transações:
- `depositar(conta, valor)` - Adiciona saldo à conta (validação de valor > 0)
- `sacar(conta, valor)` - Remove saldo com validação de saldo suficiente
- `transferir(origem, destino, valor)` - Move valores com transação atômica

---

## 🚀 Como Executar

### Pré-requisitos

Certifique-se de ter instalado:
- ☕ **Java JDK 8** ou superior
- 🐘 **PostgreSQL 12+** instalado e em execução
- 🔗 **PostgreSQL JDBC Driver** (postgres-X.X.jar)

### 1️⃣ Configurar o Banco de Dados

Conecte-se ao PostgreSQL e execute:

```sql
-- Criar banco de dados
CREATE DATABASE banco_mm;

-- Conectar ao banco
\c banco_mm;

-- Criar tabela de contas
CREATE TABLE contas (
    numero SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(50) NOT NULL,
    saldo DECIMAL(10, 2) DEFAULT 0.00,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar índices para melhor performance
CREATE INDEX idx_email ON contas(email);
CREATE INDEX idx_numero_email ON contas(numero, email);
```

### 2️⃣ Configurar Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto com:

```env
DB_URL=jdbc:postgresql://localhost:5432/banco_mm
DB_USUARIO=seu_usuario_postgres
DB_SENHA=sua_senha_postgres
```

> ⚠️ **IMPORTANTE:** Não committe o arquivo `.env` por razões de segurança!

### 3️⃣ Adicionar JDBC Driver

1. Baixe o PostgreSQL JDBC Driver em [postgresql.org/download/jdbc](https://jdbc.postgresql.org/download)
2. Copie o arquivo `.jar` para a pasta `lib/`
3. Configure o classpath da IDE para incluir a pasta `lib/`

### 4️⃣ Compilar e Executar

**Via Terminal:**
```bash
cd "Java code 10 - Sys.bank_update"

# Compilar
javac -cp lib/*:. src/*.java -d bin/

# Executar
java -cp lib/*:bin Main
```

**Via IntelliJ IDEA:**
1. Abra o projeto
2. Vá em File → Project Structure → Libraries
3. Adicione o JAR do PostgreSQL JDBC
4. Clique em Run → Run 'Main'

**Via Eclipse:**
1. Abra o projeto
2. Vá em Project → Properties → Java Build Path
3. Abra a aba Libraries e adicione o JAR externo
4. Right-click em Main.java → Run As → Java Application

---

## 📖 Guia de Uso

### 🔐 Tela de Autenticação

```
Bem vindo ao MM bank !!
Você já é nosso cliente;[1] Sim [2] Não: 
```

### 1️⃣ Acessar Conta Existente

```
[1] Sim

Me informe o número da sua conta: 1
Me informe o email da sua conta: joao@email.com
Me informe a senha da sua conta: senha123

✓ Bem vindo de volta João Silva
```

### 2️⃣ Criar Nova Conta

```
[2] Não
[1] Sim

Me informe seu nome: Maria Santos
Me informe seu email: maria@email.com
Agora me informe uma senha: senha456

✓ Conta criada com sucesso, seu número de conta é 2
```

### 3️⃣ Menu de Operações

Após autenticado, acesse o menu:

```
Bem vindo ao Banco MM, João Silva segue abaixo o menu
----MENU----
1 - Ver saldo
2 - Depositar
3 - Sacar
4 - Transferência
5 - Sair

Me diga o que você deseja fazer:
```

#### Exemplos de Operações

**📊 Consultar Saldo (Opção 1)**
```
Me diga o que você deseja fazer: 1
Seu saldo atual é de R$ 1000.00
```

**💰 Depositar (Opção 2)**
```
Me diga o que você deseja fazer: 2
Informe o valor a ser depositado: 500
✓ Deposito realizado com sucesso, novo saldo R$ 1500.00
```

**💸 Sacar (Opção 3)**
```
Me diga o que você deseja fazer: 3
Seu saldo atual é de R$ 1500.00
Informe o valor que você deseja sacar: 200
✓ Saque realizado com sucesso, seu novo saldo é de R$ 1300.00 e o saque realizado foi de R$ 200.00
```

**🔄 Transferir (Opção 4)**
```
Me diga o que você deseja fazer: 4
Me informe o número da conta que será depositado: 2
Qual valor deseja transferir: 100
✓ Transferência realizada com sucesso, o valor transferido foi de R$ 100.00
```

---

## 🔒 Segurança

### Medidas Implementadas

| Medida | Descrição |
|--------|-----------|
| **Prepared Statements** | Proteção contra SQL Injection em todas as queries |
| **Transações Atômicas** | Transferências usam commit/rollback garantindo consistência |
| **Validação de Saldo** | Verificação antes de saques e transferências |
| **Autenticação Dupla** | Email + Senha para acesso à conta |
| **Variáveis de Ambiente** | Credenciais não são hardcoded no código |
| **Índices de Busca** | Performance otimizada em buscas frequentes |

### Sugestões para Produção

> ⚠️ Para um ambiente de produção seria necessário:
> - ✅ Criptografia de senhas (bcrypt, PBKDF2)
> - ✅ Auditoria completa de transações
> - ✅ Limite de tentativas de login
> - ✅ Recuperação de senha segura
> - ✅ API REST com autenticação JWT
> - ✅ Logs estruturados
> - ✅ Testes unitários e integração
> - ✅ Validação de dados mais rigorosa

---

## 🐛 Tratamento de Erros

### Cenários Tratados

| Cenário | Mensagem | Ação |
|---------|----------|------|
| Conta não encontrada | "Conta não encontrada" | Retorna ao login |
| Senha incorreta | "Senha incorreta" | Permite nova tentativa |
| Saldo insuficiente | "Você não tem saldo suficiente para realizar o saque" | Rejeita operação |
| Email duplicado | Erro SQL | Rejeitado no BD |
| Conexão BD falha | "Erro ao conectar ao banco: [mensagem]" | Encerra programa |
| Arquivo .env faltando | "Erro ao ler o .env: [mensagem]" | Encerra programa |
| Conta destino inválida | "Conta destino não encontrada!" | Rejeita transferência |
| Transferência com saldo baixo | "Ocorreu um erro na transferência" | Rejeita operação |

---

## ✅ Validações Implementadas

- ✓ **Email Único** - Banco rejeita duplicatas via constraint UNIQUE
- ✓ **Número Automático** - Gerado pelo SERIAL do PostgreSQL
- ✓ **Saldo Nunca Negativo** - Validações antes de saques
- ✓ **Valores Positivos** - Depositos e saques devem ser > 0
- ✓ **Saldo Suficiente** - Verificado na query de atualização
- ✓ **Existência de Contas** - Validadas antes de operações
- ✓ **Transação Atômica** - Tudo ou nada em transferências
- ✓ **Exception Handling** - Capturas de SQLException e IOException
- ✓ **Prepared Statements** - Previne SQL Injection
- ✓ **Conexão Fechada** - Liberação de recursos em finally

---

## 🔄 Fluxo de Transferência Bancária

```
┌─────────────────────────────────────────────┐
│ 1. Usuário solicita transferência           │
└──────────────┬──────────────────────────────┘
               │
┌──────────────v──────────────────────────────┐
│ 2. Validar contas origem e destino          │
└──────────────┬──────────────────────────────┘
               │
┌──────────────v──────────────────────────────┐
│ 3. Iniciar transação (setAutoCommit false)  │
└──────────────┬──────────────────────────────┘
               │
┌──────────────v──────────────────────────────┐
│ 4. Subtrair valor da conta origem           │
└──────────────┬──────────────────────────────┘
               │
        ┌──────v──────┐
        │  Sucesso?   │
        └──────┬──────┘
             Não: ROLLBACK
               │
        ┌──────v──────┐
┌───────▶│  Adicionar  │
│        │ valor dest. │
│        └──────┬──────┘
│          Não: ROLLBACK
│               │
│        ┌──────v──────┐
│        │  Sucesso?   │
│        └──────┬──────┘
│            Sim
│               │
│        ┌──────v──────────────┐
│        │ COMMIT e liberar BD  │
│        └─────────────────────┘
│
└─────── Mensagem de Sucesso
```

---

## 📊 Exemplos de Uso Completo

### Cenário 1: Novo Usuário com Deposito e Saque

```
=== EXECUÇÃO DO PROGRAMA ===

Bem vindo ao MM bank !!
Você já é nosso cliente;[1] Sim [2] Não: 2

Deseja fazer um cadastro: [1] Sim [2] Não,Sair: 1

Ok, agora irei gerar seu número de conta !!

Me informe seu nome: João Silva
Me informe seu email: joao@email.com
Agora me informe uma senha: senha123

✓ Conta criada com sucesso, seu número de conta é de 1
E sua senha é senha123

Bem vindo ao Banco MM, João Silva segue abaixo o menu
----MENU----
1 - Ver saldo
2 - Depositar
3 - Sacar
4 - Transferência
5 - Sair
Me diga o que você deseja fazer: 1

Seu saldo atual é de R$ 0.00

Bem vindo ao Banco MM, João Silva segue abaixo o menu
----MENU----
1 - Ver saldo
2 - Depositar
3 - Sacar
4 - Transferência
5 - Sair
Me diga o que você deseja fazer: 2

Informe o valor a ser depositado: 1000
✓ Deposito realizado com sucesso, este é seu novo saldo R$ 1000.00

Bem vindo ao Banco MM, João Silva segue abaixo o menu
----MENU----
1 - Ver saldo
2 - Depositar
3 - Sacar
4 - Transferência
5 - Sair
Me diga o que você deseja fazer: 3

Seu saldo atual é de R$ 1000.00
Informe o valor que você deseja sacar: 200
✓ Saque realizado com sucesso, seu novo saldo é de R$ 800.00
```

### Cenário 2: Transferência entre Contas

```
=== CONTA 1: João (saldo R$ 800) ===
Me diga o que você deseja fazer: 4

Me informe o número da conta que será depositado: 2
Qual valor deseja tranferir: 150

✓ Transferência realizada com sucesso, o valor transferido foi de R$ 150.00

=== CONTA 2: Maria (saldo R$ 0) ===
Seu saldo atual é de R$ 150.00
```

---

## 📈 Diagrama Entidade-Relacionamento (BD)

```
┌─────────────────────────────┐
│        CONTAS               │
├─────────────────────────────┤
│ numero (PK) ─────► SERIAL   │
│ nome ──────────► VARCHAR(100)
│ email (UNIQUE) ► VARCHAR(100)
│ senha ─────────► VARCHAR(50)
│ saldo ─────────► DECIMAL(10,2)
│ data_criacao ─► TIMESTAMP   │
└─────────────────────────────┘
         ▲
         │
    ÍNDICES:
    • idx_email (rápida busca por email)
    • idx_numero_email (rápida autenticação)
```

---

## 🧪 Testando o Sistema

### Teste 1: Criar Conta
```bash
[Input] 2 → 1 → João → joao@test.com → senha123
[Expected] Conta criada com sucesso
```

### Teste 2: Falha de Autenticação
```bash
[Input] 1 → 1 → senha_errada
[Expected] "Senha incorreta"
```

### Teste 3: Saque sem Saldo
```bash
[Input] 3 → 500 (saldo < 500)
[Expected] "Saldo insuficiente"
```

### Teste 4: Transferência
```bash
[Input - Conta 1] 4 → 2 → 100
[Input - Conta 2] 1 → Ver saldo = 100
[Expected] Sucesso em ambas
```

---

## 📝 Notas Importantes

### ⚠️ Protótipo em Desenvolvimento

Este é um **protótipo educacional** v2.0. Para produção seria necessário:

- Implementar criptografia de senhas
- Adicionar auditoria completa
- Criar API REST
- Implementar autenticação JWT
- Adicionar logs estruturados
- Aumentar validações
- Implementar testes automatizados

### 💡 Melhorias Futuras

- [ ] Interface gráfica com Swing/JavaFX
- [ ] API REST com Spring Boot
- [ ] Autenticação JWT
- [ ] Histórico de transações
- [ ] Criptografia de senha (bcrypt)
- [ ] Testes unitários (JUnit)
- [ ] Containerização com Docker
- [ ] CI/CD com GitHub Actions

---

## 👨‍💻 Informações do Projeto

| Propriedade | Valor |
|-------------|-------|
| **Nome** | MM Bank - Sistema Bancário |
| **Versão** | 2.0 |
| **Status** | ✅ Funcional |
| **Linguagem** | Java 8+ |
| **Banco de Dados** | PostgreSQL 12+ |
| **Padrão** | DAO (Data Access Object) |
| **Tipo** | Aplicação Console |

---

## 📞 Troubleshooting

### Problema: "Class not found: org.postgresql.Driver"
**Solução:** Verifique se o JDBC driver está em `lib/` e no classpath

### Problema: "Could not connect to database"
**Solução:** Verifique credenciais em `.env` e se PostgreSQL está rodando
```bash
sudo systemctl status postgresql
```

### Problema: "Error reading .env file"
**Solução:** Certifique-se que `.env` existe no diretório raiz do projeto

### Problema: "UNIQUE constraint violation"
**Solução:** Email já registrado, use outro email

### Problema: "Connection refused"
**Solução:** PostgreSQL não está em execução
```bash
sudo systemctl start postgresql
```

---

## 📄 Licença

Este projeto é um protótipo educacional para fins de aprendizado.

---

**Última atualização:** 31 de agosto de 2024  
**Desenvolvido em:** Java com PostgreSQL
