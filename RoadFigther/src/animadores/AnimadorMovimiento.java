package animadores;

import GUI.Celda;

public class AnimadorMovimiento extends Thread implements Animador {
	
	protected Celda celda_animada;
	
	protected int step;
	protected int delay;
	
	protected int pos_x_destino;
	protected int pos_y_destino;
	
	public AnimadorMovimiento(int step, int delay, Celda celda) {
		this.celda_animada = celda;
		this.step = step;
		this.delay = delay;
				
		pos_x_destino = celda.get_entidad_logica().get_pos_x();
		pos_y_destino = celda.get_entidad_logica().get_pos_y();
		
		comenzar_animacion();
	}
	
	public Celda get_celda_asociada() {
		return celda_animada;
	}
	
	public void comenzar_animacion() {
		this.start();
	}
	
	public void run() {
		int size_label = celda_animada.get_size_label_x();
		int size_label_y = celda_animada.get_size_label_y();
		int pos_x_actual = celda_animada.getX();
		int pos_y_actual = celda_animada.getY();
		
		int paso_en_x = 0;
		int paso_en_y = 0;
		
		if (pos_x_actual != pos_x_destino) {
			paso_en_x = (pos_x_actual < pos_x_destino ? 1 : -1);
		}
		
		if (pos_y_actual != pos_y_destino) {
			paso_en_y = (pos_y_actual < pos_y_destino ? 1 : -1);
		}
		
		while( (pos_x_actual != pos_x_destino) || (pos_y_actual != pos_y_destino) ) {
			pos_x_actual += paso_en_x * step;
			pos_y_actual += paso_en_y * step;
			
			celda_animada.setBounds(pos_x_actual, pos_y_actual, size_label, size_label_y);
			
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
