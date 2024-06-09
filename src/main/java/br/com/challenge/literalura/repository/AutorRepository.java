package br.com.challenge.literalura.repository;

import br.com.challenge.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {


    List<Autor> findByAnoDeFalecimentoGreaterThanEqual(int anoEscolhido);
}
