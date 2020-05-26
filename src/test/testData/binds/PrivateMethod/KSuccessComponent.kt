import javax.inject.Inject
import dagger.Module
import dagger.Binds
import assets.Car
import assets.CarImpl

@Module
abstract class KProtectedMethod {

    @Binds
    protected abstract fun getCar(car: CarImpl) : Car
}

@Module
abstract class KInternalMethod {

    @Binds
    internal abstract fun getCar(car: CarImpl) : Car
}