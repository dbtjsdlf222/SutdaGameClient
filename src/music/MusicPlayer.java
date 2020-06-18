package music;

import java.io.File;
import java.io.FileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javazoom.jl.player.Player;

public class MusicPlayer extends Thread {

	private static final Logger logger = LogManager.getLogger();
	
	private Player player;
	private File file;
	boolean loop = true;
	public MusicPlayer() {
		try {
//			File file = new File(MainScreen.class.getResource("../Music/BigSleep.mp3").getFile());
//			this.player = new Player(new BufferedInputStream(new FileInputStream(file)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void close() {
		this.player.close();
		interrupt();
	}

	public void run() {
		try {
			do {
				FileInputStream buff = new FileInputStream(file);
				player = new Player(buff);
				player.play();
			} while (loop);	
		} catch (Exception e) { }
}}
