import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

class KNonModuleClass {

    @<error descr="@Provides methods can only be present within a @Module or @ProducerModule">Provides</error>
    fun getCar() : Car {
        return Car()
    }
}