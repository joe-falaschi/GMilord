package milord

class GamePlayTagLib 
{
	def startGameControl = 
	{
	    if(session.user)
	    {
	    	if(!session.game)
		      out << """[${link(action:"startGame", controller:"user"){"Start Playing"}}]"""
	    	else 
	          out << """[${link(action:"pauseGame", controller:"user"){"Stop Playing"}}]"""      
	    }
	}
	
	def hireHeroControl = { attrs, body ->
		out << link(action: "hire", controller: "hero", params:[heroId:attrs.heroId]){"Hire This Hero"}
	}
	
	def emoticon = { attrs, body ->
		out << body() << (attrs.happy == 'true' ? " :-)" : " :-(")
	 }
}
