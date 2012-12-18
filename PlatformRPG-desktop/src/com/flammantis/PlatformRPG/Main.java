package com.flammantis.PlatformRPG;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "PlatformRPG";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 500;
		
		new LwjglApplication(new PlatformRPG(), cfg);
	}
}
