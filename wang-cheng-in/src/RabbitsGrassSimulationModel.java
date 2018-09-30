import java.awt.Color;
import java.util.ArrayList;

import uchicago.src.sim.analysis.DataSource;
import uchicago.src.sim.analysis.OpenSequenceGraph;
import uchicago.src.sim.analysis.Sequence;
import uchicago.src.sim.engine.BasicAction;
import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.engine.SimModelImpl;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.ColorMap;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.gui.Value2DDisplay;
import uchicago.src.sim.util.SimUtilities;

/**
 * Class that implements the simulation model for the rabbits grass
 * simulation.  This is the first class which needs to be setup in
 * order to run Repast simulation. It manages the entire RePast
 * environment and the simulation.
 *
 * @author 
 */


public class RabbitsGrassSimulationModel extends SimModelImpl {
		
		// Default Values
		private static final int NUMAGENTS = 100;
		private static final int WORLDXSIZE = 20;
		private static final int WORLDYSIZE = 20;
		private static final int AGENT_START_LIFE = 90;
		private static final int REPRODLEVEL = 100;
		private static final int REPRODCOST = 10;
		private static final int TOTALGRASS = 20;
		private static final int GRASS_GROWTH_RATE = 3;

		private int numAgents = NUMAGENTS;
		private int worldXSize = WORLDXSIZE;
		private int worldYSize = WORLDYSIZE;
		private int agentStartLife = AGENT_START_LIFE;
		private int agentReproductionLevel = REPRODLEVEL;
		private int agentReproductionEnergyCost = REPRODCOST;
		private int totalGrass = TOTALGRASS;
		private int grassGrowthRate = GRASS_GROWTH_RATE;

        private Schedule schedule;

        private RabbitsGrassSimulationSpace rgSpace;

        private DisplaySurface displaySurf;

        private ArrayList agentList;

        private OpenSequenceGraph population;

        private OpenSequenceGraph grass_plot;

        class rabbitInSpace implements DataSource, Sequence {

            public Object execute() {
                return new Double(getSValue());
            }

            public double getSValue() {
                //return (double)rgSpace.getTotalRabbit();
                return agentList.size();
            }
        }

        class grassInSpace implements DataSource, Sequence {

            public Object execute() {
                return new Double(getSValue());
            }

            public double getSValue() {

            	int numAgents = agentList.size();
            	int totalArea = rgSpace.getCurrentGrassSpace().getSizeX() *
						rgSpace.getCurrentGrassSpace().getSizeY();
				int spaceAval = rgSpace.spaceAvailable; // = totalArea - totalRabbit - totalGrass
				// therefore totalGrass = totalAval - totalRabbit - spaceAval

				return totalArea - spaceAval - numAgents;
            }
        }

		public static void main(String[] args) {
			
			System.out.println("Rabbit skeleton");
			SimInit init = new SimInit();
			RabbitsGrassSimulationModel model = new RabbitsGrassSimulationModel();
			init.loadModel(model, "", false);
		}

		public void begin() {
			buildModel();
			buildSchedule();
			buildDisplay();
			
			displaySurf.display();
			population.display();
			grass_plot.display();
		}
		
		public void buildModel() {
			System.out.println("Running BuildModel");
			rgSpace = new RabbitsGrassSimulationSpace(worldXSize, worldYSize);
			rgSpace.spreadGrass(totalGrass);
			
			for(int i = 0; i < numAgents; i++)
			{
				addNewAgent();
			}
		    for(int i = 0; i < agentList.size(); i++){
		        RabbitsGrassSimulationAgent rga = (RabbitsGrassSimulationAgent)agentList.get(i);
		        rga.report();
		      }			
		}
		
		private boolean addNewAgent(){
			RabbitsGrassSimulationAgent a = new RabbitsGrassSimulationAgent(agentStartLife, agentReproductionLevel, agentReproductionEnergyCost);
			boolean retVal = rgSpace.addAgent(a);
			if(retVal) {
				agentList.add(a);
			}
			return retVal;
		}
		
		private void reapDeadAgents() {
		    for(int i = (agentList.size() - 1); i >= 0 ; i--){
		    	RabbitsGrassSimulationAgent rga = (RabbitsGrassSimulationAgent)agentList.get(i);
		    	if(rga.getCurrentLife() < 1) {
		    		rgSpace.removeAgentAt(rga.getX(), rga.getY());
		    		agentList.remove(i);
		    	}
		    }
		}
		
		private int countLivingAgents() {
			int livingAgents = 0;
			for(int i = 0; i < agentList.size(); i++){
				RabbitsGrassSimulationAgent rga = (RabbitsGrassSimulationAgent)agentList.get(i);
			      if(rga.getCurrentLife() > 0) livingAgents++;
			}
			System.out.println("Number of living agents is: " + livingAgents);

		    return livingAgents;
		}

		public void buildSchedule() {
			System.out.println("Running BuildSchedule");
			
			// Schedule : Get agents to age
		    class RabbitsGrassStimulationStep extends BasicAction {
		    	public void execute() {
		            SimUtilities.shuffle(agentList);
		            int agentSize = agentList.size();
		            for(int i =0; i < agentSize; i++){
		            	RabbitsGrassSimulationAgent rga = (RabbitsGrassSimulationAgent)agentList.get(i);
		            	rga.step();
		            	//rga.report();

		            	if(rga.getCurrentLife() >= agentReproductionLevel) {
		            		if (addNewAgent()) {
		            			rga.reproduce();
		            		}
		            	}
		            }
		            reapDeadAgents();
		            rgSpace.spreadGrass(grassGrowthRate);
		            displaySurf.updateDisplay();
		        }
		    }
		    schedule.scheduleActionBeginning(0, new RabbitsGrassStimulationStep());
		    
		    // Schedule : Counts agents with stepsToLive above 0
		    class RabbitsGrassStimulationCountLiving extends BasicAction {
		    	public void execute() {
		    		countLivingAgents();
		    	}
		    }
		    schedule.scheduleActionAtInterval(10, new RabbitsGrassStimulationCountLiving());

            class rgUpdatePopulationInSpace extends BasicAction {
                public void execute(){
                    population.step();
                }
            }

            schedule.scheduleActionAtInterval(10, new rgUpdatePopulationInSpace());

            class rgUpdateGrassInSpace extends BasicAction {
                public void execute(){
                    grass_plot.step();
                }
            }

            schedule.scheduleActionAtInterval(10, new rgUpdateGrassInSpace());

        }
		
		public void buildDisplay() {
			System.out.println("Running BuildDisplay");
			
			ColorMap map = new ColorMap();

		    for(int i = 1; i<16; i++){
		      map.mapColor(i, new Color((int)(i * 8 + 127), 0, 0));
		    }
		    map.mapColor(0, Color.white);

		    Value2DDisplay displayGrass = 
		        new Value2DDisplay(rgSpace.getCurrentGrassSpace(), map);

		    Object2DDisplay displayAgents = new Object2DDisplay(rgSpace.getCurrentAgentSpace());
		    displayAgents.setObjectList(agentList);

		    displaySurf.addDisplayableProbeable(displayGrass, "Grass");
			displaySurf.addDisplayableProbeable(displayAgents, "Agents");

			population.addSequence("Population", new rabbitInSpace());
			grass_plot.addSequence("Grass Count", new grassInSpace());
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
			agentList = new ArrayList();
		    schedule = new Schedule(1);
			
			if (displaySurf != null){
			      displaySurf.dispose();
			}
			displaySurf = null;

			if (population != null) {
			    population.dispose();
            }
            population = null;

            if (grass_plot != null) {
                grass_plot.dispose();
            }
            grass_plot = null;


            displaySurf = new DisplaySurface(this, "Rabbit Sim Model Window 1");
			population = new OpenSequenceGraph("Population", this);
            grass_plot = new OpenSequenceGraph("Grass Count", this);

			registerDisplaySurface("Rabbit Sim Model Window 1", displaySurf);
		    this.registerMediaProducer("Population ", population);
            this.registerMediaProducer("Grass Count", grass_plot);
        }
		
		public String[] getInitParam() {
			String[] initParams = {"NumAgents", "WorldXSize", "WorldYSize", "GrassGrowthRate", "AgentReproductionLevel" };
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
		
		public int getTotalGrass(){
			return totalGrass;
		}

		public void setTotalGrass(int i){
			totalGrass = i;
		}
		
		public int getGrassGrowthRate(){
			return grassGrowthRate;
		}

		public void setGrassGrowthRate(int i){
			grassGrowthRate = i;
		}
		
		public int getAgentReproductionLevel(){
			return agentReproductionLevel;
		}

		public void setAgentReproductionLevel(int level){
			agentReproductionLevel = level;
		}
		
		public int getAgentReproductionEnergyCost(){
			return agentReproductionEnergyCost;
		}

		public void setAgentReproductionEnergyCost(int cost){
			agentReproductionEnergyCost = cost;
		}
		
		public int getAgentStartLife(){
			return agentStartLife;
		}

		public void setAgentStartLife(int life){
			agentStartLife = life;
		}
}

