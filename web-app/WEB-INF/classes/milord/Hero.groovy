package milord

class Hero 
{
	String name
	
    static constraints = 
    {
	    name( size: 1..50, blank: false, unique: true )
    }
    
    String toString()
    {
    	name
    }
}
