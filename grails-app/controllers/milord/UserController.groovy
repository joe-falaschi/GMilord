package milord

import org.springframework.dao.DataIntegrityViolationException

class UserController 
{
	def scaffold = User
  
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
        [userInstance: new User(params)]
    }

    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def update() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
    
  def login = {}
  
  def authenticate = {
    def user = User.findByLoginAndPassword(params.login, params.password)
    
    if(user){
      session.user = user
      flash.message = "Hello ${user.name}!"
	  redirect(action: "show", id: user.id)
    }
    else
    {
      flash.message = "Sorry, ${params.login}. Please try again."
      redirect(action:"login")
    }
  }
  
  def logout = {
    flash.message = "Goodbye ${session.user.name}"
    session.user = null
      redirect(action:"login")
  }  
  
  def gamePlayService
  
  def startGame = 
  {
  	flash.message = "Game Started, ${session.user}"
  	session.game = gamePlayService.startGame()
	  redirect(action: "show", id: session.user.id)
  }
  
  def pauseGame = 
  {
  	flash.message = "Game Paused, ${session.user}"
  	gamePlayService.pauseGame()
  	session.game = null
	  redirect(action: "show", id: session.user.id)
  }
  
  def endGame =
  {
  	flash.message = "Game Over"
  	redirect(action: "show", id: session.user.id)
  }
  
  def beforeInterceptor = [action:this.&auth, 
                           except:["login", "authenticate", "show", "list", "logout"]]

  def auth() {
    if( !(session?.user?.role == "admin") ){
      flash.message = "You must be an administrator to perform that task."
      redirect(action:"login")
      return false
    }
  }
}
