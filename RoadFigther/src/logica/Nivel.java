package logica;

import java.util.List;

import GUI.Carretera;
import entidades.Entidad;
import entidades.VehiculoJugador;

public class Nivel {

	protected int nivel;
	protected int vidas;
	protected Carretera carretera;
	protected VehiculoJugador jugador;
	protected List<Entidad> entidades;

	public static class Builder {

		private int nivel;
	    private int vidas;
	    private Carretera carretera;
	    private VehiculoJugador jugador;
	    private List<Entidad> entidades;
    
	    public Nivel.Builder nivelActual(int nivel_nro) {
	    	this.nivel = nivel_nro;
	    	return this;
	    }
	
	    public Nivel.Builder vidas(int vidas) {
	    	this.vidas = vidas;
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
	    
	    public Nivel.Builder entidades(List<Entidad> entidades) {
	    	this.entidades = entidades;
	    	return this;
	    }
	
	    public Nivel build() {
	    	return new Nivel(this);
	    }

	}
	
	public Nivel(Builder builder) {
	    this.nivel = builder.nivel;
	    this.vidas = builder.vidas;
	    this.carretera = builder.carretera;
	    this.jugador = builder.jugador;
	    this.entidades = builder.entidades;
	}
	
	public int get_numero_nivel() {
	    return nivel;
	}
	
	public int get_vidas() {
	    return vidas;
	}
	
	public Carretera get_carretera() {
	    return carretera;
	}
	
	public VehiculoJugador get_vehiculo_jugador() {
	    return jugador;
	}
	
	public List<Entidad> get_entidades() {
	    return entidades;
	}
	
}