package client;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.ui.LoginFrame;

public class RunClient {
	
	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // OS 호환
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException
				| UnsupportedLookAndFeelException e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
		EventQueue.invokeLater(() -> new LoginFrame()); // 쓰레드 충돌 방지
	} // main();
} // class RunClient;