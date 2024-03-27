package entidades;

import java.awt.Rectangle;
import java.util.List;

import GUI.EntidadGrafica;
import GUI.Ventana;
import logica.EntidadLogica;
import logica.Juego;

public abstract class Vehiculo implements EntidadLogica {
	
	protected int pos_x;
	protected int pos_y;
	protected Juego mi_juego;
	
	protected int limite_izquierdo, limite_derecho;
	
	protected int size_label = Ventana.size_label_x;
	
	protected boolean detonado;
	
	protected String [] imagenes_representativas;
	protected EntidadGrafica entidad_grafica;
	
	protected Vehiculo(int x, int y, String path_img, Juego j) {
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
	
	public boolean get_detonado() {
		return detonado;
	}
	
	public Rectangle get_bounds() {
		return new Rectangle(pos_x+40, pos_y+10, size_label-40, size_label-10);
	}
	
	public void set_entidad_grafica(EntidadGrafica e) {
		entidad_grafica = e;
	}
	
	public void actualizar_limite_izquierdo(int limite) {
		limite_izquierdo = limite;
	}
	
	public void actualizar_limite_derecho(int limite) {
		limite_derecho = limite;
	}
	
	public void cambiar_posicion(int nueva_x, int nueva_y) {
		pos_x = nueva_x;
		pos_y = nueva_y;
		entidad_grafica.notificarse_cambio_posicion();
	}
	
	public void cambiar_posicion_animado(int nueva_x, int nueva_y) {
		pos_x = nueva_x;
		pos_y = nueva_y;
		entidad_grafica.notificarse_cambio_posicion_animado();
		verificar_colision();
		if(nueva_x <= limite_izquierdo | nueva_x >= limite_derecho)
			detonar();
	}
	
	public void verificar_colision() {
		List<Vehiculo> vehiculos = mi_juego.get_entidades();
		for(Vehiculo vehiculo: vehiculos) {
			if(vehiculo.get_pos_y() > 0 && !detonado && vehiculo != this)
				if(get_bounds().intersects(vehiculo.get_bounds())) {
					detonar();
					vehiculo.detonar();
				}
		}
	}
	
	public void detonar() {
		detonado = true;
		entidad_grafica.notificarse_cambio_estado();
	}
	
	@Override
	public String get_imagen_representativa() {
		int indice = 0;
		indice += (detonado ? 1 : 0);
		return imagenes_representativas[indice];
	}
	
	protected void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [2];
		imagenes_representativas[0] = path_img + ".png";
		imagenes_representativas[1] = "/imagenes/vehiculo_detonado.png";
	}

}
