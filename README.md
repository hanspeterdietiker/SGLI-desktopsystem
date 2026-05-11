# Sistema de Gestão de Locação e Relatórios Fiscais - SGLI

Uma aplicação desktop robusta e intuitiva desenvolvida para simplificar a gestão financeira de contratos de aluguel e a preparação de dados para a Declaração de Imposto de Renda.

## 🚀 Sobre o Projeto

Este sistema foi concebido para resolver a complexidade de organizar pagamentos mensais de aluguel, IPTU e condomínio, transformando dados brutos em relatórios organizados e prontos para fins fiscais. Com foco em privacidade e performance, a aplicação opera de forma totalmente local.

## ✨ Funcionalidades Principais

* **Gestão de Pagamentos:** Registro simplificado de valores de Aluguel, IPTU e Condomínio.
* **Cálculos Automáticos:** Totalização imediata de Pagamentos mensais e Acumulados anuais.
* **Monitoramento de Contratos:** Controle por Tabelas de vigencia de Contrato e Valores.
* **Exportação para PDF:** Geração de documentos formatados especificamente para a Declaração de Imposto de Renda, separando valores dedutíveis.
* **Privacidade Local:** Todos os dados são armazenados de forma segura no computador do usuário, sem necessidade de conexão externa.

## 🛠️ Tecnologias Utilizadas

* **Interface Gráfica:** JavaFX (com FXML e CSS para design responsivo).
* **Persistência:** Banco de dados embarcado (SQLite) garantindo portabilidade.
* **Relatórios:** Apache ItextPdf para a construção dinâmica de documentos.
* **Distribuição:** Empacotamento nativo via `jpackage` para instalação direta.

## 📂 Estrutura do Repositório

O projeto segue uma estrutura modular para facilitar a manutenção e escalabilidade:

* `src/main/java`: Contém a lógica de negócio, controladores de interface e serviços de persistência.
* `src/main/resources`: Arquivos FXML, estilos CSS e recursos de imagem.


## 🔧 Como Executar (Desenvolvimento)

Para rodar o projeto localmente, certifique-se de ter o JDK 21 instalado.

1. Clone o repositório.
2. Importe o projeto na sua IDE de preferência.
3. Execute a classe principal através do seu gerenciador de build (Maven).

---
Desenvolvido 

Github: [Hanspeter Dietiker](https://github.com/hanspeterdietiker)

Linkedin: [Hanspeter Dietiker](https://www.linkedin.com/in/hanspeterdietiker/)
