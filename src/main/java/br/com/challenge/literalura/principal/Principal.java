package br.com.challenge.literalura.principal;

import br.com.challenge.literalura.model.*;
import br.com.challenge.literalura.repository.AutorRepository;
import br.com.challenge.literalura.repository.LivroRepository;
import br.com.challenge.literalura.service.ConsumoApi;
import br.com.challenge.literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private LivroRepository repositorio;
    private AutorRepository autorRepository;
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autors = new ArrayList<>();

    public Principal(LivroRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
    }


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
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmUmDeterminadoAno();
                    break;
                case 5:
                    listarLivrosApartirDeUmIdioma();
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
        //asaa

        // Converte os dados recebidos em uma lista de Livro
        livros = dados.results().stream()
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

    private void listarLivrosRegistrados() {
        livros = repositorio.findAll();
        livros.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autors = autorRepository.findAll();
        autors.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEmUmDeterminadoAno() {
        System.out.println("Informe o ano para a busca: ");
        var anoEscolhido = leitura.nextInt();
        List<Autor> autoresVivos = autorRepository.findByAnoDeFalecimentoGreaterThanEqual(anoEscolhido);

        autoresVivos.stream()
                .forEach(System.out::println);
    }

    private void listarLivrosApartirDeUmIdioma() {
        System.out.println( """
                            Qual idioma voce escolhe:
                            - pt
                            - en                             
                            """);
        var idiomaEscolhido = leitura.nextLine();
        List<Livro> idiomaLivro = repositorio.findByIdiomaIsContainingIgnoreCase(idiomaEscolhido);

        idiomaLivro.stream()
                .forEach(System.out::println);

    }
}
