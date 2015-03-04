package myPhysics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entities.Entity;

public class Fixture extends Entity{
	public Rectangle rect;
	public int id;
	
	public Fixture(Vector2 center, Vector2 size, float z, int idNum) {
		super(center, z);
		this.id = idNum;
		this.rect = new Rectangle();
        this.rect.setSize(size.x,size.y);
        this.rect.setPosition(center);
	}
	
	public void setSize(float width, float height){
		this.rect.setSize(width, height);
	}

    public void translate(Vector2 operator){
        Vector2 pos = new Vector2();
        this.rect.getPosition(pos);
        pos.add(operator);
        this.rect.setPosition(pos);
    }
	
}
