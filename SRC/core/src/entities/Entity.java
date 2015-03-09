package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Entity implements Comparable<Entity> {
	public float time;
	public Vector2 position;
	//TODO: add geometry to this or "InteractiveEntity"
	protected float zIndex;
	
	public Entity(){
		this(new Vector2(0,0), 0);
	}
	
	public Entity(Vector2 pos){
		this(new Vector2(0,0), 0);
	}
	
	public Entity(Vector2 pos, float z){
		this.position = new Vector2(pos);
		this.zIndex = z;
	}
	
	public float getZIndex(){
		return this.zIndex;
	}
	
	public void update(float delta){
		this.time += delta;
	}
	
	public void render(SpriteBatch batch){
		
	}

	@Override
	public int compareTo(Entity otherEntity) {
		float myZ = this.getZIndex();
		float otherZ = otherEntity.getZIndex();
		if(otherZ > myZ) return -1;
		else if ( otherZ < myZ) return 1;
		return 0;
	}
	
	public Array<Entity> getEntities(){
		Array<Entity> entitiesArray = new Array<Entity>();
		entitiesArray.add(this);
		return entitiesArray;
	}
	
}
