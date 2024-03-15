package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Carretera extends Canvas {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 10000;
    private static final int ROAD_WIDTH = 250;
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
    }

    public void moveRoad() {
        roadPositionY += 25; // Ajusta la velocidad de movimiento aquí
        repaint();
    }
}
