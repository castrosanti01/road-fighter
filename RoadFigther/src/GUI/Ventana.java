package GUI;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import animadores.AnimadorMovimiento;
import entidades.VehiculoJugador;
import logica.EntidadLogica;
import logica.Juego;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements VentanaAnimable {

    protected Juego mi_juego;
    protected Carretera mi_carretera; 
    protected JLayeredPane panel_principal;
    protected JPanel panel_carretera;

    protected boolean bloquear_intercambios, estaAcelerando;

    protected Timer leftTimer, rightTimer, zTimer, xTimer, desacelerar;
    
    protected JLabel velocidad;

    public static final int size_label_x = 40;
    

    public Ventana(Juego j) {
        mi_juego = j;
        panel_principal = new JLayeredPane();
        bloquear_intercambios = false;
        inicializar();
    }
    
    public void resetear_carretera(Carretera c) {
        mi_carretera = c;
        panel_carretera.add(mi_carretera);
	}
    
    public EntidadGrafica agregar_entidad(EntidadLogica e) {
        Celda celda = new Celda(this, e);
        panel_principal.add(celda, 0);
        return celda;
    }

    @Override
    public void animar_movimiento(Celda c) {
        new AnimadorMovimiento(1, 2, c);
    }

    @Override
    public void animar_detonacion(Celda celda) {
        // TODO Auto-generated method stub
    }
    
    public void actualizar_velocidad(int velocimetro) {
    	velocidad.setText(velocimetro+" Km/h");
	}
    
    private void inicializar() {
        setTitle("Road Fighter");
        setSize(650, 525);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.GREEN);
        setResizable(false);
        setVisible(true);
        getContentPane().add(panel_principal);
        
        panel_carretera = new JPanel();
        panel_carretera.setBackground(Color.DARK_GRAY);
        panel_carretera.setBounds(0, 0, 400, 500);
        panel_principal.add(panel_carretera);
        panel_carretera.setLayout(null);
        
        velocidad = new JLabel("0 Km/h");
        velocidad.setBounds(406, 154, 76, 28);
        panel_principal.add(velocidad);
        
        panel_principal.requestFocusInWindow();
        panel_principal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT: leftTimer.start(); break;
                    
                    case KeyEvent.VK_RIGHT: rightTimer.start(); break;
                    
                    case KeyEvent.VK_Z: zTimer.start(); estaAcelerando = true; break;
                    
                    case KeyEvent.VK_X: xTimer.start(); estaAcelerando = true; break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT: leftTimer.stop(); break;
                    
                    case KeyEvent.VK_RIGHT: rightTimer.stop(); break;
                    
                    case KeyEvent.VK_Z:
                    	estaAcelerando = false;
                    	zTimer.stop();
                        desacelerar.start();
                        break;
                    case KeyEvent.VK_X:
                    	estaAcelerando = false;
                        xTimer.stop();
                        desacelerar.start();
                        break;
                }
            }
        });
        
        desacelerar = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(estaAcelerando)
            		desacelerar.stop();
            	else {
            		mi_juego.desacelerar();
            		VehiculoJugador aux = (VehiculoJugador) mi_juego.get_vehiculo_jugador();
                	if(aux.get_velocidad() == 0)
                        desacelerar.stop();
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
