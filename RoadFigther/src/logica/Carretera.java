package logica;

import java.util.LinkedList;
import java.util.List;

import entidades.Entidad;

public class Carretera extends Entidad{
	
	protected Juego mi_juego;
	protected List<Entidad> entidades;
	protected Entidad vehiculo_jugador;
	
	public Carretera(Juego j) {
		super(0, 0, null);
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
				avanzar_carretera(pos_y + 10);
				//vehiculo_jugador.cambiar_posicion(vehiculo_jugador.get_pos_x() + 10);
				break;
			}
			case Juego.EQUIS: {
				System.out.println("x");
				//vehiculo_jugador.cambiar_posicion(vehiculo_jugador.get_pos_x() + 10);
				break;
			}
		}
	}
	
	private void avanzar_carretera(int nueva_y) {
		pos_y = nueva_y;
		System.out.println(pos_y);
		entidad_grafica.notificarse_cambio_posicion();
	}

	public void resetear_carretera(int limite_menor, int limite_mayor, String path) {
		cargar_imagenes_representativas(path);
		size_label = 500;
		entidades.add(this);
	}

	public void agregar_jugador(Entidad vehiculoJugador) {
		vehiculo_jugador = vehiculoJugador;
		entidades.add(vehiculo_jugador);
	}
	
	

	
}
