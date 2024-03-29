package puntaje;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoPuntaje {

    protected static String nombreArchivo;

    public ArchivoPuntaje() {
        nombreArchivo = getClass().getResource("").getPath() + "HighScore.txt";
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                FileWriter escritor = new FileWriter(archivo);
                escritor.write("0");
                escritor.close();
                //System.out.println("Archivo puntaje en: "+archivo.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }
        } else {
            //System.out.println("El archivo ya existente en: "+archivo.getAbsolutePath());
        }
    }

    public int cargar_puntaje() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
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
            BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
            bw.write(Integer.toString(puntaje));
            bw.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el puntaje: " + e.getMessage());
        }
    }
}
