package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;

import logica.Juego;

@SuppressWarnings("serial")
public class Carretera extends JLabel {

	private static final Color ROAD_COLOR = Color.DARK_GRAY;
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dibujar pasto a los lados de la carretera
        g.setColor(Color.GREEN.darker());
        g.fillRect(0, 0, (WIDTH - ROAD_WIDTH) / 2, 700);
        g.fillRect((WIDTH + ROAD_WIDTH) / 2, 0, (WIDTH - ROAD_WIDTH) / 2, 700);
        
        // Dibujar la carretera
        g.setColor(ROAD_COLOR);
        g.fillRect((WIDTH - ROAD_WIDTH) / 2, 0, ROAD_WIDTH, 700);
        
        // Miniatura pista
        g.setColor(ROAD_COLOR);
        g.fillRect(15, 25, 30, 425);
        g.setColor(LINE_COLOR);
        g.drawRect(14, 25, 1, 425); 
        g.drawRect(45, 25, 1, 425); 
        g.setColor(Color.RED);
        g.fillRect(21, -(roadPositionY/75)+25, 20, 10); 

        // Dibujar líneas en la carretera
        g.setColor(LINE_COLOR);
        int lineX = (WIDTH - ROAD_WIDTH) / 2 + ROAD_WIDTH / 2;
        for (int i = 0; i < (HEIGHT-100) / 100; i++) {
            g.fillRect(lineX, roadPositionY + i * 100, 5, 50);
            g.fillRect(lineX, roadPositionY + i * 100 + 100, 5, 50);
        }
        // Mostrar mensaje en la línea de salida
        g.setFont(FONT);
        g.setColor(LINE_COLOR);
        g.drawString("¡Salida!", (WIDTH - g.getFontMetrics().stringWidth("¡Salida!")) / 2, roadPositionY + HEIGHT + 30);

        // Dibujar líneas blancas en los bordes de la carretera
        g.fillRect((WIDTH - ROAD_WIDTH) / 2 - 10, 0, 10, 700);
        g.fillRect((WIDTH + ROAD_WIDTH) / 2, 0, 10, 700);
        
        // Dibujar línea de salida
        g.setColor(FINISH_LINE_COLOR);
        g.fillRect((WIDTH - ROAD_WIDTH) / 2, roadPositionY + HEIGHT, ROAD_WIDTH, 5);
        
        // Dibujar línea de llegada (375 es la distancia a la que esta situado el auto)
        g.setColor(FINISH_LINE_COLOR);
        g.fillRect((WIDTH - ROAD_WIDTH) / 2, roadPositionY + 375, ROAD_WIDTH, 5);

        // Mostrar mensaje en la línea de llegada
        g.setFont(FONT);
        g.setColor(LINE_COLOR);
        g.drawString("¡Llegada!", (WIDTH - g.getFontMetrics().stringWidth("¡Llegada!")) / 2, roadPositionY + 365);
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
        return (WIDTH + ROAD_WIDTH) / 2 - 20;
    }

}
