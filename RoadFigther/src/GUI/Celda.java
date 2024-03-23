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
	protected int size_label_x, size_label_y;
	
	
	public Celda(Ventana v, EntidadLogica e) {
		super();
		mi_ventana = v;
		entidad_logica = e;
		size_label_x =  e.get_size_label_x(); 
		size_label_y = e.get_size_label_y();
		setBounds(e.get_pos_x()-30, e.get_pos_y(), size_label_x, size_label_y);
		cambiar_imagen(e.get_imagen_representativa(0));	 
	}
	
	public EntidadLogica get_entidad_logica() {
		return entidad_logica;
	}
	
	public int get_size_label_x() {
		return size_label_x;
	}
	
	public int get_size_label_y() {
		return size_label_y;
	}
	
	@Override
	public void notificarse_cambio_posicion(){
		//System.out.println(entidad_logica.get_pos_x()+" "+ entidad_logica.get_pos_y());
		setBounds(entidad_logica.get_pos_x()-30,entidad_logica.get_pos_y(), size_label_x, size_label_y);
	}
	
	@Override
	public void notificarse_cambio_posicion_animado(){
		mi_ventana.animar_movimiento(this);
	}

	@Override
	public void notificarse_detonar() {
		mi_ventana.animar_detonacion(this);
	}
	
	@Override
	public void notificarse_revivir() {
		mi_ventana.animar_aparicion(this);
	}
	
	@Override
	public void notificarse_descarrilar(int angulo) {
		mi_ventana.animar_descarrilar(this, angulo);
		//rotar(angulo);
	}
	
	@Override
	public void rotar(int angulo) {
	    // Create an AffineTransform object for rotation
	    AffineTransform transform = new AffineTransform();
	    transform.rotate(Math.toRadians(angulo), size_label_x / 2.0, size_label_y / 2.0);

	    // Get the current image icon
	    ImageIcon currentIcon = (ImageIcon) getIcon();
	    Image currentImage = currentIcon.getImage();

	    // Apply the rotation to the image
	    BufferedImage rotatedImage = new BufferedImage(size_label_x, size_label_y, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotatedImage.createGraphics();
	    g2d.drawImage(currentImage, transform, null);
	    g2d.dispose();

	    // Set the rotated image as the new icon
	    setIcon(new ImageIcon(rotatedImage));
	}

	
	public void cambiar_imagen(String i) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(i));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label_x, size_label_y, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		setIcon(iconoEscalado);
	}

}
