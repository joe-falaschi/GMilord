package milord

class House 
{
	String house_name
	
	User user
	static belongsTo = User
	static hasMany = [heroes : Hero]
	
    static constraints =
    {
    	house_name( size: 1..50, blank: false, unique: true )
    }
    
    String toString()
    {
    	house_name
    }
    
    
}
