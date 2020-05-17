import javax.inject.Inject
import assets.Car

class KClassWithNonFinalField {

    @Inject
    lateinit var car: Car
}