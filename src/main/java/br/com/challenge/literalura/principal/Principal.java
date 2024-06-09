package br.com.challenge.literalura.principal;

import br.com.challenge.literalura.model.*;
import br.com.challenge.literalura.service.ConsumoApi;
import br.com.challenge.literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";


    public void exibeMenu() {
        var menu =  """
                    */*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
                        LiterAlura - Opções
                        
                        1 - Buscar livro pelo titulo.
                        2 - Listar livros registrados.
                        3 - Listar autores registrados.
                        4 - Listar autores vivos em um determinado ano.
                        5 - Listar livros em um determinado idioma.
                        
                        0 - Sair.
                    
                    */*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
                    """;
        var opcao = -1;

        while (opcao != 0){
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private void buscarLivroPeloTitulo() {

        System.out.println("Digite o nome do livro: ");
        var nomeLivro = leitura.nextLine().trim();
        var consumo = new ConsumoApi();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        var conversor = new ConverteDados();
        RespostaApi dados = conversor.obterDados(json, RespostaApi.class);

        // Converte os dados recebidos em uma lista de Livro
        List<Livro> livros = dados.results().stream()
                .map(Livro::new)
                .collect(Collectors.toList());

        // Tenta encontrar um livro com o título exato
        Livro livroExato = livros.stream()
                .filter(livro -> livro.getTitulo().equalsIgnoreCase(nomeLivro))
                .findFirst()
                .orElse(null);

        // Se encontrar, imprimir o livro com o título exato, caso contrário, imprimir o primeiro da lista
        if (livroExato != null) {
            System.out.println(livroExato);
        } else if (!livros.isEmpty()) {
            System.out.println(livros.get(0));
        } else {
            System.out.println("Nenhum livro encontrado com o título especificado.");
        }
    }

//    var consumo = new ConsumoApi();
//    var json = consumo.obterDados("https://gutendex.com/books/?search=dom+casmurro");
//        System.out.println(json);
//    var conversor = new ConverteDados();
//    RespostaApi dados = conversor.obterDados(json, RespostaApi.class);
//        System.out.println(dados);
//
//    List<Livro> livro = new ArrayList<>();
//
//    livro = dados.results().stream()
//                .map(l -> new Livro(l))
//            .collect(Collectors.toList());
//
//        System.out.println(livro);
//
//    List<Autor> autors = new ArrayList<>();
//
//    autors = livro.stream()
//            .map(a -> a.getAutor())
//            .collect(Collectors.toList());
//
//        System.out.println(autors);
}
