package animadores;

import GUI.Celda;

public class AnimadorDescarrilar extends Thread implements Animador {

    protected CentralAnimaciones manager;
    protected Celda celda_animada;
    protected int delay;
    protected int prioridad;
    protected int angulo;

    public AnimadorDescarrilar(CentralAnimaciones manager, Celda celda, int delay, int angulo) {
        this.manager = manager;
        this.celda_animada = celda;
        this.delay = delay;
        this.prioridad = PrioridadAnimaciones.PRIORIDAD_SIN_PRIORIDAD;
        this.angulo = angulo;
    }

    public Celda get_celda_asociada() {
        return celda_animada;
    }

    public int get_prioridad() {
        return prioridad;
    }

    public void comenzar_animacion() {
        this.start();
    }

    public void run() {
        int i = 0;
        while (i != 15) {
            try {
            	i++;
                celda_animada.rotar(angulo);
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        manager.notificarse_finalizacion_animador(this);
    }
    
}
