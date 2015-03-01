package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.myFirstGame.game.FirstGame;

import entities.InteractiveEntity;

/*
 * This is where the meat of the game goes.  this delegated all actions every frame
 */
public class GameScreen extends FirstGameScreen {
	SpriteBatch batch;
	Texture img;
	InteractiveEntity myBox;
	
	public GameScreen(FirstGame g) {
		super(g);
		this.myBox = new InteractiveEntity(new Vector2(0,0),new Vector2(5,5),1);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		img = new Texture(Gdx.files.local("badlogic.jpg"));
	}
	
	private void update(float delta){
		//foo controls for the box
		Vector2 movement = new Vector2();
		this.myBox.update(delta);
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
