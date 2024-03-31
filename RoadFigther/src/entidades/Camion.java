package entidades;

import java.awt.Rectangle;

import logica.Juego;

public class Camion extends Vehiculo{
	
	public Camion(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
		size_label += 50; 
	}
	
	public Rectangle get_bounds() {
		return new Rectangle(pos_x+65, pos_y, size_label-85, size_label);
	}
	
	protected void intersecta(VehiculoJugador vehiculo) {
		vehiculo.detonar();
	}
	
	
}
