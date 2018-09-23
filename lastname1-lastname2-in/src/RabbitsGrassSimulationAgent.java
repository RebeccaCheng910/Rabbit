import java.awt.Color;

import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;


/**
 * Class that implements the simulation agent for the rabbits grass simulation.

 * @author
 */

public class RabbitsGrassSimulationAgent implements Drawable {
	private int x;
	private int y;
	private int reproductionEnergyLevel;
	private int currentLife;
	private static int IDNumber = 0;
	private int ID;
	
	public void draw(SimGraphics arg0) {
		arg0.drawFastRoundRect(Color.blue);		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void step(){
		currentLife--;
	  }
	
	public RabbitsGrassSimulationAgent(int currentLife, int reproductionEnergyLevel) {
		this.x = -1;
		this.y = -1;
		this.currentLife = currentLife;
		this.reproductionEnergyLevel = reproductionEnergyLevel;
		IDNumber++;
		ID = IDNumber;
	}
	
	public void setXY(int newX, int newY){
	    x = newX;
	    y = newY;
	  }
	
	 public String getID(){
		    return "A-" + ID;
		  }
	 
		  public int getCurrentLife(){
			    return currentLife;
			  }

		  public int getReproductionEnergyLevel(){
		    return reproductionEnergyLevel;
		  }

		  public void report(){
			  int untilReprod = getReproductionEnergyLevel() - getCurrentLife();
		    System.out.println(getID() + 
		                       " at " + 
		                       x + ", " + y + 
		                       " has " + 
		                       getCurrentLife() + " life left" + 
		                       " and " + 
		                       untilReprod + " energy until reproduction.");
		  }

}
