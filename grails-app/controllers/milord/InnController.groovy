package milord

import org.springframework.dao.DataIntegrityViolationException

class InnController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [innInstanceList: Inn.list(params), innInstanceTotal: Inn.count()]
    }

    def create() {
        [innInstance: new Inn(params)]
    }

    def save() {
        def innInstance = new Inn(params)
        if (!innInstance.save(flush: true)) {
            render(view: "create", model: [innInstance: innInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'inn.label', default: 'Inn'), innInstance.id])
        redirect(action: "show", id: innInstance.id)
    }

    def show() {
        def innInstance = Inn.get(params.id)
        if (!innInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'inn.label', default: 'Inn'), params.id])
            redirect(action: "list")
            return
        }

        [innInstance: innInstance]
    }

    def edit() {
        def innInstance = Inn.get(params.id)
        if (!innInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inn.label', default: 'Inn'), params.id])
            redirect(action: "list")
            return
        }

        [innInstance: innInstance]
    }

    def update() {
        def innInstance = Inn.get(params.id)
        if (!innInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inn.label', default: 'Inn'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (innInstance.version > version) {
                innInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'inn.label', default: 'Inn')] as Object[],
                          "Another user has updated this Inn while you were editing")
                render(view: "edit", model: [innInstance: innInstance])
                return
            }
        }

        innInstance.properties = params

        if (!innInstance.save(flush: true)) {
            render(view: "edit", model: [innInstance: innInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'inn.label', default: 'Inn'), innInstance.id])
        redirect(action: "show", id: innInstance.id)
    }

    def delete() {
        def innInstance = Inn.get(params.id)
        if (!innInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'inn.label', default: 'Inn'), params.id])
            redirect(action: "list")
            return
        }

        try {
            innInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'inn.label', default: 'Inn'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'inn.label', default: 'Inn'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def startPlay() {
        def innInstance = Inn.get(params.id)
        
        innInstance.dailyGrind()
    }
}
