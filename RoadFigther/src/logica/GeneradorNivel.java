package logica;

import entidades.VehiculoJugador;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Juego juego) {
		
		juego.resetear_carretera(250, 10000);
		
		juego.agregar_jugador(new VehiculoJugador(200,375,"/imagenes/vehiculo_jugador"));
		
		return new Nivel.Builder()
				.nivelActual(1)
		        .build();
	}
}
