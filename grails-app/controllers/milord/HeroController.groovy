package milord

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

class HeroController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [heroInstanceList: Hero.list(params), heroInstanceTotal: Hero.count()]
    }

    def create() {
        [heroInstance: new Hero(params)]
    }

    def save() {
        def heroInstance = new Hero(params)
        if (!heroInstance.save(flush: true)) {
            render(view: "create", model: [heroInstance: heroInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'hero.label', default: 'Hero'), heroInstance.id])
        redirect(action: "show", id: heroInstance.id)
    }

    def show() {
        def heroInstance = Hero.get(params.id)
        if (!heroInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])
            redirect(action: "list")
            return
        }

        [heroInstance: heroInstance]
    }

    def edit() {
        def heroInstance = Hero.get(params.id)
        if (!heroInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])
            redirect(action: "list")
            return
        }

        [heroInstance: heroInstance]
    }

    def update() {
        def heroInstance = Hero.get(params.id)
        if (!heroInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (heroInstance.version > version) {
                heroInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hero.label', default: 'Hero')] as Object[],
                          "Another user has updated this Hero while you were editing")
                render(view: "edit", model: [heroInstance: heroInstance])
                return
            }
        }

        heroInstance.properties = params

        if (!heroInstance.save(flush: true)) {
            render(view: "edit", model: [heroInstance: heroInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'hero.label', default: 'Hero'), heroInstance.id])
        redirect(action: "show", id: heroInstance.id)
    }

    def delete() {
        def heroInstance = Hero.get(params.id)
        if (!heroInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])
            redirect(action: "list")
            return
        }

        try {
            heroInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
    
    def hire()
    {
    	def heroInstance = Hero.get(params.heroId)
		def user = session.user.refresh()
		
		def house = session.user?.house
    	house?.addToHeroes(heroInstance)
		house.save()
    	// redirect(controller: "house", action: "show", id: house.id)
		render house as JSON		
    }
}
