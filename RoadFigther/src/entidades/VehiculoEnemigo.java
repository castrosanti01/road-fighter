package entidades;

import logica.Juego;

public class VehiculoEnemigo extends Vehiculo{
	
	public VehiculoEnemigo(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
	}
	
	public void cambiar_posicion(int nueva_x, int nueva_y) {
		super.cambiar_posicion(nueva_x, nueva_y);
		if(pos_y > 150 && pos_y < 220 && !detonado) {
			int pos_x_jugador = mi_juego.get_vehiculo_jugador().get_pos_x();
			if(pos_x > pos_x_jugador)
				cambiar_posicion_animado(pos_x - 10, pos_y);
			else
				cambiar_posicion_animado(pos_x + 10, pos_y);
		}
	}
	
}
