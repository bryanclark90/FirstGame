package entities;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.myFirstGame.game.Assets;

import entities.Creature.Heading;

/**
 * Created by chris on 3/2/15.
 */
public class Projectile extends InteractiveEntity {
    protected float SHOT_VELOCITY = 0.1f;
    protected float MIN = 0.08f;
    protected float MAX = 0.2f;
    protected int damage = 1;

    public enum Status{
        FLYING, DAMAGING, SKIPPING, BREAKING, BROKEN
    }

    Status status;
    Heading heading;

    public Projectile(Vector2 pos, Vector2 size, Vector2 direction, Vector2 hostVelocity, int hostID, float z) {
        super(pos.add(-size.x/2f,0), size, z, hostID);
        status = Status.FLYING;
        this.zIndex = z;
        this.movement.set(direction).nor().scl(SHOT_VELOCITY);
        this.movement.add((new Vector2(hostVelocity).scl(0.75f)));
        this.movement.clamp(MIN, MAX);

        if(direction.y < 0) this.heading = Heading.NORTH;
        else if(direction.y > 0) this.heading = Heading.SOUTH;
        else if(direction.x > 0) this.heading = Heading.EAST;
        else if(direction.x < 0) this.heading = Heading.WEST;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void setState(Status s, Heading h){
        if(s != this.status) this.time = 0;
        this.status = s;
        this.heading = h;
    }
    public boolean isActive(){
        if(this.status == Status.FLYING) return true;
        return false;
    }

    @Override
    public void handleContact(InteractiveEntity ie) {
        if(this.id != ie.id && status != Status.BREAKING){
            setState(Status.BREAKING, this.heading);
            Assets.soundsArray.get(0).play(Assets.effectsVol,MathUtils.random(0.5f,1.5f),0);
            this.movement.scl(1/3f);
            this.movement.add(MathUtils.random(-0.01f,0.01f),MathUtils.random(-0.01f,0.01f));
        }
    }

    public boolean isBroken(){
        if(this.status == Status.BROKEN) return true;
        else return false;
    }
}
