package logica;

import entidades.Carretera;
import entidades.VehiculoJugador;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Cerebro cerebro) {
		
		cerebro.agregar_jugador(new VehiculoJugador(200,375,"/imagenes/vehiculo_jugador"));
		for(int i = 0; i < 7; i++) //-500
			cerebro.agregar_carretera(new Carretera(0, -458*i,"/imagenes/carretera"));
		
		return new Nivel.Builder()
				.nivelActual(1)
		        .build();
	}
}
