package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myFirstGame.game.FirstGame;

/*
 * This is where the meat of the game goes.  this delegated all actions every frame
 */
public class GameScreen extends FirstGameScreen {
	SpriteBatch batch;
	Texture img;
	
	public GameScreen(FirstGame g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		img = new Texture(Gdx.files.local("badlogic.jpg"));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	

}
