package com.example.Util;

import com.example.Model.Pokemon;
import com.example.Service.LineaEvolutiva;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jol.info.ClassLayout;
import oshi.SystemInfo;

//nuevo parcialhoy
import java.util.LinkedList;
import com.example.Model.ReporteBatalla;


//logica
public class SimuladorBatallas {

    private static final Logger log = LogManager.getLogger(SimuladorBatallas.class);
    private static final Logger logTiempos = LogManager.getLogger("tiempos");
    private static final Logger logPerformance = LogManager.getLogger("performance");

    // XP que da cada Caterpie
    private static final int XP_POR_CATERPIE = 50;

    //nuevo caa negra
    // cjaa negra cpacidad
    private static final int capacidadCajaNegra = 10;

    // historial caja
    private final LinkedList<ReporteBatalla> cajaNegra = new LinkedList<>();


    //metodo registrarvictorias
    // metodo registrar victorias
    public void registrarVictoria(String nombrePokemon, String nombreEnemigo) {

        // priemra parte
        // mirar si etsa vacia
        if (!cajaNegra.isEmpty()) {

            // Obtener el ultimo reporte
            ReporteBatalla ultimaPelea = cajaNegra.getLast();

            //mirar si la iltima pelea fue igual
            if (ultimaPelea.getNombrePokemon().equals(nombrePokemon) &&
                    ultimaPelea.getNombreEnemigo().equals(nombreEnemigo)) {

                ultimaPelea.cantidadDerrotados();


                return;
            }
        }

        //parte2
        if (cajaNegra.size() >= capacidadCajaNegra) {

            cajaNegra.removeFirst();

        }

        // crrqar nuevo reporte
        ReporteBatalla nuevaPelea =
                new ReporteBatalla(nombrePokemon, nombreEnemigo, 1);

        // agregarlo
        cajaNegra.addLast(nuevaPelea);

    }

    public void mostrarCajaNegra() {

        log.info("===== CAJA NEGRA =====");

        for (ReporteBatalla reporte : cajaNegra) {
            log.info("{}", reporte);
        }
    }



    //simula una batalla por turnos, el jugador ataca primero
    public boolean realizarBatalla(Pokemon jugador, Pokemon enemigo) {

        int vidaJugador = jugador.getPuntosDeVidaMaximos();
        int vidaEnemigo = enemigo.getPuntosDeVidaMaximos();

        int turno = 1;

        //la batalla sigue hasta que alguno se quede sin vida
        while (vidaJugador > 0 && vidaEnemigo > 0) {

            //ataque del jugador
            int danoJugador = Math.max(1, jugador.getAtaque() - enemigo.getDefensa());
            vidaEnemigo -= danoJugador;

            log.debug("Turno {}: {} ataca a {} -> dano {} (vida enemigo restante {})",
                    turno,
                    jugador.getNombre(),
                    enemigo.getNombre(),
                    danoJugador,
                    Math.max(vidaEnemigo, 0));

            //si el enemigo muere- termina la batalla
            if (vidaEnemigo <= 0) {
                break;
            }

            //ataque del enemigo
            int danoEnemigo = Math.max(1, enemigo.getAtaque() - jugador.getDefensa());
            vidaJugador -= danoEnemigo;

            log.debug("Turno {}: {} ataca a {} -> dano {} (vida jugador restante {})",
                    turno,
                    enemigo.getNombre(),
                    jugador.getNombre(),
                    danoEnemigo,
                    Math.max(vidaJugador, 0));

            turno++;
        }

        boolean gano = vidaEnemigo <= 0;

        if (gano) {
            log.info("{} vencio a {}", jugador.getNombre(), enemigo.getNombre());
        } else {
            log.info("{} fue derrotado por {}", jugador.getNombre(), enemigo.getNombre());
        }

        return gano;
    }

    //genera la horda de c aterpie para la prueba de 100.000 batallas
    public Pokemon[] generarHordaCaterpie(int cantidad) {

        Pokemon[] horda = new Pokemon[cantidad];

        for (int i = 0; i < horda.length; i++) {

            //estadisticas dadas en el ejercicio
            horda[i] = new Pokemon(
                    "Caterpie",
                    45,
                    30,
                    35,
                    -1
            );
        }

        /*
        log.info("===== CAJA NEGRA FINAL =====");

        for (ReporteBatalla reporte : cajaNegra) {
            log.info("{}", reporte);
        }*/
        return horda;
    }

    //cambuar
    //recorre toda la horda y realiza las batallas -> O(n), porque recorre el arreglo una sola vez
    public void iniciarEntrenamientoMasivo(
            LinkedList<LineaEvolutiva> EquipoJuanPis,
            Pokemon[] hordaEnemigos) {

        //memoria RAM disponible antes de las batallas
        SystemInfo systemInfo = new SystemInfo();
        long memoriaAntes =
                systemInfo.getHardware().getMemory().getAvailable();

        // crear var contador para que cambien cada 50
        int contadorcambio = 0;

        //inicio de la medicion del tiempo
        long inicio = System.nanoTime();

        for (int i = 0; i < hordaEnemigos.length; i++) {


            Pokemon enemigoActual = hordaEnemigos[i];

            //obtiene la fase actual del Pokemon
                Pokemon pokemonJugador = EquipoJuanPis.getFirst().getFaseActual();



            //la vida empieza nuevamente en cada batalla
            boolean gano = realizarBatalla(
                    pokemonJugador,
                    enemigoActual
            );

            //si gana, recibe experiencia y se revisa la evolucion
            if (gano) {
                EquipoJuanPis.getFirst().agregarExperiencia(XP_POR_CATERPIE);

                registrarVictoria(pokemonJugador.getNombre(), enemigoActual.getNombre());
            }

            contadorcambio++;

            //condicion apra cambiar cada 50
            if (contadorcambio == 50) {

                LineaEvolutiva cambiarPokemon = EquipoJuanPis.removeFirst();

                EquipoJuanPis.addLast(cambiarPokemon);

                contadorcambio = 0;

                log.info("Cambio de Pokemon para -> {}", EquipoJuanPis.getFirst().getFaseActual().getNombre());
            }


            //muestra el avance cada 10.000 batallas
            if ((i + 1) % 10000 == 0) {
                log.info("Batallas completadas: {}", i + 1);
            }
        }

        //fin de la medicion
        long fin = System.nanoTime();

        //memoria RAM disponible despues de las batallas
        long memoriaDespues =
                systemInfo.getHardware().getMemory().getAvailable();

        long tiempoTotal = fin - inicio;
        double tiempoMilisegundos = tiempoTotal / 1_000_000.0;

        //registra el tiempo de ejecucion
        logTiempos.info("Tiempo de ejecucion: {} ns", tiempoTotal);
        logTiempos.info("Tiempo de ejecucion: {} ms", tiempoMilisegundos);
        logTiempos.info("Batallas procesadas: {}", hordaEnemigos.length);

        //registra el uso de memoria con Oshi
        logPerformance.info("Memoria RAM disponible antes: {} bytes", memoriaAntes);

        logPerformance.info("Memoria RAM disponible despues: {} bytes", memoriaDespues);

        logPerformance.info("Diferencia de RAM: {} bytes", memoriaAntes - memoriaDespues);

        //muestra el tamaño de los objetos usando JOL
        logPerformance.info("Tamano de Pokemon (JOL):\n{}", ClassLayout.parseClass(Pokemon.class).toPrintable());

        logPerformance.info("Tamano de LineaEvolutiva (JOL):\n{}", ClassLayout.parseClass(LineaEvolutiva.class).toPrintable());
    }
}