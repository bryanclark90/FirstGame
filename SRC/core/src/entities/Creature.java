package entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.myFirstGame.game.Assets;

public class Creature extends InteractiveEntity{

    public enum Heading{
        NORTH, SOUTH, EAST, WEST
    }

    public enum Status{
        STANDING, WALKING, HURTING, DYING, DEAD, CELEBRATING, GLITCHING
    }

    private int level;
    private Heading heading;
    private Status status;
    protected float shotTime;
    protected boolean shooting;
    public int health;
    public Vector2 spawn;
    boolean moved;

    protected Array<Projectile> globalProjectileArray;

    public Heading getHeading() {
        return heading;
    }

    public Status getStatus() {
        return status;
    }

    public Creature(Vector2 pos, Vector2 size, float z, int ID, Array<Projectile> globalProjectileArray) {
        super(pos, size, z, ID);
        this.spawn = new Vector2(pos);
        this.globalProjectileArray = globalProjectileArray;
        this.time += MathUtils.random(0f,0.5f);
        this.level = -1;
        init();

    }

    public void init(){
        this.status = Status.STANDING;
        this.heading = Heading.SOUTH;
        shotTime = 999f;
        this.time = 0f;
        health = 3;
        this.level++;
        this.moved = false;
    }

    public int getLevel(){
        return this.level;
    }

    public void respawn() {
        this.setPosition(spawn);
        this.init();
    }

    public void setState(Status s, Heading h){
        if(s != this.status && s != status.DEAD) {
            this.time = 0;
        }
        this.status = s;
        this.heading = h;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void handleContact(InteractiveEntity ie) {
        //walls block you
        if(ie instanceof Wall){
            //get vector from center of the room
            Vector2 additional = new Vector2(ie.getCenter());
            //counter whatever movement is into the wall
            additional.scl(-1).nor();
            additional.set(this.movement.len()*additional.x,
                    this.movement.len()*additional.y);
            //add movement to inition movement set
            this.movement.add(additional);
        }
        else if(ie instanceof Projectile){
            Projectile p = (Projectile) ie;
            if(p.isActive() && p.id != this.id
                    && (this.getStatus() == Status.STANDING || this.getStatus() == Status.WALKING)){
                this.setState(Status.HURTING, p.heading);
                this.shooting = false;
                this.takeDamage(p.damage);
            }
        }
    }

    public void takeDamage(int dmg){
        Assets.soundsArray.get(3+MathUtils.random(0,3)).play(Assets.effectsVol);
        this.health -= dmg;
        if(this.health <= 0){
            this.setState(Status.DYING, this.getHeading());
            //System.out.println(this.id + " Died :(");
        }
        else{
            //System.out.println(this.id + " Took Damage! only " + this.health + " health left!");
            //Assets.soundsArray.get(0).play(Assets.effectsVol);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(this.movement.isZero(0.0001f)) this.movement.set(0,0);
        this.shotTime += delta;
    }
}
