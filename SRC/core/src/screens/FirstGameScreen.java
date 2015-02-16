package screens;

import com.badlogic.gdx.Screen;
import com.myFirstGame.game.FirstGame;

// Created by Chris Hyde

/* 
 * this is a class to generalize all actions that pertain to every screen
 */
public abstract class FirstGameScreen implements Screen{

	FirstGame game;
	
	public FirstGameScreen(FirstGame g){
		this.game = g;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
