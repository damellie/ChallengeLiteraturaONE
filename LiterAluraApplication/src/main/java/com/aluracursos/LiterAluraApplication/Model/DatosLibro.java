package com.aluracursos.LiterAluraApplication.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") String descargas ,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("authors") List <DatosAutor> autor
) {
}
