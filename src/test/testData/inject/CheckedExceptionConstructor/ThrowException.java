import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;
import java.lang.Exception;

public class ThrowException {

    @<error descr="Dagger does not support checked exceptions [Exception] on @Inject constructors">Inject</error>
    ThrowException() throws Exception {
        
    }
}