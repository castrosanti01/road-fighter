package entidades;

import GUI.EntidadGrafica;
import logica.EntidadLogica;

public abstract class Entidad implements EntidadLogica {
	
	public int pos_x;
	public int pos_y;
	public int velocidad;
	
	protected String [] imagenes_representativas;
	protected EntidadGrafica entidad_grafica;
	
	protected Entidad(int x, int y, String path_img) {
		pos_x = x;
		pos_y = y;
		cargar_imagenes_representativas(path_img);
	}
	
	@Override
	public int get_pos_x() {
		return pos_x;
	}
	
	@Override
	public int get_pos_y() {
		return pos_y;
	}
	
	public void set_entidad_grafica(EntidadGrafica e) {
		entidad_grafica = e;
	}
	
	public void cambiar_posicion(int nueva_x) {
		pos_x = nueva_x;
		entidad_grafica.notificarse_cambio_posicion();
	}
	
	@Override
	public String get_imagen_representativa() {
		int indice = 0;
		//indice += (enfocada ? 1 : 0);
		return imagenes_representativas[indice];
	}
	
	protected void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [2];
		imagenes_representativas[0] = path_img + ".png";
		//imagenes_representativas[1] = path_img +"-detonado.gif";
	}
}
