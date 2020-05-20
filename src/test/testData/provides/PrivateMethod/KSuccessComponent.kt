import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
class KProtectedMethod {

    @Provides
    protected fun getProtectedCar() : Car {
        return Car()
    }
}

@Module
class KInternalMethod {

    @Provides
    internal fun getInternalCar() : Car {
        return Car()
    }
}