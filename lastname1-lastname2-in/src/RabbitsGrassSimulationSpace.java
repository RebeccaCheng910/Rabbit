import uchicago.src.sim.space.Object2DGrid;

/**
 * Class that implements the simulation space of the rabbits grass simulation.
 * @author 
 */

public class RabbitsGrassSimulationSpace {
	private Object2DGrid grassSpace;
	private Object2DGrid agentSpace;
	private int spaceAvailable = 0;
	
	
	public RabbitsGrassSimulationSpace(int xSize, int ySize){
		grassSpace = new Object2DGrid(xSize, ySize);
		agentSpace = new Object2DGrid(xSize, ySize);
		spaceAvailable = xSize * ySize;
	    for(int i = 0; i < xSize; i++){
	      for(int j = 0; j < ySize; j++){
	    	  grassSpace.putObjectAt(i,j,new Integer(0));
	      }
	    }
	}
	
	public void spreadGrass(int totalGrass){
		int i = 0;
		while ((i < totalGrass) && (spaceAvailable > 0)) {
	
	      // Choose coordinates
	      int x = (int)(Math.random()*(grassSpace.getSizeX()));
	      int y = (int)(Math.random()*(grassSpace.getSizeY()));
	      
	      // Get the value of the object at those coordinates
	      int currentValue = getGrassAt(x, y);
	      if (currentValue == 0) {
	    	  grassSpace.putObjectAt(x,y,new Integer(5));
	    	  spaceAvailable--;
		      ++i;
	      } else {
	    	  continue;
	      }
	    }
	  }
	
	public int getGrassAt(int x, int y){
	    int i;
	    if(grassSpace.getObjectAt(x,y)!= null){
	      i = ((Integer)grassSpace.getObjectAt(x,y)).intValue();
	    }
	    else{
	      i = 0;
	    }
	    return i;
	}
	
	public RabbitsGrassSimulationAgent getAgentAt(int x, int y){
		RabbitsGrassSimulationAgent retVal = null;
		if(agentSpace.getObjectAt(x, y) != null){
			retVal = (RabbitsGrassSimulationAgent)agentSpace.getObjectAt(x,y);
		}
		return retVal;
	}
	
	public Object2DGrid getCurrentGrassSpace(){
	    return grassSpace;
	}
	
	public Object2DGrid getCurrentAgentSpace(){
	    return agentSpace;
	}
	
	public boolean isCellOccupied(int x, int y){
		boolean retVal = false;
	    if(agentSpace.getObjectAt(x, y)!=null) retVal = true;
	    return retVal;
	}
	
	public boolean addAgent(RabbitsGrassSimulationAgent agent) {
		boolean retVal = false;
	    
	    while((retVal==false) && (spaceAvailable > 0)){ 
	    	int x = (int)(Math.random()*(agentSpace.getSizeX()));
	        int y = (int)(Math.random()*(agentSpace.getSizeY()));
	        if(isCellOccupied(x,y) == false){
	            agentSpace.putObjectAt(x,y,agent);
	            spaceAvailable--;
	            agent.setXY(x,y);
	            retVal = true;
	            agent.setRabbitsGrassSimulationSpace(this);
	        }
	    }
	    return retVal;
	}
	
	public void removeAgentAt(int x, int y){
		agentSpace.putObjectAt(x, y, null);
		spaceAvailable++;
	}

	
	public int takeGrassAt(int x, int y){
		int grass = getGrassAt(x, y);
		grassSpace.putObjectAt(x, y, new Integer(0));
		if (grass > 0) {
			spaceAvailable++;
		}
		return grass;
	}
	
	public boolean moveAgentAt(int x, int y, int newX, int newY){
		boolean retVal = false;
		if(!isCellOccupied(newX, newY)){
			RabbitsGrassSimulationAgent rga = (RabbitsGrassSimulationAgent)agentSpace.getObjectAt(x, y);
			removeAgentAt(x,y);
			rga.setXY(newX, newY);
			agentSpace.putObjectAt(newX, newY, rga);
			spaceAvailable--;
			retVal = true;
		}
		return retVal;
	}
}
