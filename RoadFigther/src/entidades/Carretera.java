package entidades;

public class Carretera extends Entidad{
	
	public Carretera(int x, int y, String path_img) {
		super(x, y, path_img);
		size_label = 500;
	}
	
	public void cambiar_posicion(int nueva_y) {
		pos_y = nueva_y;
		entidad_grafica.notificarse_cambio_posicion();
	}
	
}
