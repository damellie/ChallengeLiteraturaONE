package com.aluracursos.LiterAluraApplication.Service;

public interface IConvierteDatos {

     <T> T obtenerDatos (String json, Class <T> clase);
}
