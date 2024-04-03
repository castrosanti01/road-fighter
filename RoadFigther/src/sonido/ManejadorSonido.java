package sonido;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ManejadorSonido {
	
	private Map<Integer, String> sonidos;
	private int velocidad_anterior;
	private String sonido_anterior;
	private Clip clip_motor, clip_explosion, clip_power_up, clip_stage_clear, clip_lose;
	
    public ManejadorSonido() {
    	velocidad_anterior = 0; 
    	sonido_anterior = "";
    	
    	sonidos = new HashMap<>();
    	sonidos.put(0, "marcha.wav");
        sonidos.put(1, "motor1.wav");
        sonidos.put(200, "motor2.wav");
        
    	try {
    		// Cargar sonidos
        	AudioInputStream stream;
        	
			stream = AudioSystem.getAudioInputStream(this.getClass().getResource("explosion.wav"));
			clip_explosion = AudioSystem.getClip();
	        clip_explosion.open(stream);
	        
	        stream = AudioSystem.getAudioInputStream(this.getClass().getResource("powerUp.wav"));
	        clip_power_up = AudioSystem.getClip();
	        clip_power_up.open(stream);
	        
	        stream = AudioSystem.getAudioInputStream(this.getClass().getResource("stageClear.wav"));
	        clip_stage_clear = AudioSystem.getClip();
	        clip_stage_clear.open(stream);
	        
	        stream = AudioSystem.getAudioInputStream(this.getClass().getResource("lose.wav"));
	        clip_lose = AudioSystem.getClip();
	        clip_lose.open(stream);
	        
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
    }
    
    //Sonidos Motor
    public void actualizar_velocidad(int vel) {
        String sonido_actual = obtener_sonido(vel);
        
        if(vel == 0) {
        	pausar_sonido();
        	reproducir_sonido("marcha.wav");
        }
        else {
	        if (vel < velocidad_anterior)
	        	sonido_actual = "desacelerar.wav";
	
	        if (!sonido_anterior.equals(sonido_actual)) {
	            pausar_sonido();
	            reproducir_sonido(sonido_actual);
	        }
        }
        
        	
        velocidad_anterior = vel;
    }

    private String obtener_sonido(int velocidad) {
        int velocidadCercana = 0;
        for(int v : sonidos.keySet()) {
            if (velocidad >= v) {
                velocidadCercana = v;
            } else {
                break;
            }
        }
        return sonidos.get(velocidadCercana);
    }

    private void reproducir_sonido(String sonido) {
        try {
        	sonido_anterior = sonido;
            clip_motor = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(sonido));
            clip_motor.open(inputStream);
            clip_motor.loop(Clip.LOOP_CONTINUOUSLY);
            clip_motor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void pausar_sonido() {
        if (clip_motor != null && clip_motor.isRunning()) {
            clip_motor.stop();
        }
    }
    
    //Sonidos
    public void reproducir_explosion() {
    	sonido_anterior = "";
    	clip_explosion.setFramePosition(0);
    	clip_explosion.start();
    }
    
    
    public void reproducir_power_up() {
    	clip_power_up.setFramePosition(0);
    	clip_power_up.start();
    }

	public void reproducir_stage_clear() {
		clip_motor.stop();
		clip_stage_clear.setFramePosition(0);
		clip_stage_clear.start();
	}

	public void reproducir_lose() {
		clip_motor.stop();
		clip_lose.setFramePosition(0);
		clip_lose.start();
	}

}

