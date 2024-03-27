package entidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import logica.Juego;

public class VehiculoJugador extends Vehiculo{
	
	protected int velocidad;
	protected int combustible;
	protected int puntaje;
	protected boolean descarilado_izquierdo, descarilado_derecho, descarrilado_en_proceso = false;
	protected Timer timer_descarrilar;
	
	public VehiculoJugador(int x, String path_img, Juego j) {
		super(x, 375, path_img, j);
		combustible = 100;
		velocidad = 0;
		puntaje = 0;
	}
	
	public int get_velocidad() {
		return velocidad;
	}
	
	public void set_velocidad(int v) {
		velocidad = v;
	}
	
	public void carrilar_izquierdo() {
		if(descarrilado_en_proceso && descarilado_izquierdo) {
			descarrilado_en_proceso = false;
			descarilado_izquierdo = false;
			entidad_grafica.rotar(45);
			
			timer_descarrilar.stop();
        	mi_juego.notificar_descarrilado_finalizado();
		}
	}
	
	public void carrilar_derecho() {
		if(descarrilado_en_proceso && descarilado_derecho) {
			descarrilado_en_proceso = false;
			descarilado_derecho = false;
			entidad_grafica.rotar(-45);
			
			timer_descarrilar.stop();
        	mi_juego.notificar_descarrilado_finalizado();
		}
	}
	
	public void cambiar_posicion_animado(int nueva_x, int nueva_y) {
		pos_x = nueva_x;
		pos_y = nueva_y;
		entidad_grafica.notificarse_cambio_posicion_animado();
		
		if((nueva_x <= limite_izquierdo | nueva_x >= limite_derecho) && velocidad >= 150)
			detonar();
		else {
			if(nueva_x == limite_izquierdo) 
				cambiar_posicion_animado(pos_x + 20, pos_y);
			if(nueva_x == limite_derecho)
				cambiar_posicion_animado(pos_x - 20, pos_y);
		}
	}
	
	public void verificar_colision() {
		List<Vehiculo> vehiculos = mi_juego.get_entidades();
	    for(Vehiculo vehiculo : vehiculos) {
	        if(get_bounds().intersects(vehiculo.get_bounds()) && !descarrilado_en_proceso && !vehiculo.get_detonado()) {
	            //Diferencia para saber si moverse a la derecha o a la izquierda
	        	double diferencia = (pos_x + size_label/2) - (vehiculo.get_pos_x() + vehiculo.get_size_label()/2);
	            if(diferencia > 0) {
	                descarrilar(-45);
	                cambiar_posicion_animado(pos_x + 35, pos_y);
	                vehiculo.cambiar_posicion_animado(vehiculo.get_pos_x() - 35, vehiculo.get_pos_y());
	            }
	            else { 
	            	descarrilar(45);
	            	cambiar_posicion_animado(pos_x - 35, pos_y);
	            	vehiculo.cambiar_posicion_animado(vehiculo.get_pos_x() + 35, vehiculo.get_pos_y());
	            }
	        }
	    }
	}
	
	public void descarrilar(int angulo) {
		mi_juego.notificar_descarrilado_en_proceso();
		descarrilado_en_proceso = true;
		
		entidad_grafica.rotar(angulo);
		if(angulo < 0)
			descarilado_izquierdo = true;
		else
			descarilado_derecho = true;
		
		//Si no se acomoda el vehiculo antes de 1 segundo:
		timer_descarrilar = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(descarilado_izquierdo | descarilado_derecho) 
            		entidad_grafica.notificarse_descarrilar(angulo);	//Descarrilado completo
            	
            	descarrilado_en_proceso = false;
            	//mi_juego.notificar_descarrilado_finalizado();
            }
        });
        timer_descarrilar.setRepeats(false); 
        timer_descarrilar.start();
	}
	
	public void detonar() {
		super.detonar();
		velocidad = 0;
		if(descarrilado_en_proceso) {
			timer_descarrilar.stop();
			mi_juego.notificar_descarrilado_finalizado();
			descarrilado_en_proceso = false;
		}
		mi_juego.notificar_detonado();
	}
	
	public void reivir() {
		detonado = false;
		entidad_grafica.notificarse_revivir();
		cambiar_posicion_animado(200, pos_y);
	}
	
	public void aumentar_velocidad(int cambio) {
		switch(cambio) {
        	case 1: 
        		if(velocidad <= 192) 
        			velocidad += 6; 		//max 128 en primer cambio
        		break;
        	case 2: 
        		if(velocidad <= 195)
        			velocidad += 3; 		//3 para que si empezas con el 2do cambio vas mas lento
        		else if(velocidad < 300)
        			velocidad += 6;
        		else if(velocidad >= 300)
        			velocidad = 300;
        		break;
		}
	}
	
	public void disminuir_velocidad(int decremento) {
		if(velocidad - decremento > 0) {
			velocidad -= decremento;
		}
		else
			velocidad = 0;
	}

}
