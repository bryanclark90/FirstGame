package entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by chris on 3/2/15.
 */
public class Wall extends InteractiveEntity {

    public Wall(Vector2 pos, Vector2 size, int z) {
        super(pos,size,z,100);

    }
}
