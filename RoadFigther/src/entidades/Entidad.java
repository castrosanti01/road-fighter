package entidades;

import java.awt.Rectangle;

import GUI.EntidadGrafica;
import GUI.Ventana;
import logica.EntidadLogica;
import logica.Juego;

public abstract class Entidad implements EntidadLogica {
	
	protected int pos_x;
	protected int pos_y;
	protected Juego mi_juego;
	
	protected int size_label = Ventana.size_label;
	
	protected String [] imagenes_representativas;
	protected EntidadGrafica entidad_grafica;
	
	protected Entidad(int x, int y, String path_img, Juego j) {
		pos_x = x;
		pos_y = y;
		mi_juego = j;
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
	public int get_size_label() {
		return size_label;
	}
	
	public Rectangle get_bounds() {
		return new Rectangle(pos_x+45, pos_y+10, size_label-45, size_label-10);
	}
	
	public void set_entidad_grafica(EntidadGrafica e) {
		entidad_grafica = e;
	}
	
	public void cambiar_posicion(int nueva_x, int nueva_y) {
		pos_x = nueva_x;
		pos_y = nueva_y;
		entidad_grafica.notificarse_cambio_posicion();
	}
	
	protected abstract void intersecta(VehiculoJugador vehiculoJugador);
	
	@Override
	public String get_imagen_representativa() {
		int indice = 0;
		return imagenes_representativas[indice];
	}
	
	protected void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [2];
		imagenes_representativas[0] = path_img + ".png";
		imagenes_representativas[1] = "/imagenes/vehiculo_detonado.png";
	}

}
