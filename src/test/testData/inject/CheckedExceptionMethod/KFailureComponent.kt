import javax.inject.Inject
import assets.Car

import java.io.IOException
import java.lang.RuntimeException
import java.lang.Exception
import java.lang.Error

class KThrowException {

    @Throws(Exception::class)
    @<error descr="Methods with @Inject may not throw checked exceptions [Exception]. Please wrap your exceptions in a RuntimeException instead.">Inject</error>
    fun doSomething() {}
}

class KThrowThrowable {

    @Throws(Throwable::class)
    @<error descr="Methods with @Inject may not throw checked exceptions [Throwable]. Please wrap your exceptions in a RuntimeException instead.">Inject</error>
    fun doSomething() {}
}

class KThrowIOException {

    @Throws(IOException::class)
    @<error descr="Methods with @Inject may not throw checked exceptions [IOException]. Please wrap your exceptions in a RuntimeException instead.">Inject</error>
    fun doSomething() {}
}

class ThrowMultipleCheckedExceptions {

    @Throws(IOException::class, Error::class, Exception::class)
    @<error descr="Methods with @Inject may not throw checked exceptions [IOException, Exception]. Please wrap your exceptions in a RuntimeException instead.">javax.inject.Inject</error>
    fun doSomething() {}
}