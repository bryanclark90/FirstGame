package entities;


public class Sasquatch extends InteractiveEntity{

	//the health of our player
	public int health;
	
	private static enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	//set the current direction to up
	Direction currentDirection = Direction.UP;
	
	public static enum State{
		ALIVE,
		DYING,
		DEAD,
		RESPAWNING
	}
	
	//player starts as alive
	State currentState = State.ALIVE;
	
	//if an interaction occurs with hostile decrement health by x amount
	public void gotHit(/*not sure what to fill this out with*/ int enemyPower){
		
		if(health < enemyPower){
			health = 0;
		}
		else{
			health -= enemyPower;
		}
	}
	
	//if an interaction occurs with the 
	public void foundHealth(/*not sure what to fill this out with*/ int healthBonus){
		
		//boost health by Bonus amount
		if(health < 100){
			health+=healthBonus;
		}
		//health can't be greater than 100
		if(health > 100){
			health = 100;
		}
	}

}
