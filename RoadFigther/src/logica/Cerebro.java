package logica;

import java.util.LinkedList;
import java.util.List;

import entidades.Entidad;
import entidades.VehiculoJugador;

public class Cerebro {
	
	protected Juego mi_juego;
	protected Entidad vehiculo_jugador;
	protected List<Entidad> entidades;
	
	public Cerebro(Juego j) {
		mi_juego = j;
		entidades = new LinkedList<Entidad>();
	}
	
	public Entidad get_vehiculo_jugador() {
		return vehiculo_jugador;
	}
	
	public List<Entidad> get_entidades(){
		return entidades;
	}
	
	public void agregar_jugador(Entidad vehiculoJugador) {
		vehiculo_jugador = vehiculoJugador;
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
		//System.out.println(aux.get_distancia());
		for(Entidad e: entidades) {
			e.cambiar_posicion(e.get_pos_y() + (10 * cant));
		}
	}
}
