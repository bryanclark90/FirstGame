package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Hero extends Creature {

    private final float MOVE_SPEED = 2.0f;

    public void keyboardControl(){
        Vector2 control = new Vector2(0,0);
        if(Gdx.input.isKeyPressed(Input.Keys.W)) control.add(0,1);
        if(Gdx.input.isKeyPressed(Input.Keys.S)) control.add(0,-1);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) control.add(-1,0);
        if(Gdx.input.isKeyPressed(Input.Keys.D)) control.add(1,0);
        control.nor().scl(MOVE_SPEED);
        this.translate(control);
    }
}
