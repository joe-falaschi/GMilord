package milord

class LoginTagLib 
{
	def loginControl = 
	{
	    if(session.user)
	    {
	        out << """[${link(action:"show", controller:"user", id: "${session.user.id}"){"${session.user.name}"}}]"""
	        out << """[${link(action:"logout", controller:"user"){"Logout"}}]"""
	    } 
	    else 
	    {
	      	out << """[${link(action:"login", controller:"user"){"Login"}}]"""      
	    }
	}
	 
	def ifAdmin =
	{attrs, body ->
		if(session.user?.role == "admin")
			out << body()
	}  
	
	def ifPlayer =
	{attrs, body ->
		if(session.user?.role == "player")
			out << body()
	}  
	
	def ifLoggedIn =
	{attrs, body ->
		if(session.user != null)
			out << body()
	}

	def ifNotLoggedIn =
	{attrs, body ->
		if(session.user == null)
			out << body()
	}
}
