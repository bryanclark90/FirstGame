package entities;

import myPhysics.Fixture;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class InteractiveEntity extends Entity{
	
	Array<Fixture> fixturesArray;
	
	public InteractiveEntity() {
		super();
		this.fixturesArray = new Array<Fixture>();
	}
	
	public InteractiveEntity(Vector2 pos, Vector2 size, float z) {
		super(pos, z);
		this.fixturesArray = new Array<Fixture>();
		
	}
	
	public void addFixture(Vector2 pos, Vector2 size, float z){
		this.fixturesArray.add(
				new Fixture(pos,size, z, this.fixturesArray.size));
	}

	@Override
	public Array<Entity> getEntities() {
		Array<Entity>  entitiesArray = new Array<Entity>(); //new Array<Entity>();
		for(Fixture f: this.fixturesArray) entitiesArray.add(f);
		return entitiesArray;
	}
}
