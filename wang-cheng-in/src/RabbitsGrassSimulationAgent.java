import java.awt.Color;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;


/**
 * Class that implements the simulation agent for the rabbits grass simulation.

 * @author
 */

public class RabbitsGrassSimulationAgent implements Drawable {
	private int x;
	private int y;
	private int vX;
	private int vY;
	private int reproductionEnergyLevel;
	private int reproductionEnergyCost;
	private int currentLife;
	private static int IDNumber = 0;
	private int ID;
	private RabbitsGrassSimulationSpace rgSpace;
	
	public void setRabbitsGrassSimulationSpace(RabbitsGrassSimulationSpace rgs) {
		this.rgSpace = rgs;
	}
	
	public void draw(SimGraphics arg0) {
		arg0.drawFastRoundRect(Color.blue);		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public RabbitsGrassSimulationAgent(int currentLife, int reproductionEnergyLevel, int reproductionEnergyCost) {
		this.x = -1;
		this.y = -1;
		setVxVy();
		this.currentLife = currentLife;
		this.reproductionEnergyLevel = reproductionEnergyLevel;
		this.reproductionEnergyCost = reproductionEnergyCost;
		IDNumber++;
		ID = IDNumber;
	}
	
	public void setVxVy() {
		vX = 0;
		vY = 0;
		while(((vX == 0) && ( vY == 0)) || ((vX == 1) && ( vY == 1)) || ((vX == -1) && ( vY == -1))
				|| ((vX == 1) && ( vY == -1)) || ((vX == -1) && ( vY == 1))){
			vX = (int)Math.floor(Math.random() * 3) - 1;
			vY = (int)Math.floor(Math.random() * 3) - 1;
		}
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
	
	public void step() {
	    int newX = x + vX;
	    int newY = y + vY;

	    Object2DGrid grid = rgSpace.getCurrentAgentSpace();
	    newX = (newX + grid.getSizeX()) % grid.getSizeX();
	    newY = (newY + grid.getSizeY()) % grid.getSizeY();

	    if(tryMove(newX, newY)){
			currentLife += rgSpace.takeGrassAt(x,y);
			currentLife--;
	    }
	    setVxVy();
	}
	
	public void reproduce() {
		currentLife -= reproductionEnergyCost;
	}

	private boolean tryMove(int newX, int newY){
		return rgSpace.moveAgentAt(x, y, newX, newY);
	}
}
