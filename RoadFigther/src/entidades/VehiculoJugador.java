package entidades;

public class VehiculoJugador extends Entidad{
	
	public int combustible;
	public int distancia_recorrida;
	public int puntaje;
	
	public VehiculoJugador(int x, int y, String path_img) {
		super(x, y, path_img);
		combustible = 100;
		distancia_recorrida = 0;
		puntaje = 0;
	}
	
	public int get_distancia() {
		return distancia_recorrida;
	}
	
	public void aumentar_distancia(int distancia) {
		distancia_recorrida +=  distancia;
	}
	
}
