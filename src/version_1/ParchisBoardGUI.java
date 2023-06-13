package version_1;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ParchisBoardGUI extends JPanel{
	private Image imagen;
	
	public ParchisBoardGUI (String nombreImagen) {
		if (nombreImagen !=null) {
			imagen = new ImageIcon(getClass().getResource(nombreImagen)).getImage();
		}
	}
	
	public void setImagen (String nombreImagen) {
		if (nombreImagen != null) {
			imagen = new ImageIcon(getClass().getResource(nombreImagen)).getImage();
		}else {
			imagen=null;
		}
		repaint();
	}
	
	public void setImagen (Image nuevaImagen) {
		imagen = nuevaImagen;
		repaint();
	}
	
	public void paint (Graphics g) {
		g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}
}
