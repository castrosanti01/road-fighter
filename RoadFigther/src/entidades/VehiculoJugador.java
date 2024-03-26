package entidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import logica.Juego;

public class VehiculoJugador extends Vehiculo{
	
	protected Juego mi_juego;
	protected int velocidad;
	protected int combustible;
	protected int puntaje;
	protected boolean descarilado_izquierdo, descarilado_derecho, descarrilado_en_proceso = false;
	protected Timer timer;
	
	public VehiculoJugador(int x, Juego j, String path_img) {
		super(x, 375, path_img);
		mi_juego = j;
		combustible = 100;
		velocidad = 0;
		puntaje = 0;
	}
	
	public int get_velocidad() {
		return velocidad;
	}
	
	public boolean get_detonado() {
		return detonado;
	}
	
	public void set_velocidad(int v) {
		velocidad = v;
	}
	
	public void carrilar_izquierdo() {
		descarilado_izquierdo = false;
	}
	
	public void carrilar_derecho() {
		descarilado_derecho = false;
	}
	
	public void descarrilar(int angulo) {
		descarrilado_en_proceso = true;
		entidad_grafica.rotar(angulo);
		if(angulo < 0)
			descarilado_izquierdo = true;
		else
			descarilado_derecho = true;
		
		timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(descarilado_izquierdo | descarilado_derecho)
            		entidad_grafica.notificarse_descarrilar(angulo);
            	else
            		entidad_grafica.rotar(-angulo);
            	descarrilado_en_proceso = false;
            }
        });
        timer.setRepeats(false); 
        timer.start();
	}
	
	public void detonar() {
		super.detonar();
		velocidad = 0;
		if(descarrilado_en_proceso) {
			timer.stop();
			descarrilado_en_proceso = false;
		}
		mi_juego.detonar(this);
	}
	
	private void cambiar_posicion_animado(int nueva_x) {
		pos_x = nueva_x;
		entidad_grafica.notificarse_cambio_posicion_animado();
	}
	
	public void reivir() {
		detonado = false;
		entidad_grafica.notificarse_revivir();
		cambiar_posicion_animado(200);
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
