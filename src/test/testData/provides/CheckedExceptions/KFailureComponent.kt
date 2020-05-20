import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.Error

@Module
class KException {

    @Throws(Exception::class)
    @<error descr="@provides methods may only throw unchecked exceptions. [Exception] not allowed">Provides</error>
    fun getCar() : Car {
        return Car()
    }
}

@Module
class KIOException {

    @Throws(IOException::class)
    @<error descr="@provides methods may only throw unchecked exceptions. [IOException] not allowed">Provides</error>
    fun getCar() : Car {
        return Car()
    }
}

@Module
class KMultipleCheckedException {

    @Throws(Error::class, Exception::class, IOException::class)
    @<error descr="@provides methods may only throw unchecked exceptions. [Exception, IOException] not allowed">Provides</error>
    fun getCar() : Car {
        return Car()
    }
}


