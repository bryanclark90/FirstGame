package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.myFirstGame.game.Assets;
import com.myFirstGame.game.FirstGame;

import entities.Hero;
import entities.InteractiveEntity;

/*
 * This is where the meat of the game goes.  this delegated all actions every frame
 */
public class GameScreen extends FirstGameScreen {
	SpriteBatch batch;
	Texture img;
	Hero myBox;
    ShapeRenderer shapeRenderer;
    OrthographicCamera cam;
	
	public GameScreen(FirstGame g) {
		super(g);
		this.myBox = new Hero();
        this.myBox.addFixture(new Vector2(0,0),new Vector2(5,5),1);
        Assets.loadTest();
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        cam = new OrthographicCamera(16f,9f);
        cam.position.set(0,0,0);
	}
	
	private void update(float delta){
		this.myBox.update(delta);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 1. Control everything
        this.myBox.keyboardControl();

        // 2. Update and apply changes
        this.myBox.update(delta);

        // 3. Move Camera
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        // 4. Render
        myBox.debugRender(shapeRenderer);

        // 5. Clean up or whatever

	}
	

}
