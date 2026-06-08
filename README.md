# <img  src="src/main/resources/org/desktop/system/sgli/sgli/assets/icon.png" height="40" width="40"/>  SGLI - Sistema 
**Sistema de Gestão de Locação e Relatórios Fiscais**

Aplicação desktop feita em JavaFX para organizar contratos de aluguel, registrar pagamentos mensais e gerar relatórios em PDF para apoio à declaração de Imposto de Renda.

---

### Visão Geral

O SGLI foi criado para centralizar a gestão de locações em um sistema simples, local e direto ao ponto.

#### O que o sistema faz

- Cadastra contratos de locação.
- Registra pagamentos mensais.
- Controla valores de aluguel, IPTU e condomínio.
- Mantém os dados salvos localmente em SQLite.
- Gera relatórios em PDF de contratos e pagamentos.
- Funciona offline, sem depender de serviços externos.

#### Público-alvo

- Pessoas que administram imóveis alugados.
- Locadores que precisam organizar dados para fins fiscais.
- Usuários que desejam um sistema desktop local, simples e privado.


### Funcionalidades

| Área | Recursos |
| --- | --- |
| Contratos | Cadastro, edição, remoção e listagem de contratos |
| Pagamentos | Registro e manutenção de pagamentos por contrato |
| Relatórios | Exportação de relatórios em PDF |
| Persistência | Banco SQLite local criado automaticamente |
| Interface | Telas JavaFX com FXML e CSS |

---

### Tecnologias

- **Java 21**
- **JavaFX 17**
- **Maven**
- **SQLite**
- **Hibernate / JPA**
- **iText PDF**
- **JUnit 5**

---

### Estrutura do Projeto

```text
SGLI/
+-- docs/
|   +-- ARQUITETURA.md
|   +-- EXECUCAO.md
|   +-- VISAO-GERAL.md
+-- src/
|   +-- main/
|       +-- java/
|       +-- resources/
+-- pom.xml
+-- mvnw
+-- mvnw.cmd
+-- README.md
```

### Principais diretórios

- `src/main/java`: código Java da aplicação.
- `src/main/resources`: telas FXML, estilos CSS e imagens.
- `docs`: documentação detalhada do projeto.
- `pom.xml`: dependências e configuração Maven.

---

## Como Executar no Terminal

### 1. Pré-requisitos

Tenha instalado:

- JDK 21 ou superior
- Git
- Maven

> Observação: o projeto também possui Maven Wrapper (`mvnw` e `mvnw.cmd`), então é possível executar usando o wrapper sem instalar o Maven globalmente.

### 2. Clonar o repositório

```bash
git clone https://github.com/hanspeter-dietiker/sgli-system.git
```

### 3. Entrar na pasta do projeto

```bash
cd sgli-system
```

### 4. Compilar o projeto

Com Maven instalado:

```bash
mvn clean install
```

No Windows, usando Maven Wrapper:

```bash
.\mvnw.cmd clean install
```

No Linux/macOS, usando Maven Wrapper:

```bash
./mvnw clean install
```

### 5. Executar a aplicação

Com Maven instalado:

```bash
mvn javafx:run
```

No Windows, usando Maven Wrapper:

```bash
.\mvnw.cmd javafx:run
```

No Linux/macOS, usando Maven Wrapper:

```bash
./mvnw javafx:run
```

---

### Banco de Dados

O banco é criado automaticamente na primeira execução.

| Sistema | Local padrão |
| --- | --- |
| Windows | `%LOCALAPPDATA%\SGLI\sgli.db` |
| Fallback | `~/.sgli/sgli.db` |

O schema é atualizado pelo Hibernate com `hbm2ddl.auto=update`.

---

### Relatórios

Os PDFs são gerados na pasta `Downloads` do usuário:

- `Relatorio_Contratos.pdf`
- `Relatorio_Pagamentos.pdf`

---

### Documentação

Para mais detalhes:

- [Visão geral](docs/VISAO-GERAL.md)
- [Como executar](docs/EXECUCAO.md)
- [Arquitetura](docs/ARQUITETURA.md)

---

### Desenvolvedor

**Hanspeter Dietiker**

- GitHub: [Hanspeter Dietiker](https://github.com/hanspeterdietiker)
- LinkedIn: [Hanspeter Dietiker](https://www.linkedin.com/in/hanspeterdietiker/)
