package milord



import org.junit.*
import grails.test.mixin.*

@TestFor(HeroController)
@Mock(Hero)
class HeroControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/hero/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.heroInstanceList.size() == 0
        assert model.heroInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.heroInstance != null
    }

    void testSave() {
        controller.save()

        assert model.heroInstance != null
        assert view == '/hero/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/hero/show/1'
        assert controller.flash.message != null
        assert Hero.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/hero/list'


        populateValidParams(params)
        def hero = new Hero(params)

        assert hero.save() != null

        params.id = hero.id

        def model = controller.show()

        assert model.heroInstance == hero
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/hero/list'


        populateValidParams(params)
        def hero = new Hero(params)

        assert hero.save() != null

        params.id = hero.id

        def model = controller.edit()

        assert model.heroInstance == hero
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/hero/list'

        response.reset()


        populateValidParams(params)
        def hero = new Hero(params)

        assert hero.save() != null

        // test invalid parameters in update
        params.id = hero.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/hero/edit"
        assert model.heroInstance != null

        hero.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/hero/show/$hero.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        hero.clearErrors()

        populateValidParams(params)
        params.id = hero.id
        params.version = -1
        controller.update()

        assert view == "/hero/edit"
        assert model.heroInstance != null
        assert model.heroInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/hero/list'

        response.reset()

        populateValidParams(params)
        def hero = new Hero(params)

        assert hero.save() != null
        assert Hero.count() == 1

        params.id = hero.id

        controller.delete()

        assert Hero.count() == 0
        assert Hero.get(hero.id) == null
        assert response.redirectedUrl == '/hero/list'
    }
}
