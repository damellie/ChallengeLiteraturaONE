package com.aluracursos.LiterAluraApplication.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //el id incrementara automaticamente
    private Long Id;
    @Column(unique = true) // no se puede buscar la misma serie
    private String titulo;

    //private List<String> idiomas;
    //private List <Autor> autor;

    @Enumerated(EnumType.STRING)
    private Idiomas idioma;

    private Double descargas ;

    @ManyToOne
    private Autor autor;

    public Libro(){}

    public  Libro (DatosLibro datosLibro){
        this.titulo=datosLibro.titulo();
        //this.idiomas=datosLibro.idiomas();
        this.descargas= OptionalDouble.of(Double.valueOf(datosLibro.descargas())).orElse(0);
        this.idioma = Idiomas.fromString(datosLibro.idiomas().stream().limit(1).collect(Collectors.joining()));
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }


    public Idiomas getIdioma() {
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "----------Libro--------" +
                "\n Titulo='" + titulo  +
                "\n Descargas=" + descargas +
                "\n autor=" + getAutor().getNombreAutor() +
                "\n idioma=" + idioma;
    }
}
