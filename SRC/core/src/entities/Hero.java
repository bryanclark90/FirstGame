package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.myFirstGame.game.Assets;
import com.myFirstGame.game.MoveRecorder;

public class Hero extends Creature {

    public Hero(Vector2 pos, Vector2 size, float z, int ID, Array<Projectile> globalProjectileArray) {
        super(pos, size, z, ID, globalProjectileArray);
        this.shooting = false;
    }


    private final float MOVE_SPEED = 0.125f;
    private final float SHOT_SPEED = 0.5f;
    private final float WIDTH = 1f;
    private final float HEIGHT = 1f;
    private final float FLOATYNESS = 0.08f;
    public MoveRecorder recorder;

    public void keyboardControl(){
        this.record(Gdx.input.isKeyPressed(Keys.W), Gdx.input.isKeyPressed(Keys.S),
                Gdx.input.isKeyPressed(Keys.A), Gdx.input.isKeyPressed(Keys.D),
                Gdx.input.isKeyPressed(Keys.UP), Gdx.input.isKeyPressed(Keys.DOWN),
                Gdx.input.isKeyPressed(Keys.LEFT), Gdx.input.isKeyPressed(Keys.RIGHT));
    }

    public void setRecord(){
        recorder = new MoveRecorder();
    }

    public void setPlay(MoveRecorder mr){
        recorder = new MoveRecorder(mr);
        recorder.setGlitch();
    }

    public void setFirstPlay(){
        recorder = new MoveRecorder();
        recorder.record(false,false,false,false,false,false,false,false); //Sit there and do nothing lol
    }

    public void play(boolean flipX){
        if(this.getStatus() != Status.HURTING && this.getStatus() != Status.GLITCHING) {
            MoveRecorder.Move m = this.recorder.read();
            if(m.glitchFlag &&
                    this.getStatus() != Status.CELEBRATING &&
                    this.getStatus() != Status.DYING &&
                    this.getStatus() != Status.DEAD) {
                this.setState(Status.GLITCHING, Heading.EAST);
                //System.out.println("Glitch set");
                Assets.soundsArray.get(2).play(Assets.effectsVol);
            }
            if(flipX) this.control(m.w, m.s, m.d, m.a, m.up, m.down, m.right, m.left); //reverse a and d, left and right
            else this.control(m.w, m.s, m.a, m.d, m.up, m.down, m.left, m.right);
        }else{
            this.blankControl();
        }
    }

    public void blankControl(){
        control(false,false,false,false,false,false,false,false);
    }

    public void mindlesslyShoot(){
        control(false,false,false,false,false,true,false,false);
    }

    public void record(boolean w, boolean s, boolean a, boolean d,
                       boolean up, boolean down, boolean left, boolean right){
        if(w || s || a || d || up || down || left || right) this.moved = true;
        if(moved && this.getStatus() != Status.CELEBRATING && this.getStatus() != Status.DYING){
            recorder.record(w, s, a, d, up, down, left, right);
        }
        control(w, s, a, d, up, down, left, right);
    }

    public void control(boolean w, boolean s, boolean a, boolean d,
                        boolean up, boolean down, boolean left, boolean right){
        Vector2 control = new Vector2(0, 0);
        if(this.getStatus() != Status.HURTING &&
                this.getStatus() != Status.DYING &&
                this.getStatus() != Status.CELEBRATING &&
                this.getStatus() != Status.DEAD &&
                this.getStatus() != Status.GLITCHING) { //NOT HURTING
            //Control movement

            Heading h = this.getHeading();
            if (w) {
                control.add(0, 1);
                h = Heading.NORTH;
            }
            if (s) {
                control.add(0, -1);
                h = Heading.SOUTH;
            }
            if (a) {
                control.add(-1, 0);
                h = Heading.EAST;
            }
            if (d) {
                control.add(1, 0);
                h = Heading.WEST;
            }
            control.nor().scl(MOVE_SPEED);
            if (!control.isZero()) {
                //control.scl(FLOATYNESS / 1000f);
                //this.movement.scl(FLOATYNESS / 1000f);
                //this.movement(control);

            }
            movement.set(MathUtils.lerp(movement.x, control.x, FLOATYNESS),
                    MathUtils.lerp(movement.y, control.y, FLOATYNESS));
            //else this.movement.scl(1f-FLOATYNESS);
            // Control Gun
            //this.shooting;
            Vector2 shotDirection = new Vector2(0, 0);
            if (up) {
                shotDirection.add(0, 1);
                h = Heading.NORTH;
            } else if (down) {
                shotDirection.add(0, -1);
                h = Heading.SOUTH;
            } else if (left) {
                shotDirection.add(-1, 0);
                h = Heading.EAST;
            } else if (right) {
                shotDirection.add(1, 0);
                h = Heading.WEST;
            }

            //Try to Shoot
            if (this.shotTime > SHOT_SPEED && !shotDirection.isZero()) { //Shooting, baby!
                this.shoot(shotDirection);
            }

            // Determine Status
            Status st = null;
            if (control.isZero()) st = Status.STANDING;
            else st = Status.WALKING;
            this.setState(st, h);
        }
        else{
            movement.set(MathUtils.lerp(movement.x, control.x, FLOATYNESS),
                    MathUtils.lerp(movement.y, control.y, FLOATYNESS));
        }
    }

    public void shoot(Vector2 direction){
        this.shooting = true;
        this.shotTime = 0f;
        this.globalProjectileArray.add(
                new Burger(this.getCenter().add(0, 0),
                        direction.nor(), this.movement, this.id, 3));
        Assets.soundsArray.get(1).play(Assets.effectsVol);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion tr = null;
        int direction = 0;
        int state = 0;
        switch(this.getHeading()){
            case WEST:
            case EAST: direction = 0; break;
            case SOUTH: direction = 1; break;
            case NORTH: direction = 2; break;
        }
        boolean loop = true;
        switch(this.getStatus()){
            case STANDING: state = 0; break;
            case WALKING: state = 3; break;
            case HURTING: state = 6; break;
            case CELEBRATING: state = 12; direction = 0; break;
            case DYING: state = 13; direction = 0; loop = false; break;
            case DEAD: state = 13; direction = 0; loop = false; break;
            case GLITCHING: state = 15; break;
        }
        //mail man algorithm for finding the animation location
        if(this.getStatus() != Status.GLITCHING) {
            tr = Assets.animationArray.get(state + direction).getKeyFrame(this.time, loop);
            Sprite s = new Sprite(tr);

            if (this.getHeading() != Heading.WEST) s.flip(true, false);
            Vector2 pos = this.getPosition();
            batch.draw(s, pos.x - 0.25f, pos.y, WIDTH / 2f, HEIGHT / 2f, WIDTH, HEIGHT, 1f, 1f, 0);
            //Check if the hurting animation is done AND the dying is over
            if (getStatus() == Status.HURTING && Assets.animationArray.get(state + direction).isAnimationFinished(this.time)) {
                this.setState(Status.WALKING, this.getHeading());
            } else if (getStatus() == Status.DYING && Assets.animationArray.get(state + direction).isAnimationFinished(this.time)) {
                this.setState(Status.DEAD, this.getHeading());
            }else

            if (this.shooting) {
                s = new Sprite(Assets.animationArray.get(3 * 3 + direction).getKeyFrame(this.shotTime, false));
                if (this.getHeading() != Heading.WEST) s.flip(true, false);
                batch.draw(s, pos.x - 0.25f, pos.y, WIDTH / 2f, HEIGHT / 2f, WIDTH, HEIGHT, 1f, 1f, 0);
                //Check if we made it to the end
                if (Assets.animationArray.get(3 * 3 + direction).isAnimationFinished(shotTime))
                    this.shooting = false;
            }
        }
        else{
            //System.out.println("Glitching! yay!!");
            //GLITCH
            if(this.time < 1.8f){
                Sprite s = new Sprite(Assets.animationArray.get(state + direction).getKeyFrame(MathUtils.random(2.0f), loop));
                Vector2 pos = this.getPosition();
                batch.draw(s, pos.x - 0.25f, pos.y, WIDTH / 2f, HEIGHT / 2f, WIDTH, HEIGHT, 1f, 1f, 0);
                s = new Sprite(Assets.animationArray.get(state + direction).getKeyFrame(MathUtils.random(2.0f), loop));
                pos.set(this.spawn);
                batch.draw(s, pos.x - 0.25f, pos.y, WIDTH / 2f, HEIGHT / 2f, WIDTH, HEIGHT, 1f, 1f, 0);
            }
            else{
                this.setState(Status.WALKING,Heading.SOUTH);
                this.setPosition(this.spawn);
            }
        }
    }
}
