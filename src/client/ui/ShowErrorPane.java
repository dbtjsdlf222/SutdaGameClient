package client.ui;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ShowErrorPane {
	
	public ShowErrorPane(String message) {
		showError(message);
	}
	
	private void showError(String message) {
		JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
	    JDialog dialog = pane.createDialog("알림");
	    dialog.setAlwaysOnTop(true);
	    dialog.setVisible(true);
	    Toolkit tool = Toolkit.getDefaultToolkit();
		Image img = tool.getImage(RoomScreen.class.getResource("/img/titleIcon.jpg"));
		dialog.setIconImage(img);
	}
}