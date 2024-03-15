package logica;

import java.awt.EventQueue;
import java.util.List;

import GUI.EntidadGrafica;
import GUI.Ventana;
import entidades.Entidad;

public class Juego {
	
	public static final int IZQUIERDA = 15000;
	public static final int DERECHA = 15001;
	public static final int ZETA = 15002;
	public static final int EQUIS = 15003;
	
	protected Ventana mi_ventana;
	protected Nivel mi_nivel;
	protected Cerebro cerebro;
	
	public Juego() {
		mi_ventana = new Ventana(this);
		cerebro = new Cerebro(mi_ventana);
		mi_nivel = GeneradorNivel.cargar_nivel(cerebro);
		asociar_entidades_logicas_graficas();
	}
	
	private void asociar_entidades_logicas_graficas() {
		List<Entidad> entidades = cerebro.get_entidades();
		Entidad jugador = cerebro.get_vehiculo_jugador();
		EntidadGrafica eg;
	    
	    for(Entidad e: entidades) {
	    	eg = mi_ventana.agregar_entidad(e);
	        e.set_entidad_grafica(eg);
	    }
	    
	    eg = mi_ventana.agregar_entidad(jugador);
	    jugador.set_entidad_grafica(eg);
	    
	  }
	
	public void mover_jugador(int d) {
		cerebro.mover_jugador(d);
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
