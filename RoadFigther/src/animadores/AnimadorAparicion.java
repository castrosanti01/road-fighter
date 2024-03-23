package animadores;

import GUI.Celda;

public class AnimadorAparicion extends Thread implements Animador {

	protected CentralAnimaciones manager;
	protected Celda celda_animada;
	protected int delay;
	protected int prioridad;
	
	public AnimadorAparicion(CentralAnimaciones manager, Celda celda, int delay) {
		this.manager = manager;
		this.celda_animada = celda;
		this.delay = delay;
		this.prioridad = PrioridadAnimaciones.PRIORIDAD_SIN_PRIORIDAD;
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
		
		try {
			sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		celda_animada.cambiar_imagen(celda_animada.get_entidad_logica().get_imagen_representativa());
		manager.notificarse_finalizacion_animador(this);
	}

}
