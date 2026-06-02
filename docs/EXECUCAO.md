# Como Executar

Este guia mostra como preparar, compilar e executar o SGLI pelo terminal.

## Pré-requisitos

- JDK 17 ou superior.
- Git.
- Maven instalado ou Maven Wrapper do próprio projeto.

## Clonar o projeto

```bash
git clone https://github.com/hanspeter-dietiker/sgli-system.git
```

```bash
cd sgli-system
```

## Compilar

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

## Executar

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

## Rodar testes

```bash
mvn test
```

Com Maven Wrapper no Windows:

```bash
.\mvnw.cmd test
```

## Executar um teste específico

```bash
mvn test -Dtest=NomeDaClasse
```

## Banco de dados local

O banco SQLite é criado automaticamente na primeira execução.

| Ambiente | Caminho |
| --- | --- |
| Windows | `%LOCALAPPDATA%\SGLI\sgli.db` |
| Fallback | `~/.sgli/sgli.db` |

