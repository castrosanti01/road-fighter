package logica;

import entidades.VehiculoJugador;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Carretera carretera) {
		
		carretera.resetear_carretera(100, 500, "/imagenes/carretera");
		carretera.agregar_jugador(new VehiculoJugador(200,375,"/imagenes/vehiculo_jugador"));
		
		return new Nivel.Builder()
				.nivelActual(1)
		        .build();
	}
}
