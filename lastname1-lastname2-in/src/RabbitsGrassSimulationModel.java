import java.awt.Color;

import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.engine.SimModelImpl;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.ColorMap;
import uchicago.src.sim.gui.Value2DDisplay;

/**
 * Class that implements the simulation model for the rabbits grass
 * simulation.  This is the first class which needs to be setup in
 * order to run Repast simulation. It manages the entire RePast
 * environment and the simulation.
 *
 * @author 
 */


public class RabbitsGrassSimulationModel extends SimModelImpl {		
		private Schedule schedule;
		
		private RabbitsGrassSimulationSpace rgSpace;
		
		private DisplaySurface displaySurf;
		
		// Default Values
	    private static final int NUMAGENTS = 100;
	    private static final int WORLDXSIZE = 40;
	    private static final int WORLDYSIZE = 40;
	    private static final int TOTALRABBIT = 100;
		  
		private int numAgents = NUMAGENTS;
		private int worldXSize = WORLDXSIZE;
		private int worldYSize = WORLDYSIZE;
		private int numRabbits = TOTALRABBIT;
		
		public static void main(String[] args) {
			
			System.out.println("Rabbit skeleton");
			SimInit init = new SimInit();
			RabbitsGrassSimulationModel model = new RabbitsGrassSimulationModel();
			init.loadModel(model, "", false);
		}

		public void begin() {
			// TODO Auto-generated method stub
			buildModel();
			buildSchedule();
			buildDisplay();
			
			displaySurf.display();
		}
		
		public void buildModel() {
			System.out.println("Running BuildModel");
			rgSpace = new RabbitsGrassSimulationSpace(worldXSize, worldYSize);
			rgSpace.spreadRabbits(numRabbits);
		}
		
		public void buildSchedule() {
			System.out.println("Running BuildSchedule");
		}
		
		public void buildDisplay() {
			System.out.println("Running BuildDisplay");
			
			ColorMap map = new ColorMap();

		    for(int i = 1; i<16; i++){
		      map.mapColor(i, new Color((int)(i * 8 + 127), 0, 0));
		    }
		    map.mapColor(0, Color.white);

		    Value2DDisplay displayRabbit = 
		        new Value2DDisplay(rgSpace.getCurrentRabbitSpace(), map);

		    displaySurf.addDisplayable(displayRabbit, "Rabbit");
		}

		public String getName() {
			// TODO Auto-generated method stub
			return "Rabbit Model";
		}

		public Schedule getSchedule() {
			// TODO Auto-generated method stub
			return schedule;
		}

		public void setup() {
			System.out.println("Running setup");
			rgSpace = null;
			
			if (displaySurf != null){
			      displaySurf.dispose();
			}
			displaySurf = null;
			displaySurf = new DisplaySurface(this, "Carry Drop Model Window 1");
			
			registerDisplaySurface("Carry Drop Model Window 1", displaySurf);
		}
		
		public String[] getInitParam() {
			String[] initParams = {"NumAgents", "WorldXSize", "WorldYSize", "Rabbit"};
			return initParams;
		}
		
		public int getNumAgents(){
			return numAgents;
		}

		public void setNumAgents(int na){
			numAgents = na;
		}
		
		public int getWorldXSize(){
			return worldXSize;
		}
		
		public void setWorldXSize(int wxs) {
			worldXSize = wxs;
		}
		
		public int getWorldYSize(){
			return worldYSize;
		}

		public void setWorldYSize(int wys){
			worldYSize = wys;
		}
		
		public int getRabbit(){
			return numRabbits;
		}

		public void setRabbit(int i){
			numRabbits = i;
		}
}

