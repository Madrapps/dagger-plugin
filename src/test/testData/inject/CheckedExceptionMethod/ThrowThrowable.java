import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;
import java.lang.Exception;

public class ThrowThrowable {

    @<error descr="Methods with @Inject may not throw checked exceptions [Throwable]. Please wrap your exceptions in a RuntimeException instead.">Inject</error>
    public void doSomething() throws Throwable {
        
    }
}