package entities;

import myPhysics.Fixture;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	
	public void translate(Vector2 operator){
		for(Fixture f: this.fixturesArray) f.translate(operator);
	}

	@Override
	public Array<Entity> getEntities() {
		Array<Entity>  entitiesArray = new Array<Entity>(); //new Array<Entity>();
		for(Fixture f: this.fixturesArray) entitiesArray.add(f);
		return entitiesArray;
	}
	
	public void debugRender(ShapeRenderer debugRenderer){
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(0, 0, 1, 1); //BLUE BABY!
		for(Fixture f : this.fixturesArray){
			debugRenderer.rect(f.rect.x, f.rect.y, f.rect.width, f.rect.height);
		}
		debugRenderer.end();
	}
}
