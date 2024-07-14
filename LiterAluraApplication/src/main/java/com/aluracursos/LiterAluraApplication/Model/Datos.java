package com.aluracursos.LiterAluraApplication.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("count") Integer numero,
        @JsonAlias("results") List<DatosLibro> resultado
) {
}
