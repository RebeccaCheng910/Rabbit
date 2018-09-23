import uchicago.src.sim.space.Object2DGrid;

/**
 * Class that implements the simulation space of the rabbits grass simulation.
 * @author 
 */

public class RabbitsGrassSimulationSpace {
	private Object2DGrid rabbitSpace;
	
	public RabbitsGrassSimulationSpace(int xSize, int ySize){
		rabbitSpace = new Object2DGrid(xSize, ySize);
	    for(int i = 0; i < xSize; i++){
	      for(int j = 0; j < ySize; j++){
	    	  rabbitSpace.putObjectAt(i,j,new Integer(0));
	      }
	    }
	}
	
	public void spreadRabbits(int numRabbits){
	    // Randomly place money in moneySpace
	    for(int i = 0; i < numRabbits; i++){

	      // Choose coordinates
	      int x = (int)(Math.random()*(rabbitSpace.getSizeX()));
	      int y = (int)(Math.random()*(rabbitSpace.getSizeY()));
	      
	      // Get the value of the object at those coordinates
	      int currentValue = getRabbitAt(x, y);
	      // Replace the Integer object with another one with the new value
	      rabbitSpace.putObjectAt(x,y,new Integer(currentValue + 1));
	    }
	  }
	
	public int getRabbitAt(int x, int y){
	    int i;
	    if(rabbitSpace.getObjectAt(x,y)!= null){
	      i = ((Integer)rabbitSpace.getObjectAt(x,y)).intValue();
	    }
	    else{
	      i = 0;
	    }
	    return i;
	}
	
	public Object2DGrid getCurrentRabbitSpace(){
	    return rabbitSpace;
	  }
	
}
