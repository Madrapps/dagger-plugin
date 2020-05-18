import javax.inject.Inject
import javax.inject.Singleton
import assets.PrimaryScope;
import assets.Car

@Singleton
class KSingleScope @Inject constructor() {

    @PrimaryScope
    @Singleton
    @Inject
    lateinit var car: Car

    @PrimaryScope
    @Singleton
    @Inject
    fun doSomething() {

    }
}

class KNoScope @Inject constructor() {

    @PrimaryScope
    @Singleton
    @Inject
    lateinit var car: Car

    @PrimaryScope
    @Singleton
    @Inject
    fun doSomething() {

    }
}