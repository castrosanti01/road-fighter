package entidades;

import java.util.List;
import logica.Juego;

public abstract class Vehiculo extends Entidad {

	protected int limite_izquierdo, limite_derecho;
	protected boolean detonado;
	
	protected Vehiculo(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
		cargar_imagenes_representativas(path_img);
	}
	
	
	public boolean get_detonado() {
		return detonado;
	}
	
	public void actualizar_limite_izquierdo(int limite) {
		limite_izquierdo = limite;
	}
	
	public void actualizar_limite_derecho(int limite) {
		limite_derecho = limite;
	}
	
	public void cambiar_posicion(int nueva_x, int nueva_y) {
		super.cambiar_posicion(nueva_x, nueva_y);
		if(pos_y > 500)
			mi_juego.actualizar_puntaje();
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
		//Si se chochan entre si
		List<Vehiculo> vehiculos = mi_juego.get_vehiculos();
		for(Vehiculo vehiculo: vehiculos) {
			if(vehiculo.get_pos_y() > 0 && !detonado && vehiculo != this)
				if(get_bounds().intersects(vehiculo.get_bounds())) {
					detonar();
					vehiculo.detonar();
				}
		}
	}
	
	protected void intersecta(VehiculoJugador vehiculo) {
		//Diferencia para saber si moverse a la derecha o a la izquierda
		double diferencia = (vehiculo.get_pos_x() + size_label/2) - (get_pos_x() + size_label/2);
		if(!detonado && !vehiculo.descarrilado_en_proceso) {
	        if(diferencia > 0) {
	        	vehiculo.descarrilar(-45);
	        	vehiculo.cambiar_posicion_animado(vehiculo.get_pos_x() + 40, vehiculo.get_pos_y());
	            cambiar_posicion_animado(get_pos_x() - 40, get_pos_y());
	        }
	        else { 
	        	vehiculo.descarrilar(45);
	        	vehiculo.cambiar_posicion_animado(vehiculo.get_pos_x() - 40, vehiculo.get_pos_y());
	        	cambiar_posicion_animado(get_pos_x() + 40, get_pos_y());
	        }
		}
		else {
			if(diferencia > 0) {
	        	vehiculo.cambiar_posicion_animado(vehiculo.get_pos_x() + 40, vehiculo.get_pos_y());
	            cambiar_posicion_animado(get_pos_x() - 40, get_pos_y());
	        }
	        else { 
	        	vehiculo.cambiar_posicion_animado(vehiculo.get_pos_x() - 35, vehiculo.get_pos_y());
	        	cambiar_posicion_animado(get_pos_x() + 35, get_pos_y());
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
		indice = (detonado ? 1 : 0);
		return imagenes_representativas[indice];
	}
	
	protected void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [2];
		imagenes_representativas[0] = path_img + ".png";
		imagenes_representativas[1] = "/imagenes/vehiculo_detonado.png";
	}

}
