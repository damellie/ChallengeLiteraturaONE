package com.aluracursos.LiterAluraApplication.Model;

public enum Idiomas {
    INGLES("en"),
    ESPANIOL("es"),
    FRANCES("fr"),
    ALEMAN ("de");
    private String clasificacionIdioma;

    Idiomas (String clasificacionIdioma){
        this.clasificacionIdioma = clasificacionIdioma;
    }

    public static Idiomas fromString(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.clasificacionIdioma.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
