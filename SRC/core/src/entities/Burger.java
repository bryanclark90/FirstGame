package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.myFirstGame.game.Assets;

/**
 * Created by chris on 3/2/15.
 */
public class Burger extends Projectile {

    private static final float WIDTH = 0.4f;
    private static final float HEIGHT = 0.4f;

    public Burger(Vector2 pos, Vector2 direction, Vector2 hostVelocity, int hostID, float z) {
        super(pos, new Vector2(WIDTH, HEIGHT), direction, hostVelocity, hostID, z);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1,1,1,1);
        int burgerAniNum = 14;
        Sprite s = null;
        if(this.status == Status.FLYING) {
            s = new Sprite(Assets.animationArray.get(burgerAniNum).getKeyFrame(0f, false));
        }
        else if(this.status == Status.BREAKING){
            s = new Sprite(Assets.animationArray.get(burgerAniNum).getKeyFrame(this.time, false));
            if(Assets.animationArray.get(burgerAniNum).isAnimationFinished(this.time)) this.status = Status.BROKEN;
        }
        Vector2 pos = this.getCenter();
        pos.add(-WIDTH/2f,-HEIGHT/2f);
        batch.draw(s, pos.x, pos.y, WIDTH/2f, HEIGHT/2f,WIDTH, HEIGHT, 1f, 1f, 0);
    }
}
