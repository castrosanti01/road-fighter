package entidades;

public class VehiculoJugador extends Entidad{
	
	public int combustible;
	public int distancia_recorrida;
	public int puntaje;
	
	public VehiculoJugador(int x, String path_img) {
		super(x, 375, path_img);
		combustible = 100;
		distancia_recorrida = 0;
		velocidad = 0;
		puntaje = 0;
	}
	
	public int get_distancia() {
		return distancia_recorrida;
	}
	
	public int get_velocidad() {
		return velocidad;
	}
	
	public void aumentar_velocidad(int cambio) {
		switch(cambio) {
        	case 1: 
        		if(velocidad < 198)
        			velocidad += 5 * cambio; 
        		break;
        	case 2: 
        		if(velocidad < 400)
        			velocidad += 5 * cambio; 
        		break;
		}
		distancia_recorrida +=  5 * cambio;
	}
	
	public void disminuir_velocidad(int decremento) {
		if(velocidad - decremento > 0) {
			velocidad -= decremento;
			distancia_recorrida += decremento;
		}
		else
			velocidad = 0;
	}

	public void set_velocidad(int i) {
		velocidad = i;
	}
	
}
