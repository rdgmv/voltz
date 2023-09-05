# voltz

___

# Relatório Técnico:

**Sistema de Cadastro de Pessoa, Eletrodoméstico e Endereço.**
___

## 🗒 Introdução

<p align="justify">
O presente relatório técnico descreve o sistema <b>Voltz</b>, nesta segunda fase, preparamos os
serviços de : cadastro de pessoa, eletrodoméstico e endereço, desenvolvido utilizando a
arquitetura de microserviços com Spring Boot. O objetivo do sistema é fornecer uma solução
robusta e escalável para o gerenciamento de informações de pessoas, seus eletrodomésticos
e endereços associados. O sistema foi implementado utilizando tecnologias modernas como
Java 17, Lombok, Bean Validation, DevTools e Maven.
</p>

___

## ✏ Arquitetura de Microserviços

<p align="justify">
A arquitetura de microserviços é uma abordagem de desenvolvimento de software que divide
uma aplicação em um conjunto de serviços independentes, cada um responsável por uma
funcionalidade específica. Nesse sistema, foram identificados três microserviços principais:
pessoa, eletrodoméstico e endereço. Cada microserviço possui sua própria base de código,
banco de dados e API para interação com as demais partes do sistema.
</p>

___

## 🧰 Tecnologias Utilizadas

- `JPA(Java Persistence API)`: _É uma especificação do Java EE para mapearmos objeto relacional,
  permitindo que nós desenvolvedores possamos acessar e manipular dados da base de forma orientada a objetos,
  fornecendo um conjunto de anotações e APIs que abstraem a interação com a base de dados._
- `H2`: _Sistema de gerenciamento de banco de dados relacional em memória de codigo aberto.
  A sua principal vantagem além de oferecer suporte a SQL, é a facilidade de integração e de uso,
  sendo muito usado para projetos em desenvolvimento e testes_
- `Spring Boot`: _Framework Java que facilita a criação de aplicações com configurações mínimas
  e alto desempenho._
- `Java 17`: _Versão mais recente da linguagem Java, que traz melhorias e recursos adicionais
  para o desenvolvimento._
- `Lombok`: _Biblioteca que automatiza a geração de código repetitivo, como getters, setters e
  construtores, reduzindo a verbosidade do código._
- `Bean Validation`: _Framework que oferece uma forma declarativa de validar os dados de
  entrada, garantindo que eles estejam corretos e consistentes._
- `DevTools`: _Conjunto de ferramentas de desenvolvimento que auxiliam no ciclo de
  desenvolvimento, incluindo recursos como reinicialização automática da aplicação durante o
  desenvolvimento._
- `Maven`: _Gerenciador de dependências e de construção de projetos Java, utilizado para
  automatizar o processo de compilação, testes e geração de artefatos._

___

## ⚙ Funcionalidades do Sistema

*  ### Cadastro de Pessoa

<p align="justify">
   O microserviço de Pessoa permite o cadastro, consulta, atualização e exclusão de informações
   relacionadas às pessoas. As informações básicas da pessoa incluem nome, data de
   nascimento, sexo e grau de parentesco, cadastrados através do method POST na URL:
</p>

```
localhost:8080/pessoas
```

* ### Cadastro de Eletrodoméstico

<p align="justify">
   O microserviço de eletrodoméstico é responsável pelo gerenciamento das informações
   relacionadas aos eletrodomésticos. Cada eletrodoméstico possui um nome, modelo e potência,
   cadastrados através do method POST na URL:
</p>

```
localhost:8080/eletrodomesticos
```

* ### Cadastro de Endereço

<p align="justify">
   O microserviço de endereço permite o cadastro e gerenciamento das informações de
   endereços. Cada endereço possui campos como rua, número, bairro, cidade e estado,
   cadastrados através do method POST na URL:
</p>

```
localhost:8080/enderecos
```

<p align="justify">
   Além disso, o sistema atribui automaticamente uma data de entrada para cada cadastro
   e valida os campos para que não haja inserção de dados inválidos, tal
   como brancos ou nulos, em todos os serviços.
<p>

___

## ✅Testes Realizados

<p align="justify">
A Request abaixo responsável pelo cadastro de pessoa, tem como obrigatoriedade o preenchimento dos campos a serem preenchidos.
</p>

> Campos: “nome”, “dataNascimento”, “sexo”, “parentescoComUsuario”

<p align="justify">

Caso a requisição seja realizada com um dos campos **NULO** ou **BRANCO**, o sistema retorna crítica, impossibilitando
que a requisição seja concluída. Como consequência, temos o Response nos retornando o status **400 – Bad request.**

Quanto aos dados já inseridos no sistema, ou seja, dados válidos, realizamos a validação onde aponta se há ou não
duplicidade, caso a duplicidade for existente, temos o Response nos retornando o status **422 – Unprocessable Entity.**

Quanto aos dados inexistentes, uma vez solicitado na requisição, o Sistema realiza a validação e caso as informações
enviadas não sejam encontradas, temos o Response nos retornando o status **404 – Not Found.**

Quanto a requisições válidas, onde o preenchimento dos campos obrigatórios são preenchidos de forma correta, temos o
Response nos retornando o status **200 – OK.** Para requisições do tipo POST temos o retorno **201 - CREATED.**

</p>

[comment]: # (Testes da primeira fase)

<details>
<summary>Testes realizados da fase 1 apenas com persistência de dados em memória</summary>

<p align="justify">
Para realizar as validações, utilizamos o FrameWork “Bean Validation“.
</p>

> **IMPORTANTE**: Ressaltamos que as mesmas validações foram feitas para as APIs de Endereço e Eletrodoméstico.

Abaixo o fluxo completo do processo e seus respectivos retornos:


<details>
<summary>1. Ao realizar o cadastro de uma nova pessoa com todos os campos corretos, a pessoa é criada com sucesso.</summary>

> Retorno esperado: 201 – **Created**

![13_post_sucesso.png](github%20imgs%2F13_post_sucesso.png)
</details>


<details>
<summary>2. Ao realizar o cadastro de uma pessoa, o campo “nome” foi inserido como null. </summary>

> Retorno esperado: 400 – **Bad Request**

![01_post_valida_nome_pessoa.png](github%20imgs%2F01_post_valida_nome_pessoa.png)
</details>

<details>
<summary>3. Ao tentar realizar o cadastro de uma pessoa já cadastrada no Sistema através do **POST**, é apresentada uma crítica de duplicidade de registros. Essa por sua vez, impede que a ação seja executada.</summary>

> Retorno esperado: 422 - **Unprocessable Entity**

![12_post_duplicidade.png](github%20imgs%2F12_post_duplicidade.png)
</details>

<details>
<summary> 4. Realizando a consulta de uma pessoa através do **GET**, passando o campo “dataNascimento” null. </summary>

> Retorno esperado: 400 – **Bad Request**

![02_get_valida_dtnas_pessoa_.png](github%20imgs%2F02_get_valida_dtnas_pessoa_.png)
</details>

<details>
<summary>5. Realizando consulta de lista de pessoas através do **GET**, sem a necessidade de campos. Neste momento é exibida a lista de pessoas cadastrada no sistema.</summary>

> Retorno esperado: 200 – **OK**

![03_get_retorna_lista_pessoa_nv.png](github%20imgs%2F03_get_retorna_lista_pessoa_nv.png)
</details>

<details>
<summary>6. Realizando a consulta de uma pessoa em específico através do **GET**, com o preenchimento dos campos de forma correta.</summary>

> Retorno esperado: 200 – **OK**

![04_get_retorna_pessoa_nv.png](github%20imgs%2F04_get_retorna_pessoa_nv.png)
</details>

<details>
<summary>7. Tentando realizar a alteração de uma pessoa existente no sistema através do **PUT**, porém com a inserção do dado no campo “SEXO” inválido. Sistema retorna crítica para o usuário, sinalizando o parâmetro a ser inserido corretamente, conforme necessidade. </summary>

> Retorno esperado: 400 - **Bad Request**

![05_put_valida_sexo_pessoa.png](github%20imgs%2F05_put_valida_sexo_pessoa.png)
</details>

<details>
<summary>8. Tentando realizar a alteração de uma pessoa inexistente no Sistema, através do **PUT**.</summary>

> Retorno esperado: 404 – **Not Found**

![10_put_autalizando_pessoa_inexistente.png](github%20imgs%2F10_put_autalizando_pessoa_inexistente.png)
</details>

<details>
<summary>9. Tentando realizar a alteração de uma pessoa existente no sistema através do **PUT**, porém com a inserção do dado no campo “SEXO” inválido. Sistema retorna crítica para o usuário, sinalizando o parâmetro a ser inserido corretamente, conforme necessidade.</summary>

> Retorno esperado: 400 – **Bad Request**

![11_put_passagem_de_param_errado.png](github%20imgs%2F11_put_passagem_de_param_errado.png)
</details>

<details>
<summary>10. Realizando a exclusão de uma pessoa que não está cadastrada no Sistema através do **DELETE**.</summary>

> Retorno esperado: 404 – **Not Found**

![06_delete_valida_dados_pessoa_nv.png](github%20imgs%2F06_delete_valida_dados_pessoa_nv.png)
</details>

<details>
<summary>11. Realizando a exclusão de uma pessoa que está cadastrada em Sistema através do **DELETE**, porém com o campo “nome” null.</summary>

> Retorno esperado: 400 – **Bad Request**

![08_delete_valida_nome_pessoa.png](github%20imgs%2F08_delete_valida_nome_pessoa.png)
</details>

<details>
<summary>12. Realizando a exclusão de uma pessoa cadastrada no sistema através do **DELETE**, com o preenchimento correto dos campos.</summary>

> Retorno esperado: 200 – **OK**

![07_delete_pessoa_sucesso.png](github%20imgs%2F07_delete_pessoa_sucesso.png)
</details>

<details>
<summary>13. Realizando novamente a busca de uma pessoa através do **GET**, após essa mesma pessoa ser deletada.</summary>

> Retorno esperado: 404 – **Not Found**

![09_get_consulta_pessoa_deletada_nv.png](github%20imgs%2F09_get_consulta_pessoa_deletada_nv.png)
</details>

</details>

[comment]: # (Fim Testes da primeira fase)


<p align="justify"> 
Em continuação da primeira parte do projeto, fizemos o uso do Banco de dados H2. 
Um banco de dados relacional escrito em Java. Ele pode ser integrado em aplicativos Java ou executado no modo cliente-servidor. Todos os modos contam com suporte para bancos de dados persistentes e na memória. 
Neste momento, fazemos o CRUD, via API, mas temos como objetivo mostrar o comportamento entre as duas aplicações.
</p>

[comment]: # (Inicio testes da segunda fase)

<details>
<summary>Testes realizados da fase 2 utilizando H2</summary>

<details>
<summary> 1. Ao realizar o cadastro de um Usuário através do POST.</summary>

> Retorno esperado: 201 – **Created**

![1 - Cadastro Usuario POST.png](github%20imgs%2Ftestes%20fase%202%2F1%20-%20Cadastro%20Usuario%20POST.png)

> Retorno do cadastro do usuário no Banco de dados H2:
> ![1 - Retorno Usuario H2.png](github%20imgs%2Ftestes%20fase%202%2F1%20-%20Retorno%20Usuario%20H2.png)
</details>

<details>
<summary> 2. Ao realizar o cadastro de um Endereço, vinculado ao Usuário através do POST.</summary>

> Retorno esperado: 201 – **Created**

![2 - Cadastro Endereco POST.png](github%20imgs%2Ftestes%20fase%202%2F2%20-%20Cadastro%20Endereco%20POST.png)

> Retorno do cadastro do Endereço no Banco de dados H2:
> ![2 - Retorno Endereco H2.png](github%20imgs%2Ftestes%20fase%202%2F2%20-%20Retorno%20Endereco%20H2.png)

</details>

<details>
<summary> 3. Ao realizar o cadastro de uma Pessoa, vinculada ao Usuário através do POST.</summary>

> Retorno esperado: 201 – **Created**

![3 - Cadastro Pessoa Vinculada Usuario POST.png](github%20imgs%2Ftestes%20fase%202%2F3%20-%20Cadastro%20Pessoa%20Vinculada%20Usuario%20POST.png)

> Retorno do cadastro da Pessoa no Banco de dados H2:
> ![3 - Retorno Pessoa H2.png](github%20imgs%2Ftestes%20fase%202%2F3%20-%20Retorno%20Pessoa%20H2.png)
</details>

<details>
<summary> 4. Ao realizar a alteração de um dos campos de uma Pessoa já existente, vinculada ao Usuário através do PUT.</summary>

<p align="justify">
Nesta situação, independentemente se a alteração for para um ou mais campos, se faz necessário passar todos os campos para ter sucesso na alteração. A falta de um dos campos, impossibilita que a alteração seja realizada.
</p>

> Retorno esperado: 200 – **OK**

![4 - Alteracao Pessoa Ja Existente, Vinculada Usuario PUT.png](github%20imgs%2Ftestes%20fase%202%2F4%20-%20Alteracao%20Pessoa%20Ja%20Existente%2C%20Vinculada%20Usuario%20PUT.png)

> Retorno da atualização do cadastro da Pessoa no Banco de dados H2.
> A alteração foi feita no campo “PARENTESCO_COM_USUARIO”, iniciamos o cadastro com a informação neste campo de “o
> próprio”, após a alteração o campo tem como informação “PRIMO”:

> ![4 - Retorno alteracao Pessoa H2.png](github%20imgs%2Ftestes%20fase%202%2F4%20-%20Retorno%20alteracao%20Pessoa%20H2.png)
</details>

<details>
<summary> 5. Ao realizar o cadastro de um Eletrodomestico, vinculado ao Usuário através do POST.</summary>

> Retorno esperado: 201 – **Created**

![5 - Cadastro Eletrodomestico, Vinculado Usuario POST.png](github%20imgs%2Ftestes%20fase%202%2F5%20-%20Cadastro%20Eletrodomestico%2C%20Vinculado%20Usuario%20POST.png)

> Retorno do cadastro do Eletrodomestico no Banco de dados H2:
> ![5 - Retorno Eletrodomestico H2.png](github%20imgs%2Ftestes%20fase%202%2F5%20-%20Retorno%20Eletrodomestico%20H2.png)
</details>

<details>
<summary> 6. Ao realizar a exclusão de um Eletrodomestico, vinculado ao Usuário através do DELETE.</summary>

> Retorno esperado: 200 – **OK**

![6 - Exclusao Eletrodomestico, Vinculado Usuario DELETE.png](github%20imgs%2Ftestes%20fase%202%2F6%20-%20Exclusao%20Eletrodomestico%2C%20Vinculado%20Usuario%20DELETE.png)

</details>

<details>
<summary> 7. Ao realizar a exclusão de uma Pessoa, vinculada ao Usuário através do DELETE.</summary>

> Retorno esperado: 200 – **OK**

![7 - Exclusao Pessoa, Vinculado Usuario DELETE.png](github%20imgs%2Ftestes%20fase%202%2F7%20-%20Exclusao%20Pessoa%2C%20Vinculado%20Usuario%20DELETE.png)

</details>

<details>
<summary> 8. Ao realizar a exclusão de um Endereço, vinculado ao Usuário através do DELETE.</summary>

> Retorno esperado: 200 – **OK**

![8 - Exclusao Endereco, Vinculado Usuario DELETE.png](github%20imgs%2Ftestes%20fase%202%2F8%20-%20Exclusao%20Endereco%2C%20Vinculado%20Usuario%20DELETE.png)

</details>

</details>

[comment]: # (Fim testes da segunda fase)
___

## 💾Tabela do Banco de dados

![voltz database.jpeg](github%20imgs%2Fvoltz%20database.jpeg)

___

## ✔ Padronização e Validação de Dados

<p align="justify">
   O sistema utiliza a biblioteca Lombok para reduzir a verbosidade do código, gerando
   automaticamente getters, setters e construtores. Além disso, o Bean Validation é utilizado
   para garantir a integridade dos dados fornecidos pelo usuário. São aplicadas validações nos
   campos obrigatórios, evitando a inserção de informações inválidas no sistema.
</p>

___

## ✈ Escalabilidade

<p align="justify">
   A arquitetura de microserviços do sistema Voltz adotada no sistema permite a escalabilidade
   horizontal, ou seja, é possível adicionar mais instâncias de cada microserviço para lidar com
   um maior volume de requisições.
</p>

___

## 🔑🔓 Desafios Encontrados

<p align="justify">

**Implementação dos DTO**

Inicialmente, foi pensada a utilização do JMapper para a transformação das entradas recebidas
pelas requisições em objetos que fizessem a representação das classes Pessoa, Endereço e
Eletrodoméstico. A utilização foi frustrada por erros apresentados no start da aplicação,
ao importar a dependência JMapper.
Após pesquisas e análises, optou-se pela utilização de builders nas classes DTO, com métodos
toPessoa(), toEndereco() e toEletrodomestico().
Com a implementação, foi possível:

• Garantir aderência ao SRP – Single Responsability Principle, deixando a cargo das classes
DTO a responsabilidade pelo contato inicial com a requisição e pela aplicação da validação
de beans e para as classes de domínio a aplicação de regras inerentes ao negócio;

• Mitigar fragilidade de segurança, tornando restrito o acesso a alterações do atributo dataEntrada,
implementado nas três classes de domínio.

**Validação de Beans x Classes DTO, conforme tipo de requisição**

Após a implementação das classes DTO no método POST, cujos dados de entrada são completos para a
criação de uma Pessoa, Endereço ou Eletrodoméstico, observou-se que ao aplicar validação de beans
para garantir o recebimento de todas as informações necessárias, a classe DTO não mais serviria
para outros tipos de requisição, como o GET e o DELETE, uma vez que os dados necessários para o
processamento destas são reduzidos.
Para contornar a questão, optou-se por criar diferentes classes DTO conforme o tipo de requisição.
Por padrão, adotou-se uma classe DTO para os métodos POST e PUT e outra para GET e DELETE. Deste modo,
garantiu-se a validação dos dados necessários a cada requisição, mantendo-se, para todas as requisições,
a aplicação do SRP.
</p>

___

## 🏁 Considerações Finais

<p align="justify">
   O sistema de cadastro de usuário, pessoa, eletrodoméstico e endereços implementado com  arquitetura de microserviços utilizando Spring Boot, agora nesta segunda fase incluímos tambám JPA e H2 além das demais  tecnologias modernas que demonstra m alto nível de escalabilidade, modularidade e flexibilidade. O uso de bibliotecas como Lombok e Bean Validation contribuiu para a produtividade, reduzindo a quantidade de código repetitivo e garantindo a integridade dos dados. A escolha de tecnologias atualizadas, como Java 17, reforça a aderência às práticas e recursos mais recentes no desenvolvimento de software,  além do uso do sistema de gerenciamento de banco de dados H2 para agilizar o processo de desenvolvimento, usamos tambem JPA que permitiu nossa manipulação dos dados de forma orientada a objetos. 
   Dessa forma, o sistema oferece uma solução robusta para o cadastro de pessoas,
   eletrodomésticos e endereços, atendendo às necessidades do projeto e proporcionando uma
   experiência confiável para o usuário final. 
</p>

___

## 👨‍🎓 Projeto desenvolvido por:

[Jeferson Alves ](https://github.com/jefsantos)•
[Rodrigo Cotrim ](https://github.com/rdgmv)•
[Rodrigo Maciel ](https://github.com/rodrigomgalvao)•
[William Sousa ](https://github.com/willrsousa94)

