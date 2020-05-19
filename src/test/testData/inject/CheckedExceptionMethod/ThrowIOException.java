import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;
import java.lang.Exception;

public class ThrowIOException {

    @<error descr="Methods with @Inject may not throw checked exceptions [IOException]. Please wrap your exceptions in a RuntimeException instead.">Inject</error>
    public void doSomething() throws IOException {
        
    }
}