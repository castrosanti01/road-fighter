package logica;

import entidades.VehiculoJugador;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Carretera carretera) {
		
		carretera.agregar_jugador(new VehiculoJugador(275,300,"/imagenes/vehiculo_jugador"));
		
		return new Nivel.Builder()
				.nivelActual(1)
		        .build();
	}
}
