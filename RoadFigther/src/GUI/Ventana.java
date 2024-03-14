package GUI;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import animadores.AnimadorMovimiento;
import logica.EntidadLogica;
import logica.Juego;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements VentanaAnimable{

    protected Juego mi_juego;
    protected JLayeredPane panel_principal;
    
    protected boolean bloquear_intercambios;
    
    protected Timer leftTimer, rightTimer, zTimer, xTimer;
    protected boolean isMovingLeft, isMovingRight, isPressingZ, isPressingX = false;
    
    public static final int size_label = 50;

    public Ventana(Juego j) {
        mi_juego = j;
        bloquear_intercambios = false;
        panel_principal = new JLayeredPane();
        
        inicializar();
    }

	public EntidadGrafica agregar_entidad(EntidadLogica e) {
        Celda celda = new Celda(this, e, e.get_size_label());
        panel_principal.add(celda, 0);
        return celda;
    }
    
    @Override
    public void animar_movimiento(Celda c) {
    	new AnimadorMovimiento(2, 10, c);
	}

	@Override
	public void animar_detonacion(Celda celda) {
		// TODO Auto-generated method stub
	}
	
	private void inicializar() {
		setTitle("Road Fighter");
        setSize(650, 525);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.GREEN);
        setResizable(false);
        setVisible(true);
        add(panel_principal);

        panel_principal.requestFocusInWindow();

        panel_principal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        isMovingLeft = true;
                        leftTimer.start();
                        break;
                    case KeyEvent.VK_RIGHT:
                        isMovingRight = true;
                        rightTimer.start();
                        break;
                    case KeyEvent.VK_Z:
                        isPressingZ = true;
                        zTimer.start();
                        break;
                    case KeyEvent.VK_X:
                        isPressingX = true;
                        xTimer.start();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        isMovingLeft = false;
                        leftTimer.stop();
                        break;
                    case KeyEvent.VK_RIGHT:
                        isMovingRight = false;
                        rightTimer.stop();
                        break;
                    case KeyEvent.VK_Z:
                        isPressingZ = false;
                        zTimer.stop();
                        break;
                    case KeyEvent.VK_X:
                        isPressingX = false;
                        xTimer.stop();
                        break;
                }
            }
        });
        
        // Inicialización de los Timers
        leftTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mi_juego.mover_jugador(Juego.IZQUIERDA);
            }
        });

        rightTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mi_juego.mover_jugador(Juego.DERECHA);
            }
        });

        zTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mi_juego.mover_jugador(Juego.ZETA);
            }
        });

        xTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mi_juego.mover_jugador(Juego.EQUIS);
            }
        });
	}
	
}
