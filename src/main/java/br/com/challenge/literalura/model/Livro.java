package br.com.challenge.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private double totalDeDownloads;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Autor autor;

    public Livro(){}

    public Livro (DadosLivros dadosLivros){
        this.titulo = dadosLivros.titulo();
        if (dadosLivros.autor() != null && !dadosLivros.autor().isEmpty()){
            this.autor = new Autor(dadosLivros.autor().get(0));
        }
        this.idioma = dadosLivros.linguagem().get(0);
        this.totalDeDownloads = dadosLivros.totalDeDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public double getTotalDeDownloads() {
        return totalDeDownloads;
    }

    public void setTotalDeDownloads(double totalDeDownloads) {
        this.totalDeDownloads = totalDeDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "********** Livro **********\n" +
                " Titulo: " + titulo +
                "\n Idioma: " + idioma +
                "\n Autor: " + autor.getNome() +
                "\n Numero de DownLoads: " + totalDeDownloads +
                "\n***************************";

    }
}
