package animadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import GUI.Celda;

public class AnimadorDescarrilar extends Thread implements Animador {

    protected CentralAnimaciones manager;
    protected Celda celda_animada;
    protected int delay;
    protected int angulo;
	private Timer timer;
	private int i;

    public AnimadorDescarrilar(CentralAnimaciones manager, Celda celda, int delay, int angulo) {
        this.manager = manager;
        this.celda_animada = celda;
        this.delay = delay;
        this.angulo = angulo;
    }

    public Celda get_celda_asociada() {
        return celda_animada;
    }

    public void comenzar_animacion() {
        this.start();
    }

    public void run() {
    	i = 0;
    	final AnimadorDescarrilar esteAnimador = this;
    	timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	celda_animada.rotar(angulo);
            	if(i == 30) {
            		timer.stop();
            		manager.notificarse_finalizacion_animador(esteAnimador);
            	}
            	i++;
            }
        });
    	
    	timer.start();
    	
    }
    
}
