package com.example;

import com.example.Model.Pokemon;
import com.example.Service.LineaEvolutiva;
import com.example.Util.SimuladorBatallas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        //crear fases para que evolucione
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500);
        Pokemon charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000);
        Pokemon charizard = new Pokemon("Charizard", 78, 84, 78, -1);

        //conectar como una lista enlazada: Charmander -> Charmeleon -> Charizard -> null
        charmander.setSiguienteEvolucion(charmeleon);
        charmeleon.setSiguienteEvolucion(charizard);

        // creamos la linea evolutiva del jugador, empezando en charmander
        LineaEvolutiva miPokemon = new LineaEvolutiva(charmander);

        SimuladorBatallas simulador = new SimuladorBatallas();

        //prueba sencilla contra un Rattata mirar si corre
        pruebaRattata(simulador);

        //reinicar para la prueba grande, no guarde datos de la ratttata
        charmander.setSiguienteEvolucion(charmeleon);
        charmeleon.setSiguienteEvolucion(charizard);
        miPokemon = new LineaEvolutiva(charmander);

        //crear la horda de 100.000 caterpie
        Pokemon[] hordaEnemigos = simulador.generarHordaCaterpie(100000);

        //prueba del entrenamiento masivo
        simulador.iniciarEntrenamientoMasivo(miPokemon, hordaEnemigos);

        //mostrar el resultado final
        Pokemon faseFinal = miPokemon.getFaseActual();
        log.info("===== RESULTADO FINAL =====");
        log.info("Pokemon final: {}", faseFinal.getNombre());
        log.info("HP: {}, Ataque: {}, Defensa: {}", faseFinal.getPuntosDeVidaMaximos(), faseFinal.getAtaque(), faseFinal.getDefensa());
        log.info("Experiencia acumulada: {}", miPokemon.getExperienciaAcumulada());
        log.info("Cantidad de batallas: {}", hordaEnemigos.length);
    }


    private static void pruebaRattata(SimuladorBatallas simulador) {
        Pokemon charmanderPrueba = new Pokemon("Charmander", 39, 52, 43, 1500);
        Pokemon rattata = new Pokemon("Rattata", 30, 56, 35, -1);

        log.info("===== PRUEBA CONTRA RATTATA =====");
        boolean gano = simulador.realizarBatalla(charmanderPrueba, rattata);

        if (gano) {
            log.info("Prueba superada: Charmander vencio a Rattata como se esperaba.");
        } else {
            log.error("Prueba fallida: Charmander no debia perder contra Rattata.");
        }
    }
}
