package animadores;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import GUI.Celda;
import GUI.Ventana;
import GUI.VentanaNotificable;

public class CentralAnimaciones {
    
    protected VentanaNotificable ventana; 
    protected Map<Celda, List<Animador>> mapeo_celda_animadores;
    
    public CentralAnimaciones(Ventana ventana) {
        this.ventana = ventana;
        mapeo_celda_animadores = new HashMap<>();
    }
    
    public void animar_movimiento(Celda celda) {
        Animador animador = new AnimadorMovimiento(this, 1, 2, celda);
        agregar_animador_y_lanzar_pendientes(animador);
    }
    
    public void animar_aparicion(Celda celda) {
        ventana.notificarse_animacion_en_progreso();
        Animador animador = new AnimadorAparicion(this, celda, 1500);
        agregar_animador_y_lanzar_pendientes(animador);
    }
    
    public void animar_descarrilar(Celda celda, int angulo) {
        ventana.bloquear_teclado();
        Animador animador = new AnimadorDescarrilar(this, celda, 50, angulo);
        agregar_animador_y_lanzar_pendientes(animador);
    }
    
    protected void agregar_animador_y_lanzar_pendientes(Animador animador) {
        Celda celda = animador.get_celda_asociada();
        List<Animador> animadores_celda = mapeo_celda_animadores.getOrDefault(celda, new LinkedList<>());
        animadores_celda.add(animador);
        mapeo_celda_animadores.put(celda, animadores_celda);
        lanzar_animador_si_necesario(celda);
    }
    
    protected void lanzar_animador_si_necesario(Celda celda) {
        List<Animador> animadores_celda = mapeo_celda_animadores.get(celda);
        if (animadores_celda != null && !animadores_celda.isEmpty()) {
            Animador animador = animadores_celda.get(0);
            animadores_celda.remove(0);
            animador.comenzar_animacion();
        }
    }
    
    public void notificarse_finalizacion_animador(Animador animador) {
        ventana.notificarse_animacion_finalizada();
        Celda celda = animador.get_celda_asociada();
        lanzar_animador_si_necesario(celda);
    }
}
