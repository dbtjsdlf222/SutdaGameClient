package client.ui;

import javax.swing.JDialog;
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
	}
}

