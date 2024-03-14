package logica;

import java.awt.EventQueue;

import GUI.EntidadGrafica;
import GUI.Ventana;
import entidades.Entidad;

public class Juego {
	
	public static final int IZQUIERDA = 15000;
	public static final int DERECHA = 15001;
	public static final int ARRIBA = 15002;
	
	protected Ventana mi_ventana;
	protected Nivel mi_nivel;
	protected Carretera mi_carretera;
	
	public Juego() {
		mi_carretera = new Carretera(this);
		mi_ventana = new Ventana(this);
		mi_nivel = GeneradorNivel.cargar_nivel(mi_carretera);
		asociar_entidades_logicas_graficas();
	}
	
	private void asociar_entidades_logicas_graficas() {
		Entidad e;
	    EntidadGrafica eg;
	    
	    e = mi_carretera.get_vehiculo_jugador();
        eg = mi_ventana.agregar_entidad(e);
        e.set_entidad_grafica(eg);
	  }
	
	public void mover_jugador(int d) {
	    mi_carretera.mover_jugador(d);
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
