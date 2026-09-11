package com.example.Model;

//El Atributo -> "siguienteEvolucion" es una referencia a otro

//Charmander -> Charmeleon -> Charizard -> null

public class Pokemon {

    private String nombre;
    private int puntosDeVidaMaximos;
    private int ataque;
    private int defensa;
    private int experienciaRequerida;

    // Esta es la referencia al siguiente nodo de la lista
    private Pokemon siguienteEvolucion;

    // Si es null, significa que no hay mas evoluciones
    public Pokemon(String nombre, int puntosDeVidaMaximos, int ataque, int defensa, int experienciaRequerida) {

        this.nombre = nombre;
        this.puntosDeVidaMaximos = puntosDeVidaMaximos;
        this.ataque = ataque;
        this.defensa = defensa;
        this.experienciaRequerida = experienciaRequerida;

        // No conocemos la sig evolucion, entt se conecta despues con setSiguienteEvolucion().
        this.siguienteEvolucion = null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntosDeVidaMaximos() {
        return puntosDeVidaMaximos;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getExperienciaRequerida() {
        return experienciaRequerida;
    }

    //Charmander -> Charmeleon -> Charizard -> null
    //Apuntar al Pokemon que tenemos guarado como siguiente evolucion
    public Pokemon getSiguienteEvolucion() {
        return siguienteEvolucion;
    }

    // Modificar para poner la nueva evolucion
    public void setSiguienteEvolucion(Pokemon siguienteEvolucion) {
        this.siguienteEvolucion = siguienteEvolucion;
    }
}
