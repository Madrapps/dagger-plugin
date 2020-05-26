import javax.inject.Inject
import dagger.Module
import dagger.Binds
import assets.Car
import assets.CarImpl

abstract class KNonModuleClass {

    @<error descr="@Binds methods can only be present within a @Module or @ProducerModule">Binds</error>
    abstract fun getCar(car: CarImpl) : Car
}