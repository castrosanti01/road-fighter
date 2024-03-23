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
	
	protected int vidas, combustible, nivel;
	protected int limite_izquierdo, limite_derecho;
	
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
		entidades = mi_nivel.get_entidades();
		entidades_a_eliminar = new LinkedList<Entidad>();
		
		limite_izquierdo = mi_carretera.get_limite_izquierdo();
		limite_derecho = mi_carretera.get_limite_derecho();
		
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
	    
	    for(Entidad e: entidades) {
	    	eg = mi_ventana.agregar_entidad(e);
	        e.set_entidad_grafica(eg);
	    }
	}
	
	private void perder() {
		System.out.println("fuiste");
		combustible_timer.stop();
		mi_ventana.actualizar_vidas(0);
		mi_ventana.notificarse_animacion_en_progreso();
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mi_ventana.vaciar_ventana();
            	vidas = 3;
            	cargar_nivel(1);
            	mi_ventana.notificarse_animacion_finalizada();
            }
        });
        timer.setRepeats(false); 
        timer.start();
	}
	
	public Entidad get_vehiculo_jugador() {
		return vehiculo_jugador;
	}
	
	public List<Entidad> get_entidades() {
		return entidades;
	}
	
	public void mover_jugador(int d) {
		switch (d) {
			case Juego.IZQUIERDA: {
				cambiar_posicion(vehiculo_jugador.get_pos_x() - 10);
				vehiculo_jugador.carrilar_derecho();
				break;
			}
			case Juego.DERECHA: {
				cambiar_posicion(vehiculo_jugador.get_pos_x() + 10);
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
	
	private void cambiar_posicion(int nueva_x) {
		if(en_rango(nueva_x))
			vehiculo_jugador.cambiar_posicion(nueva_x, vehiculo_jugador.get_pos_y());
		if(nueva_x == limite_izquierdo | nueva_x == limite_derecho) {
			if(vehiculo_jugador.get_velocidad() >= 150)
				explotar();
			else {
				if(nueva_x == limite_izquierdo) 
					cambiar_posicion(vehiculo_jugador.get_pos_x() + 20);
				if(nueva_x == limite_derecho)
					cambiar_posicion(vehiculo_jugador.get_pos_x() - 20);
			}
		}
	}
	
	private void deslizar_posicion(Entidad e, int nueva_x) {
		Entidad entidad = e;
		entidad.cambiar_posicion_animado(nueva_x, e.get_pos_y());
		if(nueva_x <= limite_izquierdo | nueva_x >= limite_derecho) {
			entidad.detonar();
			//entidades.remove(entidad);
		}
	}

	private void explotar() {
		if(vidas == 1) {
			--vidas;
			perder();
		}
		else {
			for(Entidad e: entidades) 
				e.cambiar_posicion_animado(e.get_pos_x(), e.get_pos_y() - 600);
			vehiculo_jugador.detonar();
			vehiculo_jugador.reivir();
			mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
			mi_ventana.actualizar_vidas(--vidas);
		}
	}

	private void verificar_colision() {
	    for(Entidad e : entidades) {
	        if(vehiculo_jugador.get_bounds().intersects(e.get_bounds())) {
	            double diferencia = (vehiculo_jugador.get_pos_x()+vehiculo_jugador.get_size_label_x()/2) - (e.get_pos_x()+e.get_size_label_x()/2);
	            if(diferencia > 0) {
	                deslizar_posicion(e, e.get_pos_x() - 35);
	                vehiculo_jugador.descarrilar(45);
	            }
	            else { 
	            	deslizar_posicion(e, e.get_pos_x() + 35);
	                vehiculo_jugador.descarrilar(-45);
	            }
	        }
	    }
	}


	private void avanzar_carretera(int cambio) {
		vehiculo_jugador.aumentar_velocidad(cambio);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		verificar_colision();
		if(!entidades.isEmpty()) {
			for(Entidad e: entidades) {
				if(e.get_pos_y() > 500)
					entidades_a_eliminar.add(e);
				else
					e.cambiar_posicion(e.get_pos_x(), e.get_pos_y() + vehiculo_jugador.get_velocidad()/25);	//mas alto, mas rapido son
			}
			for(Entidad e: entidades_a_eliminar)
				entidades.remove(e);
		}
	}
	
	public void desacelerar() {
		vehiculo_jugador.disminuir_velocidad(8);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		for(Entidad e: entidades) {
			e.cambiar_posicion(e.get_pos_x(), e.get_pos_y() - 400/50);
			if(vehiculo_jugador.get_velocidad() == 0) {
				if(e.get_pos_y() >= 0) {
					e.cambiar_posicion_animado(e.get_pos_x(), -90);
					entidades_a_eliminar.add(e);
				}
			}
		}
		for(Entidad e: entidades_a_eliminar)
			entidades.remove(e);
	}
	
	public void notificar_fin_de_pista() {
		vehiculo_jugador.set_velocidad(0);
        combustible_timer.stop();
        mi_ventana.notificarse_animacion_en_progreso();
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mi_ventana.vaciar_ventana();
            	if(nivel <= 2) {
            		mi_ventana.notificarse_animacion_finalizada();
	            	cargar_nivel(++nivel);
            	}
            }
        });
        timer.setRepeats(false); 
        timer.start();	
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
