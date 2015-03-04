package com.myFirstGame.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.myFirstGame.game.FirstGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new FirstGame(), config);
        int scale = 80;
        config.width = 16 * scale;
        config.height = 9 * scale;
	}
}
