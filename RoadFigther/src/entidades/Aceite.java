package entidades;

import java.awt.Rectangle;

import logica.Juego;

public class Aceite extends Obstaculo{
	
	protected boolean aceite_activado;
	
	public Aceite(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
		aceite_activado = false;
	}
	
	public Rectangle get_bounds() {
		return new Rectangle(pos_x+10, pos_y+10, size_label-10, size_label-10);
	}

	protected void intersecta(VehiculoJugador vehiculoJugador) {
		if(!aceite_activado) {
			aceite_activado = true;
			vehiculoJugador.descarrilar(45);
		}
	}
	
}
