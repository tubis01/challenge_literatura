package com.alurachallenge.challenge_literatura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAuthor(

        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int fechaNacimiento,
        @JsonAlias("death_year") int fechaMuerte
) {
}
