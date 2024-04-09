package puntaje;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoPuntaje {

	private File archivo_high_score;

    public ArchivoPuntaje() {
        archivo_high_score = new File("HighScore.txt");
    }

    public int cargar_puntaje() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo_high_score));
            String linea = br.readLine();
            br.close();
            return Integer.parseInt(linea);
        } catch (IOException e) {
            System.err.println("Error al cargar el puntaje: " + e.getMessage());
            return 0;
        }
    }

    public void guardar_puntaje(int puntaje) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo_high_score));
            bw.write(Integer.toString(puntaje));
            bw.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el puntaje: " + e.getMessage());
        }
    }
}