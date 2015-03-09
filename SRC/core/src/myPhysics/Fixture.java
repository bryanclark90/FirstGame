package myPhysics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entities.Entity;

public class Fixture extends Entity{
	public Rectangle rect;
	public int id;
	
	public Fixture(Vector2 pos, Vector2 size, float z, int idNum) {
		super(pos, z);
		this.id = idNum;
		this.rect = new Rectangle(pos.x,pos.y, size.x, size.y);// TODO Auto-generated constructor stub
	}
	
	public void setPosition(Vector2 pos){
		this.rect.setPosition(pos);
		this.position.set(pos);
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
