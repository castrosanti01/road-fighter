package logica;

import entidades.Entidad;

public class Carretera {
	
	protected Juego mi_juego;
	protected Entidad vehiculo_jugador;
	
	public Carretera(Juego j) {
		mi_juego = j;
	}
	
	public void mover_jugador(int d) {
		switch (d) {
			case Juego.IZQUIERDA: {
				vehiculo_jugador.cambiar_posicion(vehiculo_jugador.get_pos_x() - 10);
				break;
			}
			case Juego.DERECHA: {
				vehiculo_jugador.cambiar_posicion(vehiculo_jugador.get_pos_x() + 10);
				break;
			}
		}
	}

	public void agregar_jugador(Entidad vehiculoJugador) {
		vehiculo_jugador = vehiculoJugador;
	}
	
	public Entidad get_vehiculo_jugador() {
		return vehiculo_jugador;
	}
	
}
