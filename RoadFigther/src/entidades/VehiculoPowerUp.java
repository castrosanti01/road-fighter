package entidades;

import logica.Juego;

public class VehiculoPowerUp extends Vehiculo{
	
	protected boolean usado;
	
	public VehiculoPowerUp(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
		usado = false;
	}
	
	protected void intersecta(VehiculoJugador vehiculo) {
		if(!usado) {
			usado = true;
			mi_juego.notificar_power_up(pos_x);
			entidad_grafica.notificarse_cambio_estado();
			vehiculo.set_combustible(vehiculo.get_combustible() + 5);
		}
	}
	
	@Override
	public String get_imagen_representativa() {
		int indice = 0;
		indice = (detonado ? 1 : indice);
		indice = (usado ? 2 : indice);
		return imagenes_representativas[indice];
	}
	
	protected void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [3];
		imagenes_representativas[0] = path_img + ".png";
		imagenes_representativas[1] = "/imagenes/vehiculo_detonado.png";
		imagenes_representativas[2] = "/imagenes/vehiculo_vacio.png";
	}
	
}
