package client;

import java.awt.*;

import javax.swing.*;

public class Background extends JPanel {
	Image image;

	public Background() {
		image = new ImageIcon(Background.class.getResource("../img/background.jpg")).getImage();
	}
	

	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
		setOpaque(false);

	}


}
