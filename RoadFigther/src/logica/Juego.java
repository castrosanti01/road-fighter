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
import entidades.Obstaculo;
import entidades.Vehiculo;
import entidades.VehiculoJugador;
import puntaje.ArchivoPuntaje;

public class Juego {
	
	public static final int IZQUIERDA = 15000;
	public static final int DERECHA = 15001;
	public static final int ZETA = 15002;
	public static final int EQUIS = 15003;
	
	protected Ventana mi_ventana;
	protected Nivel mi_nivel;
	protected Carretera mi_carretera;
	
	protected VehiculoJugador vehiculo_jugador;
	protected List<Vehiculo> vehiculos;
	protected List<Obstaculo> obstaculo;
	protected List<Entidad> entidades_a_eliminar;
	
	protected int vidas, nivel, puntaje;
	
	protected Timer timer_combustible, timer_puntaje_combustible, timer_fin_de_pista;
	
	protected ArchivoPuntaje mi_archivo_puntaje;
	
	public Juego() {
		mi_ventana = new Ventana(this);
		mi_archivo_puntaje = new ArchivoPuntaje();
		vidas = 3;
		nivel = 1;
		puntaje = 0;
		cargar_nivel(nivel);
	}
	
	private void cargar_nivel(int n) {
		mi_nivel = GeneradorNivel.cargar_nivel(getClass().getResourceAsStream("/niveles/"+n+"-nivel.txt"), this);
		mi_carretera = mi_nivel.get_carretera();
		vehiculo_jugador = mi_nivel.get_vehiculo_jugador();
		vehiculos = mi_nivel.get_vehiculos();
		obstaculo = mi_nivel.get_obstaculos();
		entidades_a_eliminar = new LinkedList<Entidad>();
		
		vehiculo_jugador.set_combustible(100);
		vehiculo_jugador.actualizar_limite_izquierdo(mi_carretera.get_limite_izquierdo());
		vehiculo_jugador.actualizar_limite_derecho(mi_carretera.get_limite_derecho());
		for(Vehiculo v: vehiculos) {
			v.actualizar_limite_izquierdo(mi_carretera.get_limite_izquierdo());
			v.actualizar_limite_derecho(mi_carretera.get_limite_derecho());
		}
		
		timer_combustible();
		timer_combustible.start();
		
		mi_ventana.actualizar_vidas(vidas);
		mi_ventana.actualizar_carretera(mi_carretera);
		mi_ventana.actualizar_puntaje(String.format("%06d", puntaje));
		asociar_entidades_logicas_graficas();
	}
	
	private void timer_combustible() {
		mi_ventana.actualizar_combustible(vehiculo_jugador.get_combustible());
		timer_combustible = new Timer(750, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	vehiculo_jugador.set_combustible(vehiculo_jugador.get_combustible()-1);
            	if(vehiculo_jugador.get_combustible() < 0) {
            		perder();
            		timer_combustible.stop();
            	}
            	else
            		mi_ventana.actualizar_combustible(vehiculo_jugador.get_combustible());
            }
        });
	}

	private void asociar_entidades_logicas_graficas() {
		EntidadGrafica eg;
		
		for(Obstaculo o: obstaculo) {
	    	eg = mi_ventana.agregar_entidad(o);
	        o.set_entidad_grafica(eg);
	    }
		
		eg = mi_ventana.agregar_entidad(vehiculo_jugador);
	    vehiculo_jugador.set_entidad_grafica(eg);
	    
	    for(Vehiculo v: vehiculos) {
	    	eg = mi_ventana.agregar_entidad(v);
	        v.set_entidad_grafica(eg);
	    }
	}
	
	public Vehiculo get_vehiculo_jugador() {
		return vehiculo_jugador;
	}
	
	public List<Entidad> get_entidades() {
		List<Entidad> entidades = new LinkedList<Entidad>();
		entidades.addAll(vehiculos);
		entidades.addAll(obstaculo);
		return entidades;
	}
	
	public List<Vehiculo> get_vehiculos() {
		return vehiculos;
	}
	
	public String get_puntaje() {
		String aux = null;
		if(puntaje >= mi_archivo_puntaje.cargar_puntaje()) {
			mi_archivo_puntaje.guardar_puntaje(puntaje);
			aux = String.format("%06d", puntaje)+"<br> NEW RECORD";
		}
		else
			aux = String.format("%06d", mi_archivo_puntaje.cargar_puntaje());
		return aux;
	}
	
	public void set_descarrilado_en_proceso(boolean b) {
		vehiculo_jugador.set_descarrilado_en_proceso(b);
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
				entidades_a_eliminar.add(v);
			else
				v.cambiar_posicion(v.get_pos_x(), v.get_pos_y() + vehiculo_jugador.get_velocidad()/15);	//mas alto, mas rapido son
		}
		for(Obstaculo o: obstaculo) {
			o.cambiar_posicion(o.get_pos_x(), o.get_pos_y() + vehiculo_jugador.get_velocidad()/10);
			if(o.get_pos_y() > 500)
				entidades_a_eliminar.add(o);
		}
		for(Entidad e: entidades_a_eliminar)
			vehiculos.remove(e);
	}
	
	public void desacelerar() {
		vehiculo_jugador.disminuir_velocidad(6);
		mi_carretera.moveRoad(vehiculo_jugador.get_velocidad());
		mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
		vehiculo_jugador.verificar_colision();
		for(Vehiculo v: vehiculos) {
			if(!v.get_detonado()) {
				if(vehiculo_jugador.get_velocidad() >= 150)
					v.cambiar_posicion(v.get_pos_x(), v.get_pos_y() + vehiculo_jugador.get_velocidad()/15);
				else if(vehiculo_jugador.get_velocidad() > 0)
					v.cambiar_posicion(v.get_pos_x(), v.get_pos_y() - 150/15);
				else if(v.get_pos_y() > -90 && v.get_pos_y() < 500){
					v.cambiar_posicion_animado(v.get_pos_x(), -200);
					entidades_a_eliminar.add(v);
				}
			}
			else 
				v.cambiar_posicion(v.get_pos_x(), v.get_pos_y() + vehiculo_jugador.get_velocidad()/15);
		}
		for(Obstaculo o: obstaculo) {
			o.cambiar_posicion(o.get_pos_x(), o.get_pos_y() + vehiculo_jugador.get_velocidad()/10);
			if(o.get_pos_y() > 500)
				entidades_a_eliminar.add(o);
		}
		for(Entidad e: entidades_a_eliminar)
			vehiculos.remove(e);
	}
	
	public void actualizar_puntaje() {
		puntaje += 50;
		mi_ventana.actualizar_puntaje(String.format("%06d", puntaje));
	}

	public void notificar_detonado(VehiculoJugador vj) {
		if(vidas == 1) {
			--vidas;
			perder();
		}
		else {
			for(Vehiculo v: vehiculos) 
				if(!v.get_detonado())
					v.cambiar_posicion_animado(v.get_pos_x(), v.get_pos_y() - 600);
			vehiculo_jugador.reivir();
			mi_ventana.actualizar_velocidad(vehiculo_jugador.get_velocidad());
			mi_ventana.actualizar_vidas(--vidas);
		}
	}
	
	public void notificar_detonado(Vehiculo vj) {
		mi_ventana.notificar_detonado();
	}
	
	public void notificar_descarrilado_en_proceso() {
		mi_ventana.notificar_descarrilado_en_proceso();
	}

	public void notificar_descarrilado_finalizado() {
		mi_ventana.notificar_descarrilado_finalizado();
	}
	
	public void notificar_power_up(int pos_x) {
		puntaje += 1000;
		mi_ventana.notificar_power_up(pos_x);
	}

	public void notificar_fin_de_pista() {
		notificar_descarrilado_finalizado();
		vehiculo_jugador.set_velocidad(0);
        timer_combustible.stop();
        mi_ventana.notificar_fin_de_nivel("CHECKPOINT");
        
        timer_puntaje_combustible = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(vehiculo_jugador.get_combustible() != 0) {
            		vehiculo_jugador.set_combustible(vehiculo_jugador.get_combustible()-1);
            		mi_ventana.actualizar_combustible(vehiculo_jugador.get_combustible());
            		
            		puntaje += 30;
            		mi_ventana.actualizar_puntaje(String.format("%06d", puntaje));
            	}
            	else {
            		timer_fin_de_pista.start();	
            		timer_puntaje_combustible.stop();
            	}
            		
            }
        });
        timer_puntaje_combustible.start();	
        
        timer_fin_de_pista = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mi_ventana.vaciar_ventana();
            	if(nivel <= 2) {
            		mi_ventana.notificarse_animacion_finalizada();
	            	cargar_nivel(++nivel);
            	}
            	else
            		mi_ventana.notificar_fin_de_juego("YOU WIN");
            }
        });
        timer_fin_de_pista.setRepeats(false); 
	}
	
	private void perder() {
		timer_combustible.stop();
		mi_ventana.actualizar_vidas(0);
		mi_ventana.notificar_fin_de_nivel("YOU LOSE");
        Timer timer_perder = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mi_ventana.vaciar_ventana();
            	mi_ventana.notificar_fin_de_juego("YOU LOSE");
            }
        });
        timer_perder.setRepeats(false); 
        timer_perder.start();
	}
	
	public void volver_a_jugar() {
		vidas = 3;
		nivel = 1;
		puntaje = 0;
		cargar_nivel(nivel);
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
