package entidades;

import logica.Juego;

public class Bache extends Obstaculo{
	
	protected boolean bache_activado;
	
	public Bache(int x, int y, String path_img, Juego j) {
		super(x, y, path_img, j);
		bache_activado = false;
	}
	
	protected void intersecta(VehiculoJugador vehiculoJugador) {
		if(!bache_activado) {
			bache_activado = true;
			vehiculoJugador.detonar();
		}
	}
	
}
