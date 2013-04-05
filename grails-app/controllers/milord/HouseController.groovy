package milord

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

class HouseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [houseInstanceList: House.list(params), houseInstanceTotal: House.count()]
    }

    def create() {
        [houseInstance: new House(params)]
    }

    def save() {
        def houseInstance = new House(params)
        if (!houseInstance.save(flush: true)) {
            render(view: "create", model: [houseInstance: houseInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'house.label', default: 'House'), houseInstance.id])
        redirect(action: "show", id: houseInstance.id)
    }

    def show() {
        def houseInstance = House.get(params.id)
        if (!houseInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'house.label', default: 'House'), params.id])
            redirect(action: "list")
            return
        }
		
		println houseInstance as JSON

        [houseInstance: houseInstance, innInstanceList: Inn.list(params), innInstanceTotal: Inn.count()]
    }

    def edit() {
        def houseInstance = House.get(params.id)
        
        if(!hasPermissions())
        {
        	flash.message = "Sorry, only the house owner can edit."
        	redirect(action: "list")
        }
        else
        {
	        if (!houseInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'house.label', default: 'House'), params.id])
	            redirect(action: "list")
	            return
	        }
	
	        [houseInstance: houseInstance]
	    }
    }

    def update() {
        if(!hasPermissions())
        {
        	flash.message = "Sorry, only the house owner can edit."
        	redirect(action: "list")
        }
        else
        {
	        def houseInstance = House.get(params.id)
	        if (!houseInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'house.label', default: 'House'), params.id])
	            redirect(action: "list")
	            return
	      	}

	        if (params.version) {
	            def version = params.version.toLong()
	            if (houseInstance.version > version) {
	                houseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
	                          [message(code: 'house.label', default: 'House')] as Object[],
	                          "Another user has updated this House while you were editing")
	                render(view: "edit", model: [houseInstance: houseInstance])
	                return
	            }
	        }
	
	        houseInstance.properties = params
	
	        if (!houseInstance.save(flush: true)) {
	            render(view: "edit", model: [houseInstance: houseInstance])
	            return
	        }
	
			flash.message = message(code: 'default.updated.message', args: [message(code: 'house.label', default: 'House'), houseInstance.id])
	        redirect(action: "show", id: houseInstance.id)
	    }
    }

    def delete() {
    	if(!hasPermissions())
        {
        	flash.message = "Sorry, only the house owner can edit."
        	redirect(action: "list")
        }
        else
        {
	        def houseInstance = House.get(params.id)
	        if (!houseInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'house.label', default: 'House'), params.id])
	            redirect(action: "list")
	            return
	        }
	
	        try {
	            houseInstance.delete(flush: true)
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'house.label', default: 'House'), params.id])
	            redirect(action: "list")
	        }
	        catch (DataIntegrityViolationException e) {
				flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'house.label', default: 'House'), params.id])
	            redirect(action: "show", id: params.id)
	        }
	    }
    }
    
    private hasPermissions()
    {
        boolean perm
        
		// Limit editing to house owner
        User user = session.user
        
        if(user != null)
        	if(user.login == houseInstance.user.login)
        		perm = true;    
    }
}
