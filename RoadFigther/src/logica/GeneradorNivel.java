package logica;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import GUI.Carretera;
import entidades.Entidad;
import entidades.VehiculoJugador;
import entidades.VehiculoRuta;

public class GeneradorNivel {

	public static Nivel cargar_nivel(Juego juego) {
		
		int nivel = 1;
		int vidas = 3;
		Carretera carretera = new Carretera(260, 10000, juego);
		VehiculoJugador jugador = new VehiculoJugador(200,"/imagenes/vehiculo_jugador");
		VehiculoRuta vehiculo_ruta;
		Random random = new Random();
		
		List<Entidad> entidades = new LinkedList<Entidad>();
		for(int i = 1; i < 8; i++) {
			int numeroAleatorio = random.nextInt(carretera.get_limite_derecho()-carretera.get_limite_izquierdo()) + carretera.get_limite_izquierdo();
			vehiculo_ruta = new VehiculoRuta(numeroAleatorio, -250 * i, "/imagenes/vehiculo_ruta");
			entidades.add(vehiculo_ruta);
		}
		
		return new Nivel.Builder()
				.nivelActual(nivel)
				.vidas(vidas)
				.carretera(carretera)
				.vehiculoJugador(jugador)
				.entidades(entidades)
		        .build();
	}
}
