package main;

public class Remote extends MainScreen {
	public void runMainGame(int key) {
		switch (key) {
		case 1:
			break;
		}
	}

	public void runMainGame(String key) {
		String str;
		switch ((str = key).hashCode()) {
		case 49:
			if (str.equals("1")) {
				break;
			}
		}
	}
}
