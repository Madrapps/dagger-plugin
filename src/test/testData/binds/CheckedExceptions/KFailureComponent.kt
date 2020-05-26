import javax.inject.Inject
import dagger.Module
import dagger.Provides
import dagger.Binds
import assets.Car
import assets.CarImpl
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.Error

@Module
abstract class KException {

    @Throws(Exception::class)
    @<error descr="@Binds methods may only throw unchecked exceptions. [Exception] not allowed">Binds</error>
    abstract fun getCar(car: CarImpl) : Car
}

@Module
abstract class KIOException {

    @Throws(IOException::class)
    @<error descr="@Binds methods may only throw unchecked exceptions. [IOException] not allowed">Binds</error>
    abstract fun getCar(car: CarImpl) : Car
}

@Module
abstract class KMultipleCheckedException {

    @Throws(Error::class, Exception::class, IOException::class)
    @<error descr="@Binds methods may only throw unchecked exceptions. [Exception, IOException] not allowed">Binds</error>
    abstract fun getCar(car: CarImpl) : Car
}


