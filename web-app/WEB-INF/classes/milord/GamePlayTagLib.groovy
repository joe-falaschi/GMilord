package milord

class GamePlayTagLib 
{
	def startGameControl = 
	{
	    if(session.user)
	    {
	    	if(!session.game)
		      out << """[${link(action:"startGame", controller:"user"){"Start Game"}}]"""
	    	else 
	          out << """[${link(action:"pauseGame", controller:"user"){"Stop Game"}}]"""      
	    }
	}
}
