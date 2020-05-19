import javax.inject.Inject
import assets.Car;

private class KTopLevelPrivateClass {

    @Inject
    constructor() {}

    @Inject
    lateinit var car: Car

    @Inject
    fun doSomething() {

    }
}