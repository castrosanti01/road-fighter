package logica;

import java.util.List;

import GUI.Carretera;
import entidades.Vehiculo;
import entidades.VehiculoJugador;

public class Nivel {

	protected int nivel;
	protected Carretera carretera;
	protected VehiculoJugador jugador;
	protected List<Vehiculo> entidades;

	public static class Builder {

		private int nivel;
	    private Carretera carretera;
	    private VehiculoJugador jugador;
	    private List<Vehiculo> entidades;
    
	    public Nivel.Builder nivelActual(int nivel_nro) {
	    	this.nivel = nivel_nro;
	    	return this;
	    }
	
	    public Nivel.Builder carretera(Carretera carretera) {
	    	this.carretera = carretera;
	    	return this;
	    }
	    
	    public Nivel.Builder vehiculoJugador(VehiculoJugador jugador) {
	    	this.jugador = jugador;
	    	return this;
	    }
	    
	    public Nivel.Builder entidades(List<Vehiculo> entidades) {
	    	this.entidades = entidades;
	    	return this;
	    }
	
	    public Nivel build() {
	    	return new Nivel(this);
	    }

	}
	
	public Nivel(Builder builder) {
	    this.nivel = builder.nivel;
	    this.carretera = builder.carretera;
	    this.jugador = builder.jugador;
	    this.entidades = builder.entidades;
	}
	
	public int get_numero_nivel() {
	    return nivel;
	}
	
	public Carretera get_carretera() {
	    return carretera;
	}
	
	public VehiculoJugador get_vehiculo_jugador() {
	    return jugador;
	}
	
	public List<Vehiculo> get_entidades() {
	    return entidades;
	}
	
}