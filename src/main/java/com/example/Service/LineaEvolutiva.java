package com.example.Service;

import com.example.Model.Pokemon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


 //maneja la lista enlazada de las evoluciones del Pokemon del jugador
 public class LineaEvolutiva {

     private static final Logger log = LogManager.getLogger(LineaEvolutiva.class);

     private Pokemon faseActual;
     private int experienciaAcumulada;


     public LineaEvolutiva(Pokemon faseInicial) {
         this.faseActual = faseInicial;
         this.experienciaAcumulada = 0;
     }

     // Get´s
     public Pokemon getFaseActual() {
         return faseActual;
     }

     public int getExperienciaAcumulada() {
         return experienciaAcumulada;
     }


     //se le suma la experiencia ganada y revisar si ya toca evolucionar.
     public void agregarExperiencia(int experiencia) {
         experienciaAcumulada += experiencia;
         verificarEvolucion();
     }


     //mirar si ya cumple con la exp para evolucionar si es que tiene sig evolucion
     //ejem charizard tiene experienciaRequerida = -1, entt se queda ahi porque nunca se va a cumplir, no hay mas evo (ps el null)
     private void verificarEvolucion() {
         if (experienciaAcumulada >= faseActual.getExperienciaRequerida()
                 && faseActual.getSiguienteEvolucion() != null) {

             String nombreAnterior = faseActual.getNombre();

             // aca usamos lista enlazada solo movemos el puntero al siguiente nodo.
             faseActual = faseActual.getSiguienteEvolucion();

             //escribir el log sencillo -> Registro de evolicion
             log.info("{} ha evolucionado a {}", nombreAnterior, faseActual.getNombre());
         }
     }
 }

