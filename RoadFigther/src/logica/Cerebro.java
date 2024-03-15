package logica;

import java.util.LinkedList;
import java.util.List;

import GUI.Carretera;
import GUI.Ventana;
import entidades.Entidad;
import entidades.VehiculoJugador;

public class Cerebro {
	
	protected Carretera mi_carretera;
	protected Ventana mi_ventana;
	protected Entidad vehiculo_jugador;
	protected List<Entidad> entidades;
	
	protected int limite_izquierdo;
	protected int limite_derecho;
	
	public Cerebro(Ventana v) {
		mi_ventana = v;
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
	
	public void resetear_carretera(int ancho, int largo) {
		mi_carretera = mi_ventana.get_carretera();
		limite_izquierdo = mi_carretera.get_limite_izquierdo();
		limite_derecho = mi_carretera.get_limite_derecho();
	}
	
	public void mover_jugador(int d) {
		switch (d) {
			case Juego.IZQUIERDA: {
				cambiar_posicion(vehiculo_jugador.get_pos_x() - 10);
				break;
			}
			case Juego.DERECHA: {
				cambiar_posicion(vehiculo_jugador.get_pos_x() + 10);
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
	
	private void cambiar_posicion(int nueva_pos) {
		if(en_rango(nueva_pos))
			vehiculo_jugador.cambiar_posicion(nueva_pos);
	}

	private void avanzar_carretera(int cant) {
		mi_carretera.moveRoad(cant);
		VehiculoJugador aux = (VehiculoJugador) vehiculo_jugador;
		aux.aumentar_distancia(cant);
		//System.out.println(aux.get_distancia());
		/*for(Entidad e: entidades) {
			e.cambiar_posicion(e.get_pos_y() + (10 * cant));
		}*/
	}
	
	private boolean en_rango(int nueva_pos) {
		return nueva_pos >= limite_izquierdo && nueva_pos <= limite_derecho;
	}
}
