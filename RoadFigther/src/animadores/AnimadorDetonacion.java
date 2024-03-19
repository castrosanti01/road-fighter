package animadores;

import GUI.Celda;

public class AnimadorDetonacion extends Thread implements Animador {

	protected CentralAnimaciones manager;
	protected Celda celda_animada;
	protected int delay;
	protected int prioridad;
	
	public AnimadorDetonacion(CentralAnimaciones manager, Celda celda, int delay) {
		this.manager = manager;
		this.celda_animada = celda;
		this.delay = delay;
		this.prioridad = PrioridadAnimaciones.PRIORIDAD_DETONACION;
	}
	
	public Celda get_celda_asociada() {
		return celda_animada;
	}
	
	public int get_prioridad() {
		return prioridad;
	}

	public void comenzar_animacion() {
		this.start();
	}
	
	public void run() {
		// '0' estado normal, '1' detonado, '2' imagen vacia
		celda_animada.cambiar_imagen(celda_animada.get_entidad_logica().get_imagen_representativa(1));
		
		try {
			sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		celda_animada.cambiar_imagen(celda_animada.get_entidad_logica().get_imagen_representativa(2));
		
		manager.notificarse_finalizacion_animador(this);
	}

}
