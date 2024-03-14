package entidades;

public class VehiculoJugador extends Entidad{
	
	public int Combustible;
	public int Puntaje;
	
	public VehiculoJugador(int x, int y, String path_img) {
		super(x, y, path_img);
		Combustible = 100;
		Puntaje = 0;
	}
	
}
