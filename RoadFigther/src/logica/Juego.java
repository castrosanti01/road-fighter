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
import entidades.Vehiculo;
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
	protected List<Vehiculo> vehiculos, vehiculos_a_eliminar;
	
	protected int vidas, combustible, nivel;
	
	protected Timer combustible_timer;
	
	public Juego() {
		mi_ventana = new Ventana(this);
		vidas = 3;
		nivel = 1;
		cargar_nivel(nivel);
	}
	
	private void cargar_nivel(int n) {
		mi_nivel = GeneradorNivel.cargar_nivel(getClass().getResourceAsStream("/niveles/"+n+"-nivel.txt"), this);
		mi_carretera = mi_nivel.get_carretera();
		vehiculo_jugador = mi_nivel.get_vehiculo_jugador();
		vehiculos = mi_nivel.get_entidades();
		vehiculos_a_eliminar = new LinkedList<Vehiculo>();
		
		vehiculo_jugador.actualizar_limite_izquierdo(mi_carretera.get_limite_izquierdo());
		vehiculo_jugador.actualizar_limite_derecho(mi_carretera.get_limite_derecho());
		for(Vehiculo v: vehiculos) {
			v.actualizar_limite_izquierdo(mi_carretera.get_limite_izquierdo());
			v.actualizar_limite_derecho(mi_carretera.get_limite_derecho());
		}
			
		
		combustible = 100;
		timer_combustible();
		combustible_timer.start();
		
		mi_ventana.actualizar_vidas(vidas);
		mi_ventana.actualizar_carretera(mi_carretera);
		asociar_entidades_logicas_graficas();
	}
	
	private void timer_combustible() {
		mi_ventana.actualizar_combustible(combustible);
		combustible_timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	combustible--;
            	if(combustible < 0) {
            		perder();
            		combustible_timer.stop();
            	}
            	else
            		mi_ventana.actualizar_combustible(combustible);
            }
        });
	}

	private void asociar_entidades_logicas_graficas() {
		EntidadGrafica eg;
		eg = mi_ventana.agregar_entidad(vehiculo_jugador);
	    vehiculo_jugador.set_entidad_grafica(eg);
	    
	    for(Vehiculo v: vehiculos) {
	    	eg = mi_ventana.agregar_entidad(v);
	        v.set_entidad_grafica(eg);
	    }
	}
	
	private void perder() {
		combustible_timer.stop();
		mi_ventana.actualizar_vidas(0);
		mi_ventana.notificarse_animacion_en_progreso();
        Timer timer_perder = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mi_ventana.vaciar_ventana();
            	vidas = 3;
            	cargar_nivel(1);
            	mi_ventana.notificarse_animacion_finalizada();
            }
        });
        timer_perder.setRepeats(false); 
        timer_perder.start();
	}
	
	public Vehiculo get_vehiculo_jugador() {
		return vehiculo_jugador;
	}
	
	public List<Vehiculo> get_entidades() {
		return vehiculos;
	}
	
	public void mover_jugador(int d) {
		switch (d) {
			case Juego.IZQUIERDA: {
				vehiculo_jugador.cambiar_posicion_animado(vehiculo_jugador.get_pos_x() - 10, vehiculo_jugador.get_pos_y());
				vehiculo_jugador.carrilar_derecho();
				break;
			}
			case Juego.DERECHA: {
				vehiculo_jugador.cambiar_posicion_animado(vehiculo_jugador.get_pos_x() + 10, vehiculo_jugador.get_pos_y());
				vehiculo_jugador.carrilar_izquierdo();
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
	
	private void avanzar_carretera(int cambio) {
		vehiculo_jugador.aumentar_velocidad(cambio);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		vehiculo_jugador.verificar_colision();
		for(Vehiculo v: vehiculos) {
			if(v.get_pos_y() > 500)
				vehiculos_a_eliminar.add(v);
			else
				v.cambiar_posicion(v.get_pos_x(), v.get_pos_y() + vehiculo_jugador.get_velocidad()/15);	//mas alto, mas rapido son
		}
		for(Vehiculo v: vehiculos_a_eliminar)
			vehiculos.remove(v);
	}
	
	public void desacelerar() {
		vehiculo_jugador.disminuir_velocidad(8);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		for(Vehiculo v: vehiculos) {
			if(!v.get_detonado()) {
				v.cambiar_posicion(v.get_pos_x(), v.get_pos_y() - 250/15);
				if(vehiculo_jugador.get_velocidad() == 0)
					if(v.get_pos_y() > -90) {
						v.cambiar_posicion_animado(v.get_pos_x(), -90);
						vehiculos_a_eliminar.add(v);
					}
			}
			else 
				v.cambiar_posicion(v.get_pos_x(), v.get_pos_y() + vehiculo_jugador.get_velocidad()/15);
		}
		for(Vehiculo v: vehiculos_a_eliminar)
			vehiculos.remove(v);
	}

	public void notificar_detonado() {
		if(vidas == 1) {
			--vidas;
			perder();
		}
		else {
			for(Vehiculo v: vehiculos) 
				v.cambiar_posicion_animado(v.get_pos_x(), v.get_pos_y() - 600);
			vehiculo_jugador.reivir();
			mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
			mi_ventana.actualizar_vidas(--vidas);
		}
	}

	public void notificar_fin_de_pista() {
		vehiculo_jugador.set_velocidad(0);
        combustible_timer.stop();
        mi_ventana.notificarse_animacion_en_progreso();
        Timer timer_fin_de_pista = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mi_ventana.vaciar_ventana();
            	if(nivel <= 2) {
            		mi_ventana.notificarse_animacion_finalizada();
	            	cargar_nivel(++nivel);
            	}
            }
        });
        timer_fin_de_pista.setRepeats(false); 
        timer_fin_de_pista.start();	
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

	public void notificar_descarrilado_en_proceso() {
		mi_ventana.notificar_descarrilado_en_proceso();
	}

	public void notificar_descarrilado_finalizado() {
		mi_ventana.notificar_descarrilado_finalizado();
	}
	
}
