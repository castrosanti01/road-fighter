package logica;

public class Nivel {

	protected int nivel_nro;
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;


	public static class Builder {

		private int nivel_nro;
	    private int fila_inicial_jugador;
	    private int columna_inicial_jugador;
    
	    public Nivel.Builder nivelActual(int nivel_nro) {
	    	this.nivel_nro = nivel_nro;
	    	return this;
	    }
	
	    public Nivel.Builder filaInicial(int fila_inicial_jugador) {
	    	this.fila_inicial_jugador = fila_inicial_jugador;
	    	return this;
	    }
	
	    public Nivel.Builder columnaInicial(int columna_inicial_jugador) {
	    	this.columna_inicial_jugador = columna_inicial_jugador;
	    	return this;
	    }
	
	    public Nivel build() {
	    	return new Nivel(this);
	    }
	
	}
	
	public Nivel(Builder builder) {
	    this.nivel_nro = builder.nivel_nro;
	    this.fila_inicial_jugador = builder.fila_inicial_jugador;
	    this.columna_inicial_jugador = builder.columna_inicial_jugador;
	}
	
	public int get_nro_nivel() {
	    return nivel_nro;
	}
	
	public int get_fila_inicial_jugador() {
	    return fila_inicial_jugador;
	}
	
	public int get_columna_inicial_jugador() {
	    return columna_inicial_jugador;
	}

}