# 🎬 ScreenMatch - Consumo de API com Java

Projeto desenvolvido durante o **4º curso da Formação Java da Alura**, com foco no aprendizado de integração entre aplicações Java e serviços Web.

O principal objetivo deste projeto é compreender como uma aplicação Back-end pode consumir dados de uma API externa, interpretar respostas em formato JSON, tratar possíveis erros durante a comunicação e persistir informações em arquivos locais.

> **Este repositório possui finalidade exclusivamente educacional**, sendo utilizado como apoio aos meus estudos em Desenvolvimento Back-end com Java.

---

## 📚 Objetivos de Aprendizagem

Durante o desenvolvimento deste projeto foram estudados os seguintes conceitos:

- Consumo de APIs HTTP utilizando Java;
- Envio de requisições e processamento de respostas;
- Manipulação de dados no formato JSON;
- Serialização e desserialização utilizando a biblioteca **Gson**;
- Tratamento de exceções e erros durante chamadas à API;
- Escrita e leitura de arquivos utilizando o pacote `java.io`;
- Organização e estruturação de projetos Java;
- Boas práticas na manipulação de dados externos.

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Maven
- HttpClient (Java)
- Gson
- API OMDb
- IntelliJ IDEA

---

## 📖 O que foi desenvolvido

O projeto realiza consultas a uma API de filmes, permitindo que informações sejam recuperadas dinamicamente através da internet.

Ao longo da implementação são explorados conceitos importantes do desenvolvimento Back-end, como:

- Comunicação entre sistemas através de APIs;
- Conversão de dados JSON para objetos Java;
- Persistência de informações em arquivos;
- Tratamento de respostas inválidas e exceções;
- Organização do código seguindo princípios de orientação a objetos.

---

## 🎯 Objetivo deste Repositório

Este repositório foi criado para acompanhar minha evolução na linguagem Java durante a Formação Java da Alura.

Além de servir como registro do aprendizado, ele também funciona como ambiente para experimentação, realização de melhorias e fixação dos conceitos apresentados ao longo do curso.

---

## 🚀 Competências Desenvolvidas

- Java
- Programação Orientada a Objetos (POO)
- Consumo de APIs REST
- HTTP Client
- JSON
- Gson
- Serialização e Desserialização
- Tratamento de Exceções
- Manipulação de Arquivos
- Maven

---

## 📚 Curso

**Formação Java - Alura**

Curso:
> Java: consumindo uma API, gravando arquivos e lidando com erros

---

## 📝 Observação

Este projeto foi desenvolvido como parte da Formação Java da Alura e é utilizado exclusivamente para fins de estudo e prática. O código poderá receber adaptações e melhorias conforme minha evolução nos estudos de Desenvolvimento Back-end com Java.

# Consumo de API em Java

Este trecho do projeto apresenta uma implementação básica de **consumo de uma API REST em Java**, utilizando as classes nativas do pacote `java.net.http`.

A aplicação recebe uma entrada do usuário, constrói uma URL para a **OMDb API**, realiza uma requisição HTTP e exibe no terminal a resposta retornada pelo servidor em formato JSON.

## Fluxo da aplicação

```text
Entrada do usuário
        ↓
Construção da URL
        ↓
Criação da URI
        ↓
Configuração da requisição HTTP
        ↓
Envio através do HttpClient
        ↓
Recebimento da resposta
        ↓
Leitura do JSON
        ↓
Exibição no terminal
```

## 1. Recebendo a entrada do usuário

A aplicação utiliza `Scanner` para capturar o valor informado no terminal:

```java
Scanner scanner = new Scanner(System.in);

System.out.println("Digite um filme para busca: ");
var busca = scanner.nextLine();
```

O método `nextLine()` retorna uma `String`. Por isso, o Java consegue inferir automaticamente o tipo da variável `busca` ao utilizar `var`.

Na prática:

```java
var busca = scanner.nextLine();
```

é equivalente a:

```java
String busca = scanner.nextLine();
```

O valor armazenado em `busca` será utilizado posteriormente na construção da URL.

---

## 2. Construindo a URL da API

A URL é montada dinamicamente a partir da entrada fornecida pelo usuário:

```java
String endereco = "https://www.omdbapi.com/?i=" + busca + "&apikey=930cbf45";
```

A estrutura da URL pode ser entendida da seguinte forma:

```text
https://www.omdbapi.com/
        ↓
     endereço da API

?i=...
        ↓
   parâmetro da busca

&apikey=...
        ↓
   chave de acesso
```

### Parâmetro `i`

No exemplo original, o parâmetro `i` é utilizado para informar um **IMDb ID**, como:

```text
tt0133093
```

Por isso, caso seja informado diretamente um título, como:

```text
matrix
```

a API interpretará esse valor como se fosse um IMDb ID.

Para realizar uma busca pelo **título do filme**, o parâmetro adequado é `t`:

```java
String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=930cbf45";
```

Assim, a entrada:

```text
matrix
```

será interpretada como o título procurado.

---

## 3. Criando o `HttpClient`

```java
HttpClient client = HttpClient.newHttpClient();
```

`HttpClient` é a classe responsável por realizar a comunicação HTTP com o servidor.

É através dela que a aplicação consegue enviar a requisição e receber a resposta da API.

---

## 4. Criando a requisição HTTP

```java
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .build();
```

Nesse trecho é criado o objeto `HttpRequest`, que representa a requisição que será enviada ao servidor.

### `URI.create()`

```java
URI.create(endereco)
```

Converte a URL, que está armazenada como `String`, em um objeto `URI`.

### `build()`

```java
.build();
```

Finaliza a construção da requisição.

Como nenhum método HTTP foi informado explicitamente, a requisição utiliza `GET`, que é apropriado para consultar informações.

Também seria possível deixar isso explícito:

```java
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .GET()
        .build();
```

---

## 5. Enviando a requisição

```java
HttpResponse<String> response = client
        .send(request, HttpResponse.BodyHandlers.ofString());
```

Esse é o ponto em que a comunicação com a API realmente acontece.

O método `send()` recebe:

* `request` → a requisição que será enviada;
* `BodyHandlers.ofString()` → define que o corpo da resposta será tratado como `String`.

O resultado é armazenado em:

```java
HttpResponse<String> response
```

O objeto `response` representa a resposta HTTP recebida do servidor.

---

## 6. Obtendo o conteúdo retornado pela API

```java
System.out.println(response.body());
```

O método:

```java
response.body()
```

retorna o **corpo da resposta HTTP**.

A OMDb API retorna os dados em formato **JSON**, por exemplo:

```json
{
  "Title": "The Matrix",
  "Year": "1999",
  "Genre": "Action, Sci-Fi"
}
```

Nesse estágio, o JSON ainda é tratado pelo Java como uma `String`.

Posteriormente, esse conteúdo pode ser convertido em objetos Java, permitindo trabalhar com os dados de maneira estruturada.

---

## 7. Tratamento de exceções

O método `main` foi declarado com:

```java
public static void main(String[] args) throws IOException, InterruptedException
```

Essas exceções estão relacionadas às operações envolvidas na comunicação HTTP.

O uso de `throws` indica que o método não realiza o tratamento dessas exceções diretamente. Caso ocorra uma delas, a exceção poderá ser propagada para o nível superior da aplicação.

---

## 8. Analisando uma resposta da API

Durante o teste do programa, foi realizada a seguinte entrada:

```text
Digite um filme para busca:
matrix
```

Como o código utilizava:

```text
?i=matrix
```

a API retornou:

```json
{
  "Response": "False",
  "Error": "Incorrect IMDb ID."
}
```

Isso não significa que a comunicação HTTP falhou.

Na verdade, o fluxo ocorreu corretamente:

```text
Java
 ↓
HttpClient
 ↓
HttpRequest
 ↓
OMDb API
 ↓
HttpResponse
 ↓
JSON
```

O problema estava no **valor enviado para o parâmetro `i`**.

Além disso, o programa terminou com:

```text
Process finished with exit code 0
```

O código `0` indica que a aplicação foi encerrada normalmente, sem uma exceção não tratada.

Portanto:

```text
Erro retornado pela API
≠
Erro de execução do programa
```

Essa distinção é importante ao realizar debugging de aplicações que consomem APIs.

---

## 9. Estrutura geral do código

De forma simplificada, o funcionamento pode ser representado assim:

```text
Scanner
   ↓
Recebe a entrada do usuário
   ↓
String / URL
   ↓
URI
   ↓
HttpRequest
   ↓
HttpClient
   ↓
Requisição HTTP GET
   ↓
OMDb API
   ↓
HttpResponse<String>
   ↓
response.body()
   ↓
JSON
```

## Tecnologias e conceitos utilizados

* **Java**
* **HTTP**
* **API REST**
* `HttpClient`
* `HttpRequest`
* `HttpResponse`
* `URI`
* `Scanner`
* **JSON**
* **HTTP GET**
* Tratamento de exceções
* Debugging e análise de respostas de API

## Conclusão

Este exemplo representa uma das formas mais básicas de consumir uma API em Java utilizando recursos nativos da linguagem.

O principal conceito demonstrado é o ciclo:

> **Criar a requisição → enviá-la → receber a resposta → acessar os dados retornados.**

Esse processo serve como base para aplicações mais avançadas, nas quais os dados recebidos em JSON podem ser convertidos em objetos Java e utilizados diretamente pela aplicação.
