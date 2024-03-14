package logica;

import java.util.LinkedList;
import java.util.List;

import entidades.Carretera;
import entidades.Entidad;
import entidades.VehiculoJugador;

public class Cerebro {
	
	protected Juego mi_juego;
	protected List<Entidad> entidades;
	protected Entidad vehiculo_jugador;
	
	public Cerebro(Juego j) {
		entidades = new LinkedList<Entidad>();
		mi_juego = j;
	}
	
	public Entidad get_vehiculo_jugador() {
		return vehiculo_jugador;
	}
	
	public List<Entidad> get_entidades(){
		return entidades;
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
			case Juego.ZETA: {
				avanzar_carretera(1);
				break;
			}
			case Juego.EQUIS: {
				avanzar_carretera(2);
				break;
			}
		}
	}
	
	private void avanzar_carretera(int cant) {
		VehiculoJugador aux = (VehiculoJugador) vehiculo_jugador;
		aux.aumentar_distancia(cant);
		System.out.println(aux.get_distancia());
		for(Entidad e: entidades) {
			e.cambiar_posicion(e.get_pos_y() + (10 * cant));
		}
	}

	public void agregar_carretera(Carretera carretera) {
		entidades.add(carretera);
	}

	public void agregar_jugador(Entidad vehiculoJugador) {
		vehiculo_jugador = vehiculoJugador;
	}
}
