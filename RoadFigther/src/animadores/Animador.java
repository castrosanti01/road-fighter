package animadores;

import GUI.Celda;

public interface Animador{
	
	public Celda get_celda_asociada();
	
	public void comenzar_animacion();
}
