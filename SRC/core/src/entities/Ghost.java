package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.myFirstGame.game.Assets;

/**
 * Created by chris on 3/3/15.
 */
public class Ghost extends Hero {
    public Ghost(Vector2 pos, Vector2 size, float z, int ID, Array<Projectile> globalProjectileArray) {
        super(pos, size, z, ID, globalProjectileArray);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1,1,1,0.7f);
        super.render(batch);
    }

    @Override
    public void shoot(Vector2 direction) {
        this.shooting = true;
        this.shotTime = 0f; //((float) getLevel())/3f;
        Assets.soundsArray.get(1).play(Assets.effectsVol,0.5f,0);
        for(int i = 0; i < getLevel(); i++){
            Vector2 dir = new Vector2(direction);
            float degrees = ((float) i) / ((float) getLevel()) * 360f;
            dir.rotate(degrees);
            this.globalProjectileArray.add(
                    new Burger(this.getCenter(),
                            dir.nor(), this.movement, this.id, 3));
        }
    }
}
