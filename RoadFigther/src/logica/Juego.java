package logica;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

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
	protected List<Entidad> entidades, entidades_a_eliminar;
	
	protected int vidas, combustible;
	protected int limite_izquierdo, limite_derecho;
	
	protected Timer combustible_timer;
	
	public Juego() {
		mi_ventana = new Ventana(this);
		mi_nivel = GeneradorNivel.cargar_nivel(this);
		mi_carretera = mi_nivel.get_carretera();
		cargar_nivel();
		
		asociar_entidades_logicas_graficas();
	}
	
	private void cargar_nivel() {
		vehiculo_jugador = mi_nivel.get_vehiculo_jugador();
		entidades = mi_nivel.get_entidades();
		entidades_a_eliminar = new LinkedList<Entidad>();
		
		vidas = mi_nivel.get_vidas();
		combustible = 100;
		
		limite_izquierdo = mi_carretera.get_limite_izquierdo();
		limite_derecho = mi_carretera.get_limite_derecho();
		timer_combustible();
		combustible_timer.start();
	}

	private void asociar_entidades_logicas_graficas() {
		mi_ventana.actualizar_vidas(vidas);
		mi_ventana.resetear_carretera(mi_carretera);
		
		
		EntidadGrafica eg;
		eg = mi_ventana.agregar_entidad(vehiculo_jugador);
	    vehiculo_jugador.set_entidad_grafica(eg);
	    
	    for(Entidad e: entidades) {
	    	eg = mi_ventana.agregar_entidad(e);
	        e.set_entidad_grafica(eg);
	    }
	  }
	
	private void timer_combustible() {
		mi_ventana.actualizar_combustible(combustible);
		combustible_timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mi_ventana.actualizar_combustible(--combustible);
            }
        });
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
		//Explotar
		if(nueva_pos == limite_izquierdo | nueva_pos == limite_derecho) {
			if(vehiculo_jugador.get_velocidad() >= 150){
				if(vidas == 1) {
					--vidas;
					System.out.println("loser");
				}
				else {
					
					for(Entidad e: entidades) 
						e.cambiar_posicion_animado(e.get_pos_y() - 600);
					vehiculo_jugador.detonar();
					vehiculo_jugador.reivir();
					mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
					mi_ventana.actualizar_vidas(--vidas);
				}
			}
			else {
				if(nueva_pos == limite_izquierdo)
					cambiar_posicion(vehiculo_jugador.get_pos_x() + 20);
				if(nueva_pos == limite_derecho)
					cambiar_posicion(vehiculo_jugador.get_pos_x() - 20);
			}
		}
	}

	private void avanzar_carretera(int cambio) {
		vehiculo_jugador.aumentar_velocidad(cambio);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		for(Entidad e: entidades) {
			if(e.get_pos_y() > 500) {
				//500 sale de la pantalla
				entidades_a_eliminar.add(e);
			}
			e.cambiar_posicion(e.get_pos_y() + vehiculo_jugador.get_velocidad()/25);	//mas alto, mas rapido son
		}
		for(Entidad e: entidades_a_eliminar)
			entidades.remove(e);
		
	}
	
	public void desacelerar() {
		vehiculo_jugador.disminuir_velocidad(8);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		for(Entidad e: entidades) {
			e.cambiar_posicion(e.get_pos_y() - 400/50);
			if(vehiculo_jugador.get_velocidad() == 0) {
				if(e.get_pos_y() > 0) {
					e.cambiar_posicion_animado(-90);
					entidades_a_eliminar.add(e);
				}
			}
		}
		for(Entidad e: entidades_a_eliminar)
			entidades.remove(e);
	}
	
	private boolean en_rango(int nueva_pos) {
		return nueva_pos >= limite_izquierdo && nueva_pos <= limite_derecho;
	}
	
	public void notificar_fin_de_pista() {
		vehiculo_jugador.set_velocidad(0);
		combustible_timer.stop();
		mi_ventana.notificar_fin_de_pista();
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
