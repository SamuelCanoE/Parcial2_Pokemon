package com.example;

import com.example.Model.Pokemon;
import com.example.Service.LineaEvolutiva;
import com.example.Util.SimuladorBatallas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import com.example.Model.ReporteBatalla;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        //crear fases para que evolucione
        //Pokemon1
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500);
        Pokemon charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000);
        Pokemon charizard = new Pokemon("Charizard", 78, 84, 78, -1);

        //Pokemon2
        Pokemon bulbasaur = new Pokemon("Bulbasaur", 34, 47, 40, 1500);
        Pokemon ivysaur = new Pokemon("Ivysaur", 62, 70, 53, 5000);
        Pokemon venusaur = new Pokemon("Venusaur", 80 , 80, 75, -1);

        //Pokemon3
        Pokemon squirtle = new Pokemon("Squirtle", 38, 40, 39, 1500);
        Pokemon wartortle = new Pokemon("Wartotle", 70, 65, 54, 5000);
        Pokemon blastoise = new Pokemon("Blastoise", 78, 84, 78, -1);


        //conectar como una lista enlazada: Charmander -> Charmeleon -> Charizard -> null

        //Pokemon1
        charmander.setSiguienteEvolucion(charmeleon);
        charmeleon.setSiguienteEvolucion(charizard);

        //Pokemon2
        bulbasaur.setSiguienteEvolucion(ivysaur);
        ivysaur.setSiguienteEvolucion(venusaur);

        //Pokemon3
        squirtle.setSiguienteEvolucion(wartortle);
        wartortle.setSiguienteEvolucion(blastoise);

        // creamos la linea evolutiva del jugador, empezando en charmander
        LineaEvolutiva miPokemon = new LineaEvolutiva(charmander);
        LineaEvolutiva miPokemon2 = new LineaEvolutiva(bulbasaur);
        LineaEvolutiva miPokemon3 = new LineaEvolutiva(squirtle);


        //Lista del equipo -> Evolucion
        LinkedList<LineaEvolutiva> EquipoJuanPis = new LinkedList<>();
        EquipoJuanPis.add(miPokemon);
        EquipoJuanPis.add(miPokemon2);
        EquipoJuanPis.add(miPokemon3);

        SimuladorBatallas simulador = new SimuladorBatallas();


        //crear la horda de 100.000 caterpie
        Pokemon[] hordaEnemigos = simulador.generarHordaCaterpie(100000);

        //prueba del entrenamiento masivo - x equiponuan
        simulador.iniciarEntrenamientoMasivo(EquipoJuanPis, hordaEnemigos);
        simulador.mostrarCajaNegra();

        /*
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
        */
    }

}
