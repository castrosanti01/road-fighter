package logica;

import java.util.List;

import GUI.Carretera;
import entidades.Obstaculo;
import entidades.Vehiculo;
import entidades.VehiculoJugador;

public class Nivel {

	protected int nivel;
	protected Carretera carretera;
	protected VehiculoJugador jugador;
	protected List<Vehiculo> vehiculos;
	protected List<Obstaculo> obstaculo;

	public static class Builder {

		private int nivel;
	    private Carretera carretera;
	    private VehiculoJugador jugador;
	    private List<Vehiculo> vehiculos;
	    private List<Obstaculo> obstaculo;
    
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
	    
	    public Nivel.Builder vehiculos(List<Vehiculo> vehiculos) {
	    	this.vehiculos = vehiculos;
	    	return this;
	    }
	    
	    public Nivel.Builder obstaculos(List<Obstaculo> obstaculo) {
	    	this.obstaculo = obstaculo;
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
	    this.vehiculos = builder.vehiculos;
	    this.obstaculo = builder.obstaculo;
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
	
	public List<Vehiculo> get_vehiculos() {
	    return vehiculos;
	}
	
	public List<Obstaculo> get_obstaculos() {
	    return obstaculo;
	}
	
}