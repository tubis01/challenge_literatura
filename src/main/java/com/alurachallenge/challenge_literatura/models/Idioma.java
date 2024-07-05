package com.alurachallenge.challenge_literatura.models;

public enum Idioma {
    ESPANIOL("es", "español"),
    INGLES("en", "inglés"),
    FRANCES("fr", "francés"),
    PORTUGUES("pt", "portugués"),;

    private String idiomaApi;
    private String idioma;

    Idioma(String idiomaApi, String idioma) {
        this.idiomaApi = idiomaApi;
        this.idioma = idioma;
    }

    public static Idioma fromString(String idioma) {
        for (Idioma i : Idioma.values()) {
            if (i.idiomaApi.equals(idioma)) {
                return i;
            }
        }
        return null;
    }

    public static Idioma fromEspaniol(String idioma) {
        for (Idioma i : Idioma.values()) {
            if (i.idioma.equals(idioma)) {
                return i;
            }
        }
        return null;
    }

}
