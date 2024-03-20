package entidades;

import GUI.EntidadGrafica;
import GUI.Ventana;
import logica.EntidadLogica;

public abstract class Entidad implements EntidadLogica {
	
	protected int pos_x;
	protected int pos_y;
	protected int velocidad;
	
	protected int size_label_x = Ventana.size_label_x;
	protected int size_label_y = size_label_x * 2;
	
	protected boolean detonado;
	
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
	
	@Override
	public int get_size_label_x() {
		return size_label_x;
	}
	
	@Override
	public int get_size_label_y() {
		return size_label_y;
	}
	
	public void set_entidad_grafica(EntidadGrafica e) {
		entidad_grafica = e;
	}
	
	public void cambiar_posicion(int nueva_x) {
		pos_x = nueva_x;
		entidad_grafica.notificarse_cambio_posicion();
	}
	
	public void cambiar_posicion_animado(int nueva_y) {
		pos_y = nueva_y;
		entidad_grafica.notificarse_cambio_posicion_animado();
	}
	
	public void detonar() {
		velocidad = 0;
		detonado = true;
		entidad_grafica.notificarse_detonar();
	}
	
	@Override
	public String get_imagen_representativa(int indice) {
		return imagenes_representativas[indice];
	}
	
	protected void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [3];
		imagenes_representativas[0] = path_img + ".png";
		imagenes_representativas[1] = path_img +"_detonado.png";
		imagenes_representativas[2] = "/imagenes/0.png";
	}

}
