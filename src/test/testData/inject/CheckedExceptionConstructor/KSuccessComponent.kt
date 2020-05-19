import javax.inject.Inject
import assets.Car

import java.io.IOException
import java.lang.RuntimeException

class KThrowError {

    @Throws(Error::class)
    @Inject
    constructor()
}

class KThrowRuntimeException {

    @Throws(RuntimeException::class)
    @Inject
    constructor()
}

class ThrowMultipleUncheckedExceptions {

    @Throws(RuntimeException::class, Error::class)
    @javax.inject.Inject
    constructor()
}