package milord



import org.junit.*
import grails.test.mixin.*

@TestFor(InnController)
@Mock(Inn)
class InnControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/inn/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.innInstanceList.size() == 0
        assert model.innInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.innInstance != null
    }

    void testSave() {
        controller.save()

        assert model.innInstance != null
        assert view == '/inn/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/inn/show/1'
        assert controller.flash.message != null
        assert Inn.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/inn/list'


        populateValidParams(params)
        def inn = new Inn(params)

        assert inn.save() != null

        params.id = inn.id

        def model = controller.show()

        assert model.innInstance == inn
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/inn/list'


        populateValidParams(params)
        def inn = new Inn(params)

        assert inn.save() != null

        params.id = inn.id

        def model = controller.edit()

        assert model.innInstance == inn
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/inn/list'

        response.reset()


        populateValidParams(params)
        def inn = new Inn(params)

        assert inn.save() != null

        // test invalid parameters in update
        params.id = inn.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/inn/edit"
        assert model.innInstance != null

        inn.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/inn/show/$inn.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        inn.clearErrors()

        populateValidParams(params)
        params.id = inn.id
        params.version = -1
        controller.update()

        assert view == "/inn/edit"
        assert model.innInstance != null
        assert model.innInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/inn/list'

        response.reset()

        populateValidParams(params)
        def inn = new Inn(params)

        assert inn.save() != null
        assert Inn.count() == 1

        params.id = inn.id

        controller.delete()

        assert Inn.count() == 0
        assert Inn.get(inn.id) == null
        assert response.redirectedUrl == '/inn/list'
    }
}
