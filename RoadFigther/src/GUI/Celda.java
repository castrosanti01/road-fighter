package GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import logica.EntidadLogica;

@SuppressWarnings("serial")
public class Celda extends JLabel implements EntidadGrafica {
	
	protected Ventana mi_ventana;
	protected EntidadLogica entidad_logica;
	protected int size_label;
	
	
	public Celda(Ventana v, EntidadLogica e) {
		super();
		mi_ventana = v;
		entidad_logica = e;
		size_label =  e.get_size_label(); 
		setBounds(e.get_pos_x()-30, e.get_pos_y(), size_label, size_label);
		cambiar_imagen(e.get_imagen_representativa());	 
	}
	
	public EntidadLogica get_entidad_logica() {
		return entidad_logica;
	}
	
	public int get_size_label() {
		return size_label;
	}
	
	@Override
	public void notificarse_cambio_estado() {
		cambiar_imagen(entidad_logica.get_imagen_representativa());
	}
	
	@Override
	public void notificarse_cambio_posicion(){
		//System.out.println(entidad_logica.get_pos_x()+" "+ entidad_logica.get_pos_y());
		setBounds(entidad_logica.get_pos_x()-30,entidad_logica.get_pos_y(), size_label, size_label);
	}
	
	@Override
	public void notificarse_cambio_posicion_animado(){
		mi_ventana.animar_movimiento(this);
	}

	@Override
	public void notificarse_revivir() {
		mi_ventana.animar_aparicion(this);
	}
	
	@Override
	public void notificarse_descarrilar(int angulo) {
		mi_ventana.animar_descarrilar(this, angulo);
	}
	
	@Override
	public void rotar(int angulo) {
	    // Create an AffineTransform object for rotation
	    AffineTransform transform = new AffineTransform();
	    transform.rotate(Math.toRadians(angulo), size_label / 2.0, size_label / 2.0);

	    // Get the current image icon
	    ImageIcon currentIcon = (ImageIcon) getIcon();
	    Image currentImage = currentIcon.getImage();

	    // Apply the rotation to the image
	    BufferedImage rotatedImage = new BufferedImage(size_label, size_label, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotatedImage.createGraphics();
	    g2d.drawImage(currentImage, transform, null);
	    g2d.dispose();

	    // Set the rotated image as the new icon
	    setIcon(new ImageIcon(rotatedImage));
	}

	
	public void cambiar_imagen(String i) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(i));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		setIcon(iconoEscalado);
	}

}
