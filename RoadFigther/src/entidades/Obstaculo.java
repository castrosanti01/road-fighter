package entidades;

import logica.Juego;

public abstract class Obstaculo extends Entidad {

	protected Obstaculo(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
		size_label = 50;
		cargar_imagenes_representativas(path_img);
	}
	
	@Override
	public String get_imagen_representativa() {
		int indice = 0;
		return imagenes_representativas[indice];
	}
	
	protected void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [2];
		imagenes_representativas[0] = path_img + ".png";
	}

}
