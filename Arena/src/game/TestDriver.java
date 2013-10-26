package game;

import com.google.gson.Gson;

/**
 * 
 * 
 * @author Jacob Charles
 */
public class TestDriver {

	/**
	 * Local game testing
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		//initialize
		Controller c = new Controller();
		View v = new View();
		ServerGameState gs = new ServerGameState();
		StopWatch t = new StopWatch(20);
		v.attachController(c);
		gs.initTestLevel();
		gs.addPlayer(Warehouse.SLIME); //single player

		//size examination
		System.out.println("Start state:");
		System.out.println(new Gson().toJson(gs));
		
		//main loop
		while (v.isVisible() && c.getStart() < 1) {
			t.loopStart();
			c.update();
			for (int i = 0; i < gs.getNumberOfPlayers(); i++){
				gs.readControls(gs.getPlayer(i), c); //using a hackish version to not create a Participant
			}
			gs.update();
			v.reDraw(gs.convert());
			t.loopRest();
		}
		System.out.println("End state:");
		System.out.println(new Gson().toJson(gs));
		System.exit(0);
	}
}
