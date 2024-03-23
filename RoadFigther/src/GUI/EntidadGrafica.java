package GUI;

public interface EntidadGrafica {

	public void notificarse_cambio_posicion();
	
	public void notificarse_cambio_posicion_animado();
	
	public void notificarse_detonar();

	public void notificarse_revivir();

	public void notificarse_descarrilar(int angulo);
	
	public void rotar(int angulo);
	

}