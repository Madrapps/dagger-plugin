import javax.inject.Inject
import assets.Car

import java.io.IOException
import java.lang.RuntimeException

class KThrowError {

    @Throws(Error::class)
    @Inject
    fun doSomething() {}
}

class KThrowRuntimeException {

    @Throws(RuntimeException::class)
    @Inject
    fun doSomething() {}
}

class ThrowMultipleUncheckedExceptions {

    @Throws(RuntimeException::class, Error::class)
    @javax.inject.Inject
    fun doSomething() {}
}