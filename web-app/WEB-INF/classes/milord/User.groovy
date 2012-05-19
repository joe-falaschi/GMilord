package milord

class User 
{
  String login
  String password
  String name
  String role = "player"
  
  House house
  
  static constraints = 
  {
    login(unique:true)
    password(password:true)
    name()
    house(nullable: true)
    role(inList:["player", "admin"])
  }
  
  String toString()
  {
    name
  }
}