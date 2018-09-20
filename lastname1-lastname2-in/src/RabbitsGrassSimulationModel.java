import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.engine.SimModelImpl;

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
		
		private int numAgents;
		private int worldXSize;
		private int worldYSize;
		
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
		}
		
		public void buildModel() {
			
		}
		
		public void buildSchedule() {
			
		}
		
		public void buildDisplay() {
			
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
			// TODO Auto-generated method stub
			
		}
		
		public String[] getInitParam() {
			String[] initParams = {"NumAgents", "WorldXSize", "WorldYSize"};
			return initParams;
		}
		
		public int getNumAgents(){
			return numAgents;
		}

		public void setNumAgents(int na){
			numAgents = na;
		}
		
		public int getWorldXSize(){
			return numAgents;
		}

		public void setWordXSize(int wxs){
			worldXSize = wxs;
		}
		
		public int getWorldYSize(){
			return worldYSize;
		}

		public void setWorldYSize(int wys){
			worldYSize = wys;
		}
}

