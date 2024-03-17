package GUI;

import java.awt.Image;

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
		setBounds(e.get_pos_x(), e.get_pos_y(), size_label_x, size_label_y);
		cambiar_imagen(e.get_imagen_representativa());	 
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
		mi_ventana.animar_movimiento(this);
	}

	@Override
	public void notificarse_detonar() {
		cambiar_imagen(entidad_logica.get_imagen_representativa());	
	}
	
	protected void cambiar_imagen(String i) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(i));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label_x, size_label_y, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		setIcon(iconoEscalado);
	}

	

}
