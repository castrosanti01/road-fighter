package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Carretera extends Canvas {
	
	private static final Color ROAD_COLOR = Color.DARK_GRAY;
	private static final Color LINE_COLOR = Color.WHITE;
	private static final int WIDTH = 400;
	private int ROAD_WIDTH;
	private int HEIGHT;
    private int roadPositionY;

    public Carretera(int ancho, int largo) {
    	ROAD_WIDTH = ancho;
    	HEIGHT = largo;
    	roadPositionY = -HEIGHT + 500;
    	setSize(WIDTH, HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(ROAD_COLOR);
        g.fillRect((WIDTH - ROAD_WIDTH) / 2, roadPositionY, ROAD_WIDTH, HEIGHT);

        g.setColor(LINE_COLOR);
        int lineX = (WIDTH - ROAD_WIDTH) / 2 + ROAD_WIDTH / 2;
        for (int i = 0; i < HEIGHT / 100; i++) {
            g.fillRect(lineX, roadPositionY + i * 100, 5, 50);
            g.fillRect(lineX, roadPositionY + i * 100 + 100, 5, 50);
        }
        
        // Dibujar líneas blancas en los bordes de la carretera
        g.fillRect((WIDTH - ROAD_WIDTH) / 2 - 10, roadPositionY, 10, HEIGHT);
        g.fillRect((WIDTH + ROAD_WIDTH) / 2, roadPositionY, 10, HEIGHT);
    }

    public void moveRoad(int velocidad) {
        roadPositionY += (velocidad / 10); // Ajusta la velocidad de movimiento
        if(roadPositionY >= 500)
        	System.out.println("llegaste");
        repaint();
    }
    
    public int get_limite_izquierdo() {
    	return (WIDTH - ROAD_WIDTH) / 2;
    }
    
    public int get_limite_derecho() {
    	return (WIDTH + ROAD_WIDTH) / 2 - 40;
    }
}
