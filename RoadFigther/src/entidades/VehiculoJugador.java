package entidades;

public class VehiculoJugador extends Entidad{
	
	public int combustible;
	public int puntaje;
	
	public VehiculoJugador(int x, String path_img) {
		super(x, 375, path_img);
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
	
	public void detonar() {
		super.detonar();
		cambiar_posicion_animado(200);
	}
	
	public void cambiar_posicion_animado(int nueva_x) {
		pos_x = nueva_x;
		entidad_grafica.notificarse_cambio_posicion_animado();
	}
	
	public void reivir() {
		detonado = false;
		entidad_grafica.notificarse_revivir();
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
