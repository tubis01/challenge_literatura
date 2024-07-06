package com.alurachallenge.challenge_literatura.models;

public enum Idioma {
    ES("es", "Español"),
    EN("en", "Inglés"),
    FR("fr", "Francés"),
    PT("pt", "Portugués");

    private String idiomaApi;
    private String idioma;

    Idioma(String idiomaApi, String idioma) {
        this.idiomaApi = idiomaApi;
        this.idioma = idioma;
    }

    public static Idioma fromString(String idioma) {
        for (Idioma i : Idioma.values()) {
            if (i.idiomaApi.equalsIgnoreCase(idioma)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + idioma);
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
