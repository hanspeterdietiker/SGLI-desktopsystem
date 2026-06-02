# Visão Geral

O **SGLI System** é uma aplicação desktop para gestão de contratos de locação e geração de relatórios fiscais.

## Objetivo

Facilitar a organização de dados relacionados a contratos de aluguel, pagamentos mensais e valores utilizados em relatórios para Imposto de Renda.

## Principais recursos

- Cadastro de contratos.
- Cadastro de pagamentos.
- Relatórios em PDF.
- Banco de dados local.
- Funcionamento offline.
- Interface gráfica com JavaFX.

## Dados gerenciados

### Contratos

- Nome do locador.
- CPF do locador.
- Nome do locatário.
- CPF do locatário.
- Tipo do contrato.
- Valor do aluguel.
- Valor do IPTU.
- Valor do condomínio.
- Data de início.
- Data de término.

### Pagamentos

- Contrato relacionado.
- Mês de referência.
- Valor base.

## Saída de relatórios

O sistema gera arquivos PDF na pasta `Downloads` do usuário:

- `Relatorio_Contratos.pdf`
- `Relatorio_Pagamentos.pdf`

## Privacidade

O SGLI não depende de serviços externos. Os dados ficam armazenados localmente no computador do usuário.

