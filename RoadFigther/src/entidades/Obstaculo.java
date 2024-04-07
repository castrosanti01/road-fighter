package entidades;

import java.awt.Rectangle;

import logica.Juego;

public abstract class Obstaculo extends Entidad {

	protected Obstaculo(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
		size_label = 50;
		cargar_imagenes_representativas(path_img);
	}
	
	public Rectangle get_bounds() {
		return new Rectangle(pos_x+30, pos_y, size_label-10, size_label-10);
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
