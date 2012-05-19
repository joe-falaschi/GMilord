package milord

class Inn
{
	String name
	
	static hasMany = [heroes : Hero]
	
    static constraints = 
    {
	    name( size: 1..50, blank: false, unique: true )
    }
    
    void dailyGrind()
    {
    	println "Test one"
    	
   		addNewHero()

   		println "Test two"
    }
    
    private int hcount;
    
    void addNewHero()
    {
    	println "Adding new hero:"
    	Hero h = new Hero(name: 'Hero' + hcount++)
    	h.save()
    	println(h)
    	addToHeroes(h)
    }
    
    String toString()
    {
    	name
    }
}
