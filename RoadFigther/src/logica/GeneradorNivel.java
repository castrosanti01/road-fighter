package logica;

import GUI.Carretera;
import entidades.VehiculoJugador;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Juego juego) {
		
		juego.resetear_carretera(new Carretera(260, 5000, juego));
		juego.agregar_jugador(new VehiculoJugador(200,"/imagenes/vehiculo_jugador"));
		
		return new Nivel.Builder()
				.nivelActual(1)
		        .build();
	}
}
