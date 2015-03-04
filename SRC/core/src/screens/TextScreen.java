package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.myFirstGame.game.Assets;
import com.myFirstGame.game.FirstGame;

import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by chris on 3/3/15.
 */
public class TextScreen extends FirstGameScreen {
    private Array<String> textArray;
    SpriteBatch batch;
    private BitmapFont font;
    OrthographicCamera cam;
    Iterator<String> stringIterator;
    String t1 = "";
    String t2 = "";

    public TextScreen(FirstGame g, boolean needsToLoad) {
        super(g);
        if(needsToLoad) Assets.load();
        this.font = new BitmapFont();
        batch = new SpriteBatch();
        cam = new OrthographicCamera(1600,900);
        this.setTextPairs();
    }

    public void setTextPairs(){
        this.textArray = new Array<String>();
        this.textArray.add("A Game by\nStudio Rufio");
        this.textArray.add(
                "Programming & Design: \n           Chris Hyde   \n   @drumbumLOLcatz\n\n      Press SPACE");
        this.textArray.add("A Game by\nStudio Rufio");
        this.textArray.add(
                "      Art    &   Sound:  \n        Ian Skavdahl  \n       @ianskavdahl  \n\n      Press SPACE");
        this.textArray.add("My Own\nWorst Enemy");
        this.textArray.add("               Press SPACE");
        this.textArray.add("Enemies learn\nhow you play");
        this.textArray.add("They use your previous \n  actions against you!\n\n    Press SPACE");
        this.textArray.add("MOVE: \n    WASD\nSHOOT: \n    Arrow Keys");
        this.textArray.add("");
        this.stringIterator = this.textArray.iterator();
        this.nextText();
    }

    @Override
    public void show() {
        cam.update();
    }

    public void nextText(){
        if(this.stringIterator.hasNext()){
            this.t1 = this.stringIterator.next();
            this.t2 = this.stringIterator.next();
        }
        else{
            this.game.setScreen(new GameScreen(this.game));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) this.nextText();
        this.batch.setProjectionMatrix(cam.combined);
        batch.begin();
        this.font.setScale(10f);
        font.drawMultiLine(batch, this.t1, -400, 300f);
        this.font.setScale(3f);
        font.drawMultiLine(batch, this.t2, -275f, -100);
        batch.end();
    }
}
