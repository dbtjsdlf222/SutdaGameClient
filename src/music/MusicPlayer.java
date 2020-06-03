package music;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
import main.Main;

public class MusicPlayer extends Thread {
	private Player player;
	private File file;
	boolean loop = true;
	public MusicPlayer() {
		try {
			File file = new File(Main.class.getResource("../Music/BigSleep.mp3").getFile());
			this.player = new Player(new BufferedInputStream(new FileInputStream(file)));
		} catch (Exception e) {
			e.printStackTrace();
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
