# voltz

### 📖 Projeto


# Relatório Técnico:

**Sistema de Cadastro de Pessoa, Eletrodoméstico e Endereço.**
___

## 🗒 Introdução

<p align="justify">
O presente relatório técnico descreve o sistema Voltz, nesta primeira fase, preparamos os
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
persistência de dados e API para interação com as demais partes do sistema.
</p>

___
## 🧰 Tecnologias Utilizadas

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
   nascimento, sexo e grau parentesco, cadastrados através do method POST na URL:
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
localhost:8080/enderecos.
```

<p align="justify">
   Além disso, o sistem atribui automaticamente uma data de entrada para cada cadastro
   e valida os campos para que não haja inserção de dados inválidos, tal
   como brancos ou nulos, em todos os serviços.
<p>

___

## ✅Testes Realizados

<p align="justify">
A Request abaixo responsável pelo cadastro de pessoa, tem como obrigatoriedade o preenchimento dos campos a serem preenchidos.
</p>

> Campos: “nome”, “dataNascimento”, “sexo”, “parentescoComUsuário”

<p align="justify">
Caso a requisição seja realizada com um dos campos NULO ou BRANCO, o sistema retorna crítica, impossibilitando que a requisição seja concluida. 
Como consequencia, temos o Response nos retornando o status **400 – Bad Request.**
</p>

<p align="justify">
Para realizar as validações, utilizamos o FrameWork “Bean Validation“.
</p>

> **IMPORTANTE**: Ressaltamos que as mesmas validações foram feitas para as APIs de Endereço e Eletrodoméstico.

Abaixo o fluxo completo do processo e seus respectivos retornos:

<details>
<summary>1. Ao realizar o cadastro de uma pessoa, o campo “nome” foi inserido como null. </summary>

> Retorno esperado: 400 – **Bad Request**

![GET consulta uma pessoa- nome null - retorno 400 bad request.png](github%20imgs%2FGET%20consulta%20uma%20pessoa-%20nome%20null%20-%20retorno%20400%20bad%20request.png)
</details>

<details>
<summary> 2. Realizando a consulta de uma pessoa através do GET, passando o campo “dataNascimento” null. </summary>

> Retorno esperado: 400 – **Bad Request** 

![GET consulta uma pessoa - dataNascimento null - retorno 400 bad request.png](github%20imgs%2FGET%20consulta%20uma%20pessoa%20-%20dataNascimento%20null%20-%20retorno%20400%20bad%20request.png)
</details>

<details>
<summary>3. Realizando consulta de lista de pessoas através do GET, sem a necessidade de campos. Neste momento é exibida a lista de pessoas cadastrada no sistema.</summary>

> Retorno esperado: 200 – **OK**

![GET consulta lista pessoas - retorno 200 OK.png](github%20imgs%2FGET%20consulta%20lista%20pessoas%20-%20retorno%20200%20OK.png)
</details>

<details>
<summary>4. Realizando a consulta de uma pessoa em específico através do GET, com o preenchimento dos campos de forma correta.</summary>

> Retorno esperado: 200 – **OK**

![GET consulta uma pessoa - campos corretos- retorno 200 OK.png](github%20imgs%2FGET%20consulta%20uma%20pessoa%20-%20campos%20corretos-%20retorno%20200%20OK.png)
</details>

<details>
<summary>5. Realizando alteração em uma pessoa já cadastrada no sistema através do PUT com o campo “sexo” null.</summary>

> Retorno esperado: 400 – **Bad Request**

![PUT alteracao pessoa - sexo null - retorno 400 Bad Request.png](github%20imgs%2FPUT%20alteracao%20pessoa%20-%20sexo%20null%20-%20retorno%20400%20Bad%20Request.png)
</details>

<details>
<summary>6. Realizando a exclusão de uma pessoa que não está cadastrada em Sistema através do DELETE.</summary>

> Retorno esperado: 400 – **Bad Request**

![DELETE - pessoa inexistente - retorno 400 Bad Request.png](github%20imgs%2FDELETE%20-%20pessoa%20inexistente%20-%20retorno%20400%20Bad%20Request.png)
</details>

<details>
<summary>7. Realizando a exclusão de uma pessoa que está cadastrada em Sistema através do DELETE, porém com o campo “nome” null.</summary>

> Retorno esperado: 400 – **Bad Request**

![DELETE - pessoa existente nome null - retorno 400 Bad Request.png](github%20imgs%2FDELETE%20-%20pessoa%20existente%20nome%20null%20-%20retorno%20400%20Bad%20Request.png)
</details>

<details>
<summary>8. Realizando a exclusão de uma pessoa cadastrada no sistema através do DELETE, com o preenchimento correto dos campos.</summary>

> Retorno esperado: 200 – **OK**

![DELETE - pessoa existente campos corretos - retorno 200 OK.png](github%20imgs%2FDELETE%20-%20pessoa%20existente%20campos%20corretos%20-%20retorno%20200%20OK.png)
</details>

<details>
<summary>9. Realizando novamente a busca de uma pessoa através do GET, após essa mesma pessoa ser deletada.</summary>

> Retorno esperado: 400 – **Bad Request**

![GET - consulta pessoa recem excluida - retorno 400 Bad Request.png](github%20imgs%2FGET%20-%20consulta%20pessoa%20recem%20excluida%20-%20retorno%20400%20Bad%20Request.png)
</details>

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

## 🏁 Considerações Finais

<p align="justify">
   O sistema de cadastro de pessoa, eletrodoméstico e endereços implementado com a
   arquitetura de microserviços utilizando Spring Boot e outras tecnologias modernas demonstra
   um alto nível de escalabilidade, modularidade e flexibilidade. O uso de bibliotecas como
   Lombok e Bean Validation contribuiu para a produtividade, reduzindo a quantidade de código
   repetitivo e garantindo a integridade dos dados. A escolha de tecnologias atualizadas, como
   Java 17, reforça a aderência às práticas e recursos mais recentes no desenvolvimento de
   software.
   Dessa forma, o sistema oferece uma solução robusta para o cadastro de pessoas,
   eletrodomésticos e endereços, atendendo às necessidades do projeto e proporcionando uma
   experiência confiável para os usuários finais. 
</p>

___

## 👨‍🎓 Projeto desenvolvido por:

[Jeferson Alves ](https://github.com/jefsantos)•
[Rodrigo Cotrim ](https://github.com/rdgmv)•
[Rodrigo Maciel ](https://github.com/rodrigomgalvao)•
[William Sousa ](https://github.com/willrsousa94)

