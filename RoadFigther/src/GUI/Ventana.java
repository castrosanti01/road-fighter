package GUI;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import animadores.CentralAnimaciones;
import entidades.VehiculoJugador;
import logica.EntidadLogica;
import logica.Juego;
import java.awt.Font;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements VentanaAnimable, VentanaNotificable{

    protected Juego mi_juego;
    protected Carretera mi_carretera; 
    protected CentralAnimaciones mi_animador;
    
    protected JLayeredPane panel_principal;
    protected JPanel panel_carretera;

    protected boolean bloquear_jugabilidad, bloquear_aceleracion, isPressingX, isPressingZ;

    protected Timer leftTimer, rightTimer, zTimer, xTimer, desacelerar;
    
    protected JLabel velocimetro, combustible, puntaje, vidas, info_nivel, info_vehiculo;
	
    public static final int size_label = 80;
    

    public Ventana(Juego j) {
        mi_juego = j;
        mi_animador = new CentralAnimaciones(this);
        
        panel_principal = new JLayeredPane();
        bloquear_jugabilidad = false;
        inicializar();
    }
    
    public EntidadGrafica agregar_entidad(EntidadLogica e) {
        Celda celda = new Celda(this, e);
        panel_principal.add(celda, 0);
        return celda;
    }
    
    @Override
	public void notificarse_animacion_en_progreso() {
		synchronized(this){
			bloquear_jugabilidad = true;
			leftTimer.stop();
			rightTimer.stop();
			zTimer.stop();
			xTimer.stop();
		}
	}
	
	@Override
	public void notificarse_animacion_finalizada() {
		synchronized(this){
			bloquear_jugabilidad = false;
			isPressingZ = false;
			isPressingX = false;
		}
	}
	
	@Override
	public void notificar_descarrilado_en_proceso() {
		//bloqueo aceleracion
		synchronized(this){
			bloquear_aceleracion = true;
			isPressingZ = false;
			isPressingX = false;
			zTimer.stop();
			xTimer.stop();
			desacelerar.start();
		}
	}
	
	@Override
	public void notificar_descarrilado_finalizado() {
		synchronized(this){
			mi_juego.set_descarrilado_en_proceso(false);
			bloquear_aceleracion = false;
			bloquear_jugabilidad = false;
		}
	}
	
	public void notificar_fin_de_nivel(String info) {
		notificarse_animacion_en_progreso();
		info_nivel.setText("<html>"+info+"<br>HI: "+mi_juego.get_puntaje()+"</html>");
		info_nivel.setVisible(true);
	}
	
	public void notificar_fin_de_juego(String info) {
		notificarse_animacion_en_progreso();
		panel_principal.removeAll();
		
		info_nivel.setBounds((this.getWidth()-200)/2, 150, 200, 120);
		info_nivel.setText("<html>"+info+"<br>HI: "+mi_juego.get_puntaje()+"</html>");
		info_nivel.setVisible(true);
		panel_principal.add(info_nivel);
		
		JButton boton_reintentar = new JButton("Volver a Jugar");
		boton_reintentar.setBounds((this.getWidth()-140)/2, 365, 140, 30);
        boton_reintentar.setBackground(new Color(65, 105, 225)); 
        boton_reintentar.setForeground(Color.WHITE);
        boton_reintentar.setFont(new Font("Consolas", Font.BOLD, 16));
        boton_reintentar.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255)));
        panel_principal.add(boton_reintentar);
        
        boton_reintentar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new Juego();
        	}
        });
	}
	
	public void notificar_power_up(int pos_x) {
		info_vehiculo.setBounds(pos_x, 342, 85, 33);
		info_vehiculo.setVisible(true);
		
		Timer timer_power_up = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	info_vehiculo.setVisible(false);
            }
        });
        timer_power_up.setRepeats(false); 
        timer_power_up.start();
	}

    @Override
    public void animar_movimiento(Celda c) {
    	mi_animador.animar_movimiento(c);
    }
    
    @Override
    public void animar_aparicion(Celda c) {
    	mi_animador.animar_aparicion(c);
    }
    
    @Override
    public void animar_descarrilar(Celda c, int angulo) {
    	mi_animador.animar_descarrilar(c, angulo);
	}
    
    public void actualizar_carretera(Carretera c) {
        mi_carretera = c;
        panel_carretera.add(mi_carretera);
	}

    public void actualizar_puntaje(String puntos) {
    	puntaje.setText(puntos);
	}
    
    public void actualizar_velocidad(int vel) {
    	velocimetro.setText(vel + " Km/h");
	}
    
    public void actualizar_vidas(int cant) {
    	vidas.setText("VIDAS: " + cant);
	}
    
    public void actualizar_combustible(int cant) {
    	combustible.setText("FUEL: " + cant);
	}
    
    private void inicializar() {
        setTitle("Road Fighter");
        setSize(650, 525);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setResizable(false);
        setIconImage(new ImageIcon(this.getClass().getResource("/imagenes/icono.png")).getImage());
        setVisible(true);
        getContentPane().add(panel_principal);
        
        panel_carretera = new JPanel();
        panel_carretera.setBounds(0, 0, 400, 500);
        panel_principal.add(panel_carretera);
        panel_carretera.setLayout(null);
        
        info_nivel = new JLabel("");
        info_nivel.setForeground(new Color(255, 255, 255));
        info_nivel.setFont(new Font("Consolas", Font.BOLD, 30));
        info_nivel.setHorizontalAlignment(SwingConstants.CENTER);
        info_nivel.setOpaque(true);
        info_nivel.setBackground(Color.BLACK);
        info_nivel.setBounds(10, 102, 380, 120);
        panel_carretera.add(info_nivel);
        info_nivel.setVisible(false);
        
        info_vehiculo = new JLabel("1000");
        info_vehiculo.setFont(new Font("Consolas", Font.BOLD, 30));
        info_vehiculo.setForeground(Color.WHITE);
        info_vehiculo.setBounds(177, 342, 85, 33);
        panel_carretera.add(info_vehiculo);
        info_vehiculo.setVisible(false);
        
        velocimetro = new JLabel("0 Km/h");
        velocimetro.setForeground(new Color(255, 255, 255));
        velocimetro.setFont(new Font("Consolas", Font.BOLD, 30));
        velocimetro.setBounds(410, 125, 179, 36);
        panel_principal.add(velocimetro);
        
        combustible = new JLabel("FUEL: ");
        combustible.setForeground(new Color(255, 255, 255));
        combustible.setFont(new Font("Consolas", Font.BOLD, 30));
        combustible.setBounds(410, 230, 179, 36);
        panel_principal.add(combustible);
        
        puntaje = new JLabel("000000");
        puntaje.setForeground(new Color(255, 255, 255));
        puntaje.setFont(new Font("Consolas", Font.BOLD, 30));
        puntaje.setBounds(410, 37, 179, 36);
        panel_principal.add(puntaje);
        
        vidas = new JLabel("VIDAS: ");
        vidas.setForeground(new Color(255, 255, 255));
        vidas.setFont(new Font("Consolas", Font.BOLD, 30));
        vidas.setBounds(410, 416, 179, 36);
        panel_principal.add(vidas);
        
        panel_principal.requestFocusInWindow();
        panel_principal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (!bloquear_jugabilidad) {
                    switch (key) {
                        case KeyEvent.VK_LEFT:
                            leftTimer.start();
                            break;

                        case KeyEvent.VK_RIGHT:
                            rightTimer.start();
                            break;

                        case KeyEvent.VK_Z:
                            if (!bloquear_aceleracion) {
                                zTimer.start();
                                isPressingZ = true;
                            }
                            break;

                        case KeyEvent.VK_X:
                            if (!bloquear_aceleracion) {
                                xTimer.start();
                                isPressingX = true;
                            }
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (!bloquear_jugabilidad) {
                    switch (key) {
                        case KeyEvent.VK_LEFT:
                            leftTimer.stop();
                            break;

                        case KeyEvent.VK_RIGHT:
                            rightTimer.stop();
                            break;

                        case KeyEvent.VK_Z:
                            isPressingZ = false;
                            zTimer.stop();
                            desacelerar.start();
                            break;

                        case KeyEvent.VK_X:
                            isPressingX = false;
                            xTimer.stop();
                            desacelerar.start();
                            break;
                    }
                }
            }
        });

        
        // Inicialización de los Timers
        desacelerar = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	VehiculoJugador aux = (VehiculoJugador) mi_juego.get_vehiculo_jugador();
            	if(isPressingX)
            		desacelerar.stop();
            	else if(isPressingZ){
            		if(aux.get_velocidad() > 198)
            			mi_juego.desacelerar();
            		if(aux.get_velocidad() == 198)
                        desacelerar.stop();
            	}
            	else {
            		mi_juego.desacelerar();
                	if(aux.get_velocidad() == 0) {
                        desacelerar.stop();
                	}
            	}
            }
        });
        
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
            	if(!isPressingX) {
            		VehiculoJugador aux = (VehiculoJugador) mi_juego.get_vehiculo_jugador();
                	if(aux.get_velocidad() <= 198)
                		mi_juego.mover_jugador(Juego.ZETA);
            	}
            }
        });

        xTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mi_juego.mover_jugador(Juego.EQUIS);
            }
        });
    }

	public void vaciar_ventana() {
	    panel_principal.removeAll();
	    inicializar();
	    revalidate();
	    repaint();
	}
}
