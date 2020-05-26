import javax.inject.Inject
import dagger.Module
import dagger.Binds
import assets.Car
import assets.CarImpl

@Module
abstract class KAbstractMethod {

    @Binds
    abstract fun getCar(car: CarImpl) : Car
}