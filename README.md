Condominio

Bem-vindo ao repositório do projeto java_api_condominio. Esta é a API back-end que suporta o sistema de gerenciamento de condomínios.

Descrição do Projeto

Este projeto é a camada de serviços (API) que gerencia todas as informações do sistema de condomínio, incluindo:

Gerenciamento de Moradores e Unidades: Endpoints para o ciclo de vida completo de moradores e apartamentos.

Ao cadastrar ou editar os dados do morador a API envia um email para o morador.

Validações de campos e ao excluir um imóvel com morador vinculado o sistema exibe uma notificação. 

Autenticação e Autorização: Sistema de segurança utilizando JWT para garantir que apenas usuários autorizados possam acessar a aplicação.

Tecnologias Utilizadas

Backend:

JAVA Versão 21

Spring Boot

Maven

API com Controller (com paginação)

Swagger

CORS

JWT (JSON Web Tokens) para autenticação

Banco de Dados:

PostgreSQL

Ferramentas:

IntelliJ IDEA Community Edition 2025.2.1

Pré-requisitos

Antes de começar, certifique-se de que você tem as seguintes ferramentas instaladas:

Java SE Development Kit 21.0.8

Após instalar o Java é preciso configurar as variaveis de anbiente do Windows e adicione no campo Variáveis do sistema 

no campo Nome da Variável digite 

%JAVA_HOME%\bin

no campo valor da variavel coloque o caminho do jdk do java

exemplo:

C:\Program Files\Java\jdk-21

confirme com OK e reinicie o computador

Um editor de código como IntelliJ IDEA Community Edition 2025.2.1 ou Visual Studio Code

Um servidor de banco de dados PostgreSQL instalado juntamente com pgAdmin 4 para criar database e tabelas no banco de dados

Instalação e Configuração

Siga os passos abaixo para configurar e executar a API localmente:

Clone o repositório:

https://github.com/WaineAlvesCarneiro/java_api_condominio

Configuração do Banco de Dados:

Ao instalar o PostgreSQL defina:

username=postgres

password=postgres

Em seguida execute os scripts disponíveis no projeto condomínio de Backend

https://github.com/WaineAlvesCarneiro/PostgreSQL_sql_condominio

Execução da Aplicação:

Para rodar o projeto, execute o seguinte comando:

Após abrir o projeto no IntelliJ IDEA Community Edition 2025.2.1 ou Visual Studio Code

Procure o arquivo JavaApiCondominioApplication e apos abrir procure um botão verde "uma seta" e clique para executar a API em JAVA.

Então no navegador cole a url para abrir o swagger

https://localhost:44369/swagger-ui/index.html#/

Para testar no Swagger ou frontend em Angular

Como logar no sistema:

Usuário: admin

Senha: 12345

Documentação da API

Você pode usar essa interface do swagger para testar os endpoints e entender a estrutura da API.

Ou se preferir usar a versão Web desenvolvida por mim em Angular ou React basta baixar um dos dois projeto no repositório do GitHub e seguir o passo a passo descrito no README.md:

https://github.com/WaineAlvesCarneiro/angular_condominio

https://github.com/WaineAlvesCarneiro/react_condominio

Desenvolvido Waine Alves Carneiro
