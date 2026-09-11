package com.example.Model;

public class ReporteBatalla {

    private String nombrePokemon;
    private String nombreEnemigo;
    private int cantidadDerrotados;

    public ReporteBatalla(String nombrePokemon, String nombreEnemigo, int cantidadDerrotados) {
        this.nombrePokemon = nombrePokemon;
        this.nombreEnemigo = nombreEnemigo;
        this.cantidadDerrotados = cantidadDerrotados;
    }

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public String getNombreEnemigo() {
        return nombreEnemigo;
    }

    public void cantidadDerrotados() {
        cantidadDerrotados++;
    }

    @Override
    public String toString() {
        return "ReporteBatalla{" +
                "nombrePokemon='" + nombrePokemon + '\'' +
                ", nombreEnemigo='" + nombreEnemigo + '\'' +
                ", cantidadDerrotados=" + cantidadDerrotados +
                '}';
    }
}


