import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;
import java.lang.Exception;

public class ThrowIOException {

    @<error descr="Dagger does not support checked exceptions [IOException] on @Inject constructors">Inject</error>
    ThrowIOException() throws IOException {
        
    }
}