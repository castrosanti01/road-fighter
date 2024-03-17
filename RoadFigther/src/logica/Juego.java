package logica;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

import GUI.Carretera;
import GUI.EntidadGrafica;
import GUI.Ventana;
import entidades.Entidad;
import entidades.VehiculoJugador;

public class Juego {
	
	public static final int IZQUIERDA = 15000;
	public static final int DERECHA = 15001;
	public static final int ZETA = 15002;
	public static final int EQUIS = 15003;
	
	protected Ventana mi_ventana;
	protected Nivel mi_nivel;
	protected Carretera mi_carretera;
	
	protected VehiculoJugador vehiculo_jugador;
	protected List<Entidad> entidades;
	
	protected int limite_izquierdo;
	protected int limite_derecho;
	
	public Juego() {
		mi_ventana = new Ventana(this);
		mi_nivel = GeneradorNivel.cargar_nivel(this);
		entidades = new LinkedList<Entidad>();
		asociar_entidades_logicas_graficas();
	}
	
	public void resetear_carretera(Carretera carretera) {
		mi_ventana.resetear_carretera(carretera);
		mi_carretera = carretera;
		limite_izquierdo = mi_carretera.get_limite_izquierdo();
		limite_derecho = mi_carretera.get_limite_derecho();
	}
	
	public void agregar_jugador(Entidad vehiculoJugador) {
		VehiculoJugador vehiculo = (VehiculoJugador) vehiculoJugador;
		vehiculo_jugador = vehiculo;
	}
	
	private void asociar_entidades_logicas_graficas() {
		Entidad jugador = vehiculo_jugador;
		EntidadGrafica eg;
	    
	    for(Entidad e: entidades) {
	    	eg = mi_ventana.agregar_entidad(e);
	        e.set_entidad_grafica(eg);
	    }
	    eg = mi_ventana.agregar_entidad(jugador);
	    jugador.set_entidad_grafica(eg);
	    
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
		if(nueva_pos == limite_izquierdo && vehiculo_jugador.get_velocidad() > 120)
			vehiculo_jugador.detonar();
	}

	private void avanzar_carretera(int cambio) {
		vehiculo_jugador.aumentar_velocidad(cambio);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		
		/*for(Entidad e: entidades) {
			e.cambiar_posicion(e.get_pos_y() + (10 * cant));
		}*/
	}
	
	public void desacelerar() {
		vehiculo_jugador.disminuir_velocidad(10);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
	}
	
	private boolean en_rango(int nueva_pos) {
		return nueva_pos >= limite_izquierdo && nueva_pos <= limite_derecho;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        try {
	          new Juego();
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    });
	 }
}
