package milord

class GamePlayService 
{
	private Game game
	
    def serviceMethod() {

    }
    
    def startGame()
    {
    	if(game == null)
	    	game = new Game()
    
    	game.startGame();
     }
    
    def pauseGame()
    {
    	game.pauseGame();
    }
    
    def endGame()
    {
    }
}
