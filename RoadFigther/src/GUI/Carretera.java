package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Carretera extends Canvas {
	private static final int WIDTH = 400;
    private static final int HEIGHT = 10000;
    private static final int ROAD_WIDTH = 240;
    private static final Color ROAD_COLOR = Color.GRAY;
    private static final Color LINE_COLOR = Color.WHITE;
    private int roadPositionY = -HEIGHT + 500;

    public Carretera() {
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
        roadPositionY += 15 * velocidad; // Ajusta la velocidad de movimiento
        repaint();
    }
    
    public int get_limite_izquierdo() {
    	return (WIDTH - ROAD_WIDTH) / 2;
    }
    
    public int get_limite_derecho() {
    	return (WIDTH + ROAD_WIDTH) / 2 - 40;
    }
}
