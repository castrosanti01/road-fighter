package entidades;

public class VehiculoRuta extends Entidad{
	
	public VehiculoRuta(int x, int y, String path_img) {
		super(x, y, path_img);
	}
	
	public boolean get_detonado() {
		return detonado;
	}
	
	public void cambiar_posicion(int nueva_y) {
		pos_y = nueva_y;
		entidad_grafica.notificarse_cambio_posicion();
	}
	
}
