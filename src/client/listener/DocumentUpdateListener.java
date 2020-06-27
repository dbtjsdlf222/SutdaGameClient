package client.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DocumentUpdateListener implements DocumentListener {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override public void insertUpdate(DocumentEvent e)  { update(e); }
	@Override public void removeUpdate(DocumentEvent e)  { update(e); }
	@Override public void changedUpdate(DocumentEvent e) { update(e); }
	
	public void update(DocumentEvent e) {
		
		try {
			Document document = e.getDocument();
			action(document.getText(0, document.getLength()));
		} catch (BadLocationException e1) {
			logger.error(e1.getMessage(), e1);
		}
		
	} //action();
	
	public abstract void action(String text);
	
} //class DocumentUpdateListener;
