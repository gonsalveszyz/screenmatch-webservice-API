package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOMDB;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

// URL API: "https://www.omdbapi.com/?i=tt3896198&apikey=930cbf45"

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um filme para busca: ");
        var busca = scanner.nextLine();

        String endereco = "https://www.omdbapi.com/?i=" + busca + "&apikey=930cbf45";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        String json = response.body();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
        //  Titulo meuTitulo = gson.fromJson(json, Titulo.class);
        TituloOMDB meuTituloOMDB = gson.fromJson(json, TituloOMDB.class);
        System.out.println(meuTituloOMDB);
        try {
            Titulo meuTitulo = new Titulo(meuTituloOMDB.title());
            System.out.println("Titulo já convertido: ");
            System.out.println(meuTitulo);

        } catch (NumberFormatException e) {
            System.out.println("Aconteceu um Erro");
            System.out.println(e.getMessage());
        }
        System.out.println("O programa finalizou corretamente!");
    }
}
