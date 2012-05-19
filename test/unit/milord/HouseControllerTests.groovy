package milord



import org.junit.*
import grails.test.mixin.*

@TestFor(HouseController)
@Mock(House)
class HouseControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/house/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.houseInstanceList.size() == 0
        assert model.houseInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.houseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.houseInstance != null
        assert view == '/house/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/house/show/1'
        assert controller.flash.message != null
        assert House.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/house/list'


        populateValidParams(params)
        def house = new House(params)

        assert house.save() != null

        params.id = house.id

        def model = controller.show()

        assert model.houseInstance == house
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/house/list'


        populateValidParams(params)
        def house = new House(params)

        assert house.save() != null

        params.id = house.id

        def model = controller.edit()

        assert model.houseInstance == house
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/house/list'

        response.reset()


        populateValidParams(params)
        def house = new House(params)

        assert house.save() != null

        // test invalid parameters in update
        params.id = house.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/house/edit"
        assert model.houseInstance != null

        house.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/house/show/$house.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        house.clearErrors()

        populateValidParams(params)
        params.id = house.id
        params.version = -1
        controller.update()

        assert view == "/house/edit"
        assert model.houseInstance != null
        assert model.houseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/house/list'

        response.reset()

        populateValidParams(params)
        def house = new House(params)

        assert house.save() != null
        assert House.count() == 1

        params.id = house.id

        controller.delete()

        assert House.count() == 0
        assert House.get(house.id) == null
        assert response.redirectedUrl == '/house/list'
    }
}
