package com.alurachallenge.challenge_literatura.service;

public interface IConvertidorDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
