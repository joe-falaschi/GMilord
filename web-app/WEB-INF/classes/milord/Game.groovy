package milord

class Game implements Runnable
{
	private Thread thread
	private boolean run
	
    static constraints = {
    }

	void run()
	{
		int n
		
		// while(run) 
		for(i in 1..4)
		{			
		    println "runnnnning" + n++
		}
	}
	
	Game startGame()
	{
		run = true;
		thread = new Thread(this)
		thread.run()
		
		return this
	}	
	
	void pauseGame()
	{
		run = false;
	}
	
	boolean isRunning()
	{
		run
	}
}
