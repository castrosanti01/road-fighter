package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import GUI.Carretera;
import entidades.Entidad;
import entidades.VehiculoJugador;
import entidades.VehiculoRuta;

public class GeneradorNivel {

	public static Nivel cargar_nivel(InputStream nombreArchivo, Juego juego) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(nombreArchivo));
		Random random = new Random();
		
		// Variables para las características del nivel
		int nivel = 0;
		int ancho_carretera = 0;
		int largo_carretera = 0;
		int posicion_inicial = 0;
		int cantidad_vehiculos_ruta = 0;
		
		String linea;

	    try {
			while ((linea = br.readLine()) != null) {
				// Ignorar líneas de comentario
				if (linea.startsWith("#")) 
					continue;
			  
				String[] partes = linea.split(":");
				if (partes.length == 2) {
				    String clave = partes[0].trim();
				    String valor = partes[1].trim();
				    switch (clave) {
				    	case "Nivel":
				      		nivel = Integer.parseInt(valor);
				      		break;
				    	case "Ancho Carretera":
				    		ancho_carretera = Integer.parseInt(valor);
				      		break;
				    	case "Largo Carretera":
				    		largo_carretera = Integer.parseInt(valor);
				      		break;
				    	case "Posicion inicial":
				    		posicion_inicial = Integer.parseInt(valor);
				      		break;
				    	case "Cantidad de Vehiculos de Ruta":
				    		cantidad_vehiculos_ruta = Integer.parseInt(valor);
				      		break;
				    }
				}
			}
			//Cerrar el archivo
		    br.close();  
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	    
		Carretera carretera = new Carretera(ancho_carretera, largo_carretera, juego);
		VehiculoJugador jugador = new VehiculoJugador(posicion_inicial, "/imagenes/vehiculo_jugador");
		VehiculoRuta vehiculo_ruta;
		
		List<Entidad> entidades = new LinkedList<Entidad>();
		for(int i = 1; i <= cantidad_vehiculos_ruta; i++) {
			int numeroAleatorio = random.nextInt(carretera.get_limite_derecho()-carretera.get_limite_izquierdo()) + carretera.get_limite_izquierdo();
			vehiculo_ruta = new VehiculoRuta(numeroAleatorio, -250 * i, "/imagenes/vehiculo_ruta");
			entidades.add(vehiculo_ruta);
		}
		
		return new Nivel.Builder()
				.nivelActual(nivel)
				.carretera(carretera)
				.vehiculoJugador(jugador)
				.entidades(entidades)
		        .build();
	}
}
