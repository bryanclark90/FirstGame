package com.myFirstGame.game;

import screens.GameScreen;

import com.badlogic.gdx.Game;

public class FirstGame extends Game {

	@Override
	public void create() {
		this.setScreen(new GameScreen(this));
		
	}
	

}
