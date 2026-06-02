# Arquitetura

O SGLI é uma aplicação desktop JavaFX organizada em camadas.

## Fluxo principal

```text
Controller -> Service -> Repository -> Database
```

## Camadas

### Controllers

Local: `src/main/java/org/desktop/system/sgli/sgli/Controller`

Responsabilidades:

- Controlar eventos da interface JavaFX.
- Carregar dados para tabelas e formulários.
- Chamar os serviços da aplicação.
- Exibir alertas e confirmações ao usuário.

### Services

Local: `src/main/java/org/desktop/system/sgli/sgli/Services`

Responsabilidades:

- Concentrar regras de negócio.
- Validar dados antes de persistir.
- Coordenar operações entre interface e repositórios.
- Gerar relatórios PDF.

### Repositories

Local: `src/main/java/org/desktop/system/sgli/sgli/Repository`

Responsabilidades:

- Executar operações JPA.
- Controlar transações.
- Acessar o banco SQLite.
- Centralizar criação de `EntityManager`.

### Entities

Local: `src/main/java/org/desktop/system/sgli/sgli/Entity`

Entidades principais:

- `ContractModel`: representa contratos de locação.
- `PaymentModel`: representa pagamentos vinculados a contratos.

## Telas

Local: `src/main/resources/org/desktop/system/sgli/sgli/view`

- `welcome-view.fxml`: tela inicial.
- `hub-view.fxml`: tela principal de contratos e pagamentos.

## Estilos

Local: `src/main/resources/org/desktop/system/sgli/sgli/styles`

- `welcome-view.css`
- `hub-view.css`

## Persistência

O projeto usa:

- SQLite como banco local.
- Hibernate como implementação JPA.
- `persistence.xml` para configuração da unidade de persistência.
- `hbm2ddl.auto=update` para atualização automática do schema.

## Sistema de módulos

O projeto usa `module-info.java`.

Ao adicionar novos pacotes que precisem de reflexão por FXML ou Hibernate, atualize as diretivas `opens` do módulo.

