import milord.*

class BootStrap {

    def init = 
    { servletContext ->

    	House house = new House(house_name: 'Targaryn')
    	house.save()

    	User user = new User(name: 'Jojo', login: 'Jojo', role: 'player', password: 'password', house: house)
    	user.save()

    	User user2 = new User(name: 'admin', login: 'admin',  role: 'admin', password: 'password', house: null)
    	user2.save()
    	
    	Hero h = new Hero(name: 'Thundarr')
	    h.save();
		
    	Inn inn1 = new Inn(name: 'Fleece and Firkin')
    	inn1.save()
    	inn1.addToHeroes(h);
    	
    	inn1.dailyGrind()
    }
    
    def destroy = {
    }
}
