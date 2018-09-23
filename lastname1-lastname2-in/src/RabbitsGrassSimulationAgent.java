import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;


/**
 * Class that implements the simulation agent for the rabbits grass simulation.

 * @author
 */

public class RabbitsGrassSimulationAgent implements Drawable {
	private int x;
	private int y;
	private int minLifespan;
	private int reproductionEnergyLevel;
	
	public void draw(SimGraphics arg0) {
		// TODO Auto-generated method stub
		
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public RabbitsGrassSimulationAgent(int minLifespan, int reproductionEnergyLevel) {
		this.x = -1;
		this.y = -1;
		this.minLifespan = minLifespan;
		this.reproductionEnergyLevel = reproductionEnergyLevel;
	}
	
	

}
