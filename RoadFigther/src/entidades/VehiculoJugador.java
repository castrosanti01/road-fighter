package entidades;

public class VehiculoJugador extends Entidad{
	
	public int combustible;
	public int distancia_recorrida;
	public int puntaje;
	
	public VehiculoJugador(int x, int y, String path_img) {
		super(x, y, path_img);
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
	
	public void aumentar_velocidad(int incremento) {
		velocidad += incremento;
		distancia_recorrida +=  incremento;
	}
	
	public void disminuir_velocidad(int decremento) {
		if(velocidad > 0)
			velocidad -= decremento;
	}

	
	
}
