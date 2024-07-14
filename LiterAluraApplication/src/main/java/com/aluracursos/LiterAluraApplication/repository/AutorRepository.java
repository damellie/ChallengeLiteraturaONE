package com.aluracursos.LiterAluraApplication.repository;

import com.aluracursos.LiterAluraApplication.Model.Autor;
import com.aluracursos.LiterAluraApplication.Model.Idiomas;
import com.aluracursos.LiterAluraApplication.Model.Libro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Libro l JOIN l.autor a WHERE a.nombreAutor ILIKE %:nombre%")
    Optional<Autor> buscarAutorPorNombre(String nombre);

    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE l.titulo ILIKE %:tituloLibro%")
    Optional<Libro> buscarLibroPorNombre(String tituloLibro);

    @Query("SELECT l FROM Autor a JOIN a.libros l ORDER BY l.titulo")
    List<Libro> librosregistrados();

    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE  l.idioma = %:idiomaLibro%")
    List<Libro> buscarLibroPorIdioma(Idiomas idiomaLibro);

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE autores_id_seq RESTART WITH 1;",nativeQuery = true)
    void restartAutoresID();

    @Query(value = "ALTER SEQUENCE libros_id_seq RESTART WITH 1;",nativeQuery = true)
    void restartLibrosID();
}
