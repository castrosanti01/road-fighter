package entidades;

public class VehiculoRuta extends Vehiculo{
	
	public VehiculoRuta(int x, int y, String path_img) {
		super(x, y, path_img);
	}
	
	public int get_velocidad() {
		return 150;
	}
}
