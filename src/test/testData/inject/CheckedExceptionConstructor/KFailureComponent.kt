import javax.inject.Inject
import assets.Car

import java.io.IOException
import java.lang.RuntimeException
import java.lang.Exception
import java.lang.Error

class KThrowException {

    @Throws(Exception::class)
    @<error descr="Dagger does not support checked exceptions [Exception] on @Inject constructors">Inject</error>
    constructor()
}

class KThrowThrowable {

    @Throws(Throwable::class)
    @<error descr="Dagger does not support checked exceptions [Throwable] on @Inject constructors">Inject</error>
    constructor()
}

class KThrowIOException {

    @Throws(IOException::class)
    @<error descr="Dagger does not support checked exceptions [IOException] on @Inject constructors">Inject</error>
    constructor()
}

class ThrowMultipleCheckedExceptions {

    @Throws(IOException::class, Error::class, Exception::class)
    @<error descr="Dagger does not support checked exceptions [IOException, Exception] on @Inject constructors">javax.inject.Inject</error>
    constructor()
}