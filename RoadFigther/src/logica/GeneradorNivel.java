package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import GUI.Carretera;
import entidades.Vehiculo;
import entidades.VehiculoEnemigo;
import entidades.VehiculoJugador;
import entidades.VehiculoPowerUp;
import entidades.VehiculoRuta;

public class GeneradorNivel {
	
	private static final int distanciamiento = -240;

	public static Nivel cargar_nivel(InputStream nombreArchivo, Juego juego) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(nombreArchivo));
		Random random = new Random();
		
		// Variables para las características del nivel
		int nivel = 0;
		int ancho_carretera = 0;
		int largo_carretera = 0;
		int posicion_inicial = 0;
		
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
				    }
				}
			}
			//Cerrar el archivo
		    br.close();  
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	    
		Carretera carretera = new Carretera(ancho_carretera, largo_carretera, juego);
		VehiculoJugador jugador = new VehiculoJugador(posicion_inicial,"/imagenes/vehiculo_jugador", juego);
		Vehiculo vehiculo;
		
		int cantidad_vehiculos = largo_carretera/500 + 10;
		int contador = 1;
		
		int limite_carretera = carretera.get_limite_derecho() - carretera.get_limite_izquierdo();
		int carril_random;
		int carril_izquierdo_random;
		int carril_derecho_random;
		
		List<Vehiculo> entidades = new LinkedList<Vehiculo>();
		while(contador <= cantidad_vehiculos+1) {
		    int randomNumber = random.nextInt(100);
		    if(randomNumber < 50) {
		        // 50% de probabilidad para un vehiculo de ruta
		        carril_random = carretera.get_limite_izquierdo() + random.nextInt(limite_carretera);
		        vehiculo = new VehiculoRuta(carril_random, distanciamiento * contador++, "/imagenes/vehiculo_ruta", juego);
		        entidades.add(vehiculo);
		    } 
		    else if(randomNumber < 70) {
		        // 20% de probabilidad para dos vehiculo de ruta
		        carril_izquierdo_random = carretera.get_limite_izquierdo() + random.nextInt(limite_carretera / 2);
		        carril_derecho_random = carretera.get_limite_izquierdo() + (limite_carretera / 2) + random.nextInt(limite_carretera / 2);
		        
		        vehiculo = new VehiculoRuta(carril_izquierdo_random, distanciamiento * contador, "/imagenes/vehiculo_ruta", juego);
		        entidades.add(vehiculo);
		        vehiculo = new VehiculoRuta(carril_derecho_random, distanciamiento * contador++, "/imagenes/vehiculo_ruta", juego);
		        entidades.add(vehiculo);
		    } 
		    else if(randomNumber < 90){
		        // 20% de probabilidad para un vehiculo enemigo
		        carril_random = carretera.get_limite_izquierdo() + random.nextInt(limite_carretera);
		        vehiculo = new VehiculoEnemigo(carril_random, distanciamiento * contador++, "/imagenes/vehiculo_enemigo", juego);
		        entidades.add(vehiculo);
		    }
		    else {
		    	// 10% de probabilidad para un camion
		        carril_random = carretera.get_limite_izquierdo() + random.nextInt(limite_carretera);
		        vehiculo = new VehiculoRuta(carril_random, distanciamiento * contador++, "/imagenes/camion", juego);
		        entidades.add(vehiculo);
		    }
		    	
		}
		
		carril_random = carretera.get_limite_izquierdo() + random.nextInt(limite_carretera);
		vehiculo = new VehiculoPowerUp(carril_random, -340, "/imagenes/vehiculo_power_up", juego);
		entidades.add(vehiculo);

		carril_random = carretera.get_limite_izquierdo() + random.nextInt(limite_carretera);
		vehiculo = new VehiculoPowerUp(carril_random, distanciamiento * cantidad_vehiculos / 4, "/imagenes/vehiculo_power_up", juego);
		entidades.add(vehiculo);
		
		carril_random = carretera.get_limite_izquierdo() + random.nextInt(limite_carretera);
		vehiculo = new VehiculoPowerUp(carril_random, distanciamiento * (cantidad_vehiculos - cantidad_vehiculos / 4), "/imagenes/vehiculo_power_up", juego);
		entidades.add(vehiculo);
		
		
		
		return new Nivel.Builder()
				.nivelActual(nivel)
				.carretera(carretera)
				.vehiculoJugador(jugador)
				.entidades(entidades)
		        .build();
	}
}
