package entities;

import com.badlogic.gdx.math.Vector2;

public class Entity implements Comparable<Entity>{
	public float time;
	public Vector2 postion;
	//TODO: add geometry to this or "InteractiveEntity"
	protected float zIndex;
	
	public Entity(){
		this(new Vector2(0,0), 0);
	}
	
	public Entity(Vector2 pos){
		this(new Vector2(0,0), 0);
	}
	
	public Entity(Vector2 pos, float z){
		this.postion = new Vector2(pos);
		this.zIndex = z;
	}
	
	public float getZIndex(){
		return this.zIndex;
	}
	
	public void update(float delta){
		this.time += delta;
	}

	@Override
	public int compareTo(Entity otherEntity) {
		float myZ = this.getZIndex();
		float otherZ = otherEntity.getZIndex();
		if(otherZ > myZ) return -1;
		else if ( otherZ < myZ) return 1;
		return 0;
	}
	
	
	
}
