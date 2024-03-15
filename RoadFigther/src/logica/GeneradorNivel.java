package logica;

import entidades.VehiculoJugador;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Cerebro cerebro) {
		
		cerebro.agregar_jugador(new VehiculoJugador(200,375,"/imagenes/vehiculo_jugador"));
		
		//cerebro.agregar_carretera(new Carretera());
		
		return new Nivel.Builder()
				.nivelActual(1)
		        .build();
	}
}
