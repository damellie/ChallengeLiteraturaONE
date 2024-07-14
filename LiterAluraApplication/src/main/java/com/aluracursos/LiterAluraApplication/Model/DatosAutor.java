package com.aluracursos.LiterAluraApplication.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombreAutor,
        @JsonAlias("birth_year")  Integer fechaDeNacimiento,
        @JsonAlias("death_year") Integer fechaDeDefuncion
) {
}
