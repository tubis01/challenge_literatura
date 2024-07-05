package com.alurachallenge.challenge_literatura.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAuthor> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") double numeroDescargas
        ) {
}
