import javax.inject.Inject
import dagger.Module
import dagger.Provides
import dagger.Binds
import assets.Car
import assets.CarImpl
import java.lang.RuntimeException
import java.lang.Error

@Module
abstract class KRuntimeException {

    @Throws(RuntimeException::class)
    @Binds
    abstract fun getCar(car: CarImpl) : Car
}

@Module
abstract class KError {

    @Throws(Error::class)
    @Binds
    abstract fun getCar(car: CarImpl) : Car
}

@Module
abstract class KMultipleUncheckedException {

    @Throws(Error::class, RuntimeException::class)
    @Binds
    abstract fun getCar(car: CarImpl) : Car
}