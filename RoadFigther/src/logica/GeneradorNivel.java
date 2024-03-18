package logica;

import GUI.Carretera;
import entidades.VehiculoJugador;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Juego juego) {
		
		int nivel = 1;
		int vidas = 3;
		Carretera carretera = new Carretera(260, 5000, juego);
		VehiculoJugador jugador = new VehiculoJugador(200,"/imagenes/vehiculo_jugador");
		
		return new Nivel.Builder()
				.nivelActual(nivel)
				.vidas(vidas)
				.carretera(carretera)
				.vehiculoJugador(jugador)
		        .build();
	}
}
