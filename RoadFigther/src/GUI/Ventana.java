package GUI;

import java.awt.event.*;
import javax.swing.*;

import animadores.AnimadorMovimiento;
import logica.EntidadLogica;
import logica.Juego;

@SuppressWarnings("serial")
public class Ventana extends JFrame {

    protected Juego mi_juego;
    protected JLayeredPane panel_principal;
    
    protected boolean bloquear_intercambios;
    
    protected Timer movementTimer;
    protected boolean isMovingLeft = false;
    protected boolean isMovingRight = false;
    
    private int size_label = 50;

    public Ventana(Juego j) {
        mi_juego = j;
        bloquear_intercambios = false;
        panel_principal = new JLayeredPane();

        setTitle("Road Fighter");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        add(panel_principal);

        panel_principal.requestFocusInWindow();

        //Control de tecla presionada " <- "
        panel_principal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "moveLeftPressed");
        panel_principal.getActionMap().put("moveLeftPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bloquear_intercambios) {
                    isMovingLeft = true;
                    movementTimer.start();
                }
            }
        });
        panel_principal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveLeftReleased");
        panel_principal.getActionMap().put("moveLeftReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMovingLeft = false;
                movementTimer.stop();
            }
        });
        
        //Control de tecla presionada " -> "
        panel_principal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "moveRightPressed");
        panel_principal.getActionMap().put("moveRightPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bloquear_intercambios) {
                    isMovingRight = true;
                    movementTimer.start();
                }
            }
        });
        panel_principal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveRightReleased");
        panel_principal.getActionMap().put("moveRightReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMovingRight = false;
                movementTimer.stop();
            }
        });
        
        //Movimiento del jugador
        movementTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isMovingLeft) {
                    mi_juego.mover_jugador(Juego.IZQUIERDA);
                }
                if (isMovingRight) {
                    mi_juego.mover_jugador(Juego.DERECHA);
                }
            }
        });
    }

    public EntidadGrafica agregar_entidad(EntidadLogica e) {
        Celda celda = new Celda(this, e, size_label);
        panel_principal.add(celda, 0);
        return celda;
    }
    
    public void animar_movimiento(Celda c) {
	    new AnimadorMovimiento(2, 10, c);
	}

}
