import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car
import java.lang.RuntimeException
import java.lang.Error

@Module
class KRuntimeException {

    @Throws(RuntimeException::class)
    @Provides
    fun getCar() : Car {
        return Car()
    }
}

@Module
class KError {

    @Throws(Error::class)
    @Provides
    fun getCar() : Car {
        return Car()
    }
}

@Module
class KMultipleUncheckedException {

    @Throws(Error::class, RuntimeException::class)
    @Provides
    fun getCar() : Car {
        return Car()
    }
}