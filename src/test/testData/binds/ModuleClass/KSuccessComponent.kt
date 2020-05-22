import javax.inject.Inject
import dagger.Module
import dagger.Binds
import assets.Car
import assets.CarImpl

@Module
abstract class KModuleClass {

    @Binds
    abstract fun getCar(car: CarImpl) : Car
}