package entities;

import myPhysics.Fixture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class InteractiveEntity extends Entity{
	
	Array<Fixture> fixturesArray;
    Vector2 movement;
    public int id;
	
	public InteractiveEntity(Vector2 pos, Vector2 size, float z, int id) {
		super(pos, z);
		this.fixturesArray = new Array<Fixture>();
        this.addFixture(pos,size,z);
        this.movement = new Vector2(0,0);
        this.id = id;
	}

    public Vector2 getPosition(){
        Vector2 center = new Vector2(0,0);

        for(int i = 0; i < this.fixturesArray.size; i++){
            Fixture f = this.fixturesArray.get(i);
            f.rect.getPosition(center);
        }
        return center;
    }

    public void setPosition(Vector2 pos){

        for(int i = 0; i < this.fixturesArray.size; i++){
            Fixture f = this.fixturesArray.get(i);
            f.rect.setPosition(pos);
        }
    }

    public Vector2 getCenter(){
        //TODO: this function is a garbage placeholder
        Vector2 center = new Vector2(0,0);

        for(int i = 0; i < this.fixturesArray.size; i++){
            Fixture f = this.fixturesArray.get(i);
            f.rect.getCenter(center);
        }
        return center;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.translate(movement);
    }

	public void addFixture(Vector2 pos, Vector2 size, float z){
		this.fixturesArray.add(
				new Fixture(pos,size, z, this.fixturesArray.size));
	}
	
	public void translate(Vector2 operator){
		for(Fixture f: this.fixturesArray) f.translate(operator);
	}

    public Array<Fixture> getFutureFixtures() {
        return this.getFutureFixtures(this.movement);
    }

    public Array<Fixture> getFutureFixtures(Vector2 translation){
        if(movement.isZero()) return this.fixturesArray;
        Array<Fixture> fa = new Array<Fixture>(this.fixturesArray);
        for(Fixture f: fa) f.translate(translation);
        return fa;
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

    public boolean isContacting(InteractiveEntity ie){
        Iterator<Fixture> itr = ie.fixturesArray.iterator();
        while(itr.hasNext()) {
            Fixture otherFixture = itr.next();
            for (Fixture myFixture : this.fixturesArray) {
                if(otherFixture.rect.overlaps(myFixture.rect)){
                    return true;
                }
            }
        }
        return false;
    }

    public void examineContact(InteractiveEntity ie){
        Iterator<Fixture> itr = (new Array<Fixture>(ie.fixturesArray)).iterator();
        while(itr.hasNext()) {
            Fixture otherFixture = itr.next();
            int i = 0;
            while(i < this.fixturesArray.size) {
                if(otherFixture.rect.overlaps(this.fixturesArray.get(i).rect)){
                    this.handleContact(ie);
                }
                i++;
            }
        }
    }
/*
    public void examineFutureContact(InteractiveEntity ie){
        Iterator<Fixture> itr = ie.getFutureFixtures().iterator();
        while(itr.hasNext()) {
            Fixture otherFixture = itr.next();
            for (Fixture myFixture : this.getFutureFixtures()) {
                if(otherFixture.rect.overlaps(myFixture.rect)){
                    this.handleFutureContact(ie);
                }
            }
        }
    }
*/

    public void handleContact(InteractiveEntity ie){

    }

    public void handleFutureContact(InteractiveEntity ie){

    }
}
