package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import logica.Juego;

@SuppressWarnings("serial")
public class Carretera extends Canvas {
    
    private static final Color LINE_COLOR = Color.WHITE;
    private static final Color FINISH_LINE_COLOR = Color.RED;
    private static final Font FONT = new Font("Arial", Font.BOLD, 24);
    private static final int WIDTH = 400;
    private int ROAD_WIDTH;
    private int HEIGHT;
    private int roadPositionY;
    
    protected Juego mi_juego;

    public Carretera(int ancho, int largo, Juego juego) {
        ROAD_WIDTH = ancho;
        HEIGHT = largo;
        roadPositionY = -HEIGHT;
        setSize(WIDTH, HEIGHT);
        mi_juego = juego;
    }

    @Override
    public void paint(Graphics g) {

    	// Dibujar líneas en la carretera
        g.setColor(LINE_COLOR);
        int lineX = (WIDTH - ROAD_WIDTH) / 2 + ROAD_WIDTH / 2;
        for (int i = 0; i < HEIGHT / 100; i++) {
            g.fillRect(lineX, roadPositionY + i * 100, 5, 50);
            g.fillRect(lineX, roadPositionY + i * 100 + 100, 5, 50);
        }

        // Dibujar líneas blancas en los bordes de la carretera (como no se mueve el tamaño 500 es el del largo de la pantalla)
        g.fillRect((WIDTH - ROAD_WIDTH) / 2 - 10, 0, 10, 500);
        g.fillRect((WIDTH + ROAD_WIDTH) / 2, 0, 10, 500);
        
        // Dibujar línea de salida
        g.setColor(LINE_COLOR);
        g.fillRect((WIDTH - ROAD_WIDTH) / 2, roadPositionY + HEIGHT, ROAD_WIDTH, 100);

        // Dibujar línea de llegada (375 es la distancia a la que esta situado el auto)
        g.setColor(FINISH_LINE_COLOR);
        g.fillRect((WIDTH - ROAD_WIDTH) / 2, roadPositionY + 375, ROAD_WIDTH, 5);

        // Mostrar mensaje en la línea de llegada
        g.setFont(FONT);
        g.setColor(Color.WHITE);
        g.drawString("¡Línea de llegada!", (WIDTH - g.getFontMetrics().stringWidth("¡Línea de llegada!")) / 2, roadPositionY + 365);
    }

    public void moveRoad(int velocidad) {
        if (roadPositionY <= 0) {
        	roadPositionY += (velocidad / 8); // Ajusta la velocidad de movimiento
        }
        else {
        	mi_juego.notificar_fin_de_pista();
        }
        repaint();
    }
    
    public int get_limite_izquierdo() {
        return (WIDTH - ROAD_WIDTH) / 2;
    }
    
    public int get_limite_derecho() {
        return (WIDTH + ROAD_WIDTH) / 2 - 40;
    }

}
