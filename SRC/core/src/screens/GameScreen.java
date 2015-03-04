package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.myFirstGame.game.Assets;
import com.myFirstGame.game.FirstGame;

import java.util.Iterator;

import entities.Creature;
import entities.Ghost;
import entities.Hero;
import entities.InteractiveEntity;
import entities.Projectile;
import entities.Wall;

/*
 * This is where the meat of the game goes.  this delegated all actions every frame
 */
public class GameScreen extends FirstGameScreen {
    public enum Status{
        PLAYING, VICTORY, DEFEAT, COMPLETE
    }

	SpriteBatch batch;
	Texture img;
	Hero myBox;
    Ghost enemy;
    Array<Ghost> ghostArray;
    ShapeRenderer shapeRenderer;
    OrthographicCamera cam;
    Array<Projectile> globalProjectileArray;
    Array<Wall> wallsArray;
    Status gameStatus;
    float time;
    float totalTime;

    //Text
    private BitmapFont font;
    OrthographicCamera fontCam;
    String t1 = "";
    String t2 = "";
    private int level;
    Array<String> stringArray;
    Iterator<String> stringIterator;
	
	public GameScreen(FirstGame g) {
		super(g);
        this.globalProjectileArray = new  Array<Projectile>();
		this.myBox = new Hero(new Vector2(-6,0), new Vector2(0.5f,0.75f), 1, 1, this.globalProjectileArray);
        this.enemy = new Ghost(new Vector2(5,0), new Vector2(0.5f,0.75f), 1, 2, this.globalProjectileArray);
        this.ghostArray = new Array<Ghost>();
        this.myBox.setRecord();
        this.enemy.setFirstPlay();
        this.ghostArray.add(this.enemy);
        Assets.load();
        this.initializeWalls(8f, 5f, 1f);
        this.gameStatus = Status.PLAYING;
        this.time = 0;
        this.totalTime = 0;
        this.font = new BitmapFont();
        this.fontCam = new OrthographicCamera(1600,900);
        this.level = 0;
	}

    public void initializeWalls(float hw, float hh, float thickness){
        Vector2 pos = new Vector2(-hw-0.1f,-hh);
        Vector2 size = new Vector2(thickness,hh*2);
        wallsArray = new Array<Wall>();

        //Make all the walls
        wallsArray.add(new Wall(pos,size,0));
        pos.set(hw-thickness+0.1f,-hh);
        wallsArray.add(new Wall(pos,size,0));
        pos.set(-hw,-hh+0.2f); size.set(hw*2f,thickness);
        wallsArray.add(new Wall(pos, size, 0));
        pos.set(-hw, hh-thickness);
        wallsArray.add(new Wall(pos, size, 0));
    }
	@Override
	public void show() {
        Assets.soundsArray.get(9).play(Assets.musicVol);
        float scale = 1f;
		batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        cam = new OrthographicCamera(16f*scale,9f*scale);
        cam.position.set(0,0,0);
        this.setTextPairs();
	}
	
	private void update(float delta){
		this.time += delta;
	}

    private Array<InteractiveEntity> getInteractiveEntities(){
        Array<InteractiveEntity> ieArray = new Array<InteractiveEntity>();
        ieArray.add(myBox);
        ieArray.addAll(ghostArray);
        ieArray.addAll(this.globalProjectileArray);
        ieArray.addAll(this.wallsArray);

        return ieArray;
    }

    private void handleAllContacts(){
        for(InteractiveEntity ie1 : this.getInteractiveEntities()){
            for(InteractiveEntity ie2 : this.getInteractiveEntities()){
                if(ie1 != ie2){
                    ie1.examineContact(ie2);
                    //ie1.examineFutureContact(ie2);
                }
            }
        }
    }

    private void cleanProjectiles(){
        for(Iterator<Projectile> itr = this.globalProjectileArray.iterator(); itr.hasNext();){
            Projectile p = itr.next();
            if(p.isBroken()) itr.remove();
        }
    }

    private void determineWinner(){
        boolean allDead = true;
        for(Ghost g: this.ghostArray ) {
            if (g.getStatus() != Creature.Status.DEAD) {
                allDead = false;
                break;
            }
        }
        if(allDead){
            //Check for final level
            myBox.setState(Creature.Status.CELEBRATING, Creature.Heading.EAST);
            this.gameStatus = Status.VICTORY;
            if(this.level == 9) this.gameStatus = Status.COMPLETE;
            Assets.soundsArray.get(9).pause();
            Assets.soundsArray.get(8).play(Assets.musicVol);

        }
        else if(myBox.getStatus() == Creature.Status.DEAD){
            for(Ghost g: this.ghostArray ) g.setState(Creature.Status.CELEBRATING, Creature.Heading.EAST);
            this.gameStatus = Status.DEFEAT;
            Assets.soundsArray.get(9).pause();
            Assets.soundsArray.get(7).play(Assets.musicVol);
        }
    }

    private void spawnNewRoom(){
        //respawn everyone
        this.level++;
        if(this.level == 4){
            ghostArray.add(
                    new Ghost(new Vector2(myBox.spawn), new Vector2(0.5f,0.75f), 1, 2, this.globalProjectileArray));
            for(Ghost g : this.ghostArray) g.setLevel(1);
            this.myBox.spawn.set(0,3f);
        }
        else if(this.level == 6){
            ghostArray.add(
                    new Ghost(new Vector2(myBox.spawn), new Vector2(0.5f,0.75f), 1, 2, this.globalProjectileArray));
            for(Ghost g : this.ghostArray) g.setLevel(1);
            this.myBox.spawn.set(0,-3f);
        }
        for(Ghost g: this.ghostArray) g.respawn();
        myBox.respawn();
        this.globalProjectileArray.clear(); // clear bullets
        for(Ghost g: this.ghostArray) g.setPlay(myBox.recorder);
        myBox.setRecord();
        this.gameStatus = Status.PLAYING;
        this.time = 0;
        Assets.soundsArray.get(9).resume();
        nextText();
    }
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 1. Control everything
        this.myBox.keyboardControl();
        boolean isFlipped = true;
        for(Ghost g : this.ghostArray) {
            g.play(isFlipped);
            isFlipped = !isFlipped;
        }

        // 1 1/2. Check contacts
        handleAllContacts();
        if(gameStatus == Status.PLAYING){
            this.totalTime += delta;
            determineWinner();
        }
        else if (gameStatus == Status.DEFEAT || gameStatus == Status.COMPLETE){
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                for(Sound s : Assets.soundsArray) s.stop();
                this.game.setScreen(new GameScreen(game));
            }
        }
        else{
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) this.spawnNewRoom();
        }

        // 2. Update and apply changes
        this.update(delta);
        for(InteractiveEntity ie : this.getInteractiveEntities()) ie.update(delta);

        // 3. Move Camera
        cam.position.set(0,0,0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);

        // 4. Render
        batch.setColor(1,1,1,1);
        batch.begin();
        //Draw the BG first baby
        Vector2 bgPos = new Vector2(this.wallsArray.peek().getPosition());
        batch.draw(Assets.animationArray.get(16).getKeyFrame(0),-8f,-4.5f,8f,4.5f,16f,9f,1f,1f,0);
        //Draw all the things
        for(InteractiveEntity ie : this.getInteractiveEntities()) ie.render(batch);
        batch.setColor(1,1,1,1);
        this.renderText();
        renderHealth();
        batch.end();

        //debug renders
        //for(InteractiveEntity ie : this.getInteractiveEntities()) ie.debugRender(shapeRenderer);
        //myBox.debugRender(shapeRenderer);

        // 5. Clean up
        this.cleanProjectiles(); //gets rid of broken projectiles

	}

    public void renderText(){
        if(this.time < 2.0f) {
            this.batch.setProjectionMatrix(fontCam.combined);
            this.font.setScale(6f);
            font.drawMultiLine(batch, this.t1, -150, -100);
            this.font.setScale(4f);
            font.drawMultiLine(batch, this.t2, -150, -200);
        }
        else if(this.gameStatus == Status.DEFEAT){
            this.batch.setProjectionMatrix(fontCam.combined);
            this.font.setScale(5f);
            font.drawMultiLine(batch, "Your Ghost Killed You", -350, 200);
            this.font.setScale(4f);
            font.drawMultiLine(batch, "   Died in Level " + (this.level+1) + "!", -250, 50);
            font.drawMultiLine(batch, "Time: " + ((int)this.totalTime) + " seconds!", -250, -100);
            this.font.setScale(3f);
            font.drawMultiLine(batch, "Press SPACE\nto Replay", -150, -200);
        }
        else if(this.gameStatus == Status.COMPLETE){
            this.batch.setProjectionMatrix(fontCam.combined);
            this.font.setScale(5f);
            font.drawMultiLine(batch, "   YOU WIN!!!!!", -350, 200);
            this.font.setScale(4f);
            font.drawMultiLine(batch, "SURVIVED ALL \n  10 LEVELS!", -250, 50);
            font.drawMultiLine(batch, "Time: " + ((int)this.totalTime) + " seconds!", -250, -100);
            this.font.setScale(3f);
            font.drawMultiLine(batch, "Press SPACE\nto Replay", -150, -200);
        }
        else if(this.gameStatus == Status.VICTORY){
            this.batch.setProjectionMatrix(fontCam.combined);
            this.font.setScale(5f);
            font.drawMultiLine(batch, "Good Work!", -150, -100);
            this.font.setScale(3f);
            font.drawMultiLine(batch, "      Next Level\n    Press SPACE", -150, -200);
        }
    }


    public void setTextPairs(){
        this.stringArray = new Array<String>();
        this.stringArray.add("Level 1");
        this.stringArray.add("Blank Slate");
        this.stringArray.add("Level 2");
        this.stringArray.add("Monkey see, \nMonkey do");
        this.stringArray.add("Level 3");
        this.stringArray.add("Now it gets \ninteresting");
        this.stringArray.add("Level 4");
        this.stringArray.add("Your Ghost is \n   Stronger!");
        this.stringArray.add("Level 5");
        this.stringArray.add("  BOGO");
        this.stringArray.add("Level 6");
        this.stringArray.add("Keep it up!");
        this.stringArray.add("Level 7");
        this.stringArray.add("Ruh Roh!");
        this.stringArray.add("Level 8");
        this.stringArray.add("How did you\n  manage this?");
        this.stringArray.add("Level 9");
        this.stringArray.add("Are you serious?");
        this.stringArray.add("Level 10");
        this.stringArray.add("Finale");
        this.stringIterator = this.stringArray.iterator();
        this.nextText();
    }

    public void nextText(){
        if(this.stringIterator.hasNext()){
            this.t1 = stringIterator.next();
            this.t2 = stringIterator.next();
        }
    }

    public void renderHealth(){
        batch.setProjectionMatrix(cam.combined);
        batch.setColor(1,1,1,1f);
        for(int i = 0; i < this.myBox.health; i++){
            batch.draw(Assets.animationArray.get(14).getKeyFrame(0),-7f+0.9f*(float) i,3.7f,0.8f,0.8f);
        }
        /*
        batch.setColor(1,1,1,0.5f);
        for(Ghost g : this.ghostArray){
            for(int i = 0; i < this.myBox.health; i++){
                batch.draw(Assets.animationArray.get(14).getKeyFrame(0),-7f+0.5f*(float) i,4f,0.5f,0.5f);
            }
        }
        */
    }

    @Override
    public void resize(int width, int height) {
        int unitToPixels = 16*5;
        this.cam.setToOrtho(false, width/unitToPixels, height/unitToPixels);
        super.resize(width, height);
    }
}
